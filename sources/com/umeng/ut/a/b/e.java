package com.umeng.ut.a.b;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import android.text.TextUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
class e extends SSLSocketFactory {

    /* renamed from: b, reason: collision with root package name */
    private String f22990b;

    /* renamed from: a, reason: collision with root package name */
    private Method f22989a = null;
    private HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public e(String str) {
        this.f22990b = str;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        return null;
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f22990b) || !(obj instanceof e)) {
            return false;
        }
        String str = ((e) obj).f22990b;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f22990b.equals(str);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return null;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        com.umeng.ut.a.c.e.b("", "peerHost", this.f22990b, "host", str, "port", Integer.valueOf(i2), "autoClose", Boolean.valueOf(z2));
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        if (Build.VERSION.SDK_INT < 24) {
            sSLCertificateSocketFactory.setTrustManagers(f.getTrustManagers());
        } else {
            sSLCertificateSocketFactory.setTrustManagers(c.getTrustManagers());
        }
        com.umeng.ut.a.c.e.m85a("", "createSocket");
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(socket, this.f22990b, i2, true);
        com.umeng.ut.a.c.e.m85a("", "createSocket end");
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        sSLCertificateSocketFactory.setHostname(sSLSocket, this.f22990b);
        SSLSession session = sSLSocket.getSession();
        if (this.hostnameVerifier.verify(this.f22990b, session)) {
            com.umeng.ut.a.c.e.b("", "SSLSession PeerHost", session.getPeerHost());
            return sSLSocket;
        }
        throw new SSLPeerUnverifiedException("Cannot verify hostname: " + this.f22990b);
    }
}
