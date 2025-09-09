package com.alipay.sdk.m.m;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.sdk.m.u.c;
import com.alipay.sdk.m.u.i;
import com.alipay.sdk.m.u.n;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: d, reason: collision with root package name */
    public static final String f9546d = "virtualImeiAndImsi";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9547e = "virtual_imei";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9548f = "virtual_imsi";

    /* renamed from: g, reason: collision with root package name */
    public static volatile b f9549g;

    /* renamed from: a, reason: collision with root package name */
    public String f9550a;

    /* renamed from: b, reason: collision with root package name */
    public String f9551b = "sdk-and-lite";

    /* renamed from: c, reason: collision with root package name */
    public String f9552c;

    public b() {
        String strA = com.alipay.sdk.m.j.a.a();
        if (com.alipay.sdk.m.j.a.b()) {
            return;
        }
        this.f9551b += '_' + strA;
    }

    public static synchronized b b() {
        try {
            if (f9549g == null) {
                f9549g = new b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f9549g;
    }

    public static String c() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
    }

    public static String d() {
        return "-1;-1";
    }

    public static String e() {
        return "1";
    }

    public static String f() {
        Context contextB = com.alipay.sdk.m.s.b.d().b();
        SharedPreferences sharedPreferences = contextB.getSharedPreferences(f9546d, 0);
        String string = sharedPreferences.getString(f9547e, null);
        if (TextUtils.isEmpty(string)) {
            string = TextUtils.isEmpty(com.alipay.sdk.m.t.a.a(contextB).d()) ? c() : c.b(contextB).b();
            sharedPreferences.edit().putString(f9547e, string).apply();
        }
        return string;
    }

    public static String g() {
        String strC;
        Context contextB = com.alipay.sdk.m.s.b.d().b();
        SharedPreferences sharedPreferences = contextB.getSharedPreferences(f9546d, 0);
        String string = sharedPreferences.getString(f9548f, null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.m.t.a.a(contextB).d())) {
                String strC2 = com.alipay.sdk.m.s.b.d().c();
                strC = (TextUtils.isEmpty(strC2) || strC2.length() < 18) ? c() : strC2.substring(3, 18);
            } else {
                strC = c.b(contextB).c();
            }
            string = strC;
            sharedPreferences.edit().putString(f9548f, string).apply();
        }
        return string;
    }

    public String a() {
        return this.f9552c;
    }

    public static synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(com.alipay.sdk.m.s.b.d().b()).edit().putString(com.alipay.sdk.m.l.b.f9442i, str).apply();
        com.alipay.sdk.m.l.a.f9412e = str;
    }

    public static String b(com.alipay.sdk.m.s.a aVar, Context context, boolean z2) {
        if (z2) {
            return "-1";
        }
        try {
            WifiInfo wifiInfoD = com.alipay.sdk.m.w.b.d(aVar, context);
            return wifiInfoD != null ? wifiInfoD.getSSID() : "-1";
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "lacking_per_1", th);
            return "-1";
        }
    }

    public static String a(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    public static String b(Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            sb.append("(");
            sb.append(packageName);
            sb.append(i.f9802b);
            sb.append(packageInfo.versionCode);
            sb.append(")");
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public String a(com.alipay.sdk.m.s.a aVar, com.alipay.sdk.m.t.a aVar2, boolean z2) throws IOException {
        Context contextB = com.alipay.sdk.m.s.b.d().b();
        c cVarB = c.b(contextB);
        if (TextUtils.isEmpty(this.f9550a)) {
            this.f9550a = "Msp/15.8.10 (" + n.f() + i.f9802b + n.e() + i.f9802b + n.c(contextB) + i.f9802b + n.e(contextB) + i.f9802b + n.f(contextB) + i.f9802b + a(contextB);
        }
        String strB = c.d(contextB).b();
        String strB2 = n.b(contextB);
        String strE = e();
        String strC = cVarB.c();
        String strB3 = cVarB.b();
        String strG = g();
        String strF = f();
        if (aVar2 != null) {
            this.f9552c = aVar2.c();
        }
        String strReplace = Build.MANUFACTURER.replace(i.f9802b, " ");
        String strReplace2 = Build.MODEL.replace(i.f9802b, " ");
        boolean zE = com.alipay.sdk.m.s.b.e();
        String strD = cVarB.d();
        String strB4 = b(aVar, contextB, z2);
        String strA = a(aVar, contextB, z2);
        StringBuilder sb = new StringBuilder();
        sb.append(this.f9550a);
        sb.append(i.f9802b);
        sb.append(strB);
        sb.append(i.f9802b);
        sb.append(strB2);
        sb.append(i.f9802b);
        sb.append(strE);
        sb.append(i.f9802b);
        sb.append(strC);
        sb.append(i.f9802b);
        sb.append(strB3);
        sb.append(i.f9802b);
        sb.append(this.f9552c);
        sb.append(i.f9802b);
        sb.append(strReplace);
        sb.append(i.f9802b);
        sb.append(strReplace2);
        sb.append(i.f9802b);
        sb.append(zE);
        sb.append(i.f9802b);
        sb.append(strD);
        sb.append(i.f9802b);
        sb.append(d());
        sb.append(i.f9802b);
        sb.append(this.f9551b);
        sb.append(i.f9802b);
        sb.append(strG);
        sb.append(i.f9802b);
        sb.append(strF);
        sb.append(i.f9802b);
        sb.append(strB4);
        sb.append(i.f9802b);
        sb.append(strA);
        if (aVar2 != null) {
            String strA2 = com.alipay.sdk.m.w.b.a(aVar, contextB, com.alipay.sdk.m.t.a.a(contextB).d(), com.alipay.sdk.m.w.b.c(aVar, contextB));
            if (!TextUtils.isEmpty(strA2)) {
                sb.append(";;;");
                sb.append(strA2);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String a(com.alipay.sdk.m.s.a aVar, Context context, boolean z2) {
        if (z2) {
            return "00";
        }
        try {
            WifiInfo wifiInfoD = com.alipay.sdk.m.w.b.d(aVar, context);
            return wifiInfoD != null ? wifiInfoD.getBSSID() : "00";
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, "lacking_per_2", th);
            return "00";
        }
    }
}
