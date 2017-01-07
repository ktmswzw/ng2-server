package com.xecoder.core.impl;


import com.xecoder.core.entity.Permission;
import com.xecoder.core.entity.PermissionCriteria;
import com.xecoder.core.mapper.PermissionMapper;
import com.xecoder.core.service.ModuleService;
import com.xecoder.core.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper mapper;

    @Autowired
    private ModuleService moduleService;

	@Override
	public void delete(Long id) {
		mapper.deleteByPrimaryKey(id);
	}

    @Override
	public Permission get(Long id) {
		Permission p = mapper.selectByPrimaryKey(id);
		if(p != null && p.getModuleId() != null){
			p.setModule(moduleService.get(p.getModuleId()));
		}
		return p;
	}

    @Override
    public List<Permission> get(Permission permission) {
        PermissionCriteria criteria = new PermissionCriteria();
        PermissionCriteria.Criteria cri = criteria.createCriteria();

        if(StringUtils.isNotEmpty(permission.getShortName())){
            cri.andShortNameEqualTo(permission.getShortName());
        }
        if(permission.getModuleId()!=null){
            cri.andModuleIdEqualTo(permission.getModuleId());
        }
        List<Permission> list= mapper.selectByExample(criteria);
        return list;
    }

    @Override
    public List<Permission> getAll() {
        PermissionCriteria criteria = new PermissionCriteria();
        List<Permission> list= mapper.selectByExample(criteria);
        List<Permission> resultList = new ArrayList<>();
        for(Permission permission:list)
        {
            if(permission != null && permission.getModuleId() != null){
                permission.setModule(moduleService.get(permission.getModuleId()));
            }
            resultList.add(permission);
        }
        return resultList;
    }

    @Override
	public void save(Permission permission) {
		mapper.insertSelective(permission);
	}

	@Override
	public void update(Permission permission) {
		mapper.updateByPrimaryKeySelective(permission);
	}

}
