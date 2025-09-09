package anet.channel.entity;

import anet.channel.strategy.IConnStrategy;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public final IConnStrategy f6769a;

    /* renamed from: b, reason: collision with root package name */
    public int f6770b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f6771c = 0;

    /* renamed from: d, reason: collision with root package name */
    private String f6772d;

    /* renamed from: e, reason: collision with root package name */
    private String f6773e;

    public a(String str, String str2, IConnStrategy iConnStrategy) {
        this.f6769a = iConnStrategy;
        this.f6772d = str;
        this.f6773e = str2;
    }

    public String a() {
        IConnStrategy iConnStrategy = this.f6769a;
        if (iConnStrategy != null) {
            return iConnStrategy.getIp();
        }
        return null;
    }

    public int b() {
        IConnStrategy iConnStrategy = this.f6769a;
        if (iConnStrategy != null) {
            return iConnStrategy.getPort();
        }
        return 0;
    }

    public ConnType c() {
        IConnStrategy iConnStrategy = this.f6769a;
        return iConnStrategy != null ? ConnType.valueOf(iConnStrategy.getProtocol()) : ConnType.HTTP;
    }

    public int d() {
        IConnStrategy iConnStrategy = this.f6769a;
        if (iConnStrategy == null || iConnStrategy.getConnectionTimeout() == 0) {
            return 20000;
        }
        return this.f6769a.getConnectionTimeout();
    }

    public int e() {
        IConnStrategy iConnStrategy = this.f6769a;
        if (iConnStrategy == null || iConnStrategy.getReadTimeout() == 0) {
            return 20000;
        }
        return this.f6769a.getReadTimeout();
    }

    public String f() {
        return this.f6772d;
    }

    public int g() {
        IConnStrategy iConnStrategy = this.f6769a;
        if (iConnStrategy != null) {
            return iConnStrategy.getHeartbeat();
        }
        return 45000;
    }

    public String h() {
        return this.f6773e;
    }

    public String toString() {
        return "ConnInfo [ip=" + a() + ",port=" + b() + ",type=" + c() + ",hb" + g() + "]";
    }
}
