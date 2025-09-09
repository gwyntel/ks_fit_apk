package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public class SASFCompatiableSystemCA extends SSLSocketFactory {

    /* renamed from: i, reason: collision with root package name */
    private static final String f18358i = "SASFCompatiableSystemCA";

    /* renamed from: j, reason: collision with root package name */
    private static volatile SASFCompatiableSystemCA f18359j;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f18360a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f18361b;

    /* renamed from: c, reason: collision with root package name */
    private Context f18362c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f18363d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f18364e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f18365f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f18366g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f18367h;

    private SASFCompatiableSystemCA(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(keyStore);
        this.f18361b = null;
    }

    @Deprecated
    static void a(X509TrustManager x509TrustManager) {
        e.c(f18358i, "sasfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18359j = new SASFCompatiableSystemCA(null, x509TrustManager);
        } catch (KeyManagementException unused) {
            e.b(f18358i, "KeyManagementException");
        } catch (KeyStoreException unused2) {
            e.b(f18358i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused3) {
            e.b(f18358i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused4) {
            e.b(f18358i, "UnrecoverableKeyException");
        }
        e.a(f18358i, "sasf system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Deprecated
    public static SASFCompatiableSystemCA getInstance(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18359j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                try {
                    if (f18359j == null) {
                        f18359j = new SASFCompatiableSystemCA(keyStore, context, (SecureRandom) null);
                    }
                } finally {
                }
            }
        }
        return f18359j;
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        e.c(f18358i, "createSocket: socket host port autoClose");
        Socket socketCreateSocket = this.f18360a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18361b = sSLSocket;
            this.f18363d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f18365f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f18364e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f18362c;
    }

    public String[] getProtocols() {
        return this.f18367h;
    }

    public SSLContext getSslContext() {
        return this.f18360a;
    }

    public SSLSocket getSslSocket() {
        return this.f18361b;
    }

    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f18363d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f18366g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f18364e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f18365f = strArr;
    }

    public void setContext(Context context) {
        this.f18362c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f18367h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f18360a = sSLContext;
    }

    public void setSslSocket(SSLSocket sSLSocket) {
        this.f18361b = sSLSocket;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f18366g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f18364e = x509TrustManager;
    }

    private SASFCompatiableSystemCA(KeyStore keyStore, Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        super(keyStore);
        this.f18361b = null;
        if (context == null) {
            e.b(f18358i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager sSFSecureX509SingleInstance = SSFSecureX509SingleInstance.getInstance(context);
        this.f18364e = sSFSecureX509SingleInstance;
        this.f18360a.init(null, new X509TrustManager[]{sSFSecureX509SingleInstance}, secureRandom);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        e.c(f18358i, "createSocket: ");
        Socket socketCreateSocket = this.f18360a.getSocketFactory().createSocket();
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18361b = sSLSocket;
            this.f18363d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public static SASFCompatiableSystemCA getInstance(KeyStore keyStore, Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18359j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                try {
                    if (f18359j == null) {
                        f18359j = new SASFCompatiableSystemCA(keyStore, context, secureRandom);
                    }
                } finally {
                }
            }
        }
        return f18359j;
    }

    static void a(X509TrustManager x509TrustManager, SecureRandom secureRandom) {
        e.c(f18358i, "sasfc update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18359j = new SASFCompatiableSystemCA((KeyStore) null, x509TrustManager, secureRandom);
        } catch (KeyManagementException unused) {
            e.b(f18358i, "KeyManagementException");
        } catch (KeyStoreException unused2) {
            e.b(f18358i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused3) {
            e.b(f18358i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused4) {
            e.b(f18358i, "UnrecoverableKeyException");
        }
        e.a(f18358i, "sasf system ca update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Deprecated
    public SASFCompatiableSystemCA(KeyStore keyStore, X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, IllegalArgumentException {
        super(keyStore);
        this.f18361b = null;
        this.f18360a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18360a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    public SASFCompatiableSystemCA(KeyStore keyStore, X509TrustManager x509TrustManager, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, IllegalArgumentException {
        super(keyStore);
        this.f18361b = null;
        this.f18360a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18360a.init(null, new X509TrustManager[]{x509TrustManager}, secureRandom);
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18367h)) {
            z2 = false;
        } else {
            e.c(f18358i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f18367h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18366g) && com.huawei.secure.android.common.ssl.util.a.a(this.f18365f)) {
            z3 = false;
        } else {
            e.c(f18358i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f18366g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f18366g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f18365f);
            }
        }
        if (!z2) {
            e.c(f18358i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        e.c(f18358i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }
}
