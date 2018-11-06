package com.sunsky.manytomany.dao;

import com.sunsky.manytomany.entity.People;

public interface PeopleDao {
    public void insertUser(People people);
    public People getUser(Integer id);
}
