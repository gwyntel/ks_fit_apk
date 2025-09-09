package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;

/* loaded from: classes4.dex */
public final class Route {

    /* renamed from: a, reason: collision with root package name */
    final Address f19905a;

    /* renamed from: b, reason: collision with root package name */
    final Proxy f19906b;

    /* renamed from: c, reason: collision with root package name */
    final InetSocketAddress f19907c;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address == null) {
            throw new NullPointerException("address == null");
        }
        if (proxy == null) {
            throw new NullPointerException("proxy == null");
        }
        if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        }
        this.f19905a = address;
        this.f19906b = proxy;
        this.f19907c = inetSocketAddress;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }
        Route route = (Route) obj;
        return this.f19905a.equals(route.f19905a) && this.f19906b.equals(route.f19906b) && this.f19907c.equals(route.f19907c);
    }

    public Address getAddress() {
        return this.f19905a;
    }

    public Proxy getProxy() {
        return this.f19906b;
    }

    public InetSocketAddress getSocketAddress() {
        return this.f19907c;
    }

    public int hashCode() {
        return ((((527 + this.f19905a.hashCode()) * 31) + this.f19906b.hashCode()) * 31) + this.f19907c.hashCode();
    }

    public boolean requiresTunnel() {
        return this.f19905a.f19851e != null && this.f19906b.type() == Proxy.Type.HTTP;
    }
}
