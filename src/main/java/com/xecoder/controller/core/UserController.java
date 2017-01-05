package com.xecoder.controller.core;

import com.alibaba.fastjson.JSONObject;
import com.xecoder.common.utils.LocaleMessageSourceUtil;
import com.xecoder.config.BaseController;
import com.xecoder.entity.core.User;
import com.xecoder.service.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private LocaleMessageSourceUtil messageSourceUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/list")
    @ResponseBody
    public JSONObject userList(){
        System.out.println(" = " + messageSourceUtil.getMessage("error"));
        JSONObject result = new JSONObject();
        result.put("list",userService.getList(new User()));
        return result;
    }
}
