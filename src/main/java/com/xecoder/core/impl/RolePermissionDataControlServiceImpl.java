package com.xecoder.core.impl;


import com.xecoder.core.entity.RolePermissionDataControl;
import com.xecoder.core.entity.RolePermissionDataControlCriteria;
import com.xecoder.core.mapper.RolePermissionDataControlMapper;
import com.xecoder.core.service.DataControlService;
import com.xecoder.core.service.RolePermissionDataControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SuppressWarnings("unchecked")
@Service
public class RolePermissionDataControlServiceImpl implements
        RolePermissionDataControlService {

    @Autowired
    private RolePermissionDataControlMapper mapper;

    @Autowired
    private DataControlService dataControlService;

    @Override
    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Iterable<RolePermissionDataControl> entities) {
        for(RolePermissionDataControl rolePermissionDataControl:entities){
            delete(rolePermissionDataControl.getId());
        }
    }

    @Override
    public List<RolePermissionDataControl> findByExample(
            RolePermissionDataControl rolePermissionDataControl) {
        RolePermissionDataControlCriteria criteria = new RolePermissionDataControlCriteria();
        RolePermissionDataControlCriteria.Criteria cri = criteria.createCriteria();
        if (rolePermissionDataControl != null) {
            if (rolePermissionDataControl.getDataControlId() != null) {
                cri.andDataControlIdEqualTo(rolePermissionDataControl.getDataControlId());
            }
            if (rolePermissionDataControl.getRolePermissionId() != null) {
                cri.andRolePermissionIdEqualTo(rolePermissionDataControl.getRolePermissionId());
            }
        }
        return mapper.selectByExample(criteria);
    }

    @Override
    public List<RolePermissionDataControl> findByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<RolePermissionDataControl> findByRolePermissionId(
            Long rolePermissionId) {
        RolePermissionDataControlCriteria criteria = new RolePermissionDataControlCriteria();
        RolePermissionDataControlCriteria.Criteria cri = criteria.createCriteria();
        if (rolePermissionId != null) {
            cri.andRolePermissionIdEqualTo(rolePermissionId);
        }

        List<RolePermissionDataControl> list = mapper.selectByExample(criteria);
        for (RolePermissionDataControl rpdc : list) {
            if (rpdc.getDataControlId() != null) {
                rpdc.setDataControl(dataControlService.get(rpdc.getDataControlId()));
            }
        }
        return list;
    }

    @Override
    public RolePermissionDataControl get(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Iterable<RolePermissionDataControl> entities) {
        for(RolePermissionDataControl role:entities){
            mapper.insert(role);
        }
    }

    @Override
    public void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl) {
        if (rolePermissionDataControl.getId() != null) {
            mapper.updateByPrimaryKeySelective(rolePermissionDataControl);
        } else {
            mapper.insertSelective(rolePermissionDataControl);
        }

    }

}
