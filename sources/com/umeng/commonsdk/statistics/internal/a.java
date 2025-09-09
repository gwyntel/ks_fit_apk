package com.umeng.commonsdk.statistics.internal;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.ContainerUtils;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Context f22413a;

    /* renamed from: b, reason: collision with root package name */
    private String f22414b;

    /* renamed from: c, reason: collision with root package name */
    private String f22415c;

    /* renamed from: com.umeng.commonsdk.statistics.internal.a$a, reason: collision with other inner class name */
    private static class C0179a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f22416a = new a();

        private C0179a() {
        }
    }

    public static a a(Context context) {
        if (f22413a == null && context != null) {
            f22413a = context.getApplicationContext();
        }
        return C0179a.f22416a;
    }

    private void f(String str) {
        try {
            this.f22414b = str.replaceAll("&=", " ").replaceAll("&&", " ").replaceAll("==", "/") + "/" + AnalyticsConstants.SDK_TYPE + " " + HelperUtils.getUmengMD5(UMUtils.getAppkey(f22413a));
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f22413a, th);
        }
    }

    private void g(String str) {
        try {
            String str2 = str.split("&&")[0];
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            String[] strArrSplit = str2.split("&=");
            StringBuilder sb = new StringBuilder();
            sb.append(bc.aW);
            for (String str3 : strArrSplit) {
                if (!TextUtils.isEmpty(str3)) {
                    String strSubstring = str3.substring(0, 2);
                    if (strSubstring.endsWith(ContainerUtils.KEY_VALUE_DELIMITER)) {
                        strSubstring = strSubstring.replace(ContainerUtils.KEY_VALUE_DELIMITER, "");
                    }
                    sb.append(strSubstring);
                }
            }
            this.f22415c = sb.toString();
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f22413a, th);
        }
    }

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("t");
    }

    public boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(bc.aJ);
    }

    public boolean d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("h");
    }

    public void e(String str) {
        String strSubstring = str.substring(0, str.indexOf(95));
        g(strSubstring);
        f(strSubstring);
    }

    private a() {
        this.f22414b = null;
        this.f22415c = null;
    }

    public String b() {
        return this.f22414b;
    }

    public boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("a");
    }

    public String a() {
        return this.f22415c;
    }
}
