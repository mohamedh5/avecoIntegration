package com.dmc.mam.aveco;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dmc.mam.aveco.config.ApplicationConfig;
import com.dmc.mam.model.DelayedFile;

@ContextConfiguration(classes = ApplicationConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DelayedFileTest {

	@Autowired
	public BlockingQueue<DelayedFile> queue;
	String root = "D:\\javaProject MAM\\MAM\\aveco xml\\";
	@Test
	public void PushToQueue() {
		
		publishToQueue(new File(root + "180909_070715600_352091.xml"));
		publishToQueue(new File(root + "180909_070949280_352092.xml"));
		publishToQueue(new File(root + "180909_070715600_352091.xml"));
		assertEquals("didn't removed", 2, queue.size());
	}

	public void publishToQueue(File fileLocation) {
		DelayedFile file = new DelayedFile(fileLocation);
		if (queue.contains(file))
			queue.remove(file);
		queue.offer(file);
	}
	
	@Test
	public void PollFromQueue() throws InterruptedException {
		DelayedFile file = queue.take();
		assertEquals("Not the same File" ,new File(root + "180909_070949280_352092.xml"), file.getFileLocation());
		file = queue.take();
		assertEquals("Not the same File" ,new File(root + "180909_070715600_352091.xml"), file.getFileLocation());
	}
}
