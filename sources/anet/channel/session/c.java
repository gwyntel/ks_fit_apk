package anet.channel.session;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes2.dex */
final class c implements HostnameVerifier {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f6911a;

    c(String str) {
        this.f6911a = str;
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f6911a, sSLSession);
    }
}
