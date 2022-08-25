package com.sunsky.controller;

import com.alibaba.fastjson.JSON;
import com.sunsky.param.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/jsonStr")
    @ResponseBody
    public Object jsonString() {
        Map<String,String> result = new HashMap<String, String>();
        result.put("code","200");
        result.put("msg","success");
        return JSON.toJSONString(result);
    }

    @RequestMapping("/jsonObject")
    @ResponseBody
    public Object jsonObject() {
        Map<String,String> result = new HashMap<String, String>();
        result.put("code","200");
        result.put("msg","success");
        return result;
    }

}
