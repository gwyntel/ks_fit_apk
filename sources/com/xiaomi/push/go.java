package com.xiaomi.push;

import android.os.SystemClock;
import com.heytap.mcssdk.constant.Constants;
import com.xiaomi.push.gk;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;
import java.util.Hashtable;

/* loaded from: classes4.dex */
public class go {

    /* renamed from: a, reason: collision with root package name */
    private static final int f23841a = ge.PING_RTT.a();

    /* renamed from: a, reason: collision with other field name */
    private static long f482a = 0;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        static Hashtable<Integer, Long> f23842a = new Hashtable<>();
    }

    public static void a(String str, Exception exc) {
        try {
            gk.a aVarB = gk.b(exc);
            gf gfVarM438a = gm.m436a().m438a();
            gfVarM438a.a(aVarB.f23831a.a());
            gfVarM438a.c(aVarB.f471a);
            gfVarM438a.b(str);
            if (gm.a() != null && gm.a().f474a != null) {
                gfVarM438a.c(bg.c(gm.a().f474a) ? 1 : 0);
            }
            gm.m436a().a(gfVarM438a);
        } catch (NullPointerException unused) {
        }
    }

    public static void b(String str, Exception exc) {
        try {
            gk.a aVarD = gk.d(exc);
            gf gfVarM438a = gm.m436a().m438a();
            gfVarM438a.a(aVarD.f23831a.a());
            gfVarM438a.c(aVarD.f471a);
            gfVarM438a.b(str);
            if (gm.a() != null && gm.a().f474a != null) {
                gfVarM438a.c(bg.c(gm.a().f474a) ? 1 : 0);
            }
            gm.m436a().a(gfVarM438a);
        } catch (NullPointerException unused) {
        }
    }

    public static void a(String str, int i2, Exception exc) {
        gf gfVarM438a = gm.m436a().m438a();
        if (gm.a() != null && gm.a().f474a != null) {
            gfVarM438a.c(bg.c(gm.a().f474a) ? 1 : 0);
        }
        if (i2 > 0) {
            gfVarM438a.a(ge.GSLB_REQUEST_SUCCESS.a());
            gfVarM438a.b(str);
            gfVarM438a.b(i2);
            gm.m436a().a(gfVarM438a);
            return;
        }
        try {
            gk.a aVarA = gk.a(exc);
            gfVarM438a.a(aVarA.f23831a.a());
            gfVarM438a.c(aVarA.f471a);
            gfVarM438a.b(str);
            gm.m436a().a(gfVarM438a);
        } catch (NullPointerException unused) {
        }
    }

    public static void b() {
        a(0, f23841a, null, -1);
    }

    public static void a(XMPushService xMPushService, bf.b bVar) {
        new gh(xMPushService, bVar).a();
    }

    public static synchronized void a(int i2, int i3) {
        try {
            if (i3 < 16777215) {
                a.f23842a.put(Integer.valueOf((i2 << 24) | i3), Long.valueOf(System.currentTimeMillis()));
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("stats key should less than 16777215");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void a(int i2, int i3, String str, int i4) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            int i5 = (i2 << 24) | i3;
            if (a.f23842a.containsKey(Integer.valueOf(i5))) {
                gf gfVarM438a = gm.m436a().m438a();
                gfVarM438a.a(i3);
                gfVarM438a.b((int) (jCurrentTimeMillis - a.f23842a.get(Integer.valueOf(i5)).longValue()));
                gfVarM438a.b(str);
                if (i4 > -1) {
                    gfVarM438a.c(i4);
                }
                gm.m436a().a(gfVarM438a);
                a.f23842a.remove(Integer.valueOf(i3));
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("stats key not found");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void a() {
        if (f482a == 0 || SystemClock.elapsedRealtime() - f482a > Constants.MILLS_OF_WATCH_DOG) {
            f482a = SystemClock.elapsedRealtime();
            a(0, f23841a);
        }
    }

    public static void a(int i2, int i3, int i4, String str, int i5) {
        gf gfVarM438a = gm.m436a().m438a();
        gfVarM438a.a((byte) i2);
        gfVarM438a.a(i3);
        gfVarM438a.b(i4);
        gfVarM438a.b(str);
        gfVarM438a.c(i5);
        gm.m436a().a(gfVarM438a);
    }

    public static void a(int i2) {
        gf gfVarM438a = gm.m436a().m438a();
        gfVarM438a.a(ge.CHANNEL_STATS_COUNTER.a());
        gfVarM438a.c(i2);
        gm.m436a().a(gfVarM438a);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m442a() {
        gg ggVarM439a = gm.m436a().m439a();
        if (ggVarM439a != null) {
            return jx.a(ggVarM439a);
        }
        return null;
    }
}
