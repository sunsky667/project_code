package com.sunsky.service.impl;

import com.sunsky.dao.UserDao;
import com.sunsky.entity.User;
import com.sunsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public User queryUserById(Integer id){
        User user = userDao.queryById(id);
        return user;
    }

    public List<User> queryUsers() {
        List<User> users = userDao.queryUsers();
        return users;
    }
}
