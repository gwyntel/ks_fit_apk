package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
public final class Address {

    /* renamed from: a, reason: collision with root package name */
    final Proxy f19847a;

    /* renamed from: b, reason: collision with root package name */
    final String f19848b;

    /* renamed from: c, reason: collision with root package name */
    final int f19849c;

    /* renamed from: d, reason: collision with root package name */
    final SocketFactory f19850d;

    /* renamed from: e, reason: collision with root package name */
    final SSLSocketFactory f19851e;

    /* renamed from: f, reason: collision with root package name */
    final HostnameVerifier f19852f;

    /* renamed from: g, reason: collision with root package name */
    final CertificatePinner f19853g;

    /* renamed from: h, reason: collision with root package name */
    final Authenticator f19854h;

    /* renamed from: i, reason: collision with root package name */
    final List f19855i;

    /* renamed from: j, reason: collision with root package name */
    final List f19856j;

    /* renamed from: k, reason: collision with root package name */
    final ProxySelector f19857k;

    public Address(String str, int i2, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        if (str == null) {
            throw new NullPointerException("uriHost == null");
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("uriPort <= 0: " + i2);
        }
        if (authenticator == null) {
            throw new IllegalArgumentException("authenticator == null");
        }
        if (list == null) {
            throw new IllegalArgumentException("protocols == null");
        }
        if (proxySelector == null) {
            throw new IllegalArgumentException("proxySelector == null");
        }
        this.f19847a = proxy;
        this.f19848b = str;
        this.f19849c = i2;
        this.f19850d = socketFactory;
        this.f19851e = sSLSocketFactory;
        this.f19852f = hostnameVerifier;
        this.f19853g = certificatePinner;
        this.f19854h = authenticator;
        this.f19855i = Util.immutableList(list);
        this.f19856j = Util.immutableList(list2);
        this.f19857k = proxySelector;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        return Util.equal(this.f19847a, address.f19847a) && this.f19848b.equals(address.f19848b) && this.f19849c == address.f19849c && Util.equal(this.f19851e, address.f19851e) && Util.equal(this.f19852f, address.f19852f) && Util.equal(this.f19853g, address.f19853g) && Util.equal(this.f19854h, address.f19854h) && Util.equal(this.f19855i, address.f19855i) && Util.equal(this.f19856j, address.f19856j) && Util.equal(this.f19857k, address.f19857k);
    }

    public Authenticator getAuthenticator() {
        return this.f19854h;
    }

    public CertificatePinner getCertificatePinner() {
        return this.f19853g;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.f19856j;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f19852f;
    }

    public List<Protocol> getProtocols() {
        return this.f19855i;
    }

    public Proxy getProxy() {
        return this.f19847a;
    }

    public ProxySelector getProxySelector() {
        return this.f19857k;
    }

    public SocketFactory getSocketFactory() {
        return this.f19850d;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.f19851e;
    }

    public String getUriHost() {
        return this.f19848b;
    }

    public int getUriPort() {
        return this.f19849c;
    }

    public int hashCode() {
        Proxy proxy = this.f19847a;
        int iHashCode = (((((527 + (proxy != null ? proxy.hashCode() : 0)) * 31) + this.f19848b.hashCode()) * 31) + this.f19849c) * 31;
        SSLSocketFactory sSLSocketFactory = this.f19851e;
        int iHashCode2 = (iHashCode + (sSLSocketFactory != null ? sSLSocketFactory.hashCode() : 0)) * 31;
        HostnameVerifier hostnameVerifier = this.f19852f;
        int iHashCode3 = (iHashCode2 + (hostnameVerifier != null ? hostnameVerifier.hashCode() : 0)) * 31;
        CertificatePinner certificatePinner = this.f19853g;
        return ((((((((iHashCode3 + (certificatePinner != null ? certificatePinner.hashCode() : 0)) * 31) + this.f19854h.hashCode()) * 31) + this.f19855i.hashCode()) * 31) + this.f19856j.hashCode()) * 31) + this.f19857k.hashCode();
    }
}
