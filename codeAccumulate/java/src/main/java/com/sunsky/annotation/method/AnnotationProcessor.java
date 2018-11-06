package com.sunsky.annotation.method;

import java.lang.reflect.Method;

public class AnnotationProcessor {

    public static void process() throws Exception{
        Class<?> clazz = Manager.class;
        Method[] methods = clazz.getMethods();

        for(Method method : methods){
            if(method.isAnnotationPresent(MethodInfo.class)){
                MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
                System.out.println(methodInfo.author());
                System.out.println(methodInfo.date());
                System.out.println(methodInfo.description());
                method.invoke(clazz.newInstance(),null);
            }
        }
    }

}
