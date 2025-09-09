package com.huawei.hms.common.internal;

/* loaded from: classes4.dex */
public class ResolveClientBean {

    /* renamed from: a, reason: collision with root package name */
    private final int f15987a;

    /* renamed from: b, reason: collision with root package name */
    private final AnyClient f15988b;

    /* renamed from: c, reason: collision with root package name */
    private int f15989c;

    public ResolveClientBean(AnyClient anyClient, int i2) {
        this.f15988b = anyClient;
        this.f15987a = Objects.hashCode(anyClient);
        this.f15989c = i2;
    }

    public void clientReconnect() {
        this.f15988b.connect(this.f15989c, true);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ResolveClientBean)) {
            return false;
        }
        return this.f15988b.equals(((ResolveClientBean) obj).f15988b);
    }

    public AnyClient getClient() {
        return this.f15988b;
    }

    public int hashCode() {
        return this.f15987a;
    }
}
