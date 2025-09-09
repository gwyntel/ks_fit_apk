package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.io.InputStream;
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
public class SecureSSLSocketFactoryNew extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f18408i = "SSLFNew";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureSSLSocketFactoryNew f18409j;

    /* renamed from: a, reason: collision with root package name */
    protected SSLContext f18410a;

    /* renamed from: b, reason: collision with root package name */
    protected SSLSocket f18411b;

    /* renamed from: c, reason: collision with root package name */
    protected Context f18412c;

    /* renamed from: d, reason: collision with root package name */
    protected String[] f18413d;

    /* renamed from: e, reason: collision with root package name */
    protected X509TrustManager f18414e;

    /* renamed from: f, reason: collision with root package name */
    protected String[] f18415f;

    /* renamed from: g, reason: collision with root package name */
    protected String[] f18416g;

    /* renamed from: h, reason: collision with root package name */
    protected String[] f18417h;

    @Deprecated
    public SecureSSLSocketFactoryNew(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f18410a = null;
        this.f18411b = null;
        this.f18410a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18410a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18417h)) {
            z2 = false;
        } else {
            e.c(f18408i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f18417h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18416g) && com.huawei.secure.android.common.ssl.util.a.a(this.f18415f)) {
            z3 = false;
        } else {
            e.c(f18408i, "set cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (com.huawei.secure.android.common.ssl.util.a.a(this.f18416g)) {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f18415f);
            } else {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f18416g);
            }
        }
        if (!z2) {
            e.c(f18408i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        e.c(f18408i, "set default cipher");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    @Deprecated
    public static SecureSSLSocketFactoryNew getInstance(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContextUtil.setContext(context);
        if (f18409j == null) {
            synchronized (SecureSSLSocketFactoryNew.class) {
                try {
                    if (f18409j == null) {
                        f18409j = new SecureSSLSocketFactoryNew(context, (SecureRandom) null);
                    }
                } finally {
                }
            }
        }
        if (f18409j.f18412c == null && context != null) {
            f18409j.setContext(context);
        }
        e.a(f18408i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f18409j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        e.c(f18408i, "createSocket: host , port");
        Socket socketCreateSocket = this.f18410a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18411b = sSLSocket;
            this.f18413d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f18415f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f18414e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f18412c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f18417h;
    }

    public SSLContext getSslContext() {
        return this.f18410a;
    }

    public SSLSocket getSslSocket() {
        return this.f18411b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f18413d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f18416g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f18414e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f18415f = strArr;
    }

    public void setContext(Context context) {
        this.f18412c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f18417h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f18410a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f18416g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f18414e = x509TrustManager;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    public SecureSSLSocketFactoryNew(InputStream inputStream, String str, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f18410a = null;
        this.f18411b = null;
        this.f18410a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18410a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, secureRandom);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return createSocket(str, i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        e.c(f18408i, "createSocket");
        Socket socketCreateSocket = this.f18410a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18411b = sSLSocket;
            this.f18413d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public static SecureSSLSocketFactoryNew getInstance(Context context, SecureRandom secureRandom) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContextUtil.setContext(context);
        if (f18409j == null) {
            synchronized (SecureSSLSocketFactoryNew.class) {
                try {
                    if (f18409j == null) {
                        f18409j = new SecureSSLSocketFactoryNew(context, secureRandom);
                    }
                } finally {
                }
            }
        }
        if (f18409j.f18412c == null && context != null) {
            f18409j.setContext(context);
        }
        e.a(f18408i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f18409j;
    }

    private SecureSSLSocketFactoryNew(Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f18410a = null;
        this.f18411b = null;
        if (context == null) {
            e.b(f18408i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f18414e = secureX509SingleInstance;
        this.f18410a.init(null, new X509TrustManager[]{secureX509SingleInstance}, secureRandom);
    }

    @Deprecated
    public SecureSSLSocketFactoryNew(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18410a = null;
        this.f18411b = null;
        this.f18410a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18410a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    public SecureSSLSocketFactoryNew(X509TrustManager x509TrustManager, SecureRandom secureRandom) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18410a = null;
        this.f18411b = null;
        this.f18410a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18410a.init(null, new X509TrustManager[]{x509TrustManager}, secureRandom);
    }
}
