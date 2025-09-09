package anet.channel.util;

import android.util.Base64;
import java.net.InetSocketAddress;
import java.net.Proxy;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    public static g f7089a;

    /* renamed from: b, reason: collision with root package name */
    private final Proxy f7090b;

    /* renamed from: c, reason: collision with root package name */
    private final String f7091c;

    /* renamed from: d, reason: collision with root package name */
    private final String f7092d;

    public g(String str, int i2, String str2, String str3) {
        this.f7090b = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i2));
        this.f7091c = str2;
        this.f7092d = str3;
    }

    public static g a() {
        return f7089a;
    }

    public Proxy b() {
        return this.f7090b;
    }

    public String c() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.f7091c);
        sb.append(":");
        sb.append(this.f7092d);
        String strEncodeToString = Base64.encodeToString(sb.toString().getBytes(), 0);
        StringBuilder sb2 = new StringBuilder(64);
        sb2.append("Basic ");
        sb2.append(strEncodeToString);
        return sb2.toString();
    }
}
