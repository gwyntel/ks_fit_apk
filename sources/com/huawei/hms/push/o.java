package com.huawei.hms.push;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f16720a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static ThreadPoolExecutor f16721b = new ThreadPoolExecutor(1, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public static ThreadPoolExecutor a() {
        ThreadPoolExecutor threadPoolExecutor;
        synchronized (f16720a) {
            threadPoolExecutor = f16721b;
        }
        return threadPoolExecutor;
    }
}
