package com.aliyun.utils;

import com.cicada.player.utils.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes3.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static final String f12130a = "d";

    /* renamed from: b, reason: collision with root package name */
    private static ExecutorService f12131b = Executors.newCachedThreadPool();

    /* renamed from: d, reason: collision with root package name */
    private String f12133d;

    /* renamed from: c, reason: collision with root package name */
    private URLConnection f12132c = null;

    /* renamed from: e, reason: collision with root package name */
    private String f12134e = null;

    /* renamed from: f, reason: collision with root package name */
    private int f12135f = 10000;

    /* renamed from: g, reason: collision with root package name */
    private String f12136g = null;

    /* renamed from: h, reason: collision with root package name */
    private String f12137h = null;

    /* renamed from: i, reason: collision with root package name */
    private String[] f12138i = null;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HttpURLConnection httpURLConnection;
            try {
                if (d.this.f12132c instanceof HttpsURLConnection) {
                    Logger.i(d.f12130a, "HttpClientHelper stop().... HttpsURLConnection.disconnect ");
                    httpURLConnection = (HttpsURLConnection) d.this.f12132c;
                } else {
                    if (!(d.this.f12132c instanceof HttpURLConnection)) {
                        return;
                    }
                    Logger.i(d.f12130a, "HttpClientHelper stop().... HttpURLConnection.disconnect ");
                    httpURLConnection = (HttpURLConnection) d.this.f12132c;
                }
                httpURLConnection.disconnect();
            } catch (Exception e2) {
                Logger.e(d.f12130a, e2.getMessage());
            }
        }
    }

    public d(String str) {
        this.f12133d = str;
    }

    private InputStream c() {
        HttpURLConnection httpURLConnection;
        URLConnection uRLConnection = this.f12132c;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return null;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getErrorStream();
    }

    private int d() throws IOException {
        HttpURLConnection httpURLConnection;
        URLConnection uRLConnection = this.f12132c;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return 0;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getResponseCode();
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x0168 A[Catch: IOException -> 0x0170, TryCatch #6 {IOException -> 0x0170, blocks: (B:114:0x0163, B:116:0x0168, B:118:0x016d), top: B:154:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x016d A[Catch: IOException -> 0x0170, TRY_LEAVE, TryCatch #6 {IOException -> 0x0170, blocks: (B:114:0x0163, B:116:0x0168, B:118:0x016d), top: B:154:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0193 A[Catch: IOException -> 0x019b, TryCatch #18 {IOException -> 0x019b, blocks: (B:133:0x018e, B:135:0x0193, B:137:0x0198), top: B:160:0x018e }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0198 A[Catch: IOException -> 0x019b, TRY_LEAVE, TryCatch #18 {IOException -> 0x019b, blocks: (B:133:0x018e, B:135:0x0193, B:137:0x0198), top: B:160:0x018e }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0163 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x018e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.d.b():java.lang.String");
    }

    public void e() {
        Logger.d(f12130a, "HttpClientHelper stop().... urlConnection = " + this.f12132c);
        if (this.f12132c != null) {
            f12131b.execute(new a());
        }
    }

    private URLConnection b(String str) throws ProtocolException {
        Proxy proxy;
        try {
            if (this.f12136g != null) {
                try {
                    URL url = new URL(this.f12136g);
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));
                } catch (Exception unused) {
                }
            } else {
                proxy = null;
            }
            URL url2 = new URL(str);
            URLConnection uRLConnectionOpenConnection = proxy != null ? url2.openConnection(proxy) : url2.openConnection();
            if (!(uRLConnectionOpenConnection instanceof HttpsURLConnection)) {
                return null;
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(this.f12135f);
            httpsURLConnection.setReadTimeout(this.f12135f);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            return null;
        }
    }

    public void c(String str) {
        this.f12136g = str;
    }

    public void d(String str) {
        this.f12134e = str;
    }

    public void e(String str) {
        this.f12137h = str;
    }

    private URLConnection a(String str) throws ProtocolException {
        Proxy proxy;
        try {
            if (this.f12136g != null) {
                try {
                    URL url = new URL(this.f12136g);
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));
                } catch (Exception unused) {
                }
            } else {
                proxy = null;
            }
            URL url2 = new URL(str);
            URLConnection uRLConnectionOpenConnection = proxy != null ? url2.openConnection(proxy) : url2.openConnection();
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(this.f12135f);
            httpURLConnection.setReadTimeout(this.f12135f);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            return null;
        }
    }

    public void a(int i2) {
        this.f12135f = i2;
    }

    public void a(String[] strArr) {
        this.f12138i = strArr;
    }
}
