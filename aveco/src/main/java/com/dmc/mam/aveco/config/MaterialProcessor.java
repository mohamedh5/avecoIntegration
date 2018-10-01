package com.dmc.mam.aveco.config;

import org.springframework.batch.item.ItemProcessor;

import com.dmc.mam.model.Material;

public class MaterialProcessor implements ItemProcessor<Material,Material> {

	@Override
	public Material process(Material item) throws Exception {
		String idec = item.getIdec();
		
		if(idec == "" || idec == null) {
			String recId = item.getRecId();
			idec = (recId.contains("_") ? recId.split("_")[0] : recId);
			item.setIdec(idec);
		}
			
		return item;
	}


}
