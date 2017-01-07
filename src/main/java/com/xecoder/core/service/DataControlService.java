package com.xecoder.core.service;

import com.xecoder.core.entity.DataControl;

import java.util.List;


public interface DataControlService {

	DataControl get(Long id);
	
	DataControl getByName(String name);

	void saveOrUpdate(DataControl dataControl);

	void delete(Long id);
	
	List<DataControl> findByExample(DataControl dataControl);
}
