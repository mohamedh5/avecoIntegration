package com.dmc.mam.aveco.service;

import java.util.concurrent.BlockingQueue;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dmc.mam.model.DelayedFile;

@Service
public class QueueConsumer {

	@Autowired
	private BlockingQueue<DelayedFile> queue;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@Scheduled(initialDelay = 5000, fixedDelay = 120000)
	public void consume() {
		while (queue.peek() != null && !queue.isEmpty()) {
			DelayedFile file = queue.poll();
			if (file == null)
				continue;
			String fileLocation = file.getFileLocation().getAbsolutePath();
			try {
				jobRunner(fileLocation);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void jobRunner(String fileLocation) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("fileLocation", fileLocation);
		jobLauncher.run(job, jobParametersBuilder.toJobParameters());
	}
}
