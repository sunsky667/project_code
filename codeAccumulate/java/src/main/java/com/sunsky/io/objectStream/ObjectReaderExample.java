package com.sunsky.io.objectStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectReaderExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"object.dat";
        readobjs(path);
    }

    public static void readobj(String path) throws Exception{
        FileInputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        User user = (User)obj;
        System.out.println(user.toString());
    }

    public static void readobjs(String path) throws Exception{
        FileInputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(bis);

        List<User> list = new ArrayList<User>();

        while (true){
            try {
                Object obj = ois.readObject();
                User user = (User)obj;
                list.add(user);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
        System.out.println(list.size());
        for(User user:list){
            System.out.println(user);
        }
        ois.close();
    }
}
