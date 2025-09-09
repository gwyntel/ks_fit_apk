package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

/* loaded from: classes3.dex */
public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    private void correctErrors(byte[] bArr, int i2) throws ChecksumException {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        try {
            this.rsDecoder.decode(iArr, bArr.length - i2);
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = (byte) iArr[i4];
            }
        } catch (ReedSolomonException unused) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    public DecoderResult decode(boolean[][] zArr) throws ChecksumException, FormatException {
        return decode(BitMatrix.parse(zArr));
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        BitMatrixParser bitMatrixParser = new BitMatrixParser(bitMatrix);
        DataBlock[] dataBlockArrB = DataBlock.b(bitMatrixParser.b(), bitMatrixParser.a());
        int iC = 0;
        for (DataBlock dataBlock : dataBlockArrB) {
            iC += dataBlock.c();
        }
        byte[] bArr = new byte[iC];
        int length = dataBlockArrB.length;
        for (int i2 = 0; i2 < length; i2++) {
            DataBlock dataBlock2 = dataBlockArrB[i2];
            byte[] bArrA = dataBlock2.a();
            int iC2 = dataBlock2.c();
            correctErrors(bArrA, iC2);
            for (int i3 = 0; i3 < iC2; i3++) {
                bArr[(i3 * length) + i2] = bArrA[i3];
            }
        }
        return DecodedBitStreamParser.a(bArr);
    }
}
