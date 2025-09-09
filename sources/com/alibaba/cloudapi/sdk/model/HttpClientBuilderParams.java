package com.alibaba.cloudapi.sdk.model;

import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.EventListener;
import okhttp3.Interceptor;

/* loaded from: classes2.dex */
public class HttpClientBuilderParams extends BaseClientInitialParam {
    SSLSocketFactory sslSocketFactory = null;
    X509TrustManager x509TrustManager = null;
    HostnameVerifier hostnameVerifier = null;
    EventListener.Factory eventListenerFactory = null;
    SocketFactory socketFactory = null;
    boolean isHttpConnectionRetry = true;
    Interceptor interceptor = null;

    @Override // com.alibaba.cloudapi.sdk.model.BaseClientInitialParam
    public void check() {
        super.check();
        if (Scheme.HTTPS == this.scheme) {
            if (this.sslSocketFactory == null || this.x509TrustManager == null || this.hostnameVerifier == null) {
                throw new SdkException("https channel need sslSocketFactory amd x509TrustManager and hostnameVerifier for communication");
            }
        }
    }

    public EventListener.Factory getEventListenerFactory() {
        return this.eventListenerFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public Interceptor getInterceptor() {
        return this.interceptor;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() {
        return this.x509TrustManager;
    }

    public boolean isHttpConnectionRetry() {
        return this.isHttpConnectionRetry;
    }

    public void setEventListenerFactory(EventListener.Factory factory) {
        this.eventListenerFactory = factory;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void setHttpConnectionRetry(boolean z2) {
        this.isHttpConnectionRetry = z2;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager) {
        this.x509TrustManager = x509TrustManager;
    }
}
