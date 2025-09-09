package com.huawei.hms.scankit.p;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class a6 implements g4 {

    /* renamed from: a, reason: collision with root package name */
    private final float f16974a;

    /* renamed from: b, reason: collision with root package name */
    private final float f16975b;

    public a6(float f2, float f3) {
        this.f16975b = f2;
        this.f16974a = f3;
    }

    @Override // com.huawei.hms.scankit.p.g4
    public void a(@NonNull w5 w5Var) {
        float fA = this.f16974a;
        float f2 = this.f16975b;
        if (fA != f2) {
            fA = n6.a(fA - f2) + this.f16975b;
        }
        w5Var.b(fA);
        w5Var.a(fA);
    }
}
