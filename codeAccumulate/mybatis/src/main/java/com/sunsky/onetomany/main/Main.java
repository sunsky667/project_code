package com.sunsky.onetomany.main;

import com.sunsky.onetomany.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class Main {

    public static void main(String[] args){
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(com.sunsky.simple.main.Main.class.getClassLoader().getResourceAsStream("Config.xml"));
        SqlSession session =ssf.openSession();

        User user = session.selectOne("findByUserId",1);
        System.out.println("====queryById====" + user);
        session.close();
    }
}
