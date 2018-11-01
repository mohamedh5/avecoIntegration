package com.dmc.mam.aveco.service;

import java.util.function.Function;

import com.dmc.mam.aveco.model.MetaData;
import com.dmc.mam.aveco.model.MetadataHistory;

public final class MetadataConvertor implements Function<MetaData, MetadataHistory>{

	@Override
	public MetadataHistory apply(MetaData t) {
		MetadataHistory mh = new MetadataHistory();
		mh.setId(t.getId());
		mh.setKey(t.getKey());
		mh.setValue(t.getValue());
		return mh;
	}

	
}
