package com.sunsky.controller;

import com.sunsky.entity.User;
import com.sunsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryUser")
    @ResponseBody
    public Map<String,Object> queryUserById(Integer userId){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("result","0");
        User user = userService.queryUserById(userId);
        result.put("datas",user);
        result.put("desc","success");
        return result;
    }

    @RequestMapping("queryUsers")
    @ResponseBody
    public Map<String,Object> queryUsers(){
        List<User> users = userService.queryUsers();
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("result","0");
        result.put("datas",users);
        result.put("desc","success");
        return result;
    }
}
