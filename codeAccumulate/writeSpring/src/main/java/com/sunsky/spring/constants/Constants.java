package com.sunsky.spring.constants;

import com.sunsky.spring.xml.FileSystemXmlApplicationContext;

/**
 * configuration file path
 */
public interface Constants {
    String PAHT = FileSystemXmlApplicationContext.class.getResource("/").getPath();
    String contextConfigLocation = "application.xml";
    String springmvcConfigLocation = "spring-mvc.xml";
    String mybatisConfigLocation = "MyUserMapper.xml";
}
