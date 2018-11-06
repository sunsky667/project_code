package com.sunsky.simple.main;

import com.sunsky.simple.dao.PersonDao;
import com.sunsky.simple.entity.Person;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * Created by sunsky on 2017/6/23.
 */
public class Main {
    public static void main(String[] args) {
        queryMapperProxy();
    }

    /**
     * 使用sqlsession提供的方法开发
     */
    public static void query(){
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(Main.class.getClassLoader().getResourceAsStream("SqlMapConfig.xml"));
        SqlSession session =ssf.openSession();

        List<Person> persons = session.selectList("queryAll");
        for(Person person:persons){
            System.out.println("====queryAll===="+person);
        }

        Person person = session.selectOne("queryById",1);
        System.out.println("====queryById====" + person);


        Person peson = new Person();
        person.setName("zhangfei");
        person.setAge(499);
        person.setSex("M");

        session.insert("insertPerson",person);
        session.commit();

        System.out.println("get auto increate id is : " +person.getId());

        session.close();
    }

    /**
     * **************** 重要
     * 使用生成代理类的模式开发
     */
    public static void queryMapperProxy(){
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(Main.class.getClassLoader().getResourceAsStream("SqlMapConfig.xml"));
        SqlSession session =ssf.openSession();

        // TODO -- 重点，利用session生成代理的dao接口，可以直接调用dao的方法进行查询
        PersonDao personDao = session.getMapper(PersonDao.class);

        Person person  = personDao.queryById(1);

        System.out.println(person);

    }
}
