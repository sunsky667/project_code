package com.sunsky.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务中心实现类
 */
public class ServiceCenter implements Server {
    //线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //服务接口名和实现类（key->接口名 value->实现类）
    private static final HashMap<String,Class> serviceRegistry = new HashMap<String, Class>();
    //是否运行
    private static boolean isRunning = false;
    //端口
    private static int port;

    /**
     * construct method
     * @param port
     */
    public ServiceCenter(int port){
        this.port = port;
    }

    /**
     * start server center
     * @throws IOException
     */
    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("start server");

        try {
            while (true){
                // 1.监听客户端的TCP连接，接到TCP连接后将其封装成task，由线程池执行
                executor.execute(new ServiceTask(serverSocket.accept()));
            }
        }finally {
            serverSocket.close();
        }
    }

    /**
     * stop server
     */
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    /**
     * register server interface and class to map(serviceRegistry)
     * @param serviceInterface
     * @param impl
     */
    public void register(Class serviceInterface, Class impl) {
        serviceRegistry.put(serviceInterface.getName(),impl);
    }

    /**
     * judge server running status
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * get server port
     * @return
     */
    public int getPort() {
        return port;
    }

    //静态内部类
    private static class ServiceTask implements Runnable{

        Socket client = null;

        /**
         * construct method
         * @param client socket client
         */
        public ServiceTask(Socket client){
            this.client = client;
        }

        public void run() {
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                //get inputStream from socket
                objectInputStream = new ObjectInputStream(client.getInputStream());
                //get server name
                String serverName = objectInputStream.readUTF();
                System.out.println("server center get server name from socket client "+serverName);
                //get method name
                String methodName = objectInputStream.readUTF();
                System.out.println("server center get method name from socket client "+methodName);

                //get method param type
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                System.out.println("server center get parameterTypes from socket client "+parameterTypes[0]);
                //get method params
                Object[] arguments = (Object[]) objectInputStream.readObject();
                System.out.println("server center get arguments from socket client "+arguments[0]);
                //get impl class from map
                Class serviceClass = serviceRegistry.get(serverName);

                if(serviceClass == null){
                    throw new ClassNotFoundException(serverName + " not found");
                }

                //get reflect method from impl class
                Method method = serviceClass.getMethod(methodName,parameterTypes);
                //invoke method and return result
                Object result = method.invoke(serviceClass.newInstance(),arguments);
                //get output stream from socket
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());

                // 3.将执行结果反序列化，通过socket发送给客户端
                //write result to socket
                objectOutputStream.writeObject(result);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(objectOutputStream != null){
                    try {
                        objectOutputStream.close();
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

                if(client != null){
                    try {
                        client.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
