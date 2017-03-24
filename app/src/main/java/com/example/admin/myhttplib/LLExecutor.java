package com.example.admin.myhttplib;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Created by admin on 2017/3/23.
 */

public class LLExecutor {

    private LLExecutor(){
        throw new RuntimeException("no access permission");
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private HandlerThread mHandlerThread;

    private Handler localHandler;//用于操作本地IO
    private static Handler netHandler;//用于操作网络IO

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"MeExecutor" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable>sPoolWorkQueue = new LinkedBlockingQueue<>(128);

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            sPoolWorkQueue,
            sThreadFactory);

    private static class NetHandler extends Handler{
        public NetHandler(){
            super(Looper.myLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {

        }
    }

    public static Handler getNetHandler(){
        synchronized (LLExecutor.class){
            if(netHandler == null){
                HandlerThread handlerThread = new HandlerThread("handler thread");
                handlerThread.start();
                netHandler = new Handler(handlerThread.getLooper());
            }
            return netHandler;
        }
    }
}
