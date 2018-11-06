package com.sunsky.rpc;

import com.sunsky.rpc.client.RPCClient;
import com.sunsky.rpc.server.HelloService;

import java.net.InetSocketAddress;

public class RpcClientApp {
    public static void main(String[] args) {
        //从远程服务器上获得对象
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class,new InetSocketAddress("localhost",8088));
        //得到返回结果并打印
        System.out.println(service.sayHi("fuck"));
    }
}
