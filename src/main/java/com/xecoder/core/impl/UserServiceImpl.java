package com.xecoder.core.impl;

import com.xecoder.common.exception.SysException;
import com.xecoder.common.exception.factor.UserExcepFactor;
import com.xecoder.common.utils.HashPassword;
import com.xecoder.common.utils.RandomUtils;
import com.xecoder.common.utils.SimpleDate;
import com.xecoder.core.entity.*;
import com.xecoder.core.mapper.UserMapper;
import com.xecoder.core.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationRoleService organizationRoleService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void delete(Long id) {
        if (isSupervisor(id)) {
            throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
        }
        User user = userMapper.selectByPrimaryKey(id);
        userMapper.deleteByPrimaryKey(user.getId());
    }

    @Override
    public void delete(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> find(User user) {
        UserCriteria criteria = new UserCriteria();
        UserCriteria.Criteria cri = criteria.createCriteria();

        if (user != null) {

            if (StringUtils.isNotBlank(user.getUsername())) {
                cri.addCriterion(" username LIKE CONCAT('%','" + user.getUsername() + "','%') ");
            }
            if (StringUtils.isNotBlank(user.getRealname())) {
                cri.addCriterion(" realname LIKE CONCAT('%','" + user.getRealname() + "','%') ");
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                cri.addCriterion(" email LIKE CONCAT('%','" + user.getEmail() + "','%') ");
            }

            if (StringUtils.isNotBlank(user.getStatus())) {
                cri.andStatusEqualTo(user.getStatus());
            } else {
                cri.andStatusEqualTo("enabled");
            }

            if (user.getOrgId() != null) {
                cri.andOrgIdEqualTo(user.getOrgId());
            }

            if (StringUtils.isNotBlank(user.getPhone())) {
                cri.andPhoneEqualTo(user.getPhone());
            }
        }

        if (user.getSort() != null && user.getOrder() != null) {
            criteria.setOrderByClause(user.getSort() + " " + user.getOrder());
        }

        List<User> list = userMapper.selectByExample(criteria);
        if (list != null && list.size() > 0) {
            for (User u : list) {
                setUserOrganization(u);
                u.setUserRoles(userRoleService.find(u.getId()));
            }
        }

        return list;
    }

    @Override
    public User get(String username) {
        UserCriteria criteria = new UserCriteria();
        UserCriteria.Criteria cri = criteria.createCriteria();
        if (StringUtils.isNotBlank(username)) {
            cri.andUsernameEqualTo(username);
        }

        List<User> list = userMapper.selectByExample(criteria);
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            setUserOrganization(user);
            user.setUserRoles(userRoleService.find(user.getId()));
            return user;
        }
        return null;
    }

    public void setUserOrganization(User user) {
        if (user != null && user.getOrgId() != null) {
            user.setOrganization(organizationService.get(user.getOrgId()));
        }
    }


    /**
     * 按用户名
     *
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        User temp = new User();
        temp.setUsername(username);
        User user = userMapper.selectOne(temp);
        if (user != null) {
            setUserOrganization(user);
            if (user.getId() != null)
                user.setUserRoles(userRoleService.find(user.getId()));
        }
        return user;
    }

    /**
     * 按邮箱
     *
     * @param email
     * @return
     */
    public User getByEmail(String email) {
        User temp = new User();
        temp.setEmail(email);
        User user = userMapper.selectOne(temp);
        if (user != null) {
            setUserOrganization(user);
            if (user.getId() != null)
                user.setUserRoles(userRoleService.find(user.getId()));
        }
        return user;
    }

    @Override
    public User login(String username, String password, DeviceEnum device,String deviceToken) {
        User user = getByUsername(username);

        if (user == null) {
            throw new SysException(UserExcepFactor.AUTH_FAILED);
        }

        if (user.getStatus().equals("disabled")) {
            throw new SysException(UserExcepFactor.AUTH_FAILED);
        }

        boolean isMatch = HashPassword.validatePassword(password, user.getPassword(), user.getSalt());
        if (!isMatch)
            throw new SysException(UserExcepFactor.AUTH_FAILED);

        AuthToken loginToken = new AuthToken(user, device);//重新生成
        user.setAvatar(user.getAvatar());
        user.setToken(loginToken.getJwt());
        return user;

    }

    @Override
    public User get(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            setUserOrganization(user);
            if (user.getId() != null)
                user.setUserRoles(userRoleService.find(user.getId()));
        }
        return user;
    }

    @Override
    public void resetPwd(User user, String newPwd) {
        if (newPwd == null) {
            newPwd = "123456";
        }
        byte[] salt = RandomUtils.getRadomByte();
        HashPassword hashPassword = HashPassword.encryptPassword(newPwd, salt);
        user.setSalt(hashPassword.salt);
        user.setPassword(hashPassword.password);

        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void save(User user) {
        //设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            HashPassword hashPassword = HashPassword.encryptPassword(user.getPlainPassword(), RandomUtils.getRadomByte());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
        }
        user.setCreateTime(SimpleDate.getDateTime());
        userMapper.insertSelective(user);

        String roles = user.getRoles();
        Long id = getByUsername(user.getUsername()).getId();
        if (StringUtils.isNotBlank(roles)) {
            if (roles.indexOf(",") > 0) {
                for (String roleId : StringUtils.split(roles, ",")) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Long.parseLong(roleId));
                    userRole.setUserId(id);
                    userRole.setPriority(99);
                    userRoleService.save(userRole);
                }
            } else {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Long.parseLong(roles));
                userRole.setUserId(id);
                userRole.setPriority(99);
                userRoleService.save(userRole);
            }
        }
    }

    @Override
    public void update(User user) {
        String roles = user.getRoles();
        Long id = user.getId();
        if (StringUtils.isNotBlank(roles)) {
            userRoleService.deleteByUserId(id);
            if (StringUtils.indexOfAny(roles, ",") > 0) {
                for (String roleId : StringUtils.split(roles, ",")) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Long.parseLong(roleId));
                    userRole.setUserId(id);
                    userRole.setPriority(99);
                    if (userRoleService.find(userRole).size() == 0) {
                        userRoleService.save(userRole);
                    }
                }
            } else {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Long.parseLong(roles));
                userRole.setUserId(id);
                userRole.setPriority(99);
                if (userRoleService.find(userRole).size() == 0) {
                    userRoleService.save(userRole);
                }
            }
        }
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            HashPassword hashPassword = HashPassword.encryptPassword(user.getPlainPassword(), RandomUtils.getRadomByte());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
            updatePwd(user, user.getPlainPassword());
        }
        userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public void updatePwd(User user, String newPwd) {
        //if (isSupervisor(user.getId())) {
        //	logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
        //	throw new ServiceException("不能修改超级管理员用户");
        //}
        //设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash

        boolean isMatch = HashPassword.validatePassword(newPwd, user.getPassword(), user.getSalt());
        if (isMatch) {
            HashPassword hashPassword = HashPassword.encryptPassword(newPwd, RandomUtils.getRadomByte());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);

            userMapper.updateByPrimaryKeySelective(user);

            return;
        }

        throw new SysException(UserExcepFactor.AUTH_FAILED);

    }

    /**
     * 判断是否超级管理员.
     */
    private boolean isSupervisor(Long id) {
        return id == 1;
    }

}
