package com.xecoder.core.service;

import com.xecoder.core.entity.Role;

import java.util.List;

/**
 * @description: 角色管理
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午02:11:00
 */
public interface RoleService {


	List<Role> find(Role role);
	
	List<Role> findAll();
	
	void save(Role role);

	Role get(Long id);

	void update(Role role);

	void delete(Long id);
}
