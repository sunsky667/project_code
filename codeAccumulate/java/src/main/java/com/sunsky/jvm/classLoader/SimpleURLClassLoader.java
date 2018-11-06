package com.sunsky.jvm.classLoader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class SimpleURLClassLoader extends URLClassLoader {

    public static String projectClassPath = "D:\\tmp\\";

//    public static String packagePath = "com\\sunsky\\jvm\\loadClass";

    public SimpleURLClassLoader(){
        //设置ClassLoader加载的路径
        super(getMyURLs());
    }

    /**
     *
     * @return
     */
    private static URL[] getMyURLs(){
        URL url = null;
        try {
            url = new File(projectClassPath).toURI().toURL();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    public Class load(String name) throws Exception{
        return loadClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name,false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        System.out.println("##############################################"+name);

        Class clazz = null;
        clazz = findLoadedClass(name);

        //查看HotSwapURLClassLoader实例缓存下，是否已经加载过class
        if(clazz != null){
            System.out.println("clazz not null ");
            if(resolve){
                resolveClass(clazz);
            }
            return clazz;
        }

        if(name.startsWith("java.")){
            System.out.println("start with java");
            try {
                //得到系统默认的加载cl，即AppClassLoader
                ClassLoader system = ClassLoader.getSystemClassLoader();
                //用默认的加载类加载器加载class
                clazz = system.loadClass(name);
                if(clazz != null){
                    if(resolve){
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //若无缓存，不是系统加载类，则调用自动以的类加载器
        return customLoad(name,this);
    }

    /**
     * 自定义加载
     * @param name
     * @param cl
     * @return
     * @throws ClassNotFoundException
     */
    public Class customLoad(String name,ClassLoader cl) throws ClassNotFoundException {
        System.out.println("===============use customLoad method");
        return customLoad(name, false,cl);
    }

    /**
     * 自定义加载
     *
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    public Class customLoad(String name, boolean resolve,ClassLoader cl) throws ClassNotFoundException {
        //findClass()调用的是URLClassLoader里面重载了ClassLoader的findClass()方法
        //这里虽然调用的是SimpleURLClassLoader的findClass方法
        //而SimpleURLClassLoader的findClass方法又是调用的父类的findClass方法
        //所以，还是调用父类的findClass方法，fuck this
        Class clazz = ((SimpleURLClassLoader)cl).findClass(name);
        System.out.println("*****************clazz "+clazz);
        if (resolve){
            ((SimpleURLClassLoader)cl).resolveClass(clazz);
        }
        return clazz;
    }

    /**
     * 重写findClass方法
     * 调用父类的findClass方法
     * 这个才是最后加载自定义类的classloader的方法  important
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    public static void main(String[] args) throws Exception{

        /**
         * simpleURLClassLoader.loadClass   ==>  loadClass(String name)  ==>  loadClass(String name, boolean resolve)[over write]
         * ==> customLoad(String name,ClassLoader cl)  ==>
         */

        SimpleURLClassLoader simpleURLClassLoader = new SimpleURLClassLoader();
        Class clazz = simpleURLClassLoader.load("com.sunsky.jvm.loadClass.Test2");
        Object object = clazz.newInstance();
        System.out.println(object.toString());
    }
}
