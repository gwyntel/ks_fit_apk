package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes3.dex */
public final class CodaBarReader extends OneDReader {
    private static final float MAX_ACCEPTABLE = 2.0f;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final float PADDING = 1.5f;
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";

    /* renamed from: a, reason: collision with root package name */
    static final char[] f15412a = ALPHABET_STRING.toCharArray();

    /* renamed from: b, reason: collision with root package name */
    static final int[] f15413b = {3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final char[] STARTEND_ENCODING = {'A', 'B', 'C', 'D'};
    private final StringBuilder decodeRowResult = new StringBuilder(20);
    private int[] counters = new int[80];
    private int counterLength = 0;

    private void counterAppend(int i2) {
        int[] iArr = this.counters;
        int i3 = this.counterLength;
        iArr[i3] = i2;
        int i4 = i3 + 1;
        this.counterLength = i4;
        if (i4 >= iArr.length) {
            int[] iArr2 = new int[i4 << 1];
            System.arraycopy(iArr, 0, iArr2, 0, i4);
            this.counters = iArr2;
        }
    }

    static boolean d(char[] cArr, char c2) {
        if (cArr != null) {
            for (char c3 : cArr) {
                if (c3 == c2) {
                    return true;
                }
            }
        }
        return false;
    }

    private int findStartPattern() throws NotFoundException {
        for (int i2 = 1; i2 < this.counterLength; i2 += 2) {
            int narrowWidePattern = toNarrowWidePattern(i2);
            if (narrowWidePattern != -1 && d(STARTEND_ENCODING, f15412a[narrowWidePattern])) {
                int i3 = 0;
                for (int i4 = i2; i4 < i2 + 7; i4++) {
                    i3 += this.counters[i4];
                }
                if (i2 == 1 || this.counters[i2 - 1] >= i3 / 2) {
                    return i2;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        int i2 = 0;
        this.counterLength = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int size = bitArray.getSize();
        if (nextUnset >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z2 = true;
        while (nextUnset < size) {
            if (bitArray.get(nextUnset) != z2) {
                i2++;
            } else {
                counterAppend(i2);
                z2 = !z2;
                i2 = 1;
            }
            nextUnset++;
        }
        counterAppend(i2);
    }

    private int toNarrowWidePattern(int i2) {
        int i3 = i2 + 7;
        if (i3 >= this.counterLength) {
            return -1;
        }
        int[] iArr = this.counters;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        int i6 = Integer.MAX_VALUE;
        int i7 = 0;
        for (int i8 = i2; i8 < i3; i8 += 2) {
            int i9 = iArr[i8];
            if (i9 < i6) {
                i6 = i9;
            }
            if (i9 > i7) {
                i7 = i9;
            }
        }
        int i10 = (i6 + i7) / 2;
        int i11 = 0;
        for (int i12 = i2 + 1; i12 < i3; i12 += 2) {
            int i13 = iArr[i12];
            if (i13 < i4) {
                i4 = i13;
            }
            if (i13 > i11) {
                i11 = i13;
            }
        }
        int i14 = (i4 + i11) / 2;
        int i15 = 128;
        int i16 = 0;
        for (int i17 = 0; i17 < 7; i17++) {
            i15 >>= 1;
            if (iArr[i2 + i17] > ((i17 & 1) == 0 ? i10 : i14)) {
                i16 |= i15;
            }
        }
        while (true) {
            int[] iArr2 = f15413b;
            if (i5 >= iArr2.length) {
                return -1;
            }
            if (iArr2[i5] == i16) {
                return i5;
            }
            i5++;
        }
    }

    private void validatePattern(int i2) throws NotFoundException {
        int[] iArr = new int[4];
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        int[] iArr2 = new int[4];
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int length = this.decodeRowResult.length() - 1;
        int i3 = i2;
        int i4 = 0;
        while (true) {
            int i5 = f15413b[this.decodeRowResult.charAt(i4)];
            for (int i6 = 6; i6 >= 0; i6--) {
                int i7 = (i6 & 1) + ((i5 & 1) << 1);
                iArr[i7] = iArr[i7] + this.counters[i3 + i6];
                iArr2[i7] = iArr2[i7] + 1;
                i5 >>= 1;
            }
            if (i4 >= length) {
                break;
            }
            i3 += 8;
            i4++;
        }
        float[] fArr = new float[4];
        float[] fArr2 = new float[4];
        for (int i8 = 0; i8 < 2; i8++) {
            fArr2[i8] = 0.0f;
            int i9 = i8 + 2;
            int i10 = iArr[i9];
            int i11 = iArr2[i9];
            float f2 = ((iArr[i8] / iArr2[i8]) + (i10 / i11)) / 2.0f;
            fArr2[i9] = f2;
            fArr[i8] = f2;
            fArr[i9] = ((i10 * 2.0f) + PADDING) / i11;
        }
        int i12 = i2;
        int i13 = 0;
        loop3: while (true) {
            int i14 = f15413b[this.decodeRowResult.charAt(i13)];
            for (int i15 = 6; i15 >= 0; i15--) {
                int i16 = (i15 & 1) + ((i14 & 1) << 1);
                float f3 = this.counters[i12 + i15];
                if (f3 < fArr2[i16] || f3 > fArr[i16]) {
                    break loop3;
                }
                i14 >>= 1;
            }
            if (i13 >= length) {
                return;
            }
            i12 += 8;
            i13++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        int i3;
        Arrays.fill(this.counters, 0);
        setCounters(bitArray);
        int iFindStartPattern = findStartPattern();
        this.decodeRowResult.setLength(0);
        int i4 = iFindStartPattern;
        while (true) {
            int narrowWidePattern = toNarrowWidePattern(i4);
            if (narrowWidePattern == -1) {
                throw NotFoundException.getNotFoundInstance();
            }
            this.decodeRowResult.append((char) narrowWidePattern);
            i3 = i4 + 8;
            if ((this.decodeRowResult.length() > 1 && d(STARTEND_ENCODING, f15412a[narrowWidePattern])) || i3 >= this.counterLength) {
                break;
            }
            i4 = i3;
        }
        int i5 = i4 + 7;
        int i6 = this.counters[i5];
        int i7 = 0;
        for (int i8 = -8; i8 < -1; i8++) {
            i7 += this.counters[i3 + i8];
        }
        if (i3 < this.counterLength && i6 < i7 / 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        validatePattern(iFindStartPattern);
        for (int i9 = 0; i9 < this.decodeRowResult.length(); i9++) {
            StringBuilder sb = this.decodeRowResult;
            sb.setCharAt(i9, f15412a[sb.charAt(i9)]);
        }
        char cCharAt = this.decodeRowResult.charAt(0);
        char[] cArr = STARTEND_ENCODING;
        if (!d(cArr, cCharAt)) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb2 = this.decodeRowResult;
        if (!d(cArr, sb2.charAt(sb2.length() - 1))) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (this.decodeRowResult.length() <= 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (map == null || !map.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
            StringBuilder sb3 = this.decodeRowResult;
            sb3.deleteCharAt(sb3.length() - 1);
            this.decodeRowResult.deleteCharAt(0);
        }
        int i10 = 0;
        for (int i11 = 0; i11 < iFindStartPattern; i11++) {
            i10 += this.counters[i11];
        }
        float f2 = i10;
        while (iFindStartPattern < i5) {
            i10 += this.counters[iFindStartPattern];
            iFindStartPattern++;
        }
        float f3 = i2;
        return new Result(this.decodeRowResult.toString(), null, new ResultPoint[]{new ResultPoint(f2, f3), new ResultPoint(i10, f3)}, BarcodeFormat.CODABAR);
    }
}
