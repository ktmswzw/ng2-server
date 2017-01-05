package com.xecoder.core.service;

import com.github.pagehelper.PageHelper;
import com.xecoder.core.entity.User;
import com.xecoder.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vincent on 1/3/17.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getList(User user) {
        PageHelper.startPage(user.getPage(), user.getRows());
        List<User> list = userMapper.selectAll();
        return list;
    }

    public User addUser(String telephone, String password) {
        User in = new User();
        in.setUsername(telephone);
        User user = userMapper.selectOne(in);
        /*if (user != null) {
            throw new
        }
        user = new User();
        if (telephone.length() != 11) {
            user.setRegister(false);//非注册用户
        }
        user.setPhone(telephone);
        user.setAvatar("0598e899-3524-4349-8e4e-db692ba343d2");
        user.setNickname("");
        this.save(user);

        String userId = user.getId();
        byte[] salt = RandomUtils.getRadomByte();
        HashPassword hashPassword = HashPassword.encryptPassword(password, salt);
        Auth auth = new Auth(userId, hashPassword.getSalt());
        auth.setPassword(hashPassword.getPassword());
        AuthToken token = null;
        try {
            token = new AuthToken(user, device);
            auth.addToken(token);
            authDao.save(auth);
            setKey(user, token);
        } catch (Exception e) {
            this.delete(user.getId());    //回滚
        }*/
        return user;
    }

}
