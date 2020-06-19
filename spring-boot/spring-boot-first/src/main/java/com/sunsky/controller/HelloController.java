package com.sunsky.controller;

import com.sunsky.param.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/test")
    @ResponseBody
    public Object test(@RequestBody Param param){
        return param;
    }

}
