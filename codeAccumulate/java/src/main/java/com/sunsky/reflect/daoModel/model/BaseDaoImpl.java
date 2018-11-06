package com.sunsky.reflect.daoModel.model;

import java.lang.reflect.ParameterizedType;

public class BaseDaoImpl<T> {

    private Class clazz;

    public BaseDaoImpl(){

        //TODO 说明： getGenericSuperclass() 通过反射获取当前类表示的实体（类，接口，基本类型或void）的直接父类的Type
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();

        System.out.println(pt);
        //TODO getActualTypeArguments()返回参数数组。
        this.clazz = (Class) pt.getActualTypeArguments()[0];

        System.out.println("T type is : "+clazz.getSimpleName());
    }

    public void save(){
        System.out.println("T type is : "+clazz.getSimpleName());
    }
}
