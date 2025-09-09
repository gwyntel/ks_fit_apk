package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class p0 extends g5 {

    /* renamed from: e, reason: collision with root package name */
    public static final char[] f17652e = "0123456789-$:/.+ABCD".toCharArray();

    /* renamed from: f, reason: collision with root package name */
    public static final int[] f17653f = {3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};

    /* renamed from: g, reason: collision with root package name */
    private static final char[] f17654g = {'A', 'B', 'C', 'D'};

    /* renamed from: a, reason: collision with root package name */
    private final StringBuilder f17655a = new StringBuilder(20);

    /* renamed from: b, reason: collision with root package name */
    private int[] f17656b = new int[80];

    /* renamed from: c, reason: collision with root package name */
    private int f17657c = 0;

    /* renamed from: d, reason: collision with root package name */
    private int f17658d;

    private int b() throws a {
        for (int i2 = 1; i2 < this.f17657c; i2 += 2) {
            int iB = b(i2);
            if (iB != -1 && a(f17654g, f17652e[iB])) {
                int i3 = 0;
                for (int i4 = i2; i4 < i2 + 7; i4++) {
                    i3 += this.f17656b[i4];
                }
                if (i2 == 1 || this.f17656b[i2 - 1] >= i3 / 2) {
                    return i2;
                }
            }
        }
        throw a.a();
    }

    private void c(int i2) throws a {
        int[] iArr = new int[4];
        int i3 = 0;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        int[] iArr2 = new int[4];
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int length = this.f17655a.length() - 1;
        int i4 = i2;
        int i5 = 0;
        while (true) {
            int i6 = f17653f[this.f17655a.charAt(i5)];
            for (int i7 = 6; i7 >= 0; i7--) {
                int i8 = (i7 & 1) + ((i6 & 1) * 2);
                iArr[i8] = iArr[i8] + this.f17656b[i4 + i7];
                iArr2[i8] = iArr2[i8] + 1;
                i6 >>= 1;
            }
            if (i5 >= length) {
                break;
            }
            i4 += 8;
            i5++;
        }
        float[] fArr = new float[4];
        float[] fArr2 = new float[4];
        for (int i9 = 0; i9 < 2; i9++) {
            fArr2[i9] = 0.0f;
            int i10 = i9 + 2;
            int i11 = iArr[i10];
            int i12 = iArr2[i10];
            float f2 = ((iArr[i9] / iArr2[i9]) + (i11 / i12)) / 2.0f;
            fArr2[i10] = f2;
            fArr[i9] = f2;
            fArr[i10] = ((i11 * 2.0f) + 1.5f) / i12;
        }
        int i13 = i2;
        loop3: while (true) {
            int i14 = f17653f[this.f17655a.charAt(i3)];
            for (int i15 = 6; i15 >= 0; i15--) {
                int i16 = (i15 & 1) + ((i14 & 1) * 2);
                float f3 = this.f17656b[i13 + i15];
                if (f3 < fArr2[i16] || f3 > fArr[i16]) {
                    break loop3;
                }
                i14 >>= 1;
            }
            if (i3 >= length) {
                return;
            }
            i13 += 8;
            i3++;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        Arrays.fill(this.f17656b, 0);
        a(rVar);
        int[] iArrA = a();
        int i3 = iArrA[0];
        int i4 = iArrA[1];
        for (int i5 = 0; i5 < this.f17655a.length(); i5++) {
            StringBuilder sb = this.f17655a;
            sb.setCharAt(i5, f17652e[sb.charAt(i5)]);
        }
        char cCharAt = this.f17655a.charAt(0);
        char[] cArr = f17654g;
        if (!a(cArr, cCharAt)) {
            throw a.a();
        }
        StringBuilder sb2 = this.f17655a;
        if (!a(cArr, sb2.charAt(sb2.length() - 1))) {
            throw a.a();
        }
        if (this.f17655a.length() <= 3) {
            throw a.a();
        }
        int i6 = this.f17658d;
        for (int i7 = 0; i7 < i3; i7++) {
            i6 += this.f17656b[i7];
        }
        float f2 = i6;
        while (i3 < i4 - 1) {
            i6 += this.f17656b[i3];
            i3++;
        }
        float f3 = i2;
        return new s6(this.f17655a.toString(), null, new u6[]{new u6(f2, f3), new u6(i6, f3)}, BarcodeFormat.CODABAR);
    }

    private int b(int i2) {
        int i3 = i2 + 7;
        if (i3 >= this.f17657c) {
            return -1;
        }
        int[] iArr = this.f17656b;
        HashSet hashSet = new HashSet();
        for (int i4 = i2; i4 < i3; i4++) {
            hashSet.add(Integer.valueOf(iArr[i4]));
        }
        Iterator it = hashSet.iterator();
        int i5 = 0;
        int iIntValue = 0;
        while (it.hasNext()) {
            iIntValue += ((Integer) it.next()).intValue();
        }
        if (hashSet.size() > 0) {
            int size = iIntValue / hashSet.size();
            int i6 = 128;
            int i7 = 0;
            for (int i8 = 0; i8 < 7; i8++) {
                i6 >>= 1;
                if (iArr[i2 + i8] > size) {
                    i7 |= i6;
                }
            }
            while (true) {
                int[] iArr2 = f17653f;
                if (i5 >= iArr2.length) {
                    break;
                }
                if (iArr2[i5] == i7) {
                    return i5;
                }
                i5++;
            }
        }
        return -1;
    }

    private int[] a() throws a {
        int i2;
        int iB = b();
        int i3 = 0;
        this.f17655a.setLength(0);
        int i4 = iB;
        while (true) {
            int iB2 = b(i4);
            if (iB2 != -1) {
                this.f17655a.append((char) iB2);
                i2 = i4 + 8;
                if ((this.f17655a.length() > 1 && a(f17654g, f17652e[iB2])) || i2 >= this.f17657c) {
                    break;
                }
                i4 = i2;
            } else {
                throw a.a();
            }
        }
        int i5 = this.f17656b[i4 + 7];
        for (int i6 = -8; i6 < -1; i6++) {
            i3 += this.f17656b[i2 + i6];
        }
        if (i2 < this.f17657c && i5 < i3 / 2) {
            throw a.a();
        }
        c(iB);
        return new int[]{iB, i2};
    }

    private void a(r rVar) throws a {
        int i2 = 0;
        this.f17657c = 0;
        int iD = rVar.d(0);
        this.f17658d = iD;
        int iE = rVar.e();
        if (iD < iE) {
            boolean z2 = true;
            while (iD < iE) {
                if (rVar.b(iD) != z2) {
                    i2++;
                } else {
                    a(i2);
                    z2 = !z2;
                    i2 = 1;
                }
                iD++;
            }
            a(i2);
            return;
        }
        throw a.a();
    }

    private void a(int i2) throws a {
        try {
            int[] iArr = this.f17656b;
            int i3 = this.f17657c;
            iArr[i3] = i2;
            int i4 = i3 + 1;
            this.f17657c = i4;
            if (i4 >= iArr.length) {
                int[] iArr2 = new int[i4 * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i4);
                this.f17656b = iArr2;
            }
        } catch (NumberFormatException unused) {
            throw a.a();
        }
    }

    public static boolean a(char[] cArr, char c2) {
        if (cArr != null) {
            for (char c3 : cArr) {
                if (c3 == c2) {
                    return true;
                }
            }
        }
        return false;
    }
}
