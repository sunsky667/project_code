package com.sunsky.dao;

import com.sunsky.entity.User;

import java.util.List;

public interface UserDao {
	
	public User queryById(Integer id);

	public List<User> queryUsers();
}
