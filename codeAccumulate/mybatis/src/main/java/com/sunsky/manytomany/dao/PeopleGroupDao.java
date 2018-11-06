package com.sunsky.manytomany.dao;

import com.sunsky.manytomany.entity.Groups;
import com.sunsky.manytomany.entity.People;
import com.sunsky.manytomany.entity.PeopleGroups;

import java.util.List;

public interface PeopleGroupDao {

    public void insertUserGroup(PeopleGroups peopleGroups);

    public List<People> getUsersByGroupId(int groupId);

    public List<Groups> getGroupsByUserId(int userId);
}
