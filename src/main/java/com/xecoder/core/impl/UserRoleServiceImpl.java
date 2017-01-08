package com.xecoder.core.impl;

import com.xecoder.core.entity.UserRole;
import com.xecoder.core.entity.UserRoleCriteria;
import com.xecoder.core.mapper.UserRoleMapper;
import com.xecoder.core.service.RoleService;
import com.xecoder.core.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public void delete(Long userRoleId) {
		userRoleMapper.deleteByPrimaryKey(userRoleId);

	}

	@Override
	public void deleteByUserId(Long userId) {
		UserRoleCriteria criteria = new UserRoleCriteria();
		UserRoleCriteria.Criteria cri = criteria.createCriteria();
		if(userId != null){
			cri.andUserIdEqualTo(userId);
		}
		userRoleMapper.deleteByExample(criteria);
	}


	@Override
	public List<UserRole> find(Long userId) {
		UserRoleCriteria criteria = new UserRoleCriteria();
		UserRoleCriteria.Criteria cri = criteria.createCriteria();
		if(userId != null){
			cri.andUserIdEqualTo(userId);
		}
		
		List<UserRole> list = userRoleMapper.selectByExample(criteria);
		for(UserRole ur : list){
			if(ur.getRoleId() != null){
				ur.setRole(roleService.get(ur.getRoleId()));
			}
		}
		return list;
	}

    /**
     *
     * @param userRole
     * @return
     */
    public List<UserRole> find(UserRole userRole) {
        UserRoleCriteria criteria = new UserRoleCriteria();
        UserRoleCriteria.Criteria cri = criteria.createCriteria();
        if(userRole.getUserId()!=null){
            cri.andUserIdEqualTo(userRole.getUserId());
        }if(userRole.getRoleId()!=null){
            cri.andRoleIdEqualTo(userRole.getRoleId());
        }
        List<UserRole> list = userRoleMapper.selectByExample(criteria);
        return list;
    }

	@Override
	public UserRole get(Long id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(UserRole userRole) {
		userRoleMapper.insertSelective(userRole);
	}

}
