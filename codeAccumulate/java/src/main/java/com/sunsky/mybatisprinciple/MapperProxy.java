package com.sunsky.mybatisprinciple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理之投鞭断流！看一下MyBatis的底层实现原理！
 * https://mp.weixin.qq.com/s?__biz=MzI1NDQ3MjQxNA==&mid=2247486856&idx=1&sn=d430be5d14d159fd36b733c83369d59a&chksm=e9c5f439deb27d2f60b69d7f09b240eb43a8b1de2d07f7511e1f1fecdf9d49df1cb7bc6e1ab5&mpshare=1&scene=1&srcid=09131kmLlbolvIk8sTl4PKnk#rd
 */
public class MapperProxy implements InvocationHandler {

    public <T> T newInstance(Class<T> clz){
        return (T) Proxy.newProxyInstance(clz.getClassLoader(),new Class[]{clz} , this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(method.getName());
        System.out.println(method.getDeclaringClass());
        System.out.println(proxy.getClass());

        if(proxy.getClass().equals(method.getDeclaringClass())){
            try {
                //将target指向当前的对象
                System.out.println("============================="+method.getName());
                return method.invoke(this,args);
            }catch (Throwable t){

            }
        }
        //投鞭断流
        return new User((Integer) args[0],"zhangsan",28);
    }
}
