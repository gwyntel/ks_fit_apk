package com.huawei.hms.scankit.p;

/* loaded from: classes4.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    private final o f17650a;

    /* renamed from: b, reason: collision with root package name */
    private s f17651b;

    public p(o oVar) {
        if (oVar == null) {
            throw new IllegalArgumentException("Binarizer must be non-null.");
        }
        this.f17650a = oVar;
    }

    public r a(int i2, int i3) throws a {
        int i4;
        int iE = e();
        if (iE < 45) {
            throw a.a();
        }
        r rVar = new r(iE);
        byte[] bArr = new byte[iE];
        a().c().a(i2, bArr);
        int[] iArr = new int[iE];
        int[] iArr2 = new int[iE];
        int i5 = bArr[0] & 255;
        iArr[0] = i5;
        iArr2[0] = i5 * i5;
        for (int i6 = 1; i6 < iE; i6++) {
            iArr[i6] = iArr[i6 - 1] + (bArr[i6] & 255);
        }
        if (i3 != 0) {
            return a(45, iE, iArr, iArr2, bArr, 22);
        }
        int i7 = 23;
        while (true) {
            i4 = iE - 22;
            if (i7 >= i4) {
                break;
            }
            if ((bArr[i7] & 255) + 5 < (iArr[i7 + 22] - iArr[i7 - 23]) / 45) {
                rVar.g(i7);
            }
            i7++;
        }
        if (rVar.b(23)) {
            rVar.c(0, 23);
        }
        if (rVar.b(iE - 23)) {
            rVar.c(i4, iE);
        }
        return rVar;
    }

    public s b() throws a {
        if (this.f17651b == null) {
            this.f17651b = this.f17650a.a();
        }
        return this.f17651b;
    }

    public int c() {
        return this.f17650a.b();
    }

    public byte[] d() {
        return this.f17650a.c().b();
    }

    public int e() {
        return this.f17650a.d();
    }

    private r a(int i2, int i3, int[] iArr, int[] iArr2, byte[] bArr, int i4) {
        int i5;
        r rVar = new r(i3);
        for (int i6 = 1; i6 < i3; i6++) {
            int i7 = iArr2[i6 - 1];
            byte b2 = bArr[i6];
            iArr2[i6] = i7 + ((b2 & 255) * (b2 & 255));
        }
        int i8 = i4 + 1;
        int i9 = i8;
        while (true) {
            i5 = i3 - i4;
            if (i9 >= i5) {
                break;
            }
            double d2 = iArr[i9 + i4] - iArr[(i9 - i4) - 1];
            double d3 = i2;
            if ((bArr[i9] & 255) <= (d2 / d3) * ((0.5f * (Math.sqrt(((iArr2[r6] - iArr2[r8]) - ((d2 * d2) / d3)) / (i2 - 1)) / 127)) + 1.0d)) {
                rVar.g(i9);
            }
            i9++;
        }
        if (rVar.b(i8)) {
            rVar.c(0, i8);
        }
        if (rVar.b(i5 - 1)) {
            rVar.c(i5, i3);
        }
        return rVar;
    }

    public r a(int i2, r rVar) throws a {
        return this.f17650a.a(i2, rVar);
    }

    public void a(s sVar) {
        this.f17651b = sVar;
    }

    public p a(int i2, int i3, int i4, int i5) {
        return new p(this.f17650a.a(this.f17650a.c().a(i2, i3, i4, i5)));
    }

    public o a() {
        return this.f17650a;
    }
}
