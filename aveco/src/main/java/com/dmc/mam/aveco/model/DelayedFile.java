package com.dmc.mam.aveco.model;

import java.io.File;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DelayedFile implements Delayed {

	private final long time = System.currentTimeMillis() + 50000;
	private File fileLocation;
	
	public long getTime() {
		return time;
	}
	
	public File getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(File fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public int compareTo(Delayed obj) {
		return (int) (this.time - ((DelayedFile) obj).getTime());
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long difference = time - System.currentTimeMillis();
		return unit.convert(difference, TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DelayedFile) {
			String objFileLocation = ((DelayedFile) obj).getFileLocation().getAbsolutePath();
			return this.fileLocation.getAbsolutePath().equals(objFileLocation);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + this.fileLocation.hashCode();
		return hash;
	}
}
