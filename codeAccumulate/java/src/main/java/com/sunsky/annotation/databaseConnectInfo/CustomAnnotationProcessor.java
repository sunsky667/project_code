package com.sunsky.annotation.databaseConnectInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;

public class CustomAnnotationProcessor {

    public static<T> void processor(Class<T> clazz){
        try {

            T t = null;
            Field[] fields = clazz.getDeclaredFields();  //获取T对象声明变量
            Constructor<?>[] constructors = clazz.getConstructors(); //获取T对象的构造函数，因为构造函数的访问级别问题

            System.out.println("======="+constructors.length);

            for(Constructor<?> constructor : constructors){
                constructor.setAccessible(true); //无论T对象的构造函数是什么访问级别都可以访问其方法
                t = (T)constructor.newInstance(new Object(){});
            }

            for(Field field : fields){

                System.out.println(field.getName());

                if(field.isAnnotationPresent(CustomConnection.class)){

                    CustomConnection customConnection = field.getAnnotation(CustomConnection.class);
                    String url = customConnection.url();
                    String driverClass = customConnection.driverClass();
                    String username = customConnection.username();
                    String password = customConnection.password();

                    Class.forName(driverClass);
                    Connection connection = DriverManager.getConnection(url,username,password);
                    field.setAccessible(true);
                    field.set(t,connection);
                    continue;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
