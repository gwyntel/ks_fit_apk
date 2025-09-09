package com.vivo.push;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.media3.common.C;

/* loaded from: classes4.dex */
public final class m {

    /* renamed from: a, reason: collision with root package name */
    private static final Handler f23181a = new Handler(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    private static final HandlerThread f23182b;

    /* renamed from: c, reason: collision with root package name */
    private static final Handler f23183c;

    static {
        HandlerThread handlerThread = new HandlerThread("push_client_thread");
        f23182b = handlerThread;
        handlerThread.start();
        f23183c = new n(handlerThread.getLooper());
    }

    public static void a(l lVar) {
        if (lVar == null) {
            com.vivo.push.util.p.a("PushClientThread", "client thread error, task is null!");
            return;
        }
        int iA = lVar.a();
        Message message = new Message();
        message.what = iA;
        message.obj = lVar;
        f23183c.sendMessageDelayed(message, 0L);
    }

    public static void b(Runnable runnable) {
        f23181a.post(runnable);
    }

    public static void c(Runnable runnable) {
        Handler handler = f23183c;
        if (handler != null) {
            handler.post(runnable);
        }
    }

    public static void a(Runnable runnable) {
        Handler handler = f23183c;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }
}
