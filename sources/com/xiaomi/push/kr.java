package com.xiaomi.push;

import java.io.IOException;

/* loaded from: classes4.dex */
public class kr extends kt {

    /* renamed from: a, reason: collision with root package name */
    private int f24394a;

    /* renamed from: a, reason: collision with other field name */
    private kb f927a;

    public kr(int i2) {
        this.f927a = new kb(i2);
    }

    @Override // com.xiaomi.push.kt
    public int a(byte[] bArr, int i2, int i3) {
        byte[] bArrM666a = this.f927a.m666a();
        if (i3 > this.f927a.a() - this.f24394a) {
            i3 = this.f927a.a() - this.f24394a;
        }
        if (i3 > 0) {
            System.arraycopy(bArrM666a, this.f24394a, bArr, i2, i3);
            this.f24394a += i3;
        }
        return i3;
    }

    public int a_() {
        return this.f927a.size();
    }

    @Override // com.xiaomi.push.kt
    /* renamed from: a */
    public void mo681a(byte[] bArr, int i2, int i3) throws IOException {
        this.f927a.write(bArr, i2, i3);
    }
}
