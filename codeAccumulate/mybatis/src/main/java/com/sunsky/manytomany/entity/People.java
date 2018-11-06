package com.sunsky.manytomany.entity;

import java.io.Serializable;
import java.util.List;

public class People implements Serializable{

    private Integer id;
    private String name;
    private Integer phoneNum;

    private List<Groups> groups;

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name=" + name +
                ", phoneNum=" + phoneNum +
                ", groups=" + groups +
                '}';
    }
}
