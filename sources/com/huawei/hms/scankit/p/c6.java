package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public class c6 implements Comparable<c6> {

    /* renamed from: a, reason: collision with root package name */
    public i2 f17072a;

    /* renamed from: b, reason: collision with root package name */
    public int f17073b;

    public c6(i2 i2Var, int i2) {
        this.f17072a = i2Var;
        this.f17073b = i2;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(c6 c6Var) {
        return Float.compare((-c6Var.f17072a.g()) + c6Var.f17072a.h(), (-this.f17072a.g()) + this.f17072a.h());
    }
}
