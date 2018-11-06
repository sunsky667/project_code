package com.sunsky.manytomany.entity;

import java.io.Serializable;
import java.util.List;

public class Groups implements Serializable {
    private Integer id;
    private String name;

    private List<People> peoples;

    public List<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(List<People> peoples) {
        this.peoples = peoples;
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

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", peoples=" + peoples +
                '}';
    }
}
