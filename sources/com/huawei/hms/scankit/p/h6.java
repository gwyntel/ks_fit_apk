package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class h6 {

    /* renamed from: a, reason: collision with root package name */
    private u4 f17328a;

    /* renamed from: b, reason: collision with root package name */
    private b3 f17329b;

    /* renamed from: c, reason: collision with root package name */
    private b8 f17330c;

    /* renamed from: d, reason: collision with root package name */
    private int f17331d = -1;

    /* renamed from: e, reason: collision with root package name */
    private c0 f17332e;

    public static boolean a(int i2) {
        return i2 >= 0 && i2 < 8;
    }

    public void b(int i2) {
        this.f17331d = i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append("<<\n");
        sb.append(" mode: ");
        sb.append(this.f17328a);
        sb.append("\n ecLevel: ");
        sb.append(this.f17329b);
        sb.append("\n version: ");
        sb.append(this.f17330c);
        sb.append("\n maskPattern: ");
        sb.append(this.f17331d);
        sb.append(">>\n");
        return sb.toString();
    }

    public c0 a() {
        return this.f17332e;
    }

    public void a(u4 u4Var) {
        this.f17328a = u4Var;
    }

    public void a(b3 b3Var) {
        this.f17329b = b3Var;
    }

    public void a(b8 b8Var) {
        this.f17330c = b8Var;
    }

    public void a(c0 c0Var) {
        this.f17332e = c0Var;
    }
}
