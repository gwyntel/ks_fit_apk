package com.meizu.cloud.pushsdk.e.c;

import com.meizu.cloud.pushsdk.e.d.k;

/* loaded from: classes4.dex */
public class a extends Exception {

    /* renamed from: a, reason: collision with root package name */
    private String f19375a;

    /* renamed from: b, reason: collision with root package name */
    private int f19376b;

    /* renamed from: c, reason: collision with root package name */
    private String f19377c;

    /* renamed from: d, reason: collision with root package name */
    private k f19378d;

    public a() {
        this.f19376b = 0;
    }

    public String a() {
        return this.f19375a;
    }

    public int b() {
        return this.f19376b;
    }

    public k c() {
        return this.f19378d;
    }

    public a(k kVar) {
        this.f19376b = 0;
        this.f19378d = kVar;
    }

    public void a(int i2) {
        this.f19376b = i2;
    }

    public void b(String str) {
        this.f19377c = str;
    }

    public a(Throwable th) {
        super(th);
        this.f19376b = 0;
    }

    public void a(String str) {
        this.f19375a = str;
    }
}
