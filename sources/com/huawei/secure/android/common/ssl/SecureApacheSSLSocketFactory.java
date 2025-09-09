package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* loaded from: classes4.dex */
public class SecureApacheSSLSocketFactory extends SSLSocketFactory {
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: i, reason: collision with root package name */
    private static final String f18388i = SecureApacheSSLSocketFactory.class.getSimpleName();

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureApacheSSLSocketFactory f18389j = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f18390a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f18391b;

    /* renamed from: c, reason: collision with root package name */
    private Context f18392c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f18393d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f18394e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f18395f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f18396g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f18397h;

    private SecureApacheSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(keyStore);
        this.f18391b = null;
    }

    @Deprecated
    static void a(X509TrustManager x509TrustManager) {
        e.c(f18388i, "sasf update socket factory trust manager");
        try {
            f18389j = new SecureApacheSSLSocketFactory(null, x509TrustManager);
        } catch (IOException unused) {
            e.b(f18388i, "IOException");
        } catch (KeyManagementException unused2) {
            e.b(f18388i, "KeyManagementException");
        } catch (KeyStoreException unused3) {
            e.b(f18388i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused4) {
            e.b(f18388i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused5) {
            e.b(f18388i, "UnrecoverableKeyException");
        } catch (CertificateException unused6) {
            e.b(f18388i, "CertificateException");
        }
    }

    @Deprecated
    public static SecureApacheSSLSocketFactory getInstance(KeyStore keyStore, Context context) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18389j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                try {
                    if (f18389j == null) {
                        f18389j = new SecureApacheSSLSocketFactory(keyStore, context, (SecureRandom) null);
                    }
                } finally {
                }
            }
        }
        return f18389j;
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        e.c(f18388i, "createSocket: socket host port autoClose");
        Socket socketCreateSocket = this.f18390a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18391b = sSLSocket;
            this.f18393d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f18395f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f18394e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f18392c;
    }

    public String[] getProtocols() {
        return this.f18397h;
    }

    public SSLContext getSslContext() {
        return this.f18390a;
    }

    public SSLSocket getSslSocket() {
        return this.f18391b;
    }

    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f18393d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f18396g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f18394e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f18395f = strArr;
    }

    public void setContext(Context context) {
        this.f18392c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f18397h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f18390a = sSLContext;
    }

    public void setSslSocket(SSLSocket sSLSocket) {
        this.f18391b = sSLSocket;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f18396g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f18394e = x509TrustManager;
    }

    private SecureApacheSSLSocketFactory(KeyStore keyStore, Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        super(keyStore);
        this.f18391b = null;
        if (context == null) {
            e.b(f18388i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f18394e = secureX509SingleInstance;
        this.f18390a.init(null, new X509TrustManager[]{secureX509SingleInstance}, secureRandom);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        e.c(f18388i, "createSocket: ");
        Socket socketCreateSocket = this.f18390a.getSocketFactory().createSocket();
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18391b = sSLSocket;
            this.f18393d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public static SecureApacheSSLSocketFactory getInstance(KeyStore keyStore, Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        ContextUtil.setContext(context);
        if (f18389j == null) {
            synchronized (SecureApacheSSLSocketFactory.class) {
                try {
                    if (f18389j == null) {
                        f18389j = new SecureApacheSSLSocketFactory(keyStore, context, secureRandom);
                    }
                } finally {
                }
            }
        }
        return f18389j;
    }

    static void a(X509TrustManager x509TrustManager, SecureRandom secureRandom) {
        e.c(f18388i, "sasf update socket factory trust manager");
        try {
            f18389j = new SecureApacheSSLSocketFactory((KeyStore) null, x509TrustManager, secureRandom);
        } catch (IOException unused) {
            e.b(f18388i, "IOException");
        } catch (KeyManagementException unused2) {
            e.b(f18388i, "KeyManagementException");
        } catch (KeyStoreException unused3) {
            e.b(f18388i, "KeyStoreException");
        } catch (NoSuchAlgorithmException unused4) {
            e.b(f18388i, "NoSuchAlgorithmException");
        } catch (UnrecoverableKeyException unused5) {
            e.b(f18388i, "UnrecoverableKeyException");
        } catch (CertificateException unused6) {
            e.b(f18388i, "CertificateException");
        }
    }

    @Deprecated
    public SecureApacheSSLSocketFactory(KeyStore keyStore, InputStream inputStream, String str) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f18391b = null;
        this.f18390a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18390a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    public SecureApacheSSLSocketFactory(KeyStore keyStore, InputStream inputStream, String str, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f18391b = null;
        this.f18390a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18390a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, secureRandom);
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18397h)) {
            z2 = false;
        } else {
            e.c(f18388i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f18397h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18396g) && com.huawei.secure.android.common.ssl.util.a.a(this.f18395f)) {
            z3 = false;
        } else {
            e.c(f18388i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f18396g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f18396g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f18395f);
            }
        }
        if (!z2) {
            e.c(f18388i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        e.c(f18388i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    @Deprecated
    public SecureApacheSSLSocketFactory(KeyStore keyStore, X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f18391b = null;
        this.f18390a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18390a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    public SecureApacheSSLSocketFactory(KeyStore keyStore, X509TrustManager x509TrustManager, SecureRandom secureRandom) throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyManagementException, KeyStoreException, CertificateException, IllegalArgumentException {
        super(keyStore);
        this.f18391b = null;
        this.f18390a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18390a.init(null, new X509TrustManager[]{x509TrustManager}, secureRandom);
    }
}
