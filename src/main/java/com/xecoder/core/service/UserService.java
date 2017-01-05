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

    public List<User> getList(User user){
        PageHelper.startPage(user.getPage(), user.getRows());
        List<User> list = userMapper.selectAll();
        return list;
    }
}
