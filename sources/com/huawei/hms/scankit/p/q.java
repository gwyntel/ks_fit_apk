package com.huawei.hms.scankit.p;

import kotlin.text.Typography;

/* loaded from: classes4.dex */
final class q extends i7 {

    /* renamed from: c, reason: collision with root package name */
    private final short f17671c;

    /* renamed from: d, reason: collision with root package name */
    private final short f17672d;

    q(i7 i7Var, int i2, int i3) {
        super(i7Var);
        this.f17671c = (short) i2;
        this.f17672d = (short) i3;
    }

    @Override // com.huawei.hms.scankit.p.i7
    public void a(r rVar, byte[] bArr) {
        int i2 = 0;
        while (true) {
            short s2 = this.f17672d;
            if (i2 >= s2) {
                return;
            }
            if (i2 == 0 || (i2 == 31 && s2 <= 62)) {
                rVar.a(31, 5);
                short s3 = this.f17672d;
                if (s3 > 62) {
                    rVar.a(s3 - 31, 16);
                } else if (i2 == 0) {
                    rVar.a(Math.min((int) s3, 31), 5);
                } else {
                    rVar.a(s3 - 31, 5);
                }
            }
            rVar.a(bArr[this.f17671c + i2], 8);
            i2++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append((int) this.f17671c);
        sb.append("::");
        sb.append((this.f17671c + this.f17672d) - 1);
        sb.append(Typography.greater);
        return sb.toString();
    }
}
