package com.xecoder.core.service;

import com.xecoder.core.entity.Organization;

import java.util.List;

/** 
 * @description: 组织
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午02:15:24
 */
public interface OrganizationService {

	List<Organization> find(Organization organization);

	Organization getTree();

	void save(Organization organization);

	Organization get(Long id);

	void update(Organization organization);

	void delete(Long id) ;
	
	Organization getByName(String name);
}
