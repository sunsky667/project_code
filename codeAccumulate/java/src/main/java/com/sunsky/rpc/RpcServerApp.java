package com.sunsky.rpc;

import com.sunsky.rpc.server.HelloService;
import com.sunsky.rpc.server.HelloServiceImpl;
import com.sunsky.rpc.server.Server;
import com.sunsky.rpc.server.ServiceCenter;

import java.io.IOException;

/**
 * 服务调用的class需要公有化，即服务和客户端都需要有
 * 这就需要用maven工程将服务调用的class作为一个单独的model
 * 让服务提供方和调用方都使用
 *
 * 服务端，是在注册的时候，需要将class注册进服务端
 *
 */
public class RpcServerApp {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    Server server = new ServiceCenter(8088);
                    //将class注册到服务器端
                    server.register(HelloService.class, HelloServiceImpl.class);

                    server.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
