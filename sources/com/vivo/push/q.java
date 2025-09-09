package com.vivo.push;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes4.dex */
public abstract class q {

    /* renamed from: a, reason: collision with root package name */
    protected Context f23198a;

    /* renamed from: b, reason: collision with root package name */
    protected Handler f23199b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f23200c = new Object();

    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            q.this.b(message);
        }
    }

    public q() {
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName(), 1);
        handlerThread.start();
        this.f23199b = new a(handlerThread.getLooper());
    }

    public final void a(Context context) {
        this.f23198a = context;
    }

    public abstract void b(Message message);

    public final void a(Message message) {
        synchronized (this.f23200c) {
            try {
                Handler handler = this.f23199b;
                if (handler == null) {
                    String str = "Dead worker dropping a message: " + message.what;
                    com.vivo.push.util.p.e(getClass().getSimpleName(), str + " (Thread " + Thread.currentThread().getId() + ")");
                } else {
                    handler.sendMessage(message);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
