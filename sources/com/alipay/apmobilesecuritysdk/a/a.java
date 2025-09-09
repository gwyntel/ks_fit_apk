package com.alipay.apmobilesecuritysdk.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.alipay.apmobilesecuritysdk.d.e;
import com.alipay.apmobilesecuritysdk.e.b;
import com.alipay.apmobilesecuritysdk.e.g;
import com.alipay.apmobilesecuritysdk.e.h;
import com.alipay.apmobilesecuritysdk.e.i;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.sdk.m.f0.c;
import com.alipay.sdk.m.f0.d;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public Context f9055a;

    /* renamed from: b, reason: collision with root package name */
    public com.alipay.apmobilesecuritysdk.b.a f9056b = com.alipay.apmobilesecuritysdk.b.a.a();

    /* renamed from: c, reason: collision with root package name */
    public int f9057c = 4;

    public a(Context context) {
        this.f9055a = context;
    }

    private c b(Map<String, String> map) {
        String str;
        String str2;
        String str3;
        b bVarB;
        b bVarC;
        String str4 = "";
        try {
            Context context = this.f9055a;
            d dVar = new d();
            String strA = com.alipay.sdk.m.z.a.a(map, "appName", "");
            String strA2 = com.alipay.sdk.m.z.a.a(map, "sessionId", "");
            String strA3 = com.alipay.sdk.m.z.a.a(map, "rpcVersion", "");
            String strA4 = a(context, strA);
            String securityToken = UmidSdkWrapper.getSecurityToken(context);
            String strD = h.d(context);
            if (com.alipay.sdk.m.z.a.b(strA2)) {
                dVar.f9255c = strA2;
            } else {
                dVar.f9255c = strA4;
            }
            dVar.f9256d = securityToken;
            dVar.f9257e = strD;
            dVar.f9253a = "android";
            com.alipay.apmobilesecuritysdk.e.c cVarC = com.alipay.apmobilesecuritysdk.e.d.c(context);
            if (cVarC != null) {
                str2 = cVarC.f9065a;
                str = cVarC.f9067c;
            } else {
                str = "";
                str2 = str;
            }
            if (com.alipay.sdk.m.z.a.a(str2) && (bVarC = com.alipay.apmobilesecuritysdk.e.a.c(context)) != null) {
                str2 = bVarC.f9062a;
                str = bVarC.f9064c;
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b();
            if (cVarB != null) {
                str4 = cVarB.f9065a;
                str3 = cVarB.f9067c;
            } else {
                str3 = "";
            }
            if (com.alipay.sdk.m.z.a.a(str4) && (bVarB = com.alipay.apmobilesecuritysdk.e.a.b()) != null) {
                str4 = bVarB.f9062a;
                str3 = bVarB.f9064c;
            }
            dVar.f9260h = str2;
            dVar.f9259g = str4;
            dVar.f9262j = strA3;
            if (com.alipay.sdk.m.z.a.a(str2)) {
                dVar.f9254b = str4;
                str = str3;
            } else {
                dVar.f9254b = str2;
            }
            dVar.f9261i = str;
            dVar.f9258f = e.a(context, map);
            return com.alipay.sdk.m.d0.d.b(this.f9055a, this.f9056b.c()).a(dVar);
        } catch (Throwable th) {
            th.printStackTrace();
            com.alipay.apmobilesecuritysdk.c.a.a(th);
            return null;
        }
    }

    public final int a(Map<String, String> map) {
        boolean z2;
        String str;
        try {
            com.alipay.apmobilesecuritysdk.c.a.a(this.f9055a, com.alipay.sdk.m.z.a.a(map, "tid", ""), com.alipay.sdk.m.z.a.a(map, "utdid", ""), a(this.f9055a));
            String strA = com.alipay.sdk.m.z.a.a(map, "appName", "");
            b();
            b(this.f9055a);
            a(this.f9055a, strA);
            i.a();
            int i2 = 0;
            if (a() || com.alipay.apmobilesecuritysdk.common.a.a(this.f9055a)) {
                z2 = com.alipay.sdk.m.z.a.a(a(this.f9055a, strA)) || com.alipay.sdk.m.z.a.a(b(this.f9055a));
            } else {
                e.a();
                if (!(!com.alipay.sdk.m.z.a.a(e.b(this.f9055a, map), i.c()))) {
                    String strA2 = com.alipay.sdk.m.z.a.a(map, "tid", "");
                    String strA3 = com.alipay.sdk.m.z.a.a(map, "utdid", "");
                    if ((com.alipay.sdk.m.z.a.b(strA2) && !com.alipay.sdk.m.z.a.a(strA2, i.d())) || ((com.alipay.sdk.m.z.a.b(strA3) && !com.alipay.sdk.m.z.a.a(strA3, i.e())) || !i.a(this.f9055a, strA) || com.alipay.sdk.m.z.a.a(a(this.f9055a, strA)) || com.alipay.sdk.m.z.a.a(b(this.f9055a)))) {
                    }
                }
            }
            Context context = this.f9055a;
            com.alipay.sdk.m.a0.b.b();
            h.b(context, String.valueOf(com.alipay.sdk.m.a0.b.n()));
            if (z2) {
                new com.alipay.apmobilesecuritysdk.c.b();
                UmidSdkWrapper.startUmidTaskSync(this.f9055a, com.alipay.apmobilesecuritysdk.b.a.a().b());
                c cVarB = b(map);
                int iC = cVarB != null ? cVarB.c() : 2;
                if (iC == 1) {
                    h.a(this.f9055a, cVarB.b());
                    h.d(this.f9055a, cVarB.a());
                    h.e(this.f9055a, cVarB.f9248g);
                    h.a(this.f9055a, cVarB.f9249h);
                    h.f(this.f9055a, cVarB.f9250i);
                    h.g(this.f9055a, cVarB.f9252k);
                    i.c(e.b(this.f9055a, map));
                    i.a(strA, cVarB.f9245d);
                    i.b(cVarB.f9244c);
                    i.d(cVarB.f9251j);
                    String strA4 = com.alipay.sdk.m.z.a.a(map, "tid", "");
                    if (!com.alipay.sdk.m.z.a.b(strA4) || com.alipay.sdk.m.z.a.a(strA4, i.d())) {
                        strA4 = i.d();
                    } else {
                        i.e(strA4);
                    }
                    i.e(strA4);
                    String strA5 = com.alipay.sdk.m.z.a.a(map, "utdid", "");
                    if (!com.alipay.sdk.m.z.a.b(strA5) || com.alipay.sdk.m.z.a.a(strA5, i.e())) {
                        strA5 = i.e();
                    } else {
                        i.f(strA5);
                    }
                    i.f(strA5);
                    i.a();
                    com.alipay.apmobilesecuritysdk.e.d.a(this.f9055a, i.g());
                    com.alipay.apmobilesecuritysdk.e.d.a();
                    com.alipay.apmobilesecuritysdk.e.a.a(this.f9055a, new b(i.b(), i.c(), i.f()));
                    com.alipay.apmobilesecuritysdk.e.a.a();
                    g.a(this.f9055a, strA, i.a(strA));
                    g.a();
                    h.a(this.f9055a, strA, System.currentTimeMillis());
                } else if (iC != 3) {
                    if (cVarB != null) {
                        str = "Server error, result:" + cVarB.f9238b;
                    } else {
                        str = "Server error, returned null";
                    }
                    com.alipay.apmobilesecuritysdk.c.a.a(str);
                    if (com.alipay.sdk.m.z.a.a(a(this.f9055a, strA))) {
                        i2 = 4;
                    }
                } else {
                    i2 = 1;
                }
            }
            this.f9057c = i2;
            com.alipay.sdk.m.g0.a aVarB = com.alipay.sdk.m.d0.d.b(this.f9055a, this.f9056b.c());
            Context context2 = this.f9055a;
            ConnectivityManager connectivityManager = (ConnectivityManager) context2.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1 && h.c(context2)) {
                new com.alipay.sdk.m.c0.b(context2.getFilesDir().getAbsolutePath() + "/log/ap", aVarB).a();
            }
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
        }
        return this.f9057c;
    }

    public static String a(Context context) {
        String strB = b(context);
        return com.alipay.sdk.m.z.a.a(strB) ? h.f(context) : strB;
    }

    public static String b(Context context) {
        try {
            String strB = i.b();
            if (!com.alipay.sdk.m.z.a.a(strB)) {
                return strB;
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b(context);
            if (cVarB != null) {
                i.a(cVarB);
                String str = cVarB.f9065a;
                if (com.alipay.sdk.m.z.a.b(str)) {
                    return str;
                }
            }
            b bVarB = com.alipay.apmobilesecuritysdk.e.a.b(context);
            if (bVarB == null) {
                return "";
            }
            i.a(bVarB);
            String str2 = bVarB.f9062a;
            return com.alipay.sdk.m.z.a.b(str2) ? str2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String a(Context context, String str) {
        try {
            b();
            String strA = i.a(str);
            if (!com.alipay.sdk.m.z.a.a(strA)) {
                return strA;
            }
            String strA2 = g.a(context, str);
            i.a(str, strA2);
            return !com.alipay.sdk.m.z.a.a(strA2) ? strA2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static void b() {
        try {
            String[] strArr = {"device_feature_file_name", "wallet_times", "wxcasxx_v3", "wxcasxx_v4", "wxxzyy_v1"};
            for (int i2 = 0; i2 < 5; i2++) {
                String str = strArr[i2];
                File file = new File(Environment.getExternalStorageDirectory(), ".SystemConfig/" + str);
                if (file.exists() && file.canWrite()) {
                    file.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean a() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);
        String[] strArr = {"2017-01-27 2017-01-28", "2017-11-10 2017-11-11", "2017-12-11 2017-12-12"};
        int iRandom = (int) (Math.random() * 24.0d * 60.0d * 60.0d);
        for (int i2 = 0; i2 < 3; i2++) {
            try {
                String[] strArrSplit = strArr[i2].split(" ");
                if (strArrSplit != null && strArrSplit.length == 2) {
                    Date date = new Date();
                    Date date2 = simpleDateFormat.parse(strArrSplit[0] + " 00:00:00");
                    Date date3 = simpleDateFormat.parse(strArrSplit[1] + " 23:59:59");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date3);
                    calendar.add(13, iRandom);
                    Date time = calendar.getTime();
                    if (date.after(date2) && date.before(time)) {
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
