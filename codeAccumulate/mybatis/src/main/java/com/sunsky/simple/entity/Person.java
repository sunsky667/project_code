package com.sunsky.simple.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by sunsky on 2017/6/23.
 */
public class Person implements Serializable{

    private int id;
    private String name;
    private int age;
    private String sex;
    private Date insert_time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Date getInsert_time() {
        return insert_time;
    }
    public void setInsert_time(Date insert_time) {
        this.insert_time = insert_time;
    }
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", insert_time=" + insert_time
                + "]";
    }
}
