package com.sunsky.main;

import com.sunsky.pluggin.ActionInterface;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class MainAction {

    public static void main(String[] args) throws Exception{

//        URL url = MainAction.class.getResource("customPluggin-1.0-SNAPSHOT.jar");
        URL url = new URL("jar:file:"+"D:"+ File.separatorChar+"customPluggin-1.0-SNAPSHOT.jar"+"!/");

        System.out.println(url);

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

        Class clazz = classLoader.loadClass("com.sunsky.mypluggin.impl.MyActionPlug");

        if(clazz == null){
            return;
        }

        Object object = clazz.newInstance();

        ActionInterface actionInterface = (ActionInterface)object;

        actionInterface.process();

    }

}
