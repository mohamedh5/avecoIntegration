
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.dmc.mam.model.DelayedFile;

@Service
public class Watcher {

	@Autowired
	private BlockingQueue<DelayedFile> queue;
	private WatchService fileWatcher;
	private WatchKey key;

	private final static Path directory = Paths.get("D:\\javaProject MAM\\MAM\\aveco xml");

	public Watcher() throws IOException {
		super();
		this.fileWatcher = FileSystems.getDefault().newWatchService();
		register();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		System.out.println("************* UP *************");
		while (true) {

			try {
				key = this.fileWatcher.take();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				Path fileName = (Path) event.context();
				System.out.println(fileName.toString() + " caught **************");
				if (!fileName.toString().endsWith("xml"))
					continue;
				File fullPath = directory.resolve(fileName).toFile();
				publishToQueue(fullPath);
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}

	public void publishToQueue(File fileLocation) {
		DelayedFile file = new DelayedFile(fileLocation);
		if (queue.contains(file))
			queue.remove(file);
		queue.offer(file);
		System.out.println(file.getFileLocation() + " pushed **************");
	}

	public void register() throws IOException {
		key = directory.register(fileWatcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY);
	}

}
