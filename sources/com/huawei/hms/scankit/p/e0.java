package com.huawei.hms.scankit.p;

import android.graphics.Point;

/* loaded from: classes4.dex */
public class e0 {

    /* renamed from: a, reason: collision with root package name */
    private int f17173a;

    /* renamed from: b, reason: collision with root package name */
    private int f17174b;

    /* renamed from: c, reason: collision with root package name */
    private String f17175c;

    /* renamed from: d, reason: collision with root package name */
    private Point f17176d;

    /* renamed from: e, reason: collision with root package name */
    private int f17177e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f17178f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f17179g;

    public static class b {

        /* renamed from: e, reason: collision with root package name */
        private Point f17184e;

        /* renamed from: a, reason: collision with root package name */
        private int f17180a = 0;

        /* renamed from: b, reason: collision with root package name */
        private int f17181b = 1;

        /* renamed from: c, reason: collision with root package name */
        private int f17182c = 0;

        /* renamed from: d, reason: collision with root package name */
        private String f17183d = "off";

        /* renamed from: f, reason: collision with root package name */
        private boolean f17185f = true;

        /* renamed from: g, reason: collision with root package name */
        private boolean f17186g = false;

        public b a(int i2) {
            this.f17181b = i2;
            return this;
        }

        public b b(int i2) {
            this.f17182c = i2;
            return this;
        }

        public b a(Point point) {
            this.f17184e = point;
            return this;
        }

        public b b(boolean z2) {
            this.f17185f = z2;
            return this;
        }

        public b a(boolean z2) {
            this.f17186g = z2;
            return this;
        }

        public e0 a() {
            return new e0(this.f17180a, this.f17181b, this.f17182c, this.f17183d, this.f17184e, this.f17185f).a(this.f17186g);
        }
    }

    public int b() {
        return this.f17173a;
    }

    public int c() {
        return this.f17174b;
    }

    public int d() {
        return this.f17177e;
    }

    public boolean e() {
        return this.f17178f;
    }

    public String f() {
        return this.f17175c;
    }

    private e0(int i2, int i3, int i4, String str, Point point, boolean z2) {
        this.f17173a = i2;
        this.f17174b = i3;
        this.f17177e = i4;
        this.f17175c = str;
        this.f17176d = point;
        this.f17178f = z2;
    }

    public void a(int i2) {
        this.f17177e = i2;
    }

    public Point a() {
        return this.f17176d;
    }

    public void a(Point point) {
        this.f17176d = point;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public e0 a(boolean z2) {
        this.f17179g = z2;
        return this;
    }
}
