package com.aliyun.alink.linksdk.connectsdk.tools;

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

/* loaded from: classes2.dex */
public class ThreadPool {
    private static final String TAG = "ThreadPool";
    private int KEEP_ALIVE_TIME = 8;
    private BlockingQueue<Runnable> mBlockQueue;
    private int mCoreSize;
    private int mQueueSize;
    private ThreadPoolExecutor mThreadPool;

    public static class DefaultThreadPool {

        /* renamed from: a, reason: collision with root package name */
        ThreadPool f11075a;

        static class SingletonHolder {
            private static final DefaultThreadPool INSTANCE = new DefaultThreadPool();

            private SingletonHolder() {
            }
        }

        public static final DefaultThreadPool getInstance() {
            return SingletonHolder.INSTANCE;
        }

        public Future<?> submit(Runnable runnable) {
            return this.f11075a.submit(runnable);
        }

        private DefaultThreadPool() {
            this.f11075a = new ThreadPool();
        }
    }

    public static class MainThreadHandler {

        /* renamed from: a, reason: collision with root package name */
        WeakHandler f11076a;

        static class SingletonHolder {
            private static final MainThreadHandler INSTANCE = new MainThreadHandler();

            private SingletonHolder() {
            }
        }

        public static final MainThreadHandler getInstance() {
            return SingletonHolder.INSTANCE;
        }

        public void post(Runnable runnable) {
            this.f11076a.post(runnable);
        }

        private MainThreadHandler() {
            this.f11076a = new WeakHandler(Looper.getMainLooper());
        }

        public void post(Runnable runnable, long j2) {
            this.f11076a.postDelayed(runnable, j2);
        }
    }

    public ThreadPool() {
        int coresNumbers = getCoresNumbers();
        this.mCoreSize = coresNumbers;
        this.mQueueSize = coresNumbers * 32;
        a();
    }

    private static int getCoresNumbers() {
        int iAvailableProcessors;
        try {
            iAvailableProcessors = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() { // from class: com.aliyun.alink.linksdk.connectsdk.tools.ThreadPool.1CpuFilter
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]+", file.getName());
                }
            }).length;
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

    public Future<?> submit(Runnable runnable) {
        return this.mThreadPool.submit(runnable);
    }

    void a() {
        this.mCoreSize = Math.min(4, this.mCoreSize);
        this.mBlockQueue = new ArrayBlockingQueue(this.mQueueSize);
        int i2 = this.mCoreSize;
        this.mThreadPool = new ThreadPoolExecutor(i2, i2 * 2, this.KEEP_ALIVE_TIME, TimeUnit.SECONDS, this.mBlockQueue, new ThreadFactory() { // from class: com.aliyun.alink.linksdk.connectsdk.tools.ThreadPool.1

            /* renamed from: a, reason: collision with root package name */
            AtomicInteger f11072a = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "ALinke-Thread-Pool-" + this.f11072a.getAndDecrement());
            }
        }, new RejectedExecutionHandler() { // from class: com.aliyun.alink.linksdk.connectsdk.tools.ThreadPool.2
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                Log.e(ThreadPool.TAG, "rejectedExecution");
                Log.e(ThreadPool.TAG, ThreadPool.this.mBlockQueue.size() + "");
            }
        });
    }

    public ThreadPool(int i2, int i3) {
        this.mCoreSize = i2;
        this.mQueueSize = i3;
        a();
    }
}
