package okhttp3;

import com.alipay.sdk.m.u.i;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public final class Route {

    /* renamed from: a, reason: collision with root package name */
    final Address f26249a;

    /* renamed from: b, reason: collision with root package name */
    final Proxy f26250b;

    /* renamed from: c, reason: collision with root package name */
    final InetSocketAddress f26251c;

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
        this.f26249a = address;
        this.f26250b = proxy;
        this.f26251c = inetSocketAddress;
    }

    public Address address() {
        return this.f26249a;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Route) {
            Route route = (Route) obj;
            if (route.f26249a.equals(this.f26249a) && route.f26250b.equals(this.f26250b) && route.f26251c.equals(this.f26251c)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + this.f26249a.hashCode()) * 31) + this.f26250b.hashCode()) * 31) + this.f26251c.hashCode();
    }

    public Proxy proxy() {
        return this.f26250b;
    }

    public boolean requiresTunnel() {
        return this.f26249a.f26075i != null && this.f26250b.type() == Proxy.Type.HTTP;
    }

    public InetSocketAddress socketAddress() {
        return this.f26251c;
    }

    public String toString() {
        return "Route{" + this.f26251c + i.f9804d;
    }
}
