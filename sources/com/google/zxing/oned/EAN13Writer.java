package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes3.dex */
public final class EAN13Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 95;

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got ".concat(String.valueOf(barcodeFormat)));
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length == 12) {
            try {
                str = str + UPCEANReader.l(str);
            } catch (FormatException e2) {
                throw new IllegalArgumentException(e2);
            }
        } else if (length == 13) {
            try {
                if (!UPCEANReader.e(str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be 12 or 13 digits long, but got ".concat(String.valueOf(length)));
        }
        int i2 = EAN13Reader.f15417f[Character.digit(str.charAt(0), 10)];
        boolean[] zArr = new boolean[95];
        int iA = OneDimensionalCodeWriter.a(zArr, 0, UPCEANReader.f15419a, true);
        for (int i3 = 1; i3 <= 6; i3++) {
            int iDigit = Character.digit(str.charAt(i3), 10);
            if (((i2 >> (6 - i3)) & 1) == 1) {
                iDigit += 10;
            }
            iA += OneDimensionalCodeWriter.a(zArr, iA, UPCEANReader.f15423e[iDigit], false);
        }
        int iA2 = iA + OneDimensionalCodeWriter.a(zArr, iA, UPCEANReader.f15420b, false);
        for (int i4 = 7; i4 <= 12; i4++) {
            iA2 += OneDimensionalCodeWriter.a(zArr, iA2, UPCEANReader.f15422d[Character.digit(str.charAt(i4), 10)], true);
        }
        OneDimensionalCodeWriter.a(zArr, iA2, UPCEANReader.f15419a, true);
        return zArr;
    }
}
