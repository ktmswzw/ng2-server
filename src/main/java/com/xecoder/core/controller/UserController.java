package com.xecoder.core.controller;

import com.xecoder.config.BaseController;
import com.xecoder.core.entity.User;
import com.xecoder.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @RequestMapping(value="/list")
    @ResponseBody
    public List<User> userList(){
        User user = new User();
        user.setPage(1);
        user.setRows(10);
        return userService.getList(user);
    }
}
