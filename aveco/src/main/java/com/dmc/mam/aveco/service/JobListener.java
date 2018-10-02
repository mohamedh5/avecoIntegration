package com.dmc.mam.aveco.service;

import java.io.File;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			String fileLocation = jobExecution.getJobParameters().getString("fileLocation");
			File file = new File(fileLocation);
			if(file.exists())
				file.delete();
		}
	}
}
