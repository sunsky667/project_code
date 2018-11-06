package com.sunsky.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyUtil {

    public static PropertyDescriptor getPropertyDescriptor(Class clazz , String propertyName){
        StringBuffer sb = new StringBuffer();//构建一个可变字符串用来构建方法名称
        Method setMethod = null;
        Method getMethod = null;
        PropertyDescriptor pd = null;

        try {

            Field field = clazz.getDeclaredField(propertyName);
            if(field != null){
                //构建方法的后缀
                String methodEnd = propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
                sb.append("set"+methodEnd);
                setMethod = clazz.getDeclaredMethod(sb.toString(),new Class[]{field.getType()});
                sb.delete(0,sb.length());
                sb.append("get"+methodEnd);
                getMethod = clazz.getDeclaredMethod(sb.toString(),new Class[]{});
                //构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
                pd = new PropertyDescriptor(propertyName,getMethod,setMethod);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return pd;
    }

    public static void setProperty(Object obj,String propertyName,Object value){
        Class clazz = obj.getClass();
        PropertyDescriptor pd = getPropertyDescriptor(clazz,propertyName);
        Method setMethod = pd.getWriteMethod();

        try {
            setMethod.invoke(obj, new Object[]{value});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object getProperty(Object obj, String propertyName){
        Class clazz = obj.getClass();
        PropertyDescriptor pd = getPropertyDescriptor(clazz,propertyName);
        Method getMethod = pd.getReadMethod();
        Object value = null;
        try {
            value = getMethod.invoke(obj,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }
}
