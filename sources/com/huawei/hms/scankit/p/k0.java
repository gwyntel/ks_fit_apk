package com.huawei.hms.scankit.p;

import android.graphics.Rect;

/* loaded from: classes4.dex */
public class k0 {

    /* renamed from: a, reason: collision with root package name */
    private int f17462a;

    /* renamed from: b, reason: collision with root package name */
    private Rect f17463b;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public Rect f17464a;

        /* renamed from: b, reason: collision with root package name */
        public int f17465b;

        public a(Rect rect, int i2) {
            this.f17464a = rect;
            this.f17465b = i2;
        }
    }

    public k0(int i2, Rect rect) {
        this.f17462a = i2;
        this.f17463b = new Rect(rect);
    }

    public int a() {
        return this.f17462a;
    }

    public Rect b() {
        return this.f17463b;
    }
}
