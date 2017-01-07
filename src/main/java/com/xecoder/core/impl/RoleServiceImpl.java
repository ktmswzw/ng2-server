package com.xecoder.core.impl;


import com.xecoder.core.entity.Role;
import com.xecoder.core.entity.RoleCriteria;
import com.xecoder.core.entity.RolePermission;
import com.xecoder.core.mapper.RoleMapper;
import com.xecoder.core.service.RolePermissionService;
import com.xecoder.core.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
@SuppressWarnings("unchecked")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    public RoleCriteria getCriteria(Role role) {
        RoleCriteria criteria = new RoleCriteria();
        RoleCriteria.Criteria cri = criteria.createCriteria();
        if (role != null) {
            if (StringUtils.isNotBlank(role.getDescription())) {
                cri.andDescriptionEqualTo(role.getDescription());
            }
            if (StringUtils.isNotBlank(role.getName())) {
                cri.andNameEqualTo(role.getName());
            }
        }
        if (role != null && role.getSort() != null && role.getOrder() != null) {
            criteria.setOrderByClause(role.getSort() + " " + role.getOrder());
        }
        return criteria;
    }

    @Override
    public List<Role> find(Role role) {
        return roleMapper.selectByExample(getCriteria(role));
    }

    public List<Role> findAll() {
        RoleCriteria criteria = new RoleCriteria();
        return roleMapper.selectByExample(criteria);
    }

    @Override
    public Role get(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if (role != null) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);
            role.setRolePermissions(rolePermissionService.find(rolePermission));
        }
        return role;
    }

    @Override
    public void save(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void update(Role role) {

        roleMapper.updateByPrimaryKeySelective(role);
    }

}
