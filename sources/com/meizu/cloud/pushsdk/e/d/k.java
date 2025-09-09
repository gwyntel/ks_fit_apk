package com.meizu.cloud.pushsdk.e.d;

import com.meizu.cloud.pushsdk.e.d.c;

/* loaded from: classes4.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private final i f19452a;

    /* renamed from: b, reason: collision with root package name */
    private final int f19453b;

    /* renamed from: c, reason: collision with root package name */
    private final String f19454c;

    /* renamed from: d, reason: collision with root package name */
    private final c f19455d;

    /* renamed from: e, reason: collision with root package name */
    private final l f19456e;

    /* renamed from: f, reason: collision with root package name */
    private final k f19457f;

    /* renamed from: g, reason: collision with root package name */
    private final k f19458g;

    /* renamed from: h, reason: collision with root package name */
    private final k f19459h;

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private i f19460a;

        /* renamed from: c, reason: collision with root package name */
        private String f19462c;

        /* renamed from: e, reason: collision with root package name */
        private l f19464e;

        /* renamed from: f, reason: collision with root package name */
        private k f19465f;

        /* renamed from: g, reason: collision with root package name */
        private k f19466g;

        /* renamed from: h, reason: collision with root package name */
        private k f19467h;

        /* renamed from: b, reason: collision with root package name */
        private int f19461b = -1;

        /* renamed from: d, reason: collision with root package name */
        private c.b f19463d = new c.b();

        public b a(int i2) {
            this.f19461b = i2;
            return this;
        }

        public b a(c cVar) {
            this.f19463d = cVar.b();
            return this;
        }

        public b a(i iVar) {
            this.f19460a = iVar;
            return this;
        }

        public b a(l lVar) {
            this.f19464e = lVar;
            return this;
        }

        public b a(String str) {
            this.f19462c = str;
            return this;
        }

        public k a() {
            if (this.f19460a == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.f19461b >= 0) {
                return new k(this);
            }
            throw new IllegalStateException("code < 0: " + this.f19461b);
        }
    }

    private k(b bVar) {
        this.f19452a = bVar.f19460a;
        this.f19453b = bVar.f19461b;
        this.f19454c = bVar.f19462c;
        this.f19455d = bVar.f19463d.a();
        this.f19456e = bVar.f19464e;
        this.f19457f = bVar.f19465f;
        this.f19458g = bVar.f19466g;
        this.f19459h = bVar.f19467h;
    }

    public l a() {
        return this.f19456e;
    }

    public int b() {
        return this.f19453b;
    }

    public String toString() {
        return "Response{protocol=, code=" + this.f19453b + ", message=" + this.f19454c + ", url=" + this.f19452a.e() + '}';
    }
}
