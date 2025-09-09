package com.huawei.hms.scankit.p;

import android.view.animation.Interpolator;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class j5 implements f4 {

    /* renamed from: a, reason: collision with root package name */
    private final int f17437a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17438b;

    /* renamed from: c, reason: collision with root package name */
    private final long f17439c;

    /* renamed from: d, reason: collision with root package name */
    private final long f17440d;

    /* renamed from: e, reason: collision with root package name */
    private final float f17441e;

    /* renamed from: f, reason: collision with root package name */
    private final Interpolator f17442f;

    public j5(int i2, int i3, long j2, long j3, @NonNull Interpolator interpolator) {
        this.f17437a = i2;
        this.f17438b = i3;
        this.f17439c = j2;
        this.f17440d = j3;
        this.f17441e = j3 - j2;
        this.f17442f = interpolator;
    }

    private int a(@NonNull w5 w5Var) {
        int i2 = this.f17438b;
        return i2 == -1 ? w5Var.e() : i2;
    }

    private int b(@NonNull w5 w5Var) {
        int i2 = this.f17437a;
        return i2 == -1 ? w5Var.a() : i2;
    }

    private int c(@NonNull w5 w5Var) {
        return a(w5Var) - b(w5Var);
    }

    @Override // com.huawei.hms.scankit.p.f4
    public void a(@NonNull w5 w5Var, long j2) {
        if (j2 < this.f17439c || j2 > this.f17440d || Float.compare(this.f17441e, 0.0f) == 0) {
            return;
        }
        w5Var.a((int) (b(w5Var) + (c(w5Var) * this.f17442f.getInterpolation((j2 - this.f17439c) / this.f17441e))));
    }
}
