package com.sunsky.rpc.server;

import java.io.IOException;

/**
 * 服务中心接口定义
 */
public interface Server {
    /**
     * start
     * @throws IOException
     */
    public void start() throws IOException;

    /**
     * stop
     */
    public void stop();

    /**
     * register service to server
     * @param serviceInterface
     * @param impl
     */
    public void register(Class serviceInterface,Class impl);

    /**
     * running
     * @return
     */
    public boolean isRunning();

    /**
     * get server port
     * @return
     */
    public int getPort();
}
