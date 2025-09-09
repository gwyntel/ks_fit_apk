package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class i6 {

    /* renamed from: a, reason: collision with root package name */
    private final boolean f17394a;

    i6(boolean z2) {
        this.f17394a = z2;
    }

    public void a(u6[] u6VarArr) {
        if (!this.f17394a || u6VarArr == null || u6VarArr.length < 3) {
            return;
        }
        u6 u6Var = u6VarArr[0];
        u6VarArr[0] = u6VarArr[2];
        u6VarArr[2] = u6Var;
    }
}
