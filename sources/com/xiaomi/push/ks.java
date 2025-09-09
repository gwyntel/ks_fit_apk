package com.xiaomi.push;

/* loaded from: classes4.dex */
public final class ks extends kt {

    /* renamed from: a, reason: collision with root package name */
    private int f24395a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f928a;

    /* renamed from: b, reason: collision with root package name */
    private int f24396b;

    public void a(byte[] bArr) {
        b(bArr, 0, bArr.length);
    }

    public void b(byte[] bArr, int i2, int i3) {
        this.f928a = bArr;
        this.f24395a = i2;
        this.f24396b = i2 + i3;
    }

    @Override // com.xiaomi.push.kt
    public int a(byte[] bArr, int i2, int i3) {
        int iB = b();
        if (i3 > iB) {
            i3 = iB;
        }
        if (i3 > 0) {
            System.arraycopy(this.f928a, this.f24395a, bArr, i2, i3);
            a(i3);
        }
        return i3;
    }

    @Override // com.xiaomi.push.kt
    public int b() {
        return this.f24396b - this.f24395a;
    }

    @Override // com.xiaomi.push.kt
    /* renamed from: a */
    public void mo681a(byte[] bArr, int i2, int i3) {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    @Override // com.xiaomi.push.kt
    /* renamed from: a, reason: collision with other method in class */
    public byte[] mo682a() {
        return this.f928a;
    }

    @Override // com.xiaomi.push.kt
    public int a() {
        return this.f24395a;
    }

    @Override // com.xiaomi.push.kt
    public void a(int i2) {
        this.f24395a += i2;
    }
}
