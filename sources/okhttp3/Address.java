package okhttp3;

import com.alipay.sdk.m.u.i;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;

/* loaded from: classes5.dex */
public final class Address {

    /* renamed from: a, reason: collision with root package name */
    final HttpUrl f26067a;

    /* renamed from: b, reason: collision with root package name */
    final Dns f26068b;

    /* renamed from: c, reason: collision with root package name */
    final SocketFactory f26069c;

    /* renamed from: d, reason: collision with root package name */
    final Authenticator f26070d;

    /* renamed from: e, reason: collision with root package name */
    final List f26071e;

    /* renamed from: f, reason: collision with root package name */
    final List f26072f;

    /* renamed from: g, reason: collision with root package name */
    final ProxySelector f26073g;

    /* renamed from: h, reason: collision with root package name */
    final Proxy f26074h;

    /* renamed from: i, reason: collision with root package name */
    final SSLSocketFactory f26075i;

    /* renamed from: j, reason: collision with root package name */
    final HostnameVerifier f26076j;

    /* renamed from: k, reason: collision with root package name */
    final CertificatePinner f26077k;

    public Address(String str, int i2, Dns dns, SocketFactory socketFactory, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier, @Nullable CertificatePinner certificatePinner, Authenticator authenticator, @Nullable Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        this.f26067a = new HttpUrl.Builder().scheme(sSLSocketFactory != null ? "https" : "http").host(str).port(i2).build();
        if (dns == null) {
            throw new NullPointerException("dns == null");
        }
        this.f26068b = dns;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.f26069c = socketFactory;
        if (authenticator == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.f26070d = authenticator;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.f26071e = Util.immutableList(list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.f26072f = Util.immutableList(list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.f26073g = proxySelector;
        this.f26074h = proxy;
        this.f26075i = sSLSocketFactory;
        this.f26076j = hostnameVerifier;
        this.f26077k = certificatePinner;
    }

    boolean a(Address address) {
        return this.f26068b.equals(address.f26068b) && this.f26070d.equals(address.f26070d) && this.f26071e.equals(address.f26071e) && this.f26072f.equals(address.f26072f) && this.f26073g.equals(address.f26073g) && Util.equal(this.f26074h, address.f26074h) && Util.equal(this.f26075i, address.f26075i) && Util.equal(this.f26076j, address.f26076j) && Util.equal(this.f26077k, address.f26077k) && url().port() == address.url().port();
    }

    @Nullable
    public CertificatePinner certificatePinner() {
        return this.f26077k;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.f26072f;
    }

    public Dns dns() {
        return this.f26068b;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Address) {
            Address address = (Address) obj;
            if (this.f26067a.equals(address.f26067a) && a(address)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (((((((((((527 + this.f26067a.hashCode()) * 31) + this.f26068b.hashCode()) * 31) + this.f26070d.hashCode()) * 31) + this.f26071e.hashCode()) * 31) + this.f26072f.hashCode()) * 31) + this.f26073g.hashCode()) * 31;
        Proxy proxy = this.f26074h;
        int iHashCode2 = (iHashCode + (proxy != null ? proxy.hashCode() : 0)) * 31;
        SSLSocketFactory sSLSocketFactory = this.f26075i;
        int iHashCode3 = (iHashCode2 + (sSLSocketFactory != null ? sSLSocketFactory.hashCode() : 0)) * 31;
        HostnameVerifier hostnameVerifier = this.f26076j;
        int iHashCode4 = (iHashCode3 + (hostnameVerifier != null ? hostnameVerifier.hashCode() : 0)) * 31;
        CertificatePinner certificatePinner = this.f26077k;
        return iHashCode4 + (certificatePinner != null ? certificatePinner.hashCode() : 0);
    }

    @Nullable
    public HostnameVerifier hostnameVerifier() {
        return this.f26076j;
    }

    public List<Protocol> protocols() {
        return this.f26071e;
    }

    @Nullable
    public Proxy proxy() {
        return this.f26074h;
    }

    public Authenticator proxyAuthenticator() {
        return this.f26070d;
    }

    public ProxySelector proxySelector() {
        return this.f26073g;
    }

    public SocketFactory socketFactory() {
        return this.f26069c;
    }

    @Nullable
    public SSLSocketFactory sslSocketFactory() {
        return this.f26075i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address{");
        sb.append(this.f26067a.host());
        sb.append(":");
        sb.append(this.f26067a.port());
        if (this.f26074h != null) {
            sb.append(", proxy=");
            sb.append(this.f26074h);
        } else {
            sb.append(", proxySelector=");
            sb.append(this.f26073g);
        }
        sb.append(i.f9804d);
        return sb.toString();
    }

    public HttpUrl url() {
        return this.f26067a;
    }
}
