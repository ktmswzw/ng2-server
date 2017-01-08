package com.xecoder.core.service;

import com.xecoder.common.exception.SysException;
import com.xecoder.core.entity.DeviceEnum;
import com.xecoder.core.entity.Module;
import com.xecoder.core.entity.User;

import java.util.List;


/** 
 * @description: 用户管理
 * @version 1.0
 * @author ZHEJIANG RUIZHENG
 * @createDate 2014-1-11;下午01:14:07
 */
public interface UserService {

	User get(String username);
	
	List<User> find(User user);

	void update(User user);
	
	void updatePwd(User user, String newPwd);
	
	void resetPwd(User user, String newPwd);

	void save(User user);

	User get(Long id);

	void delete(Long id);

    void delete(User user);

	public User getByUsername(String username);

	public User getByEmail(String email);

	User login(String username, String password, DeviceEnum device,String deviceToken) throws SysException;

	Module getMenuModule(User user);
}
