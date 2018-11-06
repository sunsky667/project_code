package com.sunsky.serdser;

import java.io.*;

public class SerDserTest {

    public static String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"user.dat";

    public static void seriallize() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setName("tom");
        FileOutputStream out = new FileOutputStream(SerDserTest.path);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        oos.close();
    }

    public static void deseriallize() throws Exception{
        FileInputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        User user = (User)obj;
        System.out.println(user);
        ois.close();


        FileInputStream in1 = new FileInputStream(path);
        BufferedInputStream bis1 = new BufferedInputStream(in1);
        ObjectInputStream ois1 = new ObjectInputStream(bis1);
        Object obj1 = ois1.readObject();
        User user1 = (User)obj1;
        System.out.println(user1);
        ois1.close();

        System.out.println(user1 == user);
        System.out.println(user1.equals(user));
        System.out.println(user.hashCode());
        System.out.println(user1.hashCode());
    }

    public static void main(String[] args) throws Exception{
        //SerDserTest.seriallize();
        SerDserTest.deseriallize();
    }
}
