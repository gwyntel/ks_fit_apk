package com.huawei.hms.scankit.p;

import java.util.List;

/* loaded from: classes4.dex */
public final class w1 {

    /* renamed from: a, reason: collision with root package name */
    private final byte[] f17905a;

    /* renamed from: b, reason: collision with root package name */
    private int f17906b;

    /* renamed from: c, reason: collision with root package name */
    private final String f17907c;

    /* renamed from: d, reason: collision with root package name */
    private final List<byte[]> f17908d;

    /* renamed from: e, reason: collision with root package name */
    private final String f17909e;

    /* renamed from: f, reason: collision with root package name */
    private Integer f17910f;

    /* renamed from: g, reason: collision with root package name */
    private Integer f17911g;

    /* renamed from: h, reason: collision with root package name */
    private Object f17912h;

    /* renamed from: i, reason: collision with root package name */
    private final int f17913i;

    /* renamed from: j, reason: collision with root package name */
    private final int f17914j;

    public w1(byte[] bArr, String str, List<byte[]> list, String str2) {
        this(bArr, str, list, str2, -1, -1);
    }

    public int a() {
        return this.f17906b;
    }

    public void b(Integer num) {
        this.f17910f = num;
    }

    public byte[] c() {
        return this.f17905a;
    }

    public String d() {
        return this.f17907c;
    }

    public w1(byte[] bArr, String str, List<byte[]> list, String str2, int i2, int i3) {
        this.f17905a = bArr;
        this.f17906b = bArr == null ? 0 : bArr.length * 8;
        this.f17907c = str;
        this.f17908d = list;
        this.f17909e = str2;
        this.f17913i = i3;
        this.f17914j = i2;
    }

    public void a(int i2) {
        this.f17906b = i2;
    }

    public Object b() {
        return this.f17912h;
    }

    public void a(Integer num) {
        this.f17911g = num;
    }

    public void a(Object obj) {
        this.f17912h = obj;
    }
}
