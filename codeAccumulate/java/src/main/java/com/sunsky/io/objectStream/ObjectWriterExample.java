package com.sunsky.io.objectStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ObjectWriterExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"object.dat";
        writeObjs(path);
    }

    public static void writeObj(String path) throws Exception{
        FileOutputStream out = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        User user = new User();
        user.setName("zhangsan");
        user.setAge(26);
        user.setSex("M");
        user.setSalary(15000.0);
        oos.writeObject(user);
        oos.close();
    }

    public static void writeObjs(String path) throws Exception{
        FileOutputStream out = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        User user = new User();
        user.setName("zhangsan");
        user.setAge(26);
        user.setSex("M");
        user.setSalary(15000.0);
        oos.writeObject(user);

        User user1 = new User();
        user1.setName("lisi");
        User user2 = new User();
        user2.setName("wangwu");
        User user3 = new User();
        user3.setName("zhaoliu");
        User user4 = new User();
        user4.setName("qianqi");
        oos.writeObject(user1);
        oos.writeObject(user2);
        oos.writeObject(user3);
        oos.writeObject(user4);
        oos.close();
    }
}
