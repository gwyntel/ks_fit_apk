package com.xiaomi.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes4.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private static volatile Handler f24398a;

    /* renamed from: a, reason: collision with other field name */
    private static final Object f929a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static volatile Handler f24399b;

    public static Handler a() {
        if (f24399b == null) {
            synchronized (f929a) {
                try {
                    if (f24399b == null) {
                        HandlerThread handlerThread = new HandlerThread("receiver_task");
                        handlerThread.start();
                        f24399b = new Handler(handlerThread.getLooper());
                    }
                } finally {
                }
            }
        }
        return f24399b;
    }

    private static Handler b() {
        if (f24398a == null) {
            synchronized (l.class) {
                try {
                    if (f24398a == null) {
                        HandlerThread handlerThread = new HandlerThread("handle_receiver");
                        handlerThread.start();
                        f24398a = new Handler(handlerThread.getLooper());
                    }
                } finally {
                }
            }
        }
        return f24398a;
    }

    public static Intent a(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i2) {
        return a(context, broadcastReceiver, intentFilter, null, i2);
    }

    public static Intent a(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, int i2) {
        return a(context, broadcastReceiver, intentFilter, str, b(), i2);
    }

    public static Intent a(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i2) {
        if (context == null || broadcastReceiver == null || intentFilter == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            return context.registerReceiver(broadcastReceiver, intentFilter, str, handler, i2);
        }
        return context.registerReceiver(broadcastReceiver, intentFilter, str, handler);
    }
}
