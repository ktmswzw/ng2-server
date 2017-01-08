package com.xecoder.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.xecoder.config.BaseController;
import com.xecoder.config.NonAuthoritative;
import com.xecoder.core.entity.DeviceEnum;
import com.xecoder.core.entity.User;
import com.xecoder.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vincent on 1/5/17.
 */
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping(value="/login")
    @ResponseBody
    @NonAuthoritative
    public JSONObject login(@RequestParam String username,@RequestParam String password, @RequestParam DeviceEnum device, @RequestParam(defaultValue = "") String deviceToken){
        JSONObject object = new JSONObject();
        User user = userService.login(username,password,device,deviceToken);
        object.put("nickname",user.getRealname());
        object.put("avatar",user.getAvatar());
        object.put("phone",user.getPhone());
        object.put("token",user.getToken());
        object.put("organization",user.getOrganization().getName());
        object.put("organization_id",user.getOrganization().getId());
        object.put("module_tree",userService.getMenuModule(user));
        return object;
    }



}
