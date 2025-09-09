package com.umeng.analytics.pro;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class cd implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private final boolean f21561a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f21562b;

    /* renamed from: c, reason: collision with root package name */
    private final String f21563c;

    /* renamed from: d, reason: collision with root package name */
    private final boolean f21564d;

    public cd(byte b2, boolean z2) {
        this.f21562b = b2;
        this.f21561a = false;
        this.f21563c = null;
        this.f21564d = z2;
    }

    public boolean a() {
        return this.f21561a;
    }

    public String b() {
        return this.f21563c;
    }

    public boolean c() {
        return this.f21562b == 12;
    }

    public boolean d() {
        byte b2 = this.f21562b;
        return b2 == 15 || b2 == 13 || b2 == 14;
    }

    public boolean e() {
        return this.f21564d;
    }

    public cd(byte b2) {
        this(b2, false);
    }

    public cd(byte b2, String str) {
        this.f21562b = b2;
        this.f21561a = true;
        this.f21563c = str;
        this.f21564d = false;
    }
}
