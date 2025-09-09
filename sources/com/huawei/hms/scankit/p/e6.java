package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class e6 extends p4 {

    /* renamed from: c, reason: collision with root package name */
    private final byte[] f17206c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17207d;

    /* renamed from: e, reason: collision with root package name */
    private final int f17208e;

    /* renamed from: f, reason: collision with root package name */
    private final int f17209f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17210g;

    public e6(byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2) {
        super(i6, i7);
        if (i4 + i6 > i2 || i5 + i7 > i3) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.f17206c = bArr;
        this.f17207d = i2;
        this.f17208e = i3;
        this.f17209f = i4;
        this.f17210g = i5;
        if (z2) {
            a(i6, i7);
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
        System.arraycopy(this.f17206c, ((i2 + this.f17210g) * this.f17207d) + this.f17209f, bArr, 0, iC);
        return bArr;
    }

    @Override // com.huawei.hms.scankit.p.p4
    public byte[] b() {
        int iC = c();
        int iA = a();
        int i2 = this.f17207d;
        if (iC == i2 && iA == this.f17208e) {
            return this.f17206c;
        }
        int i3 = iC * iA;
        byte[] bArr = new byte[i3];
        int i4 = (this.f17210g * i2) + this.f17209f;
        if (iC == i2) {
            try {
                System.arraycopy(this.f17206c, i4, bArr, 0, i3);
                return bArr;
            } catch (ArrayIndexOutOfBoundsException | Exception unused) {
                return null;
            }
        }
        for (int i5 = 0; i5 < iA; i5++) {
            try {
                System.arraycopy(this.f17206c, i4, bArr, i5 * iC, iC);
                i4 += this.f17207d;
            } catch (ArrayIndexOutOfBoundsException | Exception unused2) {
                return null;
            }
        }
        return bArr;
    }

    @Override // com.huawei.hms.scankit.p.p4
    public p4 a(int i2, int i3, int i4, int i5) {
        return new e6(this.f17206c, this.f17207d, this.f17208e, this.f17209f + i2, this.f17210g + i3, i4, i5, false);
    }

    private void a(int i2, int i3) {
        byte[] bArr = this.f17206c;
        int i4 = (this.f17210g * this.f17207d) + this.f17209f;
        int i5 = 0;
        while (i5 < i3) {
            int i6 = (i2 / 2) + i4;
            int i7 = (i4 + i2) - 1;
            int i8 = i4;
            while (i8 < i6) {
                if (w7.a(bArr, i8) && w7.a(bArr, i7)) {
                    byte b2 = bArr[i8];
                    bArr[i8] = bArr[i7];
                    bArr[i7] = b2;
                }
                i8++;
                i7--;
            }
            i5++;
            i4 += this.f17207d;
        }
    }
}
