package com.sunsky.onetomany.dao;

import com.sunsky.onetomany.entity.User;

public interface UserDao {
    public User findByUserId(int userId);
}
