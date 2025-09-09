package com.aliyun.iot.aep.sdk.threadpool;

import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class ThreadPool {
    private static final String TAG = "ThreadPool";
    private int KEEP_ALIVE_TIME = 8;
    private BlockingQueue<Runnable> mBlockQueue;
    private int mCoreSize;
    private int mQueueSize;
    private ThreadPoolExecutor mThreadPool;

    public static class DefaultThreadPool {
        ThreadPool mThreadPool;

        static class a {

            /* renamed from: a, reason: collision with root package name */
            private static final DefaultThreadPool f12041a = new DefaultThreadPool();
        }

        public static final DefaultThreadPool getInstance() {
            return a.f12041a;
        }

        public Future<?> submit(Runnable runnable) {
            return this.mThreadPool.submit(runnable);
        }

        private DefaultThreadPool() {
            this.mThreadPool = new ThreadPool();
        }
    }

    public static class MainThreadHandler {
        WeakHandler mHandler;

        static class a {

            /* renamed from: a, reason: collision with root package name */
            private static final MainThreadHandler f12042a = new MainThreadHandler();
        }

        public static final MainThreadHandler getInstance() {
            return a.f12042a;
        }

        public void post(Runnable runnable) {
            this.mHandler.post(runnable);
        }

        private MainThreadHandler() {
            this.mHandler = new WeakHandler(Looper.getMainLooper());
        }

        public void post(Runnable runnable, long j2) {
            this.mHandler.postDelayed(runnable, j2);
        }
    }

    class a implements FileFilter {
        a() {
        }

        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return Pattern.matches("cpu[0-9]+", file.getName());
        }
    }

    public ThreadPool() {
        int coresNumbers = getCoresNumbers();
        this.mCoreSize = coresNumbers;
        this.mQueueSize = coresNumbers * 32;
        init();
    }

    private static int getCoresNumbers() {
        int iAvailableProcessors;
        try {
            iAvailableProcessors = new File("/sys/devices/system/cpu/").listFiles(new a()).length;
        } catch (Exception e2) {
            e2.printStackTrace();
            iAvailableProcessors = 0;
        }
        if (iAvailableProcessors < 1) {
            iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        }
        int i2 = iAvailableProcessors >= 1 ? iAvailableProcessors : 1;
        Log.i(TAG, "CPU cores: " + i2);
        return i2;
    }

    void init() {
        this.mCoreSize = Math.min(4, this.mCoreSize);
        this.mBlockQueue = new ArrayBlockingQueue(this.mQueueSize);
        int i2 = this.mCoreSize;
        this.mThreadPool = new ThreadPoolExecutor(i2, i2 * 2, this.KEEP_ALIVE_TIME, TimeUnit.SECONDS, this.mBlockQueue, new ThreadFactory() { // from class: com.aliyun.iot.aep.sdk.threadpool.ThreadPool.1

            /* renamed from: a, reason: collision with root package name */
            AtomicInteger f12038a = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "ALinke-Thread-Pool-" + this.f12038a.getAndDecrement());
            }
        }, new RejectedExecutionHandler() { // from class: com.aliyun.iot.aep.sdk.threadpool.ThreadPool.2
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                Log.e(ThreadPool.TAG, "rejectedExecution");
                Log.e(ThreadPool.TAG, ThreadPool.this.mBlockQueue.size() + "");
            }
        });
    }

    public Future<?> submit(Runnable runnable) {
        return this.mThreadPool.submit(runnable);
    }

    public ThreadPool(int i2, int i3) {
        this.mCoreSize = i2;
        this.mQueueSize = i3;
        init();
    }
}
