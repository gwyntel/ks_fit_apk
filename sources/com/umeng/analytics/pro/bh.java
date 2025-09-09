package com.umeng.analytics.pro;

import android.text.TextUtils;
import com.umeng.analytics.pro.bj;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.net.URL;

/* loaded from: classes4.dex */
public class bh {

    /* renamed from: a, reason: collision with root package name */
    public static final String f21493a = "resolve.umeng.com";

    /* renamed from: b, reason: collision with root package name */
    public static final int f21494b = 15000;

    /* renamed from: c, reason: collision with root package name */
    private static bl f21495c = null;

    /* renamed from: d, reason: collision with root package name */
    private static volatile int f21496d = -1;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final bh f21497a = new bh();

        private a() {
        }
    }

    public static bh a() {
        return a.f21497a;
    }

    private String c() {
        if (f21495c == null) {
            f21495c = bl.b();
        }
        bj bjVar = new bj("https://resolve.umeng.com/resolve", bj.a.GET, null, f21495c);
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 发送domain下发请求。");
        return bjVar.a(15000, "");
    }

    public synchronized boolean b() {
        try {
            if (f21496d < 0) {
                String strImprintProperty = UMEnvelopeBuild.imprintProperty(UMGlobalContext.getAppContext(), "cj_domain", "0");
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> cj_domain读取值：" + strImprintProperty);
                if ("1".equalsIgnoreCase(strImprintProperty)) {
                    f21496d = 1;
                } else {
                    f21496d = 0;
                }
            }
            return f21496d <= 0;
        } catch (Throwable th) {
            throw th;
        }
    }

    private bh() {
    }

    public synchronized String a(String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        c();
        String strC = bl.b().c();
        if (!TextUtils.isEmpty(strC)) {
            str2 = "https://" + strC + "/" + str;
        }
        return str2;
    }

    private static String c(String str) {
        try {
            return new URL(str).getHost();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            return "https://" + str + "/" + str2;
        }
        return "";
    }

    public static String b(String str) {
        try {
            String strC = c(str);
            return str.substring(str.indexOf(strC) + strC.length() + 1);
        } catch (Throwable unused) {
            return "";
        }
    }
}
