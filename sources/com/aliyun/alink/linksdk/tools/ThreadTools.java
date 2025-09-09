package com.aliyun.alink.linksdk.tools;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ThreadTools {

    /* renamed from: a, reason: collision with root package name */
    private static LoopHandler f11450a = new LoopHandler(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    private static ExecutorService f11451b = null;

    static class ALXHDefaultThreadFactory implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        private final AtomicInteger f11452a;

        /* renamed from: b, reason: collision with root package name */
        private final ThreadGroup f11453b;

        /* renamed from: c, reason: collision with root package name */
        private final AtomicInteger f11454c;

        /* renamed from: d, reason: collision with root package name */
        private final String f11455d;

        ALXHDefaultThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            this.f11452a = atomicInteger;
            this.f11454c = new AtomicInteger(1);
            SecurityManager securityManager = System.getSecurityManager();
            this.f11453b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f11455d = "pool-" + atomicInteger.getAndIncrement() + "-thread-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) throws SecurityException, IllegalArgumentException {
            Thread thread = new Thread(this.f11453b, runnable, this.f11455d + this.f11454c.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            Process.setThreadPriority(10);
            return thread;
        }
    }

    public static final class LoopHandler extends Handler {
        public LoopHandler(Looper looper) {
            super(looper);
        }

        public void enqueue(Runnable runnable) {
            enqueue(runnable, 0);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Object obj;
            if (message == null || (obj = message.obj) == null || !(obj instanceof Runnable)) {
                return;
            }
            try {
                ((Runnable) obj).run();
            } catch (Exception e2) {
                ALog.e("ThreadTools_ThreadTools", "run task error: " + e2.getMessage());
                e2.printStackTrace();
            }
        }

        public void enqueue(Runnable runnable, int i2) {
            Message messageObtainMessage = obtainMessage();
            messageObtainMessage.obj = runnable;
            sendMessageDelayed(messageObtainMessage, i2);
        }
    }

    private static ExecutorService a() {
        if (f11451b == null) {
            c();
        }
        return f11451b;
    }

    private static LoopHandler b() {
        if (f11450a == null) {
            f11450a = new LoopHandler(Looper.myLooper());
        }
        return f11450a;
    }

    private static void c() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        if (iAvailableProcessors > 10) {
            f11451b = new ThreadPoolExecutor(iAvailableProcessors, iAvailableProcessors + 7, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(15), new ALXHDefaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        } else {
            f11451b = new ThreadPoolExecutor(iAvailableProcessors, 15, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(15), new ALXHDefaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        }
    }

    public static String getProcessName(Context context, int i2) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == i2) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }

    public static boolean isAppBroughtToBackgroundByTask(Application application) throws SecurityException {
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) application.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1);
            if (runningTasks.isEmpty()) {
                return false;
            }
            ComponentName componentName = runningTasks.get(0).topActivity;
            return !componentName.getPackageName().equals(application.getPackageName());
        } catch (Exception unused) {
            return true;
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        f11450a.enqueue(runnable);
    }

    public static void submitTask(Runnable runnable, boolean z2) {
        submitTask(runnable, z2, 0);
    }

    public static void submitTask(Runnable runnable, boolean z2, int i2) {
        if (z2) {
            b().enqueue(runnable, i2);
        } else {
            a().submit(runnable);
        }
    }
}
