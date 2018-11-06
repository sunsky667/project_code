package com.sunsky.onetomany.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
    private Integer userId;
    private String name;
    private Integer phoneNum;

    private List<Post> posts;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNum=" + phoneNum +
                ", posts=" + posts +
                '}';
    }
}
