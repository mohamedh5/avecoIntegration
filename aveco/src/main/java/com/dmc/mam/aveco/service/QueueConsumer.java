package com.dmc.mam.aveco.service;



import java.io.File;
import java.util.concurrent.BlockingQueue;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.dmc.mam.aveco.model.DelayedFile;


@Service
public class QueueConsumer {

	@Resource(name = "myQueue")
	private BlockingQueue<DelayedFile> myQueue;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	private final  static Logger LOG = LoggerFactory.getLogger(QueueConsumer.class);

	

	
	@Scheduled(initialDelay = 60000, fixedDelay = 120000)
	public void consume() {
		while (myQueue.peek() != null && !myQueue.isEmpty()) {
			DelayedFile file = myQueue.poll();
			if (file == null)
				continue;
			File asFile = file.getFileLocation();
			String fileLocation = asFile.getAbsolutePath();
			try {
				jobRunner(fileLocation);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException e) {
				LOG.error("Exception happend by jobRunner",e);
				String str = fileLocation.substring(0, asFile.getAbsolutePath().lastIndexOf(File.separator));
				JobListener.checkForSubFolderORCreate(str);
				JobListener.moveToSub(str,asFile);
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
