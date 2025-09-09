package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import anet.channel.util.HttpConstant;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes2.dex */
public final class q implements Callable<u> {

    /* renamed from: e, reason: collision with root package name */
    public static final HttpRequestRetryHandler f9023e = new ad();

    /* renamed from: a, reason: collision with root package name */
    public l f9024a;

    /* renamed from: b, reason: collision with root package name */
    public Context f9025b;

    /* renamed from: c, reason: collision with root package name */
    public o f9026c;

    /* renamed from: d, reason: collision with root package name */
    public String f9027d;

    /* renamed from: f, reason: collision with root package name */
    public HttpUriRequest f9028f;

    /* renamed from: i, reason: collision with root package name */
    public CookieManager f9031i;

    /* renamed from: j, reason: collision with root package name */
    public AbstractHttpEntity f9032j;

    /* renamed from: k, reason: collision with root package name */
    public HttpHost f9033k;

    /* renamed from: l, reason: collision with root package name */
    public URL f9034l;

    /* renamed from: q, reason: collision with root package name */
    public String f9039q;

    /* renamed from: g, reason: collision with root package name */
    public HttpContext f9029g = new BasicHttpContext();

    /* renamed from: h, reason: collision with root package name */
    public CookieStore f9030h = new BasicCookieStore();

    /* renamed from: m, reason: collision with root package name */
    public int f9035m = 0;

    /* renamed from: n, reason: collision with root package name */
    public boolean f9036n = false;

    /* renamed from: o, reason: collision with root package name */
    public boolean f9037o = false;

    /* renamed from: p, reason: collision with root package name */
    public String f9038p = null;

    public q(l lVar, o oVar) {
        this.f9024a = lVar;
        this.f9025b = lVar.f9001a;
        this.f9026c = oVar;
    }

