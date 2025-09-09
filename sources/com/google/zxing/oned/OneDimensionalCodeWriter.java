package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class OneDimensionalCodeWriter implements Writer {
    protected static int a(boolean[] zArr, int i2, int[] iArr, boolean z2) {
        int i3 = 0;
        for (int i4 : iArr) {
            int i5 = 0;
            while (i5 < i4) {
                zArr[i2] = z2;
                i5++;
                i2++;
            }
            i3 += i4;
            z2 = !z2;
        }
        return i3;
    }

    private static BitMatrix renderResult(boolean[] zArr, int i2, int i3, int i4) {
        int length = zArr.length;
        int i5 = i4 + length;
        int iMax = Math.max(i2, i5);
        int iMax2 = Math.max(1, i3);
        int i6 = iMax / i5;
        int i7 = (iMax - (length * i6)) / 2;
        BitMatrix bitMatrix = new BitMatrix(iMax, iMax2);
        int i8 = 0;
        while (i8 < length) {
            if (zArr[i8]) {
                bitMatrix.setRegion(i7, 0, i6, iMax2);
            }
            i8++;
            i7 += i6;
        }
        return bitMatrix;
    }

    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3) throws WriterException {
        return encode(str, barcodeFormat, i2, i3, null);
    }

    public abstract boolean[] encode(String str);

    public int getDefaultMargin() {
        return 10;
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws NumberFormatException, WriterException {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (i2 < 0 || i3 < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + i2 + 'x' + i3);
        }
        int defaultMargin = getDefaultMargin();
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.MARGIN;
            if (map.containsKey(encodeHintType)) {
                defaultMargin = Integer.parseInt(map.get(encodeHintType).toString());
            }
        }
        return renderResult(encode(str), i2, i3, defaultMargin);
    }
}
