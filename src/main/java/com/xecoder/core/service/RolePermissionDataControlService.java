package com.xecoder.core.service;

import com.xecoder.core.entity.RolePermissionDataControl;

import java.util.List;


/** 
 * @description: 
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午02:14:56
 */
public interface RolePermissionDataControlService {

	RolePermissionDataControl get(Long id);

	void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl);

	void delete(Long id);

	List<RolePermissionDataControl> findByExample(RolePermissionDataControl rolePermissionDataControl);
	
	void save(Iterable<RolePermissionDataControl> entities);
	
	void delete(Iterable<RolePermissionDataControl> entities);

	List<RolePermissionDataControl> findByRolePermissionId(Long rolePermissionId);
	
	List<RolePermissionDataControl> findByRoleId(Long roleId);
}
