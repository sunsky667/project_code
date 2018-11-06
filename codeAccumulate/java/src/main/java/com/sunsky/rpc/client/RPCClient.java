package com.sunsky.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RPCClient<T> {

    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr){
        // 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),new Class<?>[]{serviceInterface}
        ,new InvocationHandler(){
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Socket socket = null;
                        ObjectInputStream objectInputStream = null;
                        ObjectOutputStream objectOutputStream = null;

                        try {
                            // 2.创建Socket客户端，根据指定地址连接远程服务提供者
                            socket = new Socket();
                            socket.connect(addr);

                            // 3.将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            //发送服务接口名
                            objectOutputStream.writeUTF(serviceInterface.getName());
                            //发送方法名称
                            objectOutputStream.writeUTF(method.getName());
                            //发送方法参数类型
                            objectOutputStream.writeObject(method.getParameterTypes());
                            //发送参数
                            objectOutputStream.writeObject(args);

                            // 4.同步阻塞等待服务器返回应答，获取应答后返回
                            objectInputStream = new ObjectInputStream(socket.getInputStream());
                            //返回远程服务器执行方法后的结果
                            return objectInputStream.readObject();
                        }finally {
                            if(socket != null){
                                try {
                                    socket.close();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            if(objectInputStream != null){
                                try {
                                    objectInputStream.close();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            if(objectOutputStream != null){
                                try {
                                    objectOutputStream.close();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );
    }

}
