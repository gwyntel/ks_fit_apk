package anet.channel.util;

import java.net.Inet6Address;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public int f7087a;

    /* renamed from: b, reason: collision with root package name */
    public Inet6Address f7088b;

    public f(Inet6Address inet6Address, int i2) {
        this.f7087a = i2;
        this.f7088b = inet6Address;
    }

    public String toString() {
        return this.f7088b.getHostAddress() + "/" + this.f7087a;
    }
}
