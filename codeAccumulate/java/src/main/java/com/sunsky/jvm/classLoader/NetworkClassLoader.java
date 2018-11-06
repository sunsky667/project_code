package com.sunsky.jvm.classLoader;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;

public class NetworkClassLoader extends ClassLoader{

    private String rootUrl;

    /**
     * construct method
     * @param rootUrl
     */
    public NetworkClassLoader(String rootUrl){
        this.rootUrl = rootUrl;
    }

    /**
     * @param name
     * @return
     */
    private String classNameToPath(String name){
        return rootUrl + File.separatorChar + name.replace(".",File.separator) + ".class";
    }

    private byte[] getClassData(String name){
        InputStream inputStream = null;
        try {
            String path = classNameToPath(name);
            System.out.println("======path======"+path);
            byte[] buff = new byte[1024*4];
            int len = -1;

            //for network
//            URL url = new URL(path);
//            inputStream = url.openStream();

            //for local file system
//            inputStream = new FileInputStream(path);

            //anther method load local file system
            URL url = new File(path).toURI().toURL();
            inputStream = url.openStream();

            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            while((len = inputStream.read(buff)) != -1){
                byteArrayInputStream.write(buff,0,len);
            }
            return byteArrayInputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clzz = null;

        byte[] clzzData = getClassData(name);

        if(clzzData == null){
            throw new ClassNotFoundException();
        }

        clzz = defineClass(name,clzzData,0, clzzData.length);

        return clzz;
    }

    public static void main(String[] args) throws Exception{
        NetworkClassLoader networkClassLoader = new NetworkClassLoader("d:\\tmp");
        Class clzz = networkClassLoader.findClass("com.sunsky.jvm.loadClass.Test2");

        System.out.println(clzz);

        Method[] methods = clzz.getDeclaredMethods();
        System.out.println("method size : "+methods.length);
        Object object = clzz.newInstance();
        String str = object.toString();
        System.out.println("=====object to string=====: "+str);
        for(Method method : methods){
            System.out.println("method name is : "+method);
            method.invoke(object);
        }
    }
}
