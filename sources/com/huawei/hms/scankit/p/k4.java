package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class k4 extends h5 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[] f17479a = {1, 1, 1, 1};

    /* renamed from: b, reason: collision with root package name */
    private static final int[] f17480b = {3, 1, 1};

    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.ITF) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode ITF, but got " + barcodeFormat);
    }

    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The length of the input should be even");
        }
        if (length <= 80) {
            boolean[] zArr = new boolean[(length * 9) + 9];
            int iA = h5.a(zArr, 0, f17479a, true);
            for (int i2 = 0; i2 < length; i2 += 2) {
                int iDigit = Character.digit(str.charAt(i2), 10);
                int iDigit2 = Character.digit(str.charAt(i2 + 1), 10);
                int[] iArr = new int[10];
                for (int i3 = 0; i3 < 5; i3++) {
                    int i4 = i3 * 2;
                    int[][] iArr2 = j4.f17434f;
                    iArr[i4] = iArr2[iDigit][i3];
                    iArr[i4 + 1] = iArr2[iDigit2][i3];
                }
                iA += h5.a(zArr, iA, iArr, true);
            }
            h5.a(zArr, iA, f17480b, true);
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }
}
