package com.sunsky.rpc.server;

/**
 * 服务提供者实现类
 */
public class HelloServiceImpl implements HelloService {
    public String sayHi(String name) {
        System.out.println(name + " say hi , system out method ");
        return "return result : say hi "+name;
    }
}
