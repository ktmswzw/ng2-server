package com.xecoder.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.xecoder.config.BaseController;
import com.xecoder.core.entity.User;
import com.xecoder.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/list")
    @ResponseBody
    public JSONObject userList(){
        JSONObject object = new JSONObject();
        User user = new User();
        user.setPage(1);
        user.setRows(10);
        object.put("list",userService.find(user));
        return object;
    }


}
