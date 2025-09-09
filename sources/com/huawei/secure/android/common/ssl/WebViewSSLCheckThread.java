package com.huawei.secure.android.common.ssl;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import com.huawei.secure.android.common.ssl.hostname.StrictHostnameVerifier;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* loaded from: classes4.dex */
public class WebViewSSLCheckThread extends Thread {

    /* renamed from: i, reason: collision with root package name */
    private static final String f18429i = "WebViewSSLCheckThread";

    /* renamed from: a, reason: collision with root package name */
    private SSLSocketFactory f18430a;

    /* renamed from: b, reason: collision with root package name */
    private HostnameVerifier f18431b;

    /* renamed from: c, reason: collision with root package name */
    private org.apache.http.conn.ssl.SSLSocketFactory f18432c;

    /* renamed from: d, reason: collision with root package name */
    private X509HostnameVerifier f18433d;

    /* renamed from: e, reason: collision with root package name */
    private SslErrorHandler f18434e;

    /* renamed from: f, reason: collision with root package name */
    private String f18435f;

    /* renamed from: g, reason: collision with root package name */
    private Callback f18436g;

    /* renamed from: h, reason: collision with root package name */
    private Context f18437h;

    public interface Callback {
        void onCancel(Context context, String str);

        void onProceed(Context context, String str);
    }

    static class a implements okhttp3.Callback {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Callback f18438a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ Context f18439b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f18440c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ SslErrorHandler f18441d;

        a(Callback callback, Context context, String str, SslErrorHandler sslErrorHandler) {
            this.f18438a = callback;
            this.f18439b = context;
            this.f18440c = str;
            this.f18441d = sslErrorHandler;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            e.b(WebViewSSLCheckThread.f18429i, "onFailure , IO Exception : " + iOException.getMessage());
            Callback callback = this.f18438a;
            if (callback != null) {
                callback.onCancel(this.f18439b, this.f18440c);
            } else {
                this.f18441d.cancel();
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            e.b(WebViewSSLCheckThread.f18429i, "onResponse . proceed");
            Callback callback = this.f18438a;
            if (callback != null) {
                callback.onProceed(this.f18439b, this.f18440c);
            } else {
                this.f18441d.proceed();
            }
        }
    }

    public WebViewSSLCheckThread() {
    }

    private void b() {
        String str = f18429i;
        e.c(str, "callbackCancel: ");
        Callback callback = this.f18436g;
        if (callback != null) {
            callback.onCancel(this.f18437h, this.f18435f);
        } else if (this.f18434e != null) {
            e.c(str, "callbackCancel 2: ");
            this.f18434e.cancel();
        }
    }

    private void c() {
        e.c(f18429i, "callbackProceed: ");
        Callback callback = this.f18436g;
        if (callback != null) {
            callback.onProceed(this.f18437h, this.f18435f);
            return;
        }
        SslErrorHandler sslErrorHandler = this.f18434e;
        if (sslErrorHandler != null) {
            sslErrorHandler.proceed();
        }
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context) {
        checkServerCertificateWithOK(sslErrorHandler, str, context, null);
    }

    public X509HostnameVerifier getApacheHostnameVerifier() {
        return this.f18433d;
    }

    public org.apache.http.conn.ssl.SSLSocketFactory getApacheSSLSocketFactory() {
        return this.f18432c;
    }

    public Callback getCallback() {
        return this.f18436g;
    }

    public Context getContext() {
        return this.f18437h;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f18431b;
    }

    public SslErrorHandler getSslErrorHandler() {
        return this.f18434e;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.f18430a;
    }

    public String getUrl() {
        return this.f18435f;
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0149  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.ssl.WebViewSSLCheckThread.run():void");
    }

    public void setApacheHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.f18433d = x509HostnameVerifier;
    }

    public void setApacheSSLSocketFactory(org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory) {
        this.f18432c = sSLSocketFactory;
    }

    public void setCallback(Callback callback) {
        this.f18436g = callback;
    }

    public void setContext(Context context) {
        this.f18437h = context;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.f18431b = hostnameVerifier;
    }

    public void setSslErrorHandler(SslErrorHandler sslErrorHandler) {
        this.f18434e = sslErrorHandler;
    }

    public void setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.f18430a = sSLSocketFactory;
    }

    public void setUrl(String str) {
        this.f18435f = str;
    }

    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException, KeyStoreException {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setContext(context);
        setSslSocketFactory(new SecureSSLSocketFactoryNew(new c(context)));
        setHostnameVerifier(new StrictHostnameVerifier());
        try {
            setApacheSSLSocketFactory(new SecureApacheSSLSocketFactory(null, new c(context)));
        } catch (UnrecoverableKeyException e2) {
            e.b(f18429i, "WebViewSSLCheckThread: UnrecoverableKeyException : " + e2.getMessage());
        }
        setApacheHostnameVerifier(SecureApacheSSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context, Callback callback) {
        if (sslErrorHandler == null || TextUtils.isEmpty(str) || context == null) {
            e.b(f18429i, "checkServerCertificateWithOK: handler or url or context is null");
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            SecureSSLSocketFactoryNew secureSSLSocketFactoryNew = new SecureSSLSocketFactoryNew(new c(context));
            secureSSLSocketFactoryNew.setContext(context);
            builder.sslSocketFactory(secureSSLSocketFactoryNew, new c(context));
            builder.hostnameVerifier(new StrictHostnameVerifier());
            builder.build().newCall(new Request.Builder().url(str).build()).enqueue(new a(callback, context, str, sslErrorHandler));
        } catch (Exception e2) {
            e.b(f18429i, "checkServerCertificateWithOK: exception : " + e2.getMessage());
            sslErrorHandler.cancel();
        }
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setSslSocketFactory(sSLSocketFactory);
        setHostnameVerifier(hostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setApacheSSLSocketFactory(sSLSocketFactory);
        setApacheHostnameVerifier(x509HostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier, Callback callback, Context context) {
        this.f18434e = sslErrorHandler;
        this.f18435f = str;
        this.f18432c = sSLSocketFactory;
        this.f18433d = x509HostnameVerifier;
        this.f18436g = callback;
        this.f18437h = context;
    }
}
