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
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

@Deprecated
/* loaded from: classes4.dex */
public class SecureSSLSocketFactory extends SSLSocketFactory {

    @Deprecated
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();

    @Deprecated
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* renamed from: i, reason: collision with root package name */
    private static final String f18398i = SecureSSLSocketFactory.class.getSimpleName();

    /* renamed from: j, reason: collision with root package name */
    private static volatile SecureSSLSocketFactory f18399j = null;

    /* renamed from: a, reason: collision with root package name */
    private SSLContext f18400a;

    /* renamed from: b, reason: collision with root package name */
    private SSLSocket f18401b;

    /* renamed from: c, reason: collision with root package name */
    private Context f18402c;

    /* renamed from: d, reason: collision with root package name */
    private String[] f18403d;

    /* renamed from: e, reason: collision with root package name */
    private X509TrustManager f18404e;

    /* renamed from: f, reason: collision with root package name */
    private String[] f18405f;

    /* renamed from: g, reason: collision with root package name */
    private String[] f18406g;

    /* renamed from: h, reason: collision with root package name */
    private String[] f18407h;

    @Deprecated
    public SecureSSLSocketFactory(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f18400a = null;
        this.f18401b = null;
        this.f18400a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18400a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    @Deprecated
    static void a(X509TrustManager x509TrustManager) {
        e.c(f18398i, "ssf update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18399j = new SecureSSLSocketFactory(x509TrustManager);
        } catch (KeyManagementException unused) {
            e.b(f18398i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            e.b(f18398i, "NoSuchAlgorithmException");
        }
        e.a(f18398i, "update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Deprecated
    public static SecureSSLSocketFactory getInstance(Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContextUtil.setContext(context);
        if (f18399j == null) {
            synchronized (SecureSSLSocketFactory.class) {
                try {
                    if (f18399j == null) {
                        f18399j = new SecureSSLSocketFactory(context, (SecureRandom) null);
                    }
                } finally {
                }
            }
        }
        if (f18399j.f18402c == null && context != null) {
            f18399j.setContext(context);
        }
        e.a(f18398i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f18399j;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        e.c(f18398i, "createSocket: host , port");
        Socket socketCreateSocket = this.f18400a.getSocketFactory().createSocket(str, i2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18401b = sSLSocket;
            this.f18403d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public String[] getBlackCiphers() {
        return this.f18405f;
    }

    public X509Certificate[] getChain() {
        X509TrustManager x509TrustManager = this.f18404e;
        return x509TrustManager instanceof SecureX509TrustManager ? ((SecureX509TrustManager) x509TrustManager).getChain() : new X509Certificate[0];
    }

    public Context getContext() {
        return this.f18402c;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getProtocols() {
        return this.f18407h;
    }

    public SSLContext getSslContext() {
        return this.f18400a;
    }

    public SSLSocket getSslSocket() {
        return this.f18401b;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] strArr = this.f18403d;
        return strArr != null ? strArr : new String[0];
    }

    public String[] getWhiteCiphers() {
        return this.f18406g;
    }

    public X509TrustManager getX509TrustManager() {
        return this.f18404e;
    }

    public void setBlackCiphers(String[] strArr) {
        this.f18405f = strArr;
    }

    public void setContext(Context context) {
        this.f18402c = context.getApplicationContext();
    }

    public void setProtocols(String[] strArr) {
        this.f18407h = strArr;
    }

    public void setSslContext(SSLContext sSLContext) {
        this.f18400a = sSLContext;
    }

    public void setWhiteCiphers(String[] strArr) {
        this.f18406g = strArr;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.f18404e = x509TrustManager;
    }

    static void a(X509TrustManager x509TrustManager, SecureRandom secureRandom) {
        e.c(f18398i, "ssf update socket factory trust manager");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            f18399j = new SecureSSLSocketFactory(x509TrustManager, secureRandom);
        } catch (KeyManagementException unused) {
            e.b(f18398i, "KeyManagementException");
        } catch (NoSuchAlgorithmException unused2) {
            e.b(f18398i, "NoSuchAlgorithmException");
        }
        e.a(f18398i, "update: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i2);
    }

    public SecureSSLSocketFactory(InputStream inputStream, String str, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateException, KeyStoreException, IllegalArgumentException {
        this.f18400a = null;
        this.f18401b = null;
        this.f18400a = SSLUtil.setSSLContext();
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        setX509TrustManager(hiCloudX509TrustManager);
        this.f18400a.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, secureRandom);
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
        e.c(f18398i, "createSocket s host port autoClose");
        Socket socketCreateSocket = this.f18400a.getSocketFactory().createSocket(socket, str, i2, z2);
        if (socketCreateSocket instanceof SSLSocket) {
            a(socketCreateSocket);
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            this.f18401b = sSLSocket;
            this.f18403d = (String[]) sSLSocket.getEnabledCipherSuites().clone();
        }
        return socketCreateSocket;
    }

    public static SecureSSLSocketFactory getInstance(Context context, SecureRandom secureRandom) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContextUtil.setContext(context);
        if (f18399j == null) {
            synchronized (SecureSSLSocketFactory.class) {
                try {
                    if (f18399j == null) {
                        f18399j = new SecureSSLSocketFactory(context, secureRandom);
                    }
                } finally {
                }
            }
        }
        if (f18399j.f18402c == null && context != null) {
            f18399j.setContext(context);
        }
        e.a(f18398i, "getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f18399j;
    }

    private void a(Socket socket) {
        boolean z2;
        boolean z3 = true;
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18407h)) {
            z2 = false;
        } else {
            e.c(f18398i, "set protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket, this.f18407h);
            z2 = true;
        }
        if (com.huawei.secure.android.common.ssl.util.a.a(this.f18406g) && com.huawei.secure.android.common.ssl.util.a.a(this.f18405f)) {
            z3 = false;
        } else {
            e.c(f18398i, "set white cipher or black cipher");
            SSLSocket sSLSocket = (SSLSocket) socket;
            SSLUtil.setEnabledProtocols(sSLSocket);
            if (!com.huawei.secure.android.common.ssl.util.a.a(this.f18406g)) {
                SSLUtil.setWhiteListCipherSuites(sSLSocket, this.f18406g);
            } else {
                SSLUtil.setBlackListCipherSuites(sSLSocket, this.f18405f);
            }
        }
        if (!z2) {
            e.c(f18398i, "set default protocols");
            SSLUtil.setEnabledProtocols((SSLSocket) socket);
        }
        if (z3) {
            return;
        }
        e.c(f18398i, "set default cipher suites");
        SSLUtil.setEnableSafeCipherSuites((SSLSocket) socket);
    }

    private SecureSSLSocketFactory(Context context, SecureRandom secureRandom) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, KeyManagementException {
        this.f18400a = null;
        this.f18401b = null;
        if (context == null) {
            e.b(f18398i, "SecureSSLSocketFactory: context is null");
            return;
        }
        setContext(context);
        setSslContext(SSLUtil.setSSLContext());
        SecureX509TrustManager secureX509SingleInstance = SecureX509SingleInstance.getInstance(context);
        this.f18404e = secureX509SingleInstance;
        this.f18400a.init(null, new X509TrustManager[]{secureX509SingleInstance}, secureRandom);
    }

    @Deprecated
    public SecureSSLSocketFactory(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18400a = null;
        this.f18401b = null;
        this.f18400a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18400a.init(null, new X509TrustManager[]{x509TrustManager}, null);
    }

    public SecureSSLSocketFactory(X509TrustManager x509TrustManager, SecureRandom secureRandom) throws NoSuchAlgorithmException, KeyManagementException, IllegalArgumentException {
        this.f18400a = null;
        this.f18401b = null;
        this.f18400a = SSLUtil.setSSLContext();
        setX509TrustManager(x509TrustManager);
        this.f18400a.init(null, new X509TrustManager[]{x509TrustManager}, secureRandom);
    }
}
