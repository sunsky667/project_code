package com.sunsky.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String hiError(String name){
        return "hi,"+name+",sorry,error!";
    }

    @HystrixCommand(fallbackMethod = "findUserIdError")
    public String findUser(Integer id){
        return restTemplate.getForObject("http://SERVICE-HI/findUserById?id="+id,String.class);
    }

    public String findUserIdError(Integer id){
        return "system error";
    }

}
