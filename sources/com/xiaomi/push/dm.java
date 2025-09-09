package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class dm {

    /* renamed from: a, reason: collision with root package name */
    private static int f23582a = -1;

    /* renamed from: a, reason: collision with other field name */
    private static ds f278a;

    /* renamed from: a, reason: collision with other field name */
    private static String f279a;

    public static void a(Context context, hb hbVar) {
        if (m283a(context)) {
            if (f278a == null) {
                f278a = new ds(context);
            }
            hbVar.a(f278a);
            a("startStats");
        }
    }

    public static void b(Context context, hb hbVar) {
        ds dsVar = f278a;
        if (dsVar != null) {
            hbVar.b(dsVar);
            f278a = null;
            a("stopStats");
        }
    }

    private static synchronized void b(String str) {
        try {
            if ("WIFI-ID-UNKNOWN".equals(str)) {
                String str2 = f279a;
                if (str2 == null || !str2.startsWith("W-")) {
                    f279a = null;
                }
            } else {
                f279a = str;
            }
            a("updateNetId new networkId = " + str + ", finally netId = " + f279a);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m283a(Context context) {
        return dj.a(context);
    }

    public static void a(Context context, String str, int i2) {
        if (!m283a(context)) {
            a("onDisconnection shouldSampling = false");
            return;
        }
        dp.a(context, str, bg.c(context), System.currentTimeMillis(), i2, com.xiaomi.push.service.p.a(context).m787b(), a(context), a(), f23582a);
        a("onDisconnection");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m282a(Context context) {
        if (!m283a(context)) {
            a("onReconnection shouldSampling = false");
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        f23582a = a(context);
        dp.a(context, jCurrentTimeMillis);
        a("onReconnection connectedNetworkType = " + f23582a);
    }

    public static void a(Context context, String str) {
        if (!m283a(context)) {
            a("onWifiChanged shouldSampling = false");
            return;
        }
        a("onWifiChanged wifiDigest = " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        b("W-" + str);
    }

    private static int a(Context context) {
        String str;
        try {
            bj bjVarM201a = bg.m201a();
            if (bjVarM201a != null) {
                if (bjVarM201a.a() == 0) {
                    String strM213b = bjVarM201a.m213b();
                    if (TextUtils.isEmpty(strM213b) || "UNKNOWN".equalsIgnoreCase(strM213b)) {
                        str = null;
                    } else {
                        str = "M-" + strM213b;
                    }
                    b(str);
                    return 0;
                }
                if (bjVarM201a.a() != 1 && bjVarM201a.a() != 6) {
                    b(null);
                    return -1;
                }
                b("WIFI-ID-UNKNOWN");
                return 1;
            }
            b(null);
            return -1;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("DisconnectStatsHelper getNetType occurred error: " + e2.getMessage());
            b(null);
            return -1;
        }
    }

    private static synchronized String a() {
        return f279a;
    }

    static void a(String str) {
        dj.a("Push-DiscntStats", str);
    }
}
