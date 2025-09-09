package com.umeng.ccg;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static Handler f22032a = null;

    /* renamed from: b, reason: collision with root package name */
    public static final int f22033b = 101;

    /* renamed from: c, reason: collision with root package name */
    public static final int f22034c = 102;

    /* renamed from: d, reason: collision with root package name */
    public static final int f22035d = 103;

    /* renamed from: e, reason: collision with root package name */
    public static final int f22036e = 104;

    /* renamed from: f, reason: collision with root package name */
    public static final int f22037f = 105;

    /* renamed from: g, reason: collision with root package name */
    public static final int f22038g = 106;

    /* renamed from: h, reason: collision with root package name */
    public static final int f22039h = 107;

    /* renamed from: i, reason: collision with root package name */
    public static final int f22040i = 0;

    /* renamed from: j, reason: collision with root package name */
    public static final int f22041j = 1;

    /* renamed from: k, reason: collision with root package name */
    public static final int f22042k = 2;

    /* renamed from: l, reason: collision with root package name */
    public static final int f22043l = 201;

    /* renamed from: m, reason: collision with root package name */
    public static final int f22044m = 202;

    /* renamed from: n, reason: collision with root package name */
    public static final int f22045n = 203;

    /* renamed from: o, reason: collision with root package name */
    public static final int f22046o = 301;

    /* renamed from: p, reason: collision with root package name */
    public static final int f22047p = 302;

    /* renamed from: q, reason: collision with root package name */
    public static final int f22048q = 303;

    /* renamed from: r, reason: collision with root package name */
    public static final int f22049r = 304;

    /* renamed from: s, reason: collision with root package name */
    public static final int f22050s = 305;

    /* renamed from: t, reason: collision with root package name */
    private static HandlerThread f22051t = null;

    /* renamed from: u, reason: collision with root package name */
    private static HashMap<Integer, a> f22052u = null;

    /* renamed from: v, reason: collision with root package name */
    private static final int f22053v = 256;

    public interface a {
        void a(Object obj, int i2);
    }

    private c() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Message message) {
        int i2 = message.arg1;
        Object obj = message.obj;
        Integer numValueOf = Integer.valueOf(i2 / 100);
        HashMap<Integer, a> map = f22052u;
        if (map == null) {
            return;
        }
        a aVar = map.containsKey(numValueOf) ? f22052u.get(numValueOf) : null;
        if (aVar != null) {
            aVar.a(obj, i2);
        }
    }

    public static void a(Context context, int i2, int i3, a aVar, Object obj, long j2) {
        if (context == null || aVar == null) {
            return;
        }
        if (f22052u == null) {
            f22052u = new HashMap<>();
        }
        Integer numValueOf = Integer.valueOf(i3 / 100);
        if (!f22052u.containsKey(numValueOf)) {
            f22052u.put(numValueOf, aVar);
        }
        if (f22051t == null || f22032a == null) {
            a();
        }
        try {
            Handler handler = f22032a;
            if (handler != null) {
                Message messageObtainMessage = handler.obtainMessage();
                messageObtainMessage.what = i2;
                messageObtainMessage.arg1 = i3;
                messageObtainMessage.obj = obj;
                f22032a.sendMessageDelayed(messageObtainMessage, j2);
            }
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context, int i2, a aVar, Object obj) {
        a(context, 256, i2, aVar, obj, 0L);
    }

    public static void a(Context context, int i2, a aVar, Object obj, long j2) {
        a(context, 256, i2, aVar, obj, j2);
    }

    private static synchronized void a() {
        try {
            if (f22051t == null) {
                HandlerThread handlerThread = new HandlerThread("ccg_dispatch");
                f22051t = handlerThread;
                handlerThread.start();
                if (f22032a == null) {
                    f22032a = new Handler(f22051t.getLooper()) { // from class: com.umeng.ccg.c.1
                        @Override // android.os.Handler
                        public void handleMessage(Message message) {
                            if (message.what != 256) {
                                return;
                            }
                            c.b(message);
                        }
                    };
                }
            }
        } catch (Throwable unused) {
        }
    }
}
