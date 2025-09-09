package com.umeng.commonsdk.config;

import android.content.Context;
import android.util.Pair;
import com.umeng.commonsdk.config.d;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FieldManager {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22106a = "cfgfd";

    /* renamed from: b, reason: collision with root package name */
    private static b f22107b = b.b();

    /* renamed from: c, reason: collision with root package name */
    private static boolean f22108c = false;

    /* renamed from: d, reason: collision with root package name */
    private static Object f22109d = new Object();

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final FieldManager f22110a = new FieldManager();

        private a() {
        }
    }

    public static FieldManager a() {
        return a.f22110a;
    }

    public static boolean allow(String str) {
        synchronized (f22109d) {
            try {
                if (!f22108c) {
                    return false;
                }
                return b.a(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean b() {
        boolean z2;
        synchronized (f22109d) {
            z2 = f22108c;
        }
        return z2;
    }

    private FieldManager() {
    }

    public void a(Context context) {
        String str;
        String str2 = "1001@3749699455,2130669566,262139,1983";
        String[] strArr = {d.a.class.getName(), d.b.class.getName(), d.c.class.getName(), d.EnumC0177d.class.getName()};
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, "cfgfd", "1001@3749699455,2130669566,262139,1983");
        synchronized (f22109d) {
            try {
                Pair<Long, String> pairA = a(strImprintProperty);
                if (((Long) pairA.first).longValue() > 1000 && (str = (String) pairA.second) != null && str.length() > 0) {
                    str2 = str;
                }
                String[] strArrSplit = str2.split(",");
                int length = strArrSplit.length;
                if (length > 0) {
                    ArrayList arrayList = new ArrayList();
                    g gVar = new g();
                    for (int i2 = 0; i2 < length; i2++) {
                        arrayList.add(gVar);
                        ((e) arrayList.get(i2)).a(strArrSplit[i2], f22107b, d.b(strArr[i2]));
                    }
                }
                f22108c = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(Context context, String str) {
        String str2;
        String str3 = "1001@3749699455,2130669566,262139,1983";
        String[] strArr = {d.a.class.getName(), d.b.class.getName(), d.c.class.getName(), d.EnumC0177d.class.getName()};
        synchronized (f22109d) {
            try {
                f22107b.a();
                if (str != null) {
                    Pair<Long, String> pairA = a(str);
                    if (((Long) pairA.first).longValue() > 1000 && (str2 = (String) pairA.second) != null && str2.length() > 0) {
                        str3 = str2;
                    }
                }
                String[] strArrSplit = str3.split(",");
                int length = strArrSplit.length;
                if (length > 0) {
                    ArrayList arrayList = new ArrayList();
                    g gVar = new g();
                    for (int i2 = 0; i2 < length; i2++) {
                        arrayList.add(gVar);
                        ((e) arrayList.get(i2)).a(strArrSplit[i2], f22107b, d.b(strArr[i2]));
                    }
                }
                f22108c = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Pair<Long, String> a(String str) {
        Pair<Long, String> pair = new Pair<>(-1L, null);
        if (str != null && str.length() >= 2) {
            String[] strArrSplit = str.split("@");
            if (strArrSplit.length < 2) {
                return pair;
            }
            try {
                long j2 = Long.parseLong(strArrSplit[0]);
                return new Pair<>(Long.valueOf(j2), strArrSplit[1]);
            } catch (Throwable unused) {
            }
        }
        return pair;
    }
}
