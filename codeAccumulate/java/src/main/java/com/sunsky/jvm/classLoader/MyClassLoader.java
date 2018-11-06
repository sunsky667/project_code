package com.sunsky.jvm.classLoader;

import com.sunsky.jvm.loadClass.Test2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

    private String root;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if(classData == null){
            throw new ClassNotFoundException();
        }else{
            return defineClass(name,classData,0,classData.length);
        }
    }

    private byte[] loadClassData(String className){
        String fileName = root + File.separatorChar + className.replace('.',File.separatorChar)+".class";
        System.out.println("filename ====> "+fileName);
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1){
                baos.write(buffer,0,length);
            }
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot("D:\\tmp");
        Class<Test2> testClass = null;
        try {
            //testClass = classLoader.loadClass("com.sunsky.jvm.loadClass.Test2");
            testClass = (Class<Test2>)classLoader.findClass("com.sunsky.jvm.loadClass.Test2");
            Object obj = testClass.newInstance();
            System.out.println("=====> "+obj.getClass().getClassLoader());
            System.out.println(classLoader.getRoot());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
