package com.sunsky.manytomany.dao;

import com.sunsky.manytomany.entity.Groups;

public interface GroupDao {
    public void insertGroup(Groups group);
    public Groups getGroup(Integer id);
}
