package com.huawei.hms.scankit.p;

import java.nio.charset.StandardCharsets;

/* loaded from: classes4.dex */
final class y2 {

    /* renamed from: a, reason: collision with root package name */
    private final String f18016a;

    /* renamed from: b, reason: collision with root package name */
    private e7 f18017b;

    /* renamed from: c, reason: collision with root package name */
    private l2 f18018c;

    /* renamed from: d, reason: collision with root package name */
    private l2 f18019d;

    /* renamed from: e, reason: collision with root package name */
    private final StringBuilder f18020e;

    /* renamed from: f, reason: collision with root package name */
    int f18021f;

    /* renamed from: g, reason: collision with root package name */
    private int f18022g;

    /* renamed from: h, reason: collision with root package name */
    private d7 f18023h;

    /* renamed from: i, reason: collision with root package name */
    private int f18024i;

    y2(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c2 = (char) (bytes[i2] & 255);
            if (c2 == '?' && str.charAt(i2) != '?') {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            sb.append(c2);
        }
        this.f18016a = sb.toString();
        this.f18017b = e7.FORCE_NONE;
        this.f18020e = new StringBuilder(str.length());
        this.f18022g = -1;
    }

    private int h() {
        return this.f18016a.length() - this.f18024i;
    }

    public void a(e7 e7Var) {
        this.f18017b = e7Var;
    }

    public StringBuilder b() {
        return this.f18020e;
    }

    public char c() {
        return this.f18016a.charAt(this.f18021f);
    }

    public String d() {
        return this.f18016a;
    }

    public int e() {
        return this.f18022g;
    }

    public int f() {
        return h() - this.f18021f;
    }

    public d7 g() {
        return this.f18023h;
    }

    public boolean i() {
        return this.f18021f < h();
    }

    public void j() {
        this.f18022g = -1;
    }

    public void k() {
        this.f18023h = null;
    }

    public void l() {
        c(a());
    }

    public void a(l2 l2Var, l2 l2Var2) {
        this.f18018c = l2Var;
        this.f18019d = l2Var2;
    }

    public void b(int i2) {
        this.f18022g = i2;
    }

    public void c(int i2) {
        d7 d7Var = this.f18023h;
        if (d7Var == null || i2 > d7Var.a()) {
            this.f18023h = d7.a(i2, this.f18017b, this.f18018c, this.f18019d, true);
        }
    }

    public void a(int i2) {
        this.f18024i = i2;
    }

    public void a(String str) {
        this.f18020e.append(str);
    }

    public void a(char c2) {
        this.f18020e.append(c2);
    }

    public int a() {
        return this.f18020e.length();
    }
}
