package com.xecoder.core.service;

import com.xecoder.core.entity.LogEntity;

import java.util.List;


/** 
 * @description: 登录日志
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午02:16:30
 */
public interface LogEntityService {

void save(LogEntity logEntity);
	
	LogEntity get(Long id);
	
	void update(LogEntity logEntity);
	
	void delete(Long id);

	List<LogEntity> findAll();
	
	List<LogEntity> findByExample(LogEntity logEntity);
	
}
