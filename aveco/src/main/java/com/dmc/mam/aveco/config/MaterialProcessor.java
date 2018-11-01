package com.dmc.mam.aveco.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.dmc.mam.aveco.model.HistoryManager;
import com.dmc.mam.aveco.model.Material;
import com.dmc.mam.aveco.repository.CatigoriesJpaRepository;
import com.dmc.mam.aveco.repository.MaterialJpaRepository;
import com.dmc.mam.aveco.service.MetadataConvertor;

public class MaterialProcessor implements ItemProcessor<Material, Material> {

	@Autowired
	private MaterialJpaRepository mjpa;
	@Autowired
	private CatigoriesJpaRepository cjpa;
	
	@Override
	@Transactional
	public Material process(Material item) throws Exception {
		String id = item.getId();
		Material history = null;
		
		item.setIdec(
				newIdec(item.getIdec()
						,item.getRecId()
						)
				);
		
		if (mjpa.existsById(id))
			history = mjpa.findById(id).orElse(null);
		
		List<HistoryManager> ref = metaDataToHistoryManager(history);
		if(ref != null)
			item.setHistory(ref);
		
		String fullNameCat1 = shortNameCatigoryToFull(item.getCat1());
		String fullNameCat2 = shortNameCatigoryToFull(item.getCat2());
		
		item.setCat1(fullNameCat1 == null ? item.getCat1() : fullNameCat1 );
		item.setCat2(fullNameCat2 == null ? item.getCat2() : fullNameCat2);                                          
		return item;
	}
	
	private String shortNameCatigoryToFull(String shortName) {
		return cjpa.getFullNameByShortName(shortName);
	}
	
//	private String shortNameCatigoryToFull(String parentShortName,String shortName) {
//		return cjpa.getFullNameByShortName(parentShortName,shortName);
//	}
	
	private String newIdec(String idec,String recId) {
		if (idec == "" || idec == null) {
			idec = (recId.contains("_") ? recId.split("_")[0] : recId);
		}
			return idec;
	}

	private List<HistoryManager> metaDataToHistoryManager(Material history){
		if (history != null && history.getMetadata() != null) {
			HistoryManager hm = new HistoryManager();
			hm.setMetadataHistory(
					history.getMetadata()
					.stream().map(new MetadataConvertor())
					.collect(Collectors.toList())
					);
			history.addHistory(hm);
			return history.geHistory();
		}
		return null;
	}
}
