package com.alipay.sdk.m.p0;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import androidx.media3.exoplayer.ExoPlayer;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class c {
    public static String A = null;
    public static volatile c B = null;
    public static volatile b C = null;

    /* renamed from: a, reason: collision with root package name */
    public static final String f9645a = "VMS_IDLG_SDK_Client";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9646b = "content://com.vivo.vms.IdProvider/IdentifierId";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9647c = "persist.sys.identifierid.supported";

    /* renamed from: d, reason: collision with root package name */
    public static final String f9648d = "appid";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9649e = "type";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9650f = "OAID";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9651g = "VAID";

    /* renamed from: h, reason: collision with root package name */
    public static final String f9652h = "AAID";

    /* renamed from: i, reason: collision with root package name */
    public static final int f9653i = 0;

    /* renamed from: j, reason: collision with root package name */
    public static final int f9654j = 1;

    /* renamed from: k, reason: collision with root package name */
    public static final int f9655k = 2;

    /* renamed from: l, reason: collision with root package name */
    public static final int f9656l = 4;

    /* renamed from: m, reason: collision with root package name */
    public static final int f9657m = 11;

    /* renamed from: n, reason: collision with root package name */
    public static final int f9658n = 2000;

    /* renamed from: o, reason: collision with root package name */
    public static Context f9659o = null;

    /* renamed from: p, reason: collision with root package name */
    public static boolean f9660p = false;

    /* renamed from: q, reason: collision with root package name */
    public static d f9661q;

    /* renamed from: r, reason: collision with root package name */
    public static d f9662r;

    /* renamed from: s, reason: collision with root package name */
    public static d f9663s;

    /* renamed from: t, reason: collision with root package name */
    public static Object f9664t = new Object();

    /* renamed from: u, reason: collision with root package name */
    public static HandlerThread f9665u;

    /* renamed from: v, reason: collision with root package name */
    public static Handler f9666v;

    /* renamed from: w, reason: collision with root package name */
    public static String f9667w;

    /* renamed from: x, reason: collision with root package name */
    public static String f9668x;

    /* renamed from: y, reason: collision with root package name */
    public static String f9669y;

    /* renamed from: z, reason: collision with root package name */
    public static String f9670z;

    public static class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 11) {
                Log.e(c.f9645a, "message type valid");
                return;
            }
            String unused = c.f9667w = c.C.a(message.getData().getInt("type"), message.getData().getString("appid"));
            synchronized (c.f9664t) {
                c.f9664t.notify();
            }
        }
    }

    public static c a(Context context) {
        if (B == null) {
            synchronized (c.class) {
                f9659o = context.getApplicationContext();
                B = new c();
            }
        }
        if (C == null) {
            synchronized (c.class) {
                f9659o = context.getApplicationContext();
                g();
                C = new b(f9659o);
                f();
            }
        }
        return B;
    }

    public static void f() {
        f9660p = "1".equals(a(f9647c, "0"));
    }

    public static void g() {
        HandlerThread handlerThread = new HandlerThread("SqlWorkThread");
        f9665u = handlerThread;
        handlerThread.start();
        f9666v = new a(f9665u.getLooper());
    }

    public String b(String str) {
        if (!c()) {
            return null;
        }
        String str2 = f9669y;
        if (str2 != null) {
            return str2;
        }
        a(1, str);
        if (f9662r == null && f9669y != null) {
            a(f9659o, 1, str);
        }
        return f9669y;
    }

    public boolean c() {
        return f9660p;
    }

    public String b() {
        if (!c()) {
            return null;
        }
        a(4, (String) null);
        return A;
    }

    private void b(int i2, String str) {
        Message messageObtainMessage = f9666v.obtainMessage();
        messageObtainMessage.what = 11;
        Bundle bundle = new Bundle();
        bundle.putInt("type", i2);
        if (i2 == 1 || i2 == 2) {
            bundle.putString("appid", str);
        }
        messageObtainMessage.setData(bundle);
        f9666v.sendMessage(messageObtainMessage);
    }

    public String a() {
        if (!c()) {
            return null;
        }
        String str = f9668x;
        if (str != null) {
            return str;
        }
        a(0, (String) null);
        if (f9661q == null) {
            a(f9659o, 0, null);
        }
        return f9668x;
    }

    public String a(String str) {
        if (!c()) {
            return null;
        }
        String str2 = f9670z;
        if (str2 != null) {
            return str2;
        }
        a(2, str);
        if (f9663s == null && f9670z != null) {
            a(f9659o, 2, str);
        }
        return f9670z;
    }

    public void a(int i2, String str) {
        synchronized (f9664t) {
            b(i2, str);
            long jUptimeMillis = SystemClock.uptimeMillis();
            try {
                f9664t.wait(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            if (SystemClock.uptimeMillis() - jUptimeMillis >= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                Log.d(f9645a, "query timeout");
            } else if (i2 == 0) {
                f9668x = f9667w;
                f9667w = null;
            } else if (i2 != 1) {
                if (i2 == 2) {
                    String str2 = f9667w;
                    if (str2 != null) {
                        f9670z = str2;
                        f9667w = null;
                    } else {
                        Log.e(f9645a, "get aaid failed");
                    }
                } else if (i2 != 4) {
                }
                A = f9667w;
                f9667w = null;
            } else {
                String str3 = f9667w;
                if (str3 != null) {
                    f9669y = str3;
                    f9667w = null;
                } else {
                    Log.e(f9645a, "get vaid failed");
                }
            }
        }
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(cls, str, "unknown");
            } catch (Exception e2) {
                e2.printStackTrace();
                return str2;
            }
        } catch (Throwable unused) {
            return str2;
        }
    }

    public static void a(Context context, int i2, String str) {
        if (i2 == 0) {
            f9661q = new d(B, 0, null);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), true, f9661q);
            return;
        }
        if (i2 == 1) {
            f9662r = new d(B, 1, str);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str), false, f9662r);
            return;
        }
        if (i2 != 2) {
            return;
        }
        f9663s = new d(B, 2, str);
        context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str), false, f9663s);
    }
}
