package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class f {

    /* renamed from: c, reason: collision with root package name */
    static String f8854c;

    /* renamed from: a, reason: collision with other field name */
    static String[] f19a = {"203.107.1.1"};

    /* renamed from: b, reason: collision with root package name */
    static final String[] f8853b = {"203.107.1.97", "203.107.1.100", "httpdns-sc.aliyuncs.com"};

    /* renamed from: c, reason: collision with other field name */
    static final String[] f20c = new String[0];

    /* renamed from: d, reason: collision with root package name */
    static String f8855d = "80";
    static String PROTOCOL = "http://";

    /* renamed from: a, reason: collision with root package name */
    static int f8852a = 15000;
    static Map<String, String> extra = new HashMap();

    /* renamed from: a, reason: collision with other field name */
    static List<IPProbeItem> f18a = null;

    static synchronized void a(List<IPProbeItem> list) {
        f18a = list;
    }

    static synchronized void c(String str) {
        f8854c = str;
    }

    static synchronized void clearSdnsGlobalParams() {
        extra.clear();
    }

    static synchronized void setHTTPSRequestEnabled(boolean z2) {
        String str;
        try {
            if (z2) {
                PROTOCOL = "https://";
                str = "443";
            } else {
                PROTOCOL = "http://";
                str = "80";
            }
            f8855d = str;
        } catch (Throwable th) {
            throw th;
        }
    }

    static synchronized void setSdnsGlobalParams(Map<String, String> map) {
        extra.putAll(map);
    }

    static synchronized void setTimeoutInterval(int i2) {
        if (i2 > 0) {
            f8852a = i2;
        }
    }

    static synchronized boolean a(String[] strArr) {
        if (strArr != null) {
            if (strArr.length != 0) {
                f19a = strArr;
                i.d("serverIps:" + Arrays.toString(f19a));
                return true;
            }
        }
        return false;
    }
}
