package com.dmc.mam.aveco.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.dmc.mam.aveco.model.DelayedFile;

@Service
public class ScanFolder {
	
	@Resource(name = "myQueue")
	private BlockingQueue<DelayedFile> myQueue;
	private final static Path DIRECTORY = Paths.get("//192.168.105.50//aveco//avecoDBXML");
	
	@EventListener(ApplicationReadyEvent.class)
	public void scan() {
		File[] files = DIRECTORY.toFile().listFiles(
				(file,name) -> name.endsWith("xml")
				);
		
		for(File push : files)
			publishToQueue(push);
	}
	
	public void publishToQueue(File fileLocation) {
		DelayedFile file = createDelayedFile();
		file.setFileLocation(fileLocation);
		if (myQueue.contains(file))
			myQueue.remove(file);
		myQueue.offer(file);
	}
	
	@Lookup
	protected DelayedFile createDelayedFile() {return null;}; 
}
