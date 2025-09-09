package com.huawei.hms.scankit.p;

import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
public final class q0 extends h5 {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f17673a;

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f17674b = {'T', 'N', '*', 'E'};

    /* renamed from: c, reason: collision with root package name */
    private static final char[] f17675c = {IOUtils.DIR_SEPARATOR_UNIX, ':', '+', '.'};

    /* renamed from: d, reason: collision with root package name */
    private static final char f17676d;

    static {
        char[] cArr = {'A', 'B', 'C', 'D'};
        f17673a = cArr;
        f17676d = cArr[0];
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int i2;
        if (str.length() < 2) {
            StringBuilder sb = new StringBuilder();
            char c2 = f17676d;
            sb.append(c2);
            sb.append(str);
            sb.append(c2);
            str = sb.toString();
        } else {
            char upperCase = Character.toUpperCase(str.charAt(0));
            char upperCase2 = Character.toUpperCase(str.charAt(str.length() - 1));
            char[] cArr = f17673a;
            boolean zA = p0.a(cArr, upperCase);
            boolean zA2 = p0.a(cArr, upperCase2);
            char[] cArr2 = f17674b;
            boolean zA3 = p0.a(cArr2, upperCase);
            boolean zA4 = p0.a(cArr2, upperCase2);
            if (zA) {
                if (!zA2) {
                    throw new IllegalArgumentException("Invalid start/end guards: error contents");
                }
            } else if (!zA3) {
                if (zA2 || zA4) {
                    throw new IllegalArgumentException("Invalid start/end guards: error contents");
                }
                StringBuilder sb2 = new StringBuilder();
                char c3 = f17676d;
                sb2.append(c3);
                sb2.append(str);
                sb2.append(c3);
                str = sb2.toString();
            } else if (!zA4) {
                throw new IllegalArgumentException("Invalid start/end guards: error contents");
            }
        }
        int i3 = 20;
        for (int i4 = 1; i4 < str.length() - 1; i4++) {
            if (Character.isDigit(str.charAt(i4)) || str.charAt(i4) == '-' || str.charAt(i4) == '$') {
                i3 += 9;
            } else {
                if (!p0.a(f17675c, str.charAt(i4))) {
                    throw new IllegalArgumentException("Cannot encode : '" + str.charAt(i4) + '\'');
                }
                i3 += 10;
            }
        }
        boolean[] zArr = new boolean[i3 + (str.length() - 1)];
        int i5 = 0;
        for (int i6 = 0; i6 < str.length(); i6++) {
            char upperCase3 = Character.toUpperCase(str.charAt(i6));
            if (i6 == 0 || i6 == str.length() - 1) {
                if (upperCase3 == '*') {
                    upperCase3 = 'C';
                } else if (upperCase3 == 'E') {
                    upperCase3 = 'D';
                } else if (upperCase3 == 'N') {
                    upperCase3 = 'B';
                } else if (upperCase3 == 'T') {
                    upperCase3 = 'A';
                }
            }
            int i7 = 0;
            while (true) {
                char[] cArr3 = p0.f17652e;
                if (i7 >= cArr3.length) {
                    i2 = 0;
                    break;
                }
                if (upperCase3 == cArr3[i7]) {
                    i2 = p0.f17653f[i7];
                    break;
                }
                i7++;
            }
            int i8 = 0;
            int i9 = 0;
            boolean z2 = true;
            while (i8 < 7) {
                zArr[i5] = z2;
                i5++;
                if (((i2 >> (6 - i8)) & 1) == 0 || i9 == 1) {
                    z2 = !z2;
                    i8++;
                    i9 = 0;
                } else {
                    i9++;
                }
            }
            if (i6 < str.length() - 1) {
                zArr[i5] = false;
                i5++;
            }
        }
        return zArr;
    }
}
