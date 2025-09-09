package com.google.zxing.oned;

import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
final class UPCEANExtensionSupport {
    private static final int[] EXTENSION_START_PATTERN = {1, 1, 2};
    private final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();
    private final UPCEANExtension5Support fiveSupport = new UPCEANExtension5Support();

    UPCEANExtensionSupport() {
    }

    Result a(int i2, BitArray bitArray, int i3) {
        int[] iArrI = UPCEANReader.i(bitArray, i3, false, EXTENSION_START_PATTERN);
        try {
            return this.fiveSupport.a(i2, bitArray, iArrI);
        } catch (ReaderException unused) {
            return this.twoSupport.a(i2, bitArray, iArrI);
        }
    }
}
