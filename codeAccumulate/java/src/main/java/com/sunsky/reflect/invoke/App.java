package com.sunsky.reflect.invoke;

import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) throws Exception{
        //加载类
        Class clazz = Class.forName("com.sunsky.reflect.invoke.RunClass");
        //反射获得对象
        Object object = clazz.newInstance();

        System.out.println(object);
        //得到方法，（后面是方法参数类型）
        Method method = clazz.getMethod("run",String[].class);

        System.out.println(method);
        //反射执行方法的参数
        String[] arges = new String[]{"a","b"};

        System.out.println(arges);

        //此处必须要转为Object，否则报错
        method.invoke(object,(Object) arges);
    }
}
