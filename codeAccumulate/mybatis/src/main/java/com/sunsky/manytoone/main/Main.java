package com.sunsky.manytoone.main;

import com.sunsky.onetomany.entity.Post;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    public static void main(String[] args){
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(com.sunsky.simple.main.Main.class.getClassLoader().getResourceAsStream("Config.xml"));
        SqlSession session =ssf.openSession();

        Post post = session.selectOne("findByPostId",1);
        System.out.println("====queryById====" + post);
        session.close();
    }
}
