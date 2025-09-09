package com.linkkit.tools.utils;

import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes4.dex */
public class HandlerThreadUtils {
    private static final String TAG = "HandlerThread";
    private static String nameThread = "defaultName";
    private HandlerThread mHandlerThread;

    static class SingletonHolder {
        private static final HandlerThreadUtils INSTANCE = new HandlerThreadUtils();

        private SingletonHolder() {
        }
    }

    public static HandlerThreadUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Looper getLooper() {
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new HandlerThread(nameThread);
        }
        Looper looper = this.mHandlerThread.getLooper();
        if (looper != null) {
            return looper;
        }
        this.mHandlerThread.start();
        return this.mHandlerThread.getLooper();
    }

    private HandlerThreadUtils() {
        HandlerThread handlerThread = new HandlerThread(nameThread);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
    }
}
