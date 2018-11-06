package com.sunsky.jvm.classLoader;

public class LoaderTest {
    //1、命令行启动应用时候由JVM初始化加载
    //2、通过Class.forName()方法动态加载
    //3、通过ClassLoader.loadClass()方法动态加载
    public static void main(String[] args) throws ClassNotFoundException{
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
        ClassLoader loader = LoaderTest.class.getClassLoader();
        System.out.println(loader);
        //loader.loadClass("com.sunsky.jvm.loadClass.Test2");


        //使用Class.forName()来加载类，默认会执行初始化块
//        Class.forName("com.sunsky.jvm.loadClass.Test2");

        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("com.sunsky.jvm.loadClass.Test2",false,loader);



        //Class.forName()：将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块；
        //ClassLoader.loadClass()：只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去执行static块。
        //Class.forName(name,initialize,loader)带参函数也可控制是否加载static块。并且只有调用了newInstance()方法采用调用构造函数，创建类的对象 。
    }
}