    public static long a(String[] strArr) {
        String str;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if ("max-age".equalsIgnoreCase(strArr[i2]) && (str = strArr[i2 + 1]) != null) {
                try {
                    return Long.parseLong(str);
                } catch (Exception unused) {
                    continue;
                }
            }
        }
        return 0L;
    }

    public static long b(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] strArrSplit = firstHeader.getValue().split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit.length >= 2) {
                try {
                    return a(strArrSplit);
                } catch (NumberFormatException unused) {
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            return b.b(firstHeader2.getValue()) - System.currentTimeMillis();
        }
        return 0L;
    }

    private HttpUriRequest c() {
        HttpUriRequest httpUriRequest = this.f9028f;
        if (httpUriRequest != null) {
            return httpUriRequest;
        }
        if (this.f9032j == null) {
            byte[] bArrB = this.f9026c.b();
            String strB = this.f9026c.b(HttpConstant.GZIP);
            if (bArrB != null) {
                if (TextUtils.equals(strB, "true")) {
                    this.f9032j = b.a(bArrB);
                } else {
                    this.f9032j = new ByteArrayEntity(bArrB);
                }
                this.f9032j.setContentType(this.f9026c.c());
            }
        }
        AbstractHttpEntity abstractHttpEntity = this.f9032j;
        if (abstractHttpEntity != null) {
            HttpPost httpPost = new HttpPost(b());
            httpPost.setEntity(abstractHttpEntity);
            this.f9028f = httpPost;
        } else {
            this.f9028f = new HttpGet(b());
        }
        return this.f9028f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f2  */
    @Override // java.util.concurrent.Callable
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alipay.android.phone.mrpc.core.u call() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1008
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mrpc.core.q.call():com.alipay.android.phone.mrpc.core.u");
    }

    private void e() throws UnsupportedOperationException {
        HttpUriRequest httpUriRequest = this.f9028f;
        if (httpUriRequest != null) {
            httpUriRequest.abort();
        }
    }

    private String f() {
        if (!TextUtils.isEmpty(this.f9039q)) {
            return this.f9039q;
        }
        String strB = this.f9026c.b("operationType");
        this.f9039q = strB;
        return strB;
    }

    private int g() {
        URL urlH = h();
        return urlH.getPort() == -1 ? urlH.getDefaultPort() : urlH.getPort();
    }

    private URL h() {
        URL url = this.f9034l;
        if (url != null) {
            return url;
        }
        URL url2 = new URL(this.f9026c.a());
        this.f9034l = url2;
        return url2;
    }

    private CookieManager i() {
        CookieManager cookieManager = this.f9031i;
        if (cookieManager != null) {
            return cookieManager;
        }
        CookieManager cookieManager2 = CookieManager.getInstance();
        this.f9031i = cookieManager2;
        return cookieManager2;
    }

    public static HttpUrlHeader a(HttpResponse httpResponse) {
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    private URI b() {
        String strA = this.f9026c.a();
        String str = this.f9027d;
        if (str != null) {
            strA = str;
        }
        if (strA != null) {
            return new URI(strA);
        }
        throw new RuntimeException("url should not be null");
    }

    public final o a() {
        return this.f9026c;
    }

    private u a(HttpResponse httpResponse, int i2, String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        String str2;
        Thread.currentThread().getId();
        HttpEntity entity = httpResponse.getEntity();
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        String str3 = null;
        if (entity == null || httpResponse.getStatusLine().getStatusCode() != 200) {
            if (entity != null) {
                return null;
            }
            httpResponse.getStatusLine().getStatusCode();
            return null;
        }
        Thread.currentThread().getId();
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th) {
            th = th;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            a(entity, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.f9037o = false;
            this.f9024a.c(System.currentTimeMillis() - jCurrentTimeMillis);
            this.f9024a.a(byteArray.length);
            p pVar = new p(a(httpResponse), i2, str, byteArray);
            long jB = b(httpResponse);
            Header contentType = httpResponse.getEntity().getContentType();
            if (contentType != null) {
                HashMap<String, String> mapA = a(contentType.getValue());
                str3 = mapA.get("charset");
                str2 = mapA.get("Content-Type");
            } else {
                str2 = null;
            }
            pVar.b(str2);
            pVar.a(str3);
            pVar.a(System.currentTimeMillis());
            pVar.b(jB);
            try {
                byteArrayOutputStream.close();
                return pVar;
            } catch (IOException e2) {
                throw new RuntimeException("ArrayOutputStream close error!", e2.getCause());
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream2 = byteArrayOutputStream;
            if (byteArrayOutputStream2 != null) {
                try {
                    byteArrayOutputStream2.close();
                } catch (IOException e3) {
                    throw new RuntimeException("ArrayOutputStream close error!", e3.getCause());
                }
            }
            throw th;
        }
    }

    public static HashMap<String, String> a(String str) {
        HashMap<String, String> map = new HashMap<>();
        for (String str2 : str.split(com.alipay.sdk.m.u.i.f9802b)) {
            String[] strArrSplit = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split(ContainerUtils.KEY_VALUE_DELIMITER);
            map.put(strArrSplit[0], strArrSplit[1]);
        }
        return map;
    }

    private void a(HttpEntity httpEntity, OutputStream outputStream) throws IllegalStateException, IOException {
        InputStream inputStreamA = b.a(httpEntity);
        httpEntity.getContentLength();
        try {
            try {
                byte[] bArr = new byte[2048];
                while (true) {
                    int i2 = inputStreamA.read(bArr);
                    if (i2 == -1 || this.f9026c.h()) {
                        break;
                    }
                    outputStream.write(bArr, 0, i2);
                    this.f9026c.f();
                }
                outputStream.flush();
                r.a(inputStreamA);
            } catch (Exception e2) {
                e2.getCause();
                throw new IOException("HttpWorker Request Error!" + e2.getLocalizedMessage());
            }
        } catch (Throwable th) {
            r.a(inputStreamA);
            throw th;
        }
    }
}
