package com.xecoder.core.impl;



import com.xecoder.core.entity.RolePermission;
import com.xecoder.core.entity.RolePermissionCriteria;
import com.xecoder.core.mapper.RolePermissionMapper;
import com.xecoder.core.service.PermissionService;
import com.xecoder.core.service.RolePermissionDataControlService;
import com.xecoder.core.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private RolePermissionDataControlService rolePermissionDataControlService;
	
	@Override
	public void delete(Long id) {
		rolePermissionMapper.deleteByPrimaryKey(id);
	}

    @Override
    public void delete(RolePermission rolePermission) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        RolePermissionCriteria.Criteria cri = criteria.createCriteria();

        if(null != rolePermission.getRoleId()){
            cri.andRoleIdEqualTo(rolePermission.getRoleId());
        }
        rolePermissionMapper.deleteByExample(criteria);
    }

    @Override
	public void delete(Iterable<RolePermission> entities) {

	}

    @Override
    public List<RolePermission> find(RolePermission rolePermission) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        RolePermissionCriteria.Criteria cri = criteria.createCriteria();
        if(rolePermission.getRoleId()!=null){
            cri.andRoleIdEqualTo(rolePermission.getRoleId());
        }

        if(rolePermission != null && rolePermission.getSort() != null && rolePermission.getOrder() != null){
            criteria.setOrderByClause(rolePermission.getSort() + " " + rolePermission.getOrder());
        }

        List<RolePermission> list = rolePermissionMapper.selectByExample(criteria);

        for(RolePermission rp:list){
            if(rp.getPermissionId() != null){
                rp.setPermission(permissionService.get(rp.getPermissionId()));
                rp.setRolePermissionDataControls(rolePermissionDataControlService.findByRolePermissionId(rp.getPermissionId()));
            }
        }
        return list;
    }

	@Override
	public RolePermission get(Long id) {
		return rolePermissionMapper.selectByPrimaryKey(id);
	}

    @Override
	public void save(RolePermission rolePermission) {
		rolePermissionMapper.insertSelective(rolePermission);

	}

	@Override
	public void save(Iterable<RolePermission> entities) {

	}

	@Override
	public void update(RolePermission rolePermission) {
		rolePermissionMapper.updateByPrimaryKeySelective(rolePermission);

	}

}
