package com.owl.factory;


import java.util.concurrent.*;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/4.
 */
public class OwlThreadPool {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
