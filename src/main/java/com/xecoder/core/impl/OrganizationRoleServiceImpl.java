package com.xecoder.core.impl;

import com.xecoder.core.entity.OrganizationRole;
import com.xecoder.core.entity.OrganizationRoleCriteria;
import com.xecoder.core.mapper.OrganizationRoleMapper;
import com.xecoder.core.service.OrganizationRoleService;
import com.xecoder.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

	@Resource(name="roleService")
	private RoleService roleService;

	@Autowired
	private OrganizationRoleMapper mapper;

	@Override
	public void delete(Long organizationRoleId) {
		mapper.deleteByPrimaryKey(organizationRoleId);
	}

	@Override
	public List<OrganizationRole> find(Long organizationId) {
		OrganizationRoleCriteria criteria = new OrganizationRoleCriteria();
		OrganizationRoleCriteria.Criteria cri = criteria.createCriteria();
		if(organizationId != null){
			cri.andOrganizationIdEqualTo(organizationId);
		}

		List<OrganizationRole> list = mapper.selectByExample(criteria);
		for(OrganizationRole or : list){
			if(or.getRoleId() != null){
				or.setRole(roleService.get(or.getRoleId()));
			}
		}

		return list;
	}

	@Override
	public OrganizationRole get(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(OrganizationRole organizationRole) {
		mapper.insert(organizationRole);

	}

}
