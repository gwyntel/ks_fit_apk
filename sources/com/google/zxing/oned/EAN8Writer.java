package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes3.dex */
public final class EAN8Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 67;

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_8) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_8, but got ".concat(String.valueOf(barcodeFormat)));
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length == 7) {
            try {
                str = str + UPCEANReader.l(str);
            } catch (FormatException e2) {
                throw new IllegalArgumentException(e2);
            }
        } else if (length == 8) {
            try {
                if (!UPCEANReader.e(str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got ".concat(String.valueOf(length)));
        }
        boolean[] zArr = new boolean[67];
        int iA = OneDimensionalCodeWriter.a(zArr, 0, UPCEANReader.f15419a, true);
        for (int i2 = 0; i2 <= 3; i2++) {
            iA += OneDimensionalCodeWriter.a(zArr, iA, UPCEANReader.f15422d[Character.digit(str.charAt(i2), 10)], false);
        }
        int iA2 = iA + OneDimensionalCodeWriter.a(zArr, iA, UPCEANReader.f15420b, false);
        for (int i3 = 4; i3 <= 7; i3++) {
            iA2 += OneDimensionalCodeWriter.a(zArr, iA2, UPCEANReader.f15422d[Character.digit(str.charAt(i3), 10)], true);
        }
        OneDimensionalCodeWriter.a(zArr, iA2, UPCEANReader.f15419a, true);
        return zArr;
    }
}
