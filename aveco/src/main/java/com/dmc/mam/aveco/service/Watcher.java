
package com.dmc.mam.aveco.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.dmc.mam.aveco.model.DelayedFile;

@Service
public class Watcher {     
	@Resource(name = "myQueue")
	private BlockingQueue<DelayedFile> myQueue;
	private WatchService fileWatcher;
	private WatchKey key;
	private final static Path DIRECTORY = Paths.get("//192.168.105.50//aveco//avecoDBXML");

	public Watcher() throws IOException {
		super();
		this.fileWatcher = FileSystems.getDefault().newWatchService();
		register();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		while (true) {

			try {
				key = this.fileWatcher.take();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				Path fileName = (Path) event.context();
				if (!fileName.toString().endsWith("xml"))
					continue;
				File fullPath = DIRECTORY.resolve(fileName).toFile();
				publishToQueue(fullPath);
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}

	public void publishToQueue(File fileLocation) {
		DelayedFile file = createDelayedFile();
		file.setFileLocation(fileLocation);
		if (myQueue.contains(file))
			myQueue.remove(file);
		myQueue.offer(file);
	}

	public void register() throws IOException {
		key = DIRECTORY.register(fileWatcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY);
	}

	@Lookup
	protected DelayedFile createDelayedFile() {return null;}; 
}
