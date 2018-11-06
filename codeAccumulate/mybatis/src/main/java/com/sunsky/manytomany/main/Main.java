package com.sunsky.manytomany.main;

import com.sunsky.manytomany.entity.Groups;
import com.sunsky.manytomany.entity.People;
import com.sunsky.manytomany.entity.PeopleGroups;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;

public class Main {

    private static SqlSession sessions;

    static {
        SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqf = sfb.build(Main.class.getClassLoader().getResourceAsStream("Config.xml"));
        sessions = sqf.openSession();
    }

    private static SqlSession getSession(){
        return sessions;
    }

    public static void insertGroup(){
        SqlSession session = getSession();
        System.out.println(session);
        Groups groups = new Groups();
        groups.setName("test_group");
        groups.setId(3);
        session.insert("insertGroup",groups);
        session.commit();
        session.close();
    }

    public static void insertPeopel(){
        SqlSession session = getSession();
        System.out.println(session);
        People people = new People();
        people.setName("太2真人");
        people.setPhoneNum(1311061066);
        session.insert("insertUser",people);
        session.commit();
        session.close();
    }

    public static void insertPeopleGroup(){
        SqlSession session = getSession();
        System.out.println(session);
        PeopleGroups peopleGroups = new PeopleGroups();
        peopleGroups.setUserId(3);
        peopleGroups.setGroupId(2);
        session.insert("insertUserGroup",peopleGroups);
        session.commit();
        session.commit();
    }

    public static void queryUsersByGrpId(){
        SqlSession session = getSession();
        System.out.println(session);
        List<People> peopleList = session.selectList("getUsersByGroupId",1);

        for(People people : peopleList){
            System.out.println(people);
        }

        session.close();
    }

    public static void queryGroupsByUserId(){
        SqlSession session = getSession();
        System.out.println(session);
        List<Groups> groups = session.selectList("getGroupsByUserId",1);
        for(Groups group : groups){
            System.out.println(group);
        }
        session.close();
    }

    public static void queryPeople(){
        SqlSession session = getSession();
        System.out.println(session);
        People people = session.selectOne("getUser",1);
        System.out.println(people);
        session.close();
    }

    public static void queryGroups(){
        SqlSession session = getSession();
        System.out.println(session);
        Groups groups = session.selectOne("getGroup",1);
        System.out.println(groups);
        session.close();
    }

    public static void main(String[] args){
        //insertPeopel();
        //insertPeopleGroup();
        //queryUsersByGrpId();
        //queryGroupsByUserId();
        //queryPeople();
        queryGroups();
    }
}
