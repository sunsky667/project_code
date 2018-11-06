package com.sunsky.reflect;

import java.lang.reflect.Field;

public class FieldOpt {
    public static void main(String[] args) throws ClassNotFoundException,IllegalAccessException,InstantiationException{
        Class<?> clazz = Class.forName("com.sunsky.reflect.User");
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.newInstance();
        String name = "fuck";
        for(Field field : fields){
            if(field.getName().equals("name")){
                field.setAccessible(true);
                field.set(object,name);
            }
        }
        System.out.println(object);
    }
}
