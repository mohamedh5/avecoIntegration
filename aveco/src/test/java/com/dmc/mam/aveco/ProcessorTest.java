package com.dmc.mam.aveco;

import static org.junit.Assert.assertEquals;

import com.dmc.mam.aveco.config.MaterialProcessor;
import com.dmc.mam.aveco.model.Material;

public class ProcessorTest {

	//@Test
	public void test() throws Exception {
		MaterialProcessor mat = new MaterialProcessor();
		Material testItem = new Material();
		testItem.setRecId("MAT000000001_01");
		
		Material result = mat.process(testItem);
		assertEquals("Not ass expected", "MAT000000001", result.getIdec());
	}

}
