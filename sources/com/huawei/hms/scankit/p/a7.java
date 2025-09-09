package com.huawei.hms.scankit.p;

import kotlin.text.Typography;

/* loaded from: classes4.dex */
final class a7 extends i7 {

    /* renamed from: c, reason: collision with root package name */
    private final short f16976c;

    /* renamed from: d, reason: collision with root package name */
    private final short f16977d;

    a7(i7 i7Var, int i2, int i3) {
        super(i7Var);
        this.f16976c = (short) i2;
        this.f16977d = (short) i3;
    }

    @Override // com.huawei.hms.scankit.p.i7
    void a(r rVar, byte[] bArr) {
        rVar.a(this.f16976c, this.f16977d);
    }

    public String toString() {
        short s2 = this.f16976c;
        int i2 = 1 << this.f16977d;
        return Typography.less + Integer.toBinaryString((s2 & (i2 - 1)) | i2 | (1 << this.f16977d)).substring(1) + Typography.greater;
    }
}
