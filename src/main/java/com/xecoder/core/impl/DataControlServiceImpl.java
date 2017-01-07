package com.xecoder.core.impl;

import com.xecoder.core.entity.DataControl;
import com.xecoder.core.entity.DataControlCriteria;
import com.xecoder.core.mapper.DataControlMapper;
import com.xecoder.core.service.DataControlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("dataControlService")
@Transactional
@SuppressWarnings("unchecked")
public class DataControlServiceImpl implements DataControlService {

	@Autowired
	private DataControlMapper dataControlMapper;

	@Override
	public void delete(Long id) {
		dataControlMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<DataControl> findByExample(DataControl dataControl) {
		DataControlCriteria criteria = new DataControlCriteria();
		DataControlCriteria.Criteria cri = criteria.createCriteria();
		
		if(dataControl != null){
			if(StringUtils.isNotBlank(dataControl.getControl())){
				cri.andControlEqualTo(dataControl.getControl());
			}
			
			if(StringUtils.isNotBlank(dataControl.getName())){
				cri.andNameEqualTo(dataControl.getName());
			}
		}

		return dataControlMapper.selectByExample(criteria);
	}

	@Override
	public DataControl get(Long id) {
		return dataControlMapper.selectByPrimaryKey(id);
	}

	@Override
	public DataControl getByName(String name) {
		DataControlCriteria criteria = new DataControlCriteria();
		DataControlCriteria.Criteria cri = criteria.createCriteria();
		if(StringUtils.isNotBlank(name)){
			cri.andNameEqualTo(name);
		}
		
		List<DataControl> list = dataControlMapper.selectByExample(criteria);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveOrUpdate(DataControl dataControl) {
		if(dataControl != null){
			if(dataControl.getId() != null){
				dataControlMapper.updateByPrimaryKeySelective(dataControl);
			}else{
				dataControlMapper.insertSelective(dataControl);
			}
		}
		
	}

}
