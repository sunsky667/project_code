package com.sunsky.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Testreflect {
    public static void main(String[] args) throws Exception{
        Class clazz = Class.forName("com.sunsky.reflect.User");
        Object object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            System.out.println("field ====> "+field);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
            Method writeMethod = pd.getWriteMethod();
            System.out.println("writeMethod ====> "+writeMethod);
//            writeMethod.invoke(object,new Object[]{2,"sb"});
            System.out.println("writeMethod name : "+ writeMethod.getName());
            if(writeMethod.getName().equals("setName")){
                writeMethod.invoke(object,"stupid asshole");
            }else{
                writeMethod.invoke(object,3);
            }
//            writeMethod.invoke(object,3);
        }

        for(Field field : fields){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
            Method readMethod = pd.getReadMethod();
            Object value = readMethod.invoke(object);
            System.out.println(value);
        }
    }
}
