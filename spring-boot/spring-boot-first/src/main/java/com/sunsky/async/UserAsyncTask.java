package com.sunsky.async;

import com.sunsky.dao.UserDao;

public class UserAsyncTask implements Runnable{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void run() {
        System.out.println("==================>"+userDao);
    }
}
