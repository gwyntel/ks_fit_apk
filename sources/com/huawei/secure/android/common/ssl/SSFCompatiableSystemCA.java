package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class SSFCompatiableSystemCA extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f18368i = "SSFCompatiableSystemCA";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SSFCompatiableSystemCA f18369j;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f18370a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f18371b;

    /* renamed from: c, reason: collision with root package name */
    private Context f18372c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f18373d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f18374e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f18375f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f18376g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f18377h;

    private SSFCompatiableSystemCA(Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f18370a = null;
        this.f18371b = null;
        if (context == null) {
            e.b(f18368i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager sSFSecureX509SingleInstance = SSFSecureX509SingleInstance.getInstance(context);
        this.f18374e = sSFSecureX509SingleInstance;
        this.f18370a.init(null, new X509TrustManager[]{sSFSecureX509SingleInstance}, secureRandom);
    }

    @Deprecated
    static void a(X509TrustManager x509TrustManager) {
        e.c(f18368i, "ssfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18369j = new SSFCompatiableSystemCA(x509TrustManager);
        } catch (KeyManagementException unused) {
            e.b(f18368i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            e.b(f18368i, "NoSuchAlgorithmException");
        }
        e.a(f18368i, "SSF system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Deprecated
    public static SSFCompatiableSystemCA getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18369j == null) {
            synchronized (SSFCompatiableSystemCA.class) {
                try {
                    if (f18369j == null) {
                        f18369j = new SSFCompatiableSystemCA(context, (SecureRandom) null);
                    }
                } finally {
                }
            }
        }
        if (f18369j.f18372c == null && context != null) {
            f18369j.setContext(context);
        }
        return f18369j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        e.c(f18368i, "createSocket: host , port");
        Socket socketCreateSocket = this.f18370a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18371b = sSLSocket;
            this.f18373d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f18375f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f18374e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f18372c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f18377h;
    }

    public SSLContext getSslContext() {
        return this.f18370a;
    }

    public SSLSocket getSslSocket() {
        return this.f18371b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f18373d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f18376g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f18374e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f18375f = strArr;
    }

    public void setContext(Context context) {
        this.f18372c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f18377h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f18370a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f18376g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f18374e = x509TrustManager;
    }

    static void a(X509TrustManager x509TrustManager, SecureRandom secureRandom) {
        e.c(f18368i, "ssfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18369j = new SSFCompatiableSystemCA(x509TrustManager, secureRandom);
        } catch (KeyManagementException unused) {
            e.b(f18368i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            e.b(f18368i, "NoSuchAlgorithmException");
        }
        e.a(f18368i, "SSF system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return createSocket(str, i2);
    }

    @Deprecated
    public SSFCompatiableSystemCA(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18370a = null;
        this.f18371b = null;
        this.f18370a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18370a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    public static SSFCompatiableSystemCA getInstance(Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18369j == null) {
            synchronized (SSFCompatiableSystemCA.class) {
                try {
                    if (f18369j == null) {
                        f18369j = new SSFCompatiableSystemCA(context, secureRandom);
                    }
                } finally {
                }
            }
        }
        if (f18369j.f18372c == null && context != null) {
            f18369j.setContext(context);
        }
        return f18369j;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        e.c(f18368i, "createSocket: s , host , port , autoClose");
        Socket socketCreateSocket = this.f18370a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18371b = sSLSocket;
            this.f18373d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18377h)) {
            z2 = false;
        } else {
            e.c(f18368i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f18377h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18376g) && com.huawei.secure.android.common.ssl.util.a.a(this.f18375f)) {
            z3 = false;
        } else {
            e.c(f18368i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f18376g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f18376g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f18375f);
            }
        }
        if (!z2) {
            e.c(f18368i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        e.c(f18368i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    public SSFCompatiableSystemCA(X509TrustManager x509TrustManager, SecureRandom secureRandom) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18370a = null;
        this.f18371b = null;
        this.f18370a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18370a.init(null, new X509TrustManager[]{x509TrustManager}, secureRandom);
    }
}
