package com.huawei.hms.scankit.p;

import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class m6 extends p4 {

    /* renamed from: c, reason: collision with root package name */
    private final byte[] f17555c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17556d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17557e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17558f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17559g;

    public m6(int i2, int i3, ByteBuffer byteBuffer) {
        super(i2, i3);
        this.f17556d = i2;
        this.f17557e = i3;
        this.f17558f = 0;
        this.f17559g = 0;
        byte[] bArrArray = byteBuffer.array();
        int i4 = i2 * i3;
        this.f17555c = new byte[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 4;
            if ((bArrArray[i6 + 3] & 255) == 0) {
                this.f17555c[i5] = -1;
            } else {
                this.f17555c[i5] = (byte) ((((((bArrArray[i6] & 255) * 306) + ((bArrArray[i6 + 1] & 255) * 601)) + ((bArrArray[i6 + 2] & 255) * 117)) + 512) >> 10);
            }
        }
    }

    @Override // com.huawei.hms.scankit.p.p4
    public byte[] a(int i2, byte[] bArr) {
        if (i2 < 0 || i2 >= a()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + i2);
        }
        int iC = c();
        if (bArr == null || bArr.length < iC) {
            bArr = new byte[iC];
        }
        System.arraycopy(this.f17555c, ((i2 + this.f17559g) * this.f17556d) + this.f17558f, bArr, 0, iC);
        return bArr;
    }

    @Override // com.huawei.hms.scankit.p.p4
    public byte[] b() {
        int iC = c();
        int iA = a();
        int i2 = this.f17556d;
        if (iC == i2 && iA == this.f17557e) {
            return this.f17555c;
        }
        int i3 = iC * iA;
        byte[] bArr = new byte[i3];
        int i4 = (this.f17559g * i2) + this.f17558f;
        if (iC == i2) {
            System.arraycopy(this.f17555c, i4, bArr, 0, i3);
            return bArr;
        }
        for (int i5 = 0; i5 < iA; i5++) {
            System.arraycopy(this.f17555c, i4, bArr, i5 * iC, iC);
            i4 += this.f17556d;
        }
        return bArr;
    }

    @Override // com.huawei.hms.scankit.p.p4
    public p4 a(int i2, int i3, int i4, int i5) {
        return new m6(this.f17555c, this.f17556d, this.f17557e, this.f17558f + i2, this.f17559g + i3, i4, i5);
    }

    private m6(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7) {
        super(i6, i7);
        if (i6 + i4 <= i2 && i7 + i5 <= i3) {
            this.f17555c = bArr;
            this.f17556d = i2;
            this.f17557e = i3;
            this.f17558f = i4;
            this.f17559g = i5;
            return;
        }
        throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }
}
