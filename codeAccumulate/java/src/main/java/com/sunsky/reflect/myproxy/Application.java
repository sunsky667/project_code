package com.sunsky.reflect.myproxy;

import com.sunsky.reflect.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Application implements InvocationHandler {

    Service service = new ServiceImpl();

    public Object newInstance(Class<?> clazz){
        System.out.println("instance");
        return Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println(method.getName());
        System.out.println("before method invoke , can add some code");
        Object object =  method.invoke(service,args);
        System.out.println("after method invoke, also can add some code");
        return object;
    }

    public static void main(String[] args) {
        Application test = new Application();
        Service service = (Service) test.newInstance(Service.class);
        service.test("a","b");
        service.test("c","d");
        System.out.println(service);
    }
}
