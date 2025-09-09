package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
public final class EAN8Reader extends UPCEANReader {
    private final int[] decodeMiddleCounters = new int[4];

    @Override // com.google.zxing.oned.UPCEANReader
    protected int h(BitArray bitArray, int[] iArr, StringBuilder sb) {
        int[] iArr2 = this.decodeMiddleCounters;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i2 = iArr[1];
        for (int i3 = 0; i3 < 4 && i2 < size; i3++) {
            sb.append((char) (UPCEANReader.f(bitArray, iArr2, i2, UPCEANReader.f15422d) + 48));
            for (int i4 : iArr2) {
                i2 += i4;
            }
        }
        int i5 = UPCEANReader.i(bitArray, i2, true, UPCEANReader.f15420b)[1];
        for (int i6 = 0; i6 < 4 && i5 < size; i6++) {
            sb.append((char) (UPCEANReader.f(bitArray, iArr2, i5, UPCEANReader.f15422d) + 48));
            for (int i7 : iArr2) {
                i5 += i7;
            }
        }
        return i5;
    }

    @Override // com.google.zxing.oned.UPCEANReader
    BarcodeFormat k() {
        return BarcodeFormat.EAN_8;
    }
}
