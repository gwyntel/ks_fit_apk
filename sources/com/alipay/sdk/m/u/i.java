package com.alipay.sdk.m.u;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9801a = "pref_trade_token";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9802b = ";";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9803c = "result={";

    /* renamed from: d, reason: collision with root package name */
    public static final String f9804d = "}";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9805e = "trade_token=\"";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9806f = "\"";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9807g = "trade_token=";

    public static void a(com.alipay.sdk.m.s.a aVar, Context context, String str) {
        try {
            String strA = a(str);
            e.b(com.alipay.sdk.m.l.a.f9433z, "trade token: " + strA);
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            j.b(aVar, context, f9801a, strA);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.I, th);
            e.a(th);
        }
    }

    public static String a(String str) {
        String strSubstring = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(f9802b);
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (strArrSplit[i2].startsWith(f9803c) && strArrSplit[i2].endsWith(f9804d)) {
                String[] strArrSplit2 = strArrSplit[i2].substring(8, r3.length() - 1).split("&");
                int i3 = 0;
                while (true) {
                    if (i3 >= strArrSplit2.length) {
                        break;
                    }
                    if (strArrSplit2[i3].startsWith(f9805e) && strArrSplit2[i3].endsWith("\"")) {
                        strSubstring = strArrSplit2[i3].substring(13, r1.length() - 1);
                        break;
                    }
                    if (strArrSplit2[i3].startsWith(f9807g)) {
                        strSubstring = strArrSplit2[i3].substring(12);
                        break;
                    }
                    i3++;
                }
            }
        }
        return strSubstring;
    }

    public static String a(com.alipay.sdk.m.s.a aVar, Context context) {
        String strA = j.a(aVar, context, f9801a, "");
        e.b(com.alipay.sdk.m.l.a.f9433z, "get trade token: " + strA);
        return strA;
    }
}
