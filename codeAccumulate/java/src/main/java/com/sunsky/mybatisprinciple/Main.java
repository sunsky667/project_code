package com.sunsky.mybatisprinciple;

public class Main {
    public static void main(String[] args) {
        MapperProxy mapperProxy = new MapperProxy();
        UserMapper userMapper = mapperProxy.newInstance(UserMapper.class);
        User user = userMapper.getUserById(001);
        System.out.println(user);
    }
}
