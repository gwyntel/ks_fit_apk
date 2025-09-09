package com.huawei.hms.scankit.p;

import java.util.Arrays;

/* loaded from: classes4.dex */
public class y1 {

    /* renamed from: a, reason: collision with root package name */
    private final CharSequence f18012a;

    /* renamed from: b, reason: collision with root package name */
    private final int f18013b;

    /* renamed from: c, reason: collision with root package name */
    private final int f18014c;

    /* renamed from: d, reason: collision with root package name */
    private final byte[] f18015d;

    public y1(CharSequence charSequence, int i2, int i3) {
        this.f18012a = charSequence;
        this.f18014c = i2;
        this.f18013b = i3;
        byte[] bArr = new byte[i2 * i3];
        this.f18015d = bArr;
        Arrays.fill(bArr, (byte) -1);
    }

    private boolean b(int i2, int i3) {
        return this.f18015d[(i3 * this.f18014c) + i2] >= 0;
    }

    private void c(int i2) {
        a(this.f18013b - 3, 0, i2, 1);
        a(this.f18013b - 2, 0, i2, 2);
        a(this.f18013b - 1, 0, i2, 3);
        a(0, this.f18014c - 2, i2, 4);
        a(0, this.f18014c - 1, i2, 5);
        a(1, this.f18014c - 1, i2, 6);
        a(2, this.f18014c - 1, i2, 7);
        a(3, this.f18014c - 1, i2, 8);
    }

    private void d(int i2) {
        a(this.f18013b - 1, 0, i2, 1);
        a(this.f18013b - 1, this.f18014c - 1, i2, 2);
        a(0, this.f18014c - 3, i2, 3);
        a(0, this.f18014c - 2, i2, 4);
        a(0, this.f18014c - 1, i2, 5);
        a(1, this.f18014c - 3, i2, 6);
        a(1, this.f18014c - 2, i2, 7);
        a(1, this.f18014c - 1, i2, 8);
    }

    public final boolean a(int i2, int i3) {
        return this.f18015d[(i3 * this.f18014c) + i2] == 1;
    }

    private void a(int i2, int i3, boolean z2) {
        this.f18015d[(i3 * this.f18014c) + i2] = z2 ? (byte) 1 : (byte) 0;
    }

    private void b(int i2) {
        a(this.f18013b - 3, 0, i2, 1);
        a(this.f18013b - 2, 0, i2, 2);
        a(this.f18013b - 1, 0, i2, 3);
        a(0, this.f18014c - 4, i2, 4);
        a(0, this.f18014c - 3, i2, 5);
        a(0, this.f18014c - 2, i2, 6);
        a(0, this.f18014c - 1, i2, 7);
        a(1, this.f18014c - 1, i2, 8);
    }

    public final void a() {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 4;
        while (true) {
            if (i6 == this.f18013b && i4 == 0) {
                a(i5);
                i5++;
            }
            if (i6 == this.f18013b - 2 && i4 == 0 && this.f18014c % 4 != 0) {
                b(i5);
                i5++;
            }
            if (i6 == this.f18013b - 2 && i4 == 0 && this.f18014c % 8 == 4) {
                c(i5);
                i5++;
            }
            if (i6 == this.f18013b + 4 && i4 == 2 && this.f18014c % 8 == 0) {
                d(i5);
                i5++;
            }
            while (true) {
                if (i6 < this.f18013b && i4 >= 0 && !b(i4, i6)) {
                    a(i6, i4, i5);
                    i5++;
                }
                int i7 = i6 - 2;
                int i8 = i4 + 2;
                if (i7 < 0 || i8 >= this.f18014c) {
                    break;
                }
                i6 = i7;
                i4 = i8;
            }
            int i9 = i6 - 1;
            int i10 = i4 + 5;
            while (true) {
                if (i9 >= 0 && i10 < this.f18014c && !b(i10, i9)) {
                    a(i9, i10, i5);
                    i5++;
                }
                int i11 = i9 + 2;
                int i12 = i10 - 2;
                i2 = this.f18013b;
                if (i11 >= i2 || i12 < 0) {
                    break;
                }
                i9 = i11;
                i10 = i12;
            }
            i6 = i9 + 5;
            i4 = i10 - 1;
            if (i6 >= i2 && i4 >= (i3 = this.f18014c)) {
                break;
            }
        }
        if (b(i3 - 1, i2 - 1)) {
            return;
        }
        a(this.f18014c - 1, this.f18013b - 1, true);
        a(this.f18014c - 2, this.f18013b - 2, true);
    }

    private void a(int i2, int i3, int i4, int i5) {
        if (i2 < 0) {
            int i6 = this.f18013b;
            i2 += i6;
            i3 += 4 - ((i6 + 4) % 8);
        }
        if (i3 < 0) {
            int i7 = this.f18014c;
            i3 += i7;
            i2 += 4 - ((i7 + 4) % 8);
        }
        a(i3, i2, (this.f18012a.charAt(i4) & (1 << (8 - i5))) != 0);
    }

    private void a(int i2, int i3, int i4) {
        int i5 = i2 - 2;
        int i6 = i3 - 2;
        a(i5, i6, i4, 1);
        int i7 = i3 - 1;
        a(i5, i7, i4, 2);
        int i8 = i2 - 1;
        a(i8, i6, i4, 3);
        a(i8, i7, i4, 4);
        a(i8, i3, i4, 5);
        a(i2, i6, i4, 6);
        a(i2, i7, i4, 7);
        a(i2, i3, i4, 8);
    }

    private void a(int i2) {
        a(this.f18013b - 1, 0, i2, 1);
        a(this.f18013b - 1, 1, i2, 2);
        a(this.f18013b - 1, 2, i2, 3);
        a(0, this.f18014c - 2, i2, 4);
        a(0, this.f18014c - 1, i2, 5);
        a(1, this.f18014c - 1, i2, 6);
        a(2, this.f18014c - 1, i2, 7);
        a(3, this.f18014c - 1, i2, 8);
    }
}
