package com.umeng.ut.a.b;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
class d implements HostnameVerifier {

    /* renamed from: a, reason: collision with root package name */
    public String f22988a;

    public d(String str) {
        this.f22988a = str;
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f22988a) || !(obj instanceof d)) {
            return false;
        }
        String str = ((d) obj).f22988a;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f22988a.equals(str);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f22988a, sSLSession);
    }
}
