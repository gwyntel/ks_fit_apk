package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

/* loaded from: classes3.dex */
public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

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
        return decode(zArr, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(boolean[][] zArr, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        return decode(BitMatrix.parse(zArr), map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(BitMatrix bitMatrix, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        ChecksumException e2;
        BitMatrixParser bitMatrixParser = new BitMatrixParser(bitMatrix);
        FormatException formatException = null;
        try {
            return decode(bitMatrixParser, map);
        } catch (ChecksumException e3) {
            e2 = e3;
            try {
                bitMatrixParser.e();
                bitMatrixParser.f(true);
                bitMatrixParser.d();
                bitMatrixParser.c();
                bitMatrixParser.a();
                DecoderResult decoderResultDecode = decode(bitMatrixParser, map);
                decoderResultDecode.setOther(new QRCodeDecoderMetaData(true));
                return decoderResultDecode;
            } catch (ChecksumException | FormatException unused) {
                if (formatException != null) {
                    throw formatException;
                }
                throw e2;
            }
        } catch (FormatException e4) {
            e2 = null;
            formatException = e4;
            bitMatrixParser.e();
            bitMatrixParser.f(true);
            bitMatrixParser.d();
            bitMatrixParser.c();
            bitMatrixParser.a();
            DecoderResult decoderResultDecode2 = decode(bitMatrixParser, map);
            decoderResultDecode2.setOther(new QRCodeDecoderMetaData(true));
            return decoderResultDecode2;
        }
    }

    private DecoderResult decode(BitMatrixParser bitMatrixParser, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        Version versionD = bitMatrixParser.d();
        ErrorCorrectionLevel errorCorrectionLevelC = bitMatrixParser.c().c();
        DataBlock[] dataBlockArrB = DataBlock.b(bitMatrixParser.b(), versionD, errorCorrectionLevelC);
        int iC = 0;
        for (DataBlock dataBlock : dataBlockArrB) {
            iC += dataBlock.c();
        }
        byte[] bArr = new byte[iC];
        int i2 = 0;
        for (DataBlock dataBlock2 : dataBlockArrB) {
            byte[] bArrA = dataBlock2.a();
            int iC2 = dataBlock2.c();
            correctErrors(bArrA, iC2);
            int i3 = 0;
            while (i3 < iC2) {
                bArr[i2] = bArrA[i3];
                i3++;
                i2++;
            }
        }
        return DecodedBitStreamParser.a(bArr, versionD, errorCorrectionLevelC, map);
    }
}
