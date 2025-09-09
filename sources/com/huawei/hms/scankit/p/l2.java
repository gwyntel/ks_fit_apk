package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class l2 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17500a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17501b;

    public int a() {
        return this.f17501b;
    }

    public int b() {
        return this.f17500a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof l2)) {
            return false;
        }
        l2 l2Var = (l2) obj;
        return this.f17500a == l2Var.f17500a && this.f17501b == l2Var.f17501b;
    }

    public int hashCode() {
        return (this.f17500a * 32713) + this.f17501b;
    }

    public String toString() {
        return this.f17500a + "x" + this.f17501b;
    }
}
