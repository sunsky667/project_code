package com.sunsky.spring.demo.controller;

import com.sunsky.spring.annotation.SunSkyAutowired;
import com.sunsky.spring.annotation.SunSkyController;
import com.sunsky.spring.demo.service.LoginService;

@SunSkyController
public class LoginController {

    @SunSkyAutowired
    private LoginService loginService;

}
