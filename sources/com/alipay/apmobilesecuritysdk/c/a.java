package com.alipay.apmobilesecuritysdk.c;

import android.content.Context;
import android.os.Build;
import com.alipay.sdk.m.c0.d;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes2.dex */
public final class a {
    public static synchronized void a(Context context, String str, String str2, String str3) {
        com.alipay.sdk.m.c0.a aVarB = b(context, str, str2, str3);
        d.a(context.getFilesDir().getAbsolutePath() + "/log/ap", new SimpleDateFormat(CalendarUtils.yyyyMMdd).format(Calendar.getInstance().getTime()) + ".log", aVarB.toString());
    }

    public static com.alipay.sdk.m.c0.a b(Context context, String str, String str2, String str3) {
        String packageName;
        try {
            packageName = context.getPackageName();
        } catch (Throwable unused) {
            packageName = "";
        }
        return new com.alipay.sdk.m.c0.a(Build.MODEL, packageName, "APPSecuritySDK-ALIPAYSDK", "3.4.0.202203211140", str, str2, str3);
    }

    public static synchronized void a(String str) {
        d.a(str);
    }

    public static synchronized void a(Throwable th) {
        d.a(th);
    }
}
