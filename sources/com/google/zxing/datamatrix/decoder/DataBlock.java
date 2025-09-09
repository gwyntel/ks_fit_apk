package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

/* loaded from: classes3.dex */
final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i2, byte[] bArr) {
        this.numDataCodewords = i2;
        this.codewords = bArr;
    }

    static DataBlock[] b(byte[] bArr, Version version) {
        Version.ECBlocks eCBlocksA = version.a();
        Version.ECB[] ecbArrA = eCBlocksA.a();
        int iA = 0;
        for (Version.ECB ecb : ecbArrA) {
            iA += ecb.a();
        }
        DataBlock[] dataBlockArr = new DataBlock[iA];
        int i2 = 0;
        for (Version.ECB ecb2 : ecbArrA) {
            int i3 = 0;
            while (i3 < ecb2.a()) {
                int iB = ecb2.b();
                dataBlockArr[i2] = new DataBlock(iB, new byte[eCBlocksA.b() + iB]);
                i3++;
                i2++;
            }
        }
        int length = dataBlockArr[0].codewords.length - eCBlocksA.b();
        int i4 = length - 1;
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int i7 = 0;
            while (i7 < i2) {
                dataBlockArr[i7].codewords[i6] = bArr[i5];
                i7++;
                i5++;
            }
        }
        boolean z2 = version.getVersionNumber() == 24;
        int i8 = z2 ? 8 : i2;
        int i9 = 0;
        while (i9 < i8) {
            dataBlockArr[i9].codewords[i4] = bArr[i5];
            i9++;
            i5++;
        }
        int length2 = dataBlockArr[0].codewords.length;
        while (length < length2) {
            int i10 = 0;
            while (i10 < i2) {
                int i11 = z2 ? (i10 + 8) % i2 : i10;
                dataBlockArr[i11].codewords[(!z2 || i11 <= 7) ? length : length - 1] = bArr[i5];
                i10++;
                i5++;
            }
            length++;
        }
        if (i5 == bArr.length) {
            return dataBlockArr;
        }
        throw new IllegalArgumentException();
    }

    byte[] a() {
        return this.codewords;
    }

    int c() {
        return this.numDataCodewords;
    }
}
