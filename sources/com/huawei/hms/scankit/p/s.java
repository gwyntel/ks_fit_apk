package com.huawei.hms.scankit.p;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class s implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private final int f17742a;

    /* renamed from: b, reason: collision with root package name */
    private final int f17743b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17744c;

    /* renamed from: d, reason: collision with root package name */
    private final int[] f17745d;

    public s(int i2) {
        this(i2, i2);
    }

    public void a(int i2, int i3) {
        int i4 = (i3 * this.f17744c) + (i2 / 32);
        if (w7.a(this.f17745d, i4)) {
            int[] iArr = this.f17745d;
            iArr[i4] = (1 << (i2 & 31)) ^ iArr[i4];
        }
    }

    public boolean b(int i2, int i3) {
        int i4 = (i3 * this.f17744c) + (i2 / 32);
        return w7.a(this.f17745d, i4) && ((this.f17745d[i4] >>> (i2 & 31)) & 1) != 0;
    }

    public void c(int i2, int i3) {
        int i4 = (i3 * this.f17744c) + (i2 / 32);
        if (w7.a(this.f17745d, i4)) {
            int[] iArr = this.f17745d;
            iArr[i4] = (1 << (i2 & 31)) | iArr[i4];
        }
    }

    public s d() {
        int[] iArr = new int[this.f17745d.length];
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.f17745d;
            if (i2 >= iArr2.length) {
                return new s(this.f17742a, this.f17743b, this.f17744c, iArr);
            }
            iArr[i2] = ~iArr2[i2];
            i2++;
        }
    }

    public int e() {
        return this.f17742a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return this.f17742a == sVar.f17742a && this.f17743b == sVar.f17743b && this.f17744c == sVar.f17744c && Arrays.equals(this.f17745d, sVar.f17745d);
    }

    public void f() {
        int iE = e();
        int iC = c();
        r rVar = new r(iE);
        r rVar2 = new r(iE);
        for (int i2 = 0; i2 < (iC + 1) / 2; i2++) {
            rVar = a(i2, rVar);
            int i3 = (iC - 1) - i2;
            rVar2 = a(i3, rVar2);
            rVar.h();
            rVar2.h();
            b(i2, rVar2);
            b(i3, rVar);
        }
    }

    public int hashCode() {
        int i2 = this.f17742a;
        return (((((((i2 * 31) + i2) * 31) + this.f17743b) * 31) + this.f17744c) * 31) + Arrays.hashCode(this.f17745d);
    }

    public String toString() {
        return a("X ", "  ");
    }

    public s(int i2, int i3) {
        if (i2 < 1 || i3 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.f17742a = i2;
        this.f17743b = i3;
        int i4 = (i2 + 31) / 32;
        this.f17744c = i4;
        this.f17745d = new int[i4 * i3];
    }

    public void a() {
        int length = this.f17745d.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.f17745d[i2] = 0;
        }
    }

    public void b(int i2, r rVar) {
        int[] iArrD = rVar.d();
        int[] iArr = this.f17745d;
        int i3 = this.f17744c;
        System.arraycopy(iArrD, 0, iArr, i2 * i3, i3);
    }

    public int c() {
        return this.f17743b;
    }

    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public s clone() {
        return new s(this.f17742a, this.f17743b, this.f17744c, (int[]) this.f17745d.clone());
    }

    public void a(int i2, int i3, int i4, int i5) {
        if (i3 < 0 || i2 < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i5 >= 1 && i4 >= 1) {
            int i6 = i4 + i2;
            int i7 = i5 + i3;
            if (i7 > this.f17743b || i6 > this.f17742a) {
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            while (i3 < i7) {
                int i8 = this.f17744c * i3;
                for (int i9 = i2; i9 < i6; i9++) {
                    int[] iArr = this.f17745d;
                    int i10 = (i9 / 32) + i8;
                    iArr[i10] = iArr[i10] | (1 << (i9 & 31));
                }
                i3++;
            }
            return;
        }
        throw new IllegalArgumentException("Height and width must be at least 1");
    }

    public s(int i2, int i3, int i4, int[] iArr) {
        this.f17742a = i2;
        this.f17743b = i3;
        this.f17744c = i4;
        this.f17745d = iArr;
    }

    public r a(int i2, r rVar) {
        if (rVar != null && rVar.e() >= this.f17742a) {
            rVar.a();
        } else {
            rVar = new r(this.f17742a);
        }
        int i3 = i2 * this.f17744c;
        for (int i4 = 0; i4 < this.f17744c; i4++) {
            rVar.b(i4 * 32, this.f17745d[i3 + i4]);
        }
        return rVar;
    }

    public String a(String str, String str2) {
        return a(str, str2, "\n");
    }

    private String a(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(this.f17743b * (this.f17742a + 1));
        for (int i2 = 0; i2 < this.f17743b; i2++) {
            for (int i3 = 0; i3 < this.f17742a; i3++) {
                sb.append(b(i3, i2) ? str : str2);
            }
            sb.append(str3);
        }
        return sb.toString();
    }
}
