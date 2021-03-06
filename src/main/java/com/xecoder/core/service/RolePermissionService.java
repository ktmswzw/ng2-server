package com.xecoder.core.service;

import com.xecoder.core.entity.RolePermission;

import java.util.List;


/** 
 * @description: 角色授权操作关联
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午02:12:59
 */
public interface RolePermissionService {

	void save(RolePermission rolePermission);
	
	RolePermission get(Long id);

	void update(RolePermission rolePermission);
	
	void delete(Long id);

    void delete(RolePermission rolePermission);

	List<RolePermission> find(RolePermission permission);

	void save(Iterable<RolePermission> entities);
	
	void delete(Iterable<RolePermission> entities);
	
}
