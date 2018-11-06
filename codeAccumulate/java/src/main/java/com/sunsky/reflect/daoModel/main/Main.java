package com.sunsky.reflect.daoModel.main;

import com.sunsky.reflect.daoModel.dao.UserDao;

public class Main {
    public static void main(String[] args){
        UserDao userdao=new UserDao();
        userdao.save();
    }
}
