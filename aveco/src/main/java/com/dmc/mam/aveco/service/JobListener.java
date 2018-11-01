package com.dmc.mam.aveco.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener{

	public static final String SUBFOLDERFORERROR = "inComplete";
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String fileLocation = jobExecution.getJobParameters().getString("fileLocation");
		File file = new File(fileLocation);
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			if(file.exists())
				file.delete();
		}else {
			String str = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);
			checkForSubFolderORCreate(str);
			moveToSub(str,file);
		}
	}
	
	public static void checkForSubFolderORCreate(String location) {
		String newSub = location+File.separator+SUBFOLDERFORERROR;
		Path newSubAsPath = Paths.get(newSub);
		if(!Files.exists(newSubAsPath)){
			try {
				Files.createDirectories(newSubAsPath);
			} catch (IOException e) {
			}
		}
	}
	
	public static void moveToSub(String location,File old) {
		String newFile = location+File.separator+SUBFOLDERFORERROR+File.separator+old.getName();
		if(Files.exists(Paths.get(newFile))) {
			String fullNewPath = newFile.substring(0,newFile.lastIndexOf(File.separator)+1);
			newFile = fullNewPath + UUID.randomUUID() +" "+old.getName();
		}
		try {
			Files.move(old.toPath(), Paths.get(newFile),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
		}
		
	}
}
