package com.ta.a.b;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
class d implements HostnameVerifier {

    /* renamed from: b, reason: collision with root package name */
    public String f20010b;

    public d(String str) {
        this.f20010b = str;
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f20010b) || !(obj instanceof d)) {
            return false;
        }
        String str = ((d) obj).f20010b;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f20010b.equals(str);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f20010b, sSLSession);
    }
}
