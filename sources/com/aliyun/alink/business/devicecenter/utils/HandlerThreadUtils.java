package com.aliyun.alink.business.devicecenter.utils;

import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes2.dex */
public class HandlerThreadUtils {

    /* renamed from: a, reason: collision with root package name */
    public static String f10637a = "defaultName";

    /* renamed from: b, reason: collision with root package name */
    public HandlerThread f10638b;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final HandlerThreadUtils f10639a = new HandlerThreadUtils();
    }

    public static HandlerThreadUtils getInstance() {
        return SingletonHolder.f10639a;
    }

    public Looper getLooper() {
        if (this.f10638b == null) {
            this.f10638b = new HandlerThread(f10637a);
        }
        Looper looper = this.f10638b.getLooper();
        if (looper != null) {
            return looper;
        }
        this.f10638b.start();
        return this.f10638b.getLooper();
    }

    public HandlerThreadUtils() {
        HandlerThread handlerThread = new HandlerThread(f10637a);
        this.f10638b = handlerThread;
        handlerThread.start();
    }
}
