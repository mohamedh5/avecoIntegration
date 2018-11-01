package com.dmc.mam.aveco;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dmc.mam.aveco.model.DelayedFile;

//@ContextConfiguration(classes = AvecoApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class DelayedFileTest {

	@Resource(name = "myQueue")
	public BlockingQueue<DelayedFile> queue;
	String root = "D:\\javaProject MAM\\MAM\\aveco xml\\";
	@Autowired
	ApplicationContext context;
	
	//@Test
	public void PushToQueue() {
		
		publishToQueue(new File(root + "180909_070715600_352091.xml"));
		publishToQueue(new File(root + "180909_070949280_352092.xml"));
		publishToQueue(new File(root + "180909_070715600_352091.xml"));
		assertEquals("didn't removed", 2, queue.size());
	}

	public void publishToQueue(File fileLocation) {
		DelayedFile Testfile = context.getBean(DelayedFile.class);
		Testfile.setFileLocation(fileLocation);
		if (queue.contains(Testfile))
			queue.remove(Testfile);
		queue.offer(Testfile);
	}
	
}
