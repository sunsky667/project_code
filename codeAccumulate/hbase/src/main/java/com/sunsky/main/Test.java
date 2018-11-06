package com.sunsky.main;


import com.sunsky.beans.UserInfo;
import com.sunsky.userdao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunsky on 2017/4/24.
 */
public class Test {
    public static void main(String[] args){
        UserDao dao = new UserDao();
        List<String> keys = new ArrayList<String>();
        keys.add("201709200956");
        keys.add("xiaoqiao");
        List<UserInfo> infos = dao.getByKeys("userinfo","info",keys);
        for(UserInfo info : infos){
            System.out.println(info);
        }

        //HbaseUtil.batchQueryRowKey("userinfo",keys);

    }
}
