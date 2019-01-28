package com.sunsky.reflect;

import java.lang.reflect.Field;

public class FieldOpt {
    public static void main(String[] args) throws ClassNotFoundException,IllegalAccessException,InstantiationException{
        Class<?> clazz = Class.forName("com.sunsky.reflect.User");
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.newInstance();
        String name = "fuck";
        for(Field field : fields){
            System.out.println("field type is : "+field.getType());
            if(field.getName().equals("name")){
                field.setAccessible(true);
                field.set(object,name);
            }
        }
        System.out.println(object);

        try {
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);

//            System.out.println(field.get(object));

            String nm = String.class.cast(field.get(object));
            System.out.println(nm);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
