package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    private static SharedPreferences f8917a = null;

    /* renamed from: a, reason: collision with other field name */
    private static a f39a = a.ENABLE;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f8918d = false;

    /* renamed from: e, reason: collision with root package name */
    private static long f8919e = 0;

    /* renamed from: g, reason: collision with root package name */
    private static volatile int f8920g = 0;

    /* renamed from: h, reason: collision with root package name */
    private static volatile int f8921h = 0;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f8922l = false;

    enum a {
        ENABLE,
        PRE_DISABLE,
        DISABLE
    }

    static synchronized String a(s sVar) {
        try {
            s sVar2 = s.QUERY_HOST;
            if (sVar != sVar2 && sVar != s.SNIFF_HOST) {
                return (sVar == s.QUERY_SCHEDULE_CENTER || sVar == s.SNIFF_SCHEDULE_CENTER) ? null : null;
            }
            if (f39a == a.ENABLE || f39a == a.PRE_DISABLE) {
                return f.f19a[f8920g];
            }
            if (sVar == sVar2) {
                return null;
            }
            return f.f19a[f8920g];
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static void b(int i2) {
        if (f8917a == null || i2 < 0 || i2 >= f.f19a.length) {
            return;
        }
        f8920g = i2;
        SharedPreferences.Editor editorEdit = f8917a.edit();
        editorEdit.putInt("activiate_ip_index", i2);
        editorEdit.putLong("activiated_ip_index_modified_time", System.currentTimeMillis());
        editorEdit.commit();
    }

    static synchronized void d(boolean z2) {
        if (f8922l != z2) {
            f8922l = z2;
            SharedPreferences sharedPreferences = f8917a;
            if (sharedPreferences != null) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putBoolean("status", f8922l);
                editorEdit.putLong("disable_modified_time", System.currentTimeMillis());
                editorEdit.commit();
            }
        }
    }

    static synchronized boolean e() {
        return f8922l;
    }

    private static void h() {
        f8920g = f8920g == f.f19a.length + (-1) ? 0 : f8920g + 1;
        b(f8920g);
    }

    static void i() {
        b(0);
        f8921h = f8920g;
        t.a().c(true);
    }

    static void j() {
        t.a().c(true);
    }

    public static void reportHttpDnsSuccess(String str, int i2) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.a(str, i2, com.alibaba.sdk.android.httpdns.d.c.a(), com.alibaba.sdk.android.httpdns.b.b.m28a() ? 1 : 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static synchronized void a(Context context) {
        if (!f8918d) {
            synchronized (u.class) {
                try {
                    if (!f8918d) {
                        if (context != null) {
                            f8917a = context.getSharedPreferences("httpdns_config_cache", 0);
                        }
                        f8922l = f8917a.getBoolean("status", false);
                        f8920g = f8917a.getInt("activiate_ip_index", 0);
                        f8921h = f8920g;
                        f8919e = f8917a.getLong("disable_modified_time", 0L);
                        if (System.currentTimeMillis() - f8919e >= 86400000) {
                            d(false);
                        }
                        f39a = f8922l ? a.DISABLE : a.ENABLE;
                        f8918d = true;
                    }
                } finally {
                }
            }
        }
    }

    private static void b(String str, String str2, long j2) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.b(str2, j2, com.alibaba.sdk.android.httpdns.d.c.a());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void h(String str) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                String strM46d = n.a().m46d();
                int length = f8920g;
                if (length == 0) {
                    length = f.f19a.length;
                }
                int i2 = length - 1;
                int length2 = i2 == 0 ? f.f19a.length - 1 : i2 - 1;
                if (i2 >= 0) {
                    String[] strArr = f.f19a;
                    if (i2 >= strArr.length || length2 < 0 || length2 >= strArr.length) {
                        return;
                    }
                    String str2 = strArr[i2];
                    bVarA.b(str, strM46d, strArr[length2] + "," + str2);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static synchronized void a(String str, String str2, long j2) {
        try {
            try {
                b(str, str2, j2);
                reportHttpDnsSuccess(str, 1);
                a aVar = f39a;
                a aVar2 = a.ENABLE;
                if (aVar != aVar2 && str2 != null && str2.equals(f.f19a[f8920g])) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(f39a == a.DISABLE ? "Disable " : "Pre_disable ");
                    sb.append("mode finished. Enter enable mode.");
                    i.f(sb.toString());
                    f39a = aVar2;
                    d(false);
                    t.a().g();
                    f8921h = f8920g;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    static synchronized void a(String str, String str2, Throwable th) {
        try {
            try {
                a(str2, th);
                if (a(th) && str2 != null && str2.equals(f.f19a[f8920g])) {
                    h();
                    if (f8921h == f8920g) {
                        t.a().c(false);
                        n.a().c();
                    }
                    if (f39a == a.ENABLE) {
                        f39a = a.PRE_DISABLE;
                        i.f("enter pre_disable mode");
                    } else if (f39a == a.PRE_DISABLE) {
                        f39a = a.DISABLE;
                        i.f("enter disable mode");
                        d(true);
                        h(str);
                        t.a().g(str);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    private static void a(String str, Throwable th) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                int iA = com.alibaba.sdk.android.httpdns.d.c.a(th);
                bVarA.a(str, String.valueOf(iA), com.alibaba.sdk.android.httpdns.d.c.m39a(th), com.alibaba.sdk.android.httpdns.d.c.a(), com.alibaba.sdk.android.httpdns.net64.a.a().i() ? 1 : 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static boolean a(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            return true;
        }
        if (th instanceof h) {
            h hVar = (h) th;
            if (hVar.getErrorCode() == 403 && hVar.getMessage().equals("ServiceLevelDeny")) {
                return true;
            }
        }
        return false;
    }
}
