package com.gupig.user.infra.common.until;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author hanbiuk
 * @date 2024-12-14
 */
public class ThreadPoolExecutorUtil {

    private ThreadPoolExecutorUtil() {
    }

    /**
     * 通用线程池
     */
    private static final String THREAD_NAME = "user-cm-%d";
    private static final ThreadPoolExecutor EXECUTOR_COMMON = new ThreadPoolExecutor(
            4, 32,
            60, TimeUnit.SECONDS,
            new TaskQueue(1024, THREAD_NAME),
            new ThreadFactoryBuilder().setNamePrefix(THREAD_NAME).build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static ThreadPoolExecutor getExecutorCommon() {
        return EXECUTOR_COMMON;
    }

    /**
     * 低优线程池
     */
    private static final String THREAD_NAME_LOW = "user-low-%d";
    private static final ThreadPoolExecutor EXECUTOR_LOW = new ThreadPoolExecutor(
            4, 16,
            60, TimeUnit.SECONDS,
            new TaskQueue(512, THREAD_NAME),
            new ThreadFactoryBuilder().setNamePrefix(THREAD_NAME).build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static ThreadPoolExecutor getExecutorLow() {
        return EXECUTOR_LOW;
    }


    /**
     * 自定义队列
     */
    public static class TaskQueue extends LinkedBlockingQueue<Runnable> {
        private final String threadName;

        public TaskQueue(int capacity, String threadName) {
            super(capacity);
            this.threadName = threadName;
        }

        @Override
        public boolean offer(Runnable o) {
            ThreadPoolExecutor threadPool = getThreadPoolExecutor(this.threadName);

            // we can't do any checks
            if (threadPool == null) {
                return super.offer(o);
            }
            // we are maxed out on threads, simply queue the object
            if (threadPool.getPoolSize() == threadPool.getMaximumPoolSize()) {
                return super.offer(o);
            }
            // we have idle threads, just add it to the queue
            if (threadPool.getActiveCount() < threadPool.getPoolSize()) {
                return super.offer(o);
            }
            // if we have less threads than maximum force creation of a new thread
            if (threadPool.getPoolSize() < threadPool.getMaximumPoolSize()) {
                return false;
            }
            // if we reached here, we need to add it to the queue
            return super.offer(o);
        }
    }

    /**
     * 根据线程池名获取线程
     *
     * @param threadName 线程池名
     * @return 线程名
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(String threadName) {
        return switch (threadName) {
            case THREAD_NAME -> ThreadPoolExecutorUtil.getExecutorCommon();
            case THREAD_NAME_LOW -> ThreadPoolExecutorUtil.getExecutorLow();
            default -> null;
        };
    }

}
