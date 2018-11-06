package com.sunsky.beans;

import com.sunsky.dao.RowKey;

import java.io.Serializable;

/**
 * Created by sunsky on 2017/4/25.
 */
@RowKey("rowKey")
public class UserInfo implements Serializable{
    private String rowKey;
    private String name;
    private String id;

    public void setId(String id) {
       this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getId() {
        return id;
    }

    public String getRowKey() {
        return rowKey;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getRowKey()+this.getName()+this.getId();
    }
}
