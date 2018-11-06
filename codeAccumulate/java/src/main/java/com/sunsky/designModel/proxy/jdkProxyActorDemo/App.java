package com.sunsky.designModel.proxy.jdkProxyActorDemo;

/**
 * Proxy类负责创建代理对象时，如果指定了handler（处理器），那么不管用户调用代理对象的什么方法，
 * 该方法都是调用处理器的invoke方法。
 * 由于invoke方法被调用需要三个参数：代理对象、方法、方法的参数，因此不管代理对象哪个方法调用处理器的invoke方法，
 * 都必须把自己所在的对象、自己（调用invoke方法的方法）、方法的参数传递进来。
 */
public class App {
    public static void main(String[] args) {

        ActorProxy actorProxy = new ActorProxy();

        //获得代理对象
        Person person = actorProxy.getProxy();

        System.out.println(person.getClass());

        //调用代理对象的sing方法
        String singRef = person.sing("bingyu");
        System.out.println(singRef);
        //调用代理对象的dance方法
        String danceRef = person.dance("jiang lan style");
        System.out.println(danceRef);
    }
}
