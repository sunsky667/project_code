package com.sunsky.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class CreateArray {

    public static void main(String[] args) {
        String name = "java.math.BigInteger";

        String[] cvalue = {"123","456","789"};

        try {
            //加载class
            Class clazz = Class.forName(name);
            //初始化一个对象数组
            Object o = Array.newInstance(clazz,cvalue.length);
            //获取java.math.bigInteger的构造方法
            Constructor constructor = clazz.getConstructor(String.class);
            for(int i=0;i<cvalue.length;i++){
                String val = cvalue[i];
                Object n = constructor.newInstance(val);
                Array.set(o,i,n);
            }

            Object[] oo = (Object[]) o;
            System.out.println("array is : "+Arrays.toString(oo));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
