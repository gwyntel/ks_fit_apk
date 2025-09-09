package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

/* loaded from: classes3.dex */
public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    static BitArray a(boolean z2, int i2, int i3) {
        BitArray bitArray = new BitArray();
        if (z2) {
            bitArray.appendBits(i2 - 1, 2);
            bitArray.appendBits(i3 - 1, 6);
            return generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(i2 - 1, 5);
        bitArray.appendBits(i3 - 1, 11);
        return generateCheckWords(bitArray, 40, 4);
    }

    static BitArray b(BitArray bitArray, int i2) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i3 = (1 << i2) - 2;
        int i4 = 0;
        while (i4 < size) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = i4 + i6;
                if (i7 >= size || bitArray.get(i7)) {
                    i5 |= 1 << ((i2 - 1) - i6);
                }
            }
            int i8 = i5 & i3;
            if (i8 == i3) {
                bitArray2.appendBits(i8, i2);
            } else if (i8 == 0) {
                bitArray2.appendBits(i5 | 1, i2);
            } else {
                bitArray2.appendBits(i5, i2);
                i4 += i2;
            }
            i4--;
            i4 += i2;
        }
        return bitArray2;
    }

    private static int[] bitsToWords(BitArray bitArray, int i2, int i3) {
        int[] iArr = new int[i3];
        int size = bitArray.getSize() / i2;
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 |= bitArray.get((i4 * i2) + i6) ? 1 << ((i2 - i6) - 1) : 0;
            }
            iArr[i4] = i5;
        }
        return iArr;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4 += 2) {
            int i5 = i2 - i4;
            int i6 = i5;
            while (true) {
                int i7 = i2 + i4;
                if (i6 <= i7) {
                    bitMatrix.set(i6, i5);
                    bitMatrix.set(i6, i7);
                    bitMatrix.set(i5, i6);
                    bitMatrix.set(i7, i6);
                    i6++;
                }
            }
        }
        int i8 = i2 - i3;
        bitMatrix.set(i8, i8);
        int i9 = i8 + 1;
        bitMatrix.set(i9, i8);
        bitMatrix.set(i8, i9);
        int i10 = i2 + i3;
        bitMatrix.set(i10, i8);
        bitMatrix.set(i10, i9);
        bitMatrix.set(i10, i10 - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean z2, int i2, BitArray bitArray) {
        int i3 = i2 / 2;
        int i4 = 0;
        if (z2) {
            while (i4 < 7) {
                int i5 = (i3 - 3) + i4;
                if (bitArray.get(i4)) {
                    bitMatrix.set(i5, i3 - 5);
                }
                if (bitArray.get(i4 + 7)) {
                    bitMatrix.set(i3 + 5, i5);
                }
                if (bitArray.get(20 - i4)) {
                    bitMatrix.set(i5, i3 + 5);
                }
                if (bitArray.get(27 - i4)) {
                    bitMatrix.set(i3 - 5, i5);
                }
                i4++;
            }
            return;
        }
        while (i4 < 10) {
            int i6 = (i3 - 5) + i4 + (i4 / 5);
            if (bitArray.get(i4)) {
                bitMatrix.set(i6, i3 - 7);
            }
            if (bitArray.get(i4 + 10)) {
                bitMatrix.set(i3 + 7, i6);
            }
            if (bitArray.get(29 - i4)) {
                bitMatrix.set(i6, i3 + 7);
            }
            if (bitArray.get(39 - i4)) {
                bitMatrix.set(i3 - 7, i6);
            }
            i4++;
        }
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    private static BitArray generateCheckWords(BitArray bitArray, int i2, int i3) {
        int size = bitArray.getSize() / i3;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i3));
        int i4 = i2 / i3;
        int[] iArrBitsToWords = bitsToWords(bitArray, i3, i4);
        reedSolomonEncoder.encode(iArrBitsToWords, i4 - size);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, i2 % i3);
        for (int i5 : iArrBitsToWords) {
            bitArray2.appendBits(i5, i3);
        }
        return bitArray2;
    }

    private static GenericGF getGF(int i2) {
        if (i2 == 4) {
            return GenericGF.AZTEC_PARAM;
        }
        if (i2 == 6) {
            return GenericGF.AZTEC_DATA_6;
        }
        if (i2 == 8) {
            return GenericGF.AZTEC_DATA_8;
        }
        if (i2 == 10) {
            return GenericGF.AZTEC_DATA_10;
        }
        if (i2 == 12) {
            return GenericGF.AZTEC_DATA_12;
        }
        throw new IllegalArgumentException("Unsupported word size ".concat(String.valueOf(i2)));
    }

    private static int totalBitsInLayer(int i2, boolean z2) {
        return ((z2 ? 88 : 112) + (i2 << 4)) * i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static AztecCode encode(byte[] bArr, int i2, int i3) {
        BitArray bitArrayB;
        int i4;
        boolean z2;
        int iAbs;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = 2;
        BitArray bitArrayEncode = new HighLevelEncoder(bArr).encode();
        int size = ((bitArrayEncode.getSize() * i2) / 100) + 11;
        int size2 = bitArrayEncode.getSize() + size;
        int i10 = 0;
        int i11 = 1;
        if (i3 == 0) {
            BitArray bitArrayB2 = null;
            int i12 = 0;
            int i13 = 0;
            while (i12 <= i) {
                boolean z3 = i12 <= 3 ? i11 : i10;
                int i14 = z3 != 0 ? i12 + 1 : i12;
                int i15 = totalBitsInLayer(i14, z3);
                if (size2 <= i15) {
                    if (bitArrayB2 == null || i13 != WORD_SIZE[i14]) {
                        int i16 = WORD_SIZE[i14];
                        i13 = i16;
                        bitArrayB2 = b(bitArrayEncode, i16);
                    }
                    int i17 = i15 - (i15 % i13);
                    if ((z3 == 0 || bitArrayB2.getSize() <= (i13 << 6)) && bitArrayB2.getSize() + size <= i17) {
                        bitArrayB = bitArrayB2;
                        i4 = i13;
                        z2 = z3;
                        iAbs = i14;
                        i5 = i15;
                    }
                }
                int i18 = i9;
                int i19 = i11;
                i12 += i19;
                i11 = i19;
                i9 = i18;
                i = 32;
                i10 = 0;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z2 = i3 < 0;
        iAbs = Math.abs(i3);
        if (iAbs > (z2 ? 4 : 32)) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(i3)));
        }
        i5 = totalBitsInLayer(iAbs, z2);
        i4 = WORD_SIZE[iAbs];
        int i20 = i5 - (i5 % i4);
        bitArrayB = b(bitArrayEncode, i4);
        if (bitArrayB.getSize() + size > i20) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z2 && bitArrayB.getSize() > (i4 << 6)) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        BitArray bitArrayGenerateCheckWords = generateCheckWords(bitArrayB, i5, i4);
        int size3 = bitArrayB.getSize() / i4;
        BitArray bitArrayA = a(z2, iAbs, size3);
        int i21 = (z2 ? 11 : 14) + (iAbs << 2);
        int[] iArr = new int[i21];
        if (z2) {
            for (int i22 = i10; i22 < i21; i22 += i11) {
                iArr[i22] = i22;
            }
            i6 = i21;
        } else {
            int i23 = i21 / 2;
            i6 = i21 + 1 + (((i23 - 1) / 15) * i9);
            int i24 = i6 / 2;
            for (int i25 = i10; i25 < i23; i25 += i11) {
                iArr[(i23 - i25) - 1] = (i24 - r15) - 1;
                iArr[i23 + i25] = (i25 / 15) + i25 + i24 + i11;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        int i26 = i10;
        int i27 = i26;
        while (i26 < iAbs) {
            int i28 = ((iAbs - i26) << i9) + (z2 ? 9 : 12);
            while (i10 < i28) {
                int i29 = i10 << 1;
                int i30 = 0;
                while (i30 < i9) {
                    if (bitArrayGenerateCheckWords.get(i27 + i29 + i30)) {
                        int i31 = i26 << 1;
                        bitMatrix.set(iArr[i31 + i30], iArr[i31 + i10]);
                    }
                    if (bitArrayGenerateCheckWords.get((i28 << 1) + i27 + i29 + i30)) {
                        int i32 = i26 << 1;
                        i7 = size3;
                        bitMatrix.set(iArr[i32 + i10], iArr[((i21 - 1) - i32) - i30]);
                    } else {
                        i7 = size3;
                    }
                    if (bitArrayGenerateCheckWords.get((i28 << 2) + i27 + i29 + i30)) {
                        int i33 = (i21 - 1) - (i26 << 1);
                        bitMatrix.set(iArr[i33 - i30], iArr[i33 - i10]);
                    }
                    if (bitArrayGenerateCheckWords.get((i28 * 6) + i27 + i29 + i30)) {
                        i8 = 1;
                        int i34 = i26 << 1;
                        bitMatrix.set(iArr[((i21 - 1) - i34) - i10], iArr[i34 + i30]);
                    } else {
                        i8 = 1;
                    }
                    i30 += i8;
                    size3 = i7;
                    i11 = i8;
                    i9 = 2;
                }
                i10 += i11;
                i9 = 2;
            }
            i27 += i28 << 3;
            i26 += i11;
            size3 = size3;
            i9 = 2;
            i10 = 0;
        }
        int i35 = size3;
        drawModeMessage(bitMatrix, z2, i6, bitArrayA);
        if (z2) {
            drawBullsEye(bitMatrix, i6 / 2, 5);
        } else {
            int i36 = i6 / 2;
            drawBullsEye(bitMatrix, i36, 7);
            int i37 = 0;
            int i38 = 0;
            while (i37 < (i21 / 2) - 1) {
                for (int i39 = i36 & 1; i39 < i6; i39 += 2) {
                    int i40 = i36 - i38;
                    bitMatrix.set(i40, i39);
                    int i41 = i36 + i38;
                    bitMatrix.set(i41, i39);
                    bitMatrix.set(i39, i40);
                    bitMatrix.set(i39, i41);
                }
                i37 += 15;
                i38 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z2);
        aztecCode.setSize(i6);
        aztecCode.setLayers(iAbs);
        aztecCode.setCodeWords(i35);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }
}
