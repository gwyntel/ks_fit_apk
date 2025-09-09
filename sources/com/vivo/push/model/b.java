package com.vivo.push.model;

import android.text.TextUtils;
import com.alipay.sdk.m.u.i;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private String f23186a;

    /* renamed from: d, reason: collision with root package name */
    private String f23189d;

    /* renamed from: b, reason: collision with root package name */
    private long f23187b = -1;

    /* renamed from: c, reason: collision with root package name */
    private int f23188c = -1;

    /* renamed from: e, reason: collision with root package name */
    private boolean f23190e = false;

    /* renamed from: f, reason: collision with root package name */
    private boolean f23191f = false;

    public b(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalAccessError("PushPackageInfo need a non-null pkgName.");
        }
        this.f23186a = str;
    }

    public final String a() {
        return this.f23186a;
    }

    public final long b() {
        return this.f23187b;
    }

    public final boolean c() {
        return this.f23190e;
    }

    public final boolean d() {
        return this.f23191f;
    }

    public final String toString() {
        return "PushPackageInfo{mPackageName=" + this.f23186a + ", mPushVersion=" + this.f23187b + ", mPackageVersion=" + this.f23188c + ", mInBlackList=" + this.f23190e + ", mPushEnable=" + this.f23191f + i.f9804d;
    }

    public final void a(long j2) {
        this.f23187b = j2;
    }

    public final void b(boolean z2) {
        this.f23191f = z2;
    }

    public final void a(boolean z2) {
        this.f23190e = z2;
    }

    public final void a(int i2) {
        this.f23188c = i2;
    }

    public final void a(String str) {
        this.f23189d = str;
    }
}
