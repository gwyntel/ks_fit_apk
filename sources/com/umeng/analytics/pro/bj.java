package com.umeng.analytics.pro;

import android.text.TextUtils;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public class bj {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21510a = "HttpClient";

    /* renamed from: f, reason: collision with root package name */
    private static HostnameVerifier f21511f;

    /* renamed from: b, reason: collision with root package name */
    private String f21512b;

    /* renamed from: c, reason: collision with root package name */
    private a f21513c;

    /* renamed from: d, reason: collision with root package name */
    private Map<String, String> f21514d;

    /* renamed from: e, reason: collision with root package name */
    private bk f21515e;

    public enum a {
        POST,
        GET
    }

    public bj(String str, a aVar, Map<String, String> map, bk bkVar) {
        this.f21512b = str;
        this.f21513c = aVar;
        this.f21514d = map;
        this.f21515e = bkVar;
    }

    private static HostnameVerifier a() {
        if (f21511f == null) {
            f21511f = new HostnameVerifier() { // from class: com.umeng.analytics.pro.bj.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str, SSLSession sSLSession) {
                    return !TextUtils.isEmpty(str) && bh.f21493a.equalsIgnoreCase(str);
                }
            };
        }
        return f21511f;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00ca A[PHI: r1
      0x00ca: PHI (r1v12 javax.net.ssl.HttpsURLConnection) = 
      (r1v6 javax.net.ssl.HttpsURLConnection)
      (r1v7 javax.net.ssl.HttpsURLConnection)
      (r1v8 javax.net.ssl.HttpsURLConnection)
      (r1v9 javax.net.ssl.HttpsURLConnection)
      (r1v10 javax.net.ssl.HttpsURLConnection)
      (r1v11 javax.net.ssl.HttpsURLConnection)
      (r1v15 javax.net.ssl.HttpsURLConnection)
     binds: [B:46:0x00da, B:48:0x00dd, B:50:0x00e0, B:52:0x00e3, B:54:0x00e6, B:56:0x00e9, B:22:0x009f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(int r6, java.lang.String r7) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.bj.a(int, java.lang.String):java.lang.String");
    }
}
