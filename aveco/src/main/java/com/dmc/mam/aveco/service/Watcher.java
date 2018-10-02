package com.dmc.mam.aveco.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Watcher {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
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

		while (true) {

			try {
				key = this.fileWatcher.take();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			for (WatchEvent event : key.pollEvents()) {
				Path fileName = (Path) event.context();
				String fullPath = directory.resolve(fileName).toString();
				try {
					jobRunner(fullPath);
				} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
						| JobParametersInvalidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}
	public void jobRunner(String fileLocation) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
	    jobParametersBuilder.addString("fileLocation", fileLocation);
		jobLauncher.run(job, jobParametersBuilder.toJobParameters());
	}
	
	public void register() throws IOException {
		key = directory.register(fileWatcher, StandardWatchEventKinds.ENTRY_CREATE);
	}

}
