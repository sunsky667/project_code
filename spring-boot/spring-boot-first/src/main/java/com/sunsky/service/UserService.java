package com.sunsky.service;

import com.sunsky.entity.User;

import java.util.List;

public interface UserService {
    User queryUserById(Integer id);
    List<User> queryUsers();
    void insertUser();
}
