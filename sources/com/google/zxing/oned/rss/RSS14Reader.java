package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class RSS14Reader extends AbstractRSSReader {
    private final List<Pair> possibleLeftPairs = new ArrayList();
    private final List<Pair> possibleRightPairs = new ArrayList();
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = {1, 10, 34, 70, 126};
    private static final int[] INSIDE_ODD_TOTAL_SUBSET = {4, 20, 48, 81};
    private static final int[] OUTSIDE_GSUM = {0, 161, 961, 2015, 2715};
    private static final int[] INSIDE_GSUM = {0, 336, 1036, 1516};
    private static final int[] OUTSIDE_ODD_WIDEST = {8, 6, 4, 3, 1};
    private static final int[] INSIDE_ODD_WIDEST = {2, 4, 6, 8};
    private static final int[][] FINDER_PATTERNS = {new int[]{3, 8, 2, 1}, new int[]{3, 5, 5, 1}, new int[]{3, 3, 7, 1}, new int[]{3, 1, 9, 1}, new int[]{2, 7, 4, 1}, new int[]{2, 5, 6, 1}, new int[]{2, 3, 8, 1}, new int[]{1, 5, 7, 1}, new int[]{1, 3, 9, 1}};

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        if (pair == null) {
            return;
        }
        for (Pair pair2 : collection) {
            if (pair2.getValue() == pair.getValue()) {
                pair2.c();
                return;
            }
        }
        collection.add(pair);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002a A[PHI: r6 r7
      0x002a: PHI (r6v5 boolean) = (r6v2 boolean), (r6v10 boolean) binds: [B:25:0x0044, B:12:0x0028] A[DONT_GENERATE, DONT_INLINE]
      0x002a: PHI (r7v5 boolean) = (r7v2 boolean), (r7v14 boolean) binds: [B:25:0x0044, B:12:0x0028] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d A[PHI: r6 r7
      0x002d: PHI (r6v4 boolean) = (r6v2 boolean), (r6v10 boolean) binds: [B:25:0x0044, B:12:0x0028] A[DONT_GENERATE, DONT_INLINE]
      0x002d: PHI (r7v4 boolean) = (r7v2 boolean), (r7v14 boolean) binds: [B:25:0x0044, B:12:0x0028] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void adjustOddEvenCounts(boolean r10, int r11) throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.RSS14Reader.adjustOddEvenCounts(boolean, int):void");
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int checksumPortion = (pair.getChecksumPortion() + (pair2.getChecksumPortion() * 16)) % 79;
        int value = (pair.b().getValue() * 9) + pair2.b().getValue();
        if (value > 72) {
            value--;
        }
        if (value > 8) {
            value--;
        }
        return checksumPortion == value;
    }

    private static Result constructResult(Pair pair, Pair pair2) {
        String strValueOf = String.valueOf((pair.getValue() * 4537077) + pair2.getValue());
        StringBuilder sb = new StringBuilder(14);
        for (int length = 13 - strValueOf.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(strValueOf);
        int i2 = 0;
        for (int i3 = 0; i3 < 13; i3++) {
            int iCharAt = sb.charAt(i3) - '0';
            if ((i3 & 1) == 0) {
                iCharAt *= 3;
            }
            i2 += iCharAt;
        }
        int i4 = 10 - (i2 % 10);
        if (i4 == 10) {
            i4 = 0;
        }
        sb.append(i4);
        ResultPoint[] resultPoints = pair.b().getResultPoints();
        ResultPoint[] resultPoints2 = pair2.b().getResultPoints();
        return new Result(sb.toString(), null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_14);
    }

    private DataCharacter decodeDataCharacter(BitArray bitArray, FinderPattern finderPattern, boolean z2) throws NotFoundException {
        int[] iArrE = e();
        for (int i2 = 0; i2 < iArrE.length; i2++) {
            iArrE[i2] = 0;
        }
        if (z2) {
            OneDReader.c(bitArray, finderPattern.getStartEnd()[0], iArrE);
        } else {
            OneDReader.b(bitArray, finderPattern.getStartEnd()[1] + 1, iArrE);
            int i3 = 0;
            for (int length = iArrE.length - 1; i3 < length; length--) {
                int i4 = iArrE[i3];
                iArrE[i3] = iArrE[length];
                iArrE[length] = i4;
                i3++;
            }
        }
        int i5 = z2 ? 16 : 15;
        float fSum = MathUtils.sum(iArrE) / i5;
        int[] iArrI = i();
        int[] iArrG = g();
        float[] fArrJ = j();
        float[] fArrH = h();
        for (int i6 = 0; i6 < iArrE.length; i6++) {
            float f2 = iArrE[i6] / fSum;
            int i7 = (int) (0.5f + f2);
            if (i7 <= 0) {
                i7 = 1;
            } else if (i7 > 8) {
                i7 = 8;
            }
            int i8 = i6 / 2;
            if ((i6 & 1) == 0) {
                iArrI[i8] = i7;
                fArrJ[i8] = f2 - i7;
            } else {
                iArrG[i8] = i7;
                fArrH[i8] = f2 - i7;
            }
        }
        adjustOddEvenCounts(z2, i5);
        int i9 = 0;
        int i10 = 0;
        for (int length2 = iArrI.length - 1; length2 >= 0; length2--) {
            int i11 = iArrI[length2];
            i9 = (i9 * 9) + i11;
            i10 += i11;
        }
        int i12 = 0;
        int i13 = 0;
        for (int length3 = iArrG.length - 1; length3 >= 0; length3--) {
            int i14 = iArrG[length3];
            i12 = (i12 * 9) + i14;
            i13 += i14;
        }
        int i15 = i9 + (i12 * 3);
        if (!z2) {
            if ((i13 & 1) != 0 || i13 > 10 || i13 < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i16 = (10 - i13) / 2;
            int i17 = INSIDE_ODD_WIDEST[i16];
            return new DataCharacter((RSSUtils.getRSSvalue(iArrG, 9 - i17, false) * INSIDE_ODD_TOTAL_SUBSET[i16]) + RSSUtils.getRSSvalue(iArrI, i17, true) + INSIDE_GSUM[i16], i15);
        }
        if ((i10 & 1) != 0 || i10 > 12 || i10 < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i18 = (12 - i10) / 2;
        int i19 = OUTSIDE_ODD_WIDEST[i18];
        return new DataCharacter((RSSUtils.getRSSvalue(iArrI, i19, false) * OUTSIDE_EVEN_TOTAL_SUBSET[i18]) + RSSUtils.getRSSvalue(iArrG, 9 - i19, true) + OUTSIDE_GSUM[i18], i15);
    }

    private Pair decodePair(BitArray bitArray, boolean z2, int i2, Map<DecodeHintType, ?> map) {
        try {
            FinderPattern foundFinderPattern = parseFoundFinderPattern(bitArray, i2, z2, findFinderPattern(bitArray, z2));
            ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (resultPointCallback != null) {
                float size = (r1[0] + r1[1]) / 2.0f;
                if (z2) {
                    size = (bitArray.getSize() - 1) - size;
                }
                resultPointCallback.foundPossibleResultPoint(new ResultPoint(size, i2));
            }
            DataCharacter dataCharacterDecodeDataCharacter = decodeDataCharacter(bitArray, foundFinderPattern, true);
            DataCharacter dataCharacterDecodeDataCharacter2 = decodeDataCharacter(bitArray, foundFinderPattern, false);
            return new Pair((dataCharacterDecodeDataCharacter.getValue() * 1597) + dataCharacterDecodeDataCharacter2.getValue(), dataCharacterDecodeDataCharacter.getChecksumPortion() + (dataCharacterDecodeDataCharacter2.getChecksumPortion() * 4), foundFinderPattern);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    private int[] findFinderPattern(BitArray bitArray, boolean z2) throws NotFoundException {
        int[] iArrF = f();
        iArrF[0] = 0;
        iArrF[1] = 0;
        iArrF[2] = 0;
        iArrF[3] = 0;
        int size = bitArray.getSize();
        int i2 = 0;
        boolean z3 = false;
        while (i2 < size) {
            z3 = !bitArray.get(i2);
            if (z2 == z3) {
                break;
            }
            i2++;
        }
        int i3 = 0;
        int i4 = i2;
        while (i2 < size) {
            if (bitArray.get(i2) != z3) {
                iArrF[i3] = iArrF[i3] + 1;
            } else {
                if (i3 != 3) {
                    i3++;
                } else {
                    if (AbstractRSSReader.l(iArrF)) {
                        return new int[]{i4, i2};
                    }
                    i4 += iArrF[0] + iArrF[1];
                    iArrF[0] = iArrF[2];
                    iArrF[1] = iArrF[3];
                    iArrF[2] = 0;
                    iArrF[3] = 0;
                    i3--;
                }
                iArrF[i3] = 1;
                z3 = !z3;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i2, boolean z2, int[] iArr) throws NotFoundException {
        int size;
        int i3;
        boolean z3 = bitArray.get(iArr[0]);
        int i4 = iArr[0] - 1;
        while (i4 >= 0 && z3 != bitArray.get(i4)) {
            i4--;
        }
        int i5 = i4 + 1;
        int i6 = iArr[0] - i5;
        int[] iArrF = f();
        System.arraycopy(iArrF, 0, iArrF, 1, iArrF.length - 1);
        iArrF[0] = i6;
        int iM = AbstractRSSReader.m(iArrF, FINDER_PATTERNS);
        int i7 = iArr[1];
        if (z2) {
            int size2 = (bitArray.getSize() - 1) - i5;
            size = (bitArray.getSize() - 1) - i7;
            i3 = size2;
        } else {
            size = i7;
            i3 = i5;
        }
        return new FinderPattern(iM, new int[]{i5, iArr[1]}, i3, size, i2);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        addOrTally(this.possibleLeftPairs, decodePair(bitArray, false, i2, map));
        bitArray.reverse();
        addOrTally(this.possibleRightPairs, decodePair(bitArray, true, i2, map));
        bitArray.reverse();
        for (Pair pair : this.possibleLeftPairs) {
            if (pair.a() > 1) {
                for (Pair pair2 : this.possibleRightPairs) {
                    if (pair2.a() > 1 && checkChecksum(pair, pair2)) {
                        return constructResult(pair, pair2);
                    }
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}
