package com.sunsky.async;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    public static BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(200);
    public static ExecutorService executorService = new ThreadPoolExecutor(2,4,10, TimeUnit.SECONDS,blockingQueue);

//    public static ExecutorService executorService = Executors.newFixedThreadPool(1);
}
