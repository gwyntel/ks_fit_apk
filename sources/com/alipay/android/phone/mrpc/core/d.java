package com.alipay.android.phone.mrpc.core;

import com.alipay.android.phone.mrpc.core.b;
import org.apache.http.client.RedirectHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes2.dex */
public final class d extends DefaultHttpClient {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b f8986a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(b bVar, ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
        this.f8986a = bVar;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public final ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new f(this);
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public final HttpContext createHttpContext() {
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute("http.authscheme-registry", getAuthSchemes());
        basicHttpContext.setAttribute("http.cookiespec-registry", getCookieSpecs());
        basicHttpContext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return basicHttpContext;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public final BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessorCreateHttpProcessor = super.createHttpProcessor();
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(b.f8979c);
        basicHttpProcessorCreateHttpProcessor.addRequestInterceptor(new b.a(this.f8986a, (byte) 0));
        return basicHttpProcessorCreateHttpProcessor;
    }

    @Override // org.apache.http.impl.client.DefaultHttpClient, org.apache.http.impl.client.AbstractHttpClient
    public final RedirectHandler createRedirectHandler() {
        return new e(this);
    }
}
