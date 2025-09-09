package com.aliyun.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes3.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    public static ExecutorService f12145a = Executors.newCachedThreadPool(new a());

    class a implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        private int f12146a = 0;

        a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            this.f12146a++;
            return new Thread(runnable, "ThreadManager#" + this.f12146a);
        }
    }
}
