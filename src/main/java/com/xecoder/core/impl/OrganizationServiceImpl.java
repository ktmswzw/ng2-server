package com.xecoder.core.impl;



import com.xecoder.common.exception.SysException;
import com.xecoder.common.exception.factor.UserExcepFactor;
import com.xecoder.core.entity.Organization;
import com.xecoder.core.entity.OrganizationCriteria;
import com.xecoder.core.entity.User;
import com.xecoder.core.mapper.OrganizationMapper;
import com.xecoder.core.service.OrganizationService;
import com.xecoder.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("organizationService")
@Transactional
@SuppressWarnings("unchecked")
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private UserService userService;

	@Override
	public void delete(Long id) {
		if (isRoot(id)) {
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		Organization temp = new Organization();
		temp.setParentId(id);
		if(find(temp).size() > 0){
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		User user = new User();
		user.setOrgId(id);
		List<User> userList = userService.find(user);
		if (userList.size() > 0) {
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		organizationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Organization> find(Organization organization) {
		OrganizationCriteria criteria = new OrganizationCriteria();
		OrganizationCriteria.Criteria cri = criteria.createCriteria();
		if(organization.getParentId() != null){
			cri.andParentIdEqualTo(organization.getParentId());
		}
		
		if(StringUtils.isNotBlank(organization.getName())){
			cri.andNameEqualTo(organization.getName());
		}
		
		List<Organization> list = organizationMapper.selectByExample(criteria);

		if(list.size() > 0){
			for(Organization o:list){
				if(o.getParentId() != null){
					o.setParent(this.get(o.getParentId()));
				}
			}
		}
		return list;
	}


    @Override
	public Organization get(Long id) {
		return organizationMapper.selectByPrimaryKey(id);
	}

	@Override
	public Organization getByName(String name) {
		OrganizationCriteria criteria = new OrganizationCriteria();
		OrganizationCriteria.Criteria cri = criteria.createCriteria();
		
		if(StringUtils.isNotBlank(name)){
			cri.andNameEqualTo(name);
		}
		List<Organization> list = organizationMapper.selectByExample(criteria);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Organization getTree() {
		List<Organization> list = find(new Organization());
		
		List<Organization> rootList = makeTree(list);
		
		return rootList.get(0);
	}
	
	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<>();
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<>(0));
				parent.add(e);
			}
		}
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Organization> parent, List<Organization> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Organization> tmp = new ArrayList<>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				c2.setChildren(new ArrayList<>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}

	@Override
	public void save(Organization organization) {
		organizationMapper.insertSelective(organization);

	}

	@Override
	public void update(Organization organization) {
		organizationMapper.updateByPrimaryKeySelective(organization);

	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

}
