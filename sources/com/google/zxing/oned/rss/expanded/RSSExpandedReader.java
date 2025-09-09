package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class RSSExpandedReader extends AbstractRSSReader {
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int MAX_PAIRS = 11;
    private final List<ExpandedPair> pairs = new ArrayList(11);
    private final List<ExpandedRow> rows = new ArrayList();
    private final int[] startEnd = new int[2];
    private boolean startFromEven;
    private static final int[] SYMBOL_WIDEST = {7, 5, 4, 3, 1};
    private static final int[] EVEN_TOTAL_SUBSET = {4, 20, 52, 104, 204};
    private static final int[] GSUM = {0, 348, 1388, 2948, 3988};
    private static final int[][] FINDER_PATTERNS = {new int[]{1, 8, 4, 1}, new int[]{3, 6, 4, 1}, new int[]{3, 4, 6, 1}, new int[]{3, 2, 8, 1}, new int[]{2, 6, 5, 1}, new int[]{2, 2, 9, 1}};
    private static final int[][] WEIGHTS = {new int[]{1, 3, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, 180, 118, 143, 7, 21, 63}, new int[]{189, 145, 13, 39, 117, 140, 209, 205}, new int[]{193, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, 169, 85, 44, 132}, new int[]{185, 133, 188, 142, 4, 12, 36, 108}, new int[]{113, 128, 173, 97, 80, 29, 87, 50}, new int[]{150, 28, 84, 41, 123, 158, 52, 156}, new int[]{46, 138, 203, 187, 139, 206, 196, 166}, new int[]{76, 17, 51, 153, 37, 111, 122, 155}, new int[]{43, 129, 176, 106, 107, 110, 119, 146}, new int[]{16, 48, 144, 10, 30, 90, 59, 177}, new int[]{109, 116, 137, 200, 178, 112, 125, 164}, new int[]{70, 210, 208, 202, 184, 130, 179, 115}, new int[]{134, 191, 151, 31, 93, 68, 204, 190}, new int[]{148, 22, 66, 198, 172, 94, 71, 2}, new int[]{6, 18, 54, 162, 64, 192, 154, 40}, new int[]{120, 149, 25, 75, 14, 42, 126, 167}, new int[]{79, 26, 78, 23, 69, 207, 199, 175}, new int[]{103, 98, 83, 38, 114, 131, 182, 124}, new int[]{161, 61, 183, 127, 170, 88, 53, 159}, new int[]{55, 165, 73, 8, 24, 72, 5, 15}, new int[]{45, 135, 194, 160, 58, 174, 100, 89}};
    private static final int[][] FINDER_PATTERN_SEQUENCES = {new int[]{0, 0}, new int[]{0, 1, 1}, new int[]{0, 2, 1, 3}, new int[]{0, 4, 1, 3, 2}, new int[]{0, 4, 1, 3, 3, 5}, new int[]{0, 4, 1, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 2, 3, 3}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 4}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}};

    /* JADX WARN: Removed duplicated region for block: B:53:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void adjustOddEvenCounts(int r11) throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 205
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.expanded.RSSExpandedReader.adjustOddEvenCounts(int):void");
    }

    private boolean checkChecksum() {
        ExpandedPair expandedPair = this.pairs.get(0);
        DataCharacter dataCharacterB = expandedPair.b();
        DataCharacter dataCharacterC = expandedPair.c();
        if (dataCharacterC == null) {
            return false;
        }
        int checksumPortion = dataCharacterC.getChecksumPortion();
        int i2 = 2;
        for (int i3 = 1; i3 < this.pairs.size(); i3++) {
            ExpandedPair expandedPair2 = this.pairs.get(i3);
            checksumPortion += expandedPair2.b().getChecksumPortion();
            int i4 = i2 + 1;
            DataCharacter dataCharacterC2 = expandedPair2.c();
            if (dataCharacterC2 != null) {
                checksumPortion += dataCharacterC2.getChecksumPortion();
                i2 += 2;
            } else {
                i2 = i4;
            }
        }
        return ((i2 + (-4)) * 211) + (checksumPortion % 211) == dataCharacterB.getValue();
    }

    private List<ExpandedPair> checkRows(boolean z2) {
        List<ExpandedPair> listCheckRows = null;
        if (this.rows.size() > 25) {
            this.rows.clear();
            return null;
        }
        this.pairs.clear();
        if (z2) {
            Collections.reverse(this.rows);
        }
        try {
            listCheckRows = checkRows(new ArrayList(), 0);
        } catch (NotFoundException unused) {
        }
        if (z2) {
            Collections.reverse(this.rows);
        }
        return listCheckRows;
    }

    private void findNextPair(BitArray bitArray, List<ExpandedPair> list, int i2) throws NotFoundException {
        int[] iArrF = f();
        iArrF[0] = 0;
        iArrF[1] = 0;
        iArrF[2] = 0;
        iArrF[3] = 0;
        int size = bitArray.getSize();
        if (i2 < 0) {
            i2 = list.isEmpty() ? 0 : list.get(list.size() - 1).a().getStartEnd()[1];
        }
        boolean z2 = list.size() % 2 != 0;
        if (this.startFromEven) {
            z2 = !z2;
        }
        boolean z3 = false;
        while (i2 < size) {
            z3 = !bitArray.get(i2);
            if (!z3) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = 0;
        boolean z4 = z3;
        int i4 = i2;
        while (i2 < size) {
            if (bitArray.get(i2) != z4) {
                iArrF[i3] = iArrF[i3] + 1;
            } else {
                if (i3 == 3) {
                    if (z2) {
                        reverseCounters(iArrF);
                    }
                    if (AbstractRSSReader.l(iArrF)) {
                        int[] iArr = this.startEnd;
                        iArr[0] = i4;
                        iArr[1] = i2;
                        return;
                    }
                    if (z2) {
                        reverseCounters(iArrF);
                    }
                    i4 += iArrF[0] + iArrF[1];
                    iArrF[0] = iArrF[2];
                    iArrF[1] = iArrF[3];
                    iArrF[2] = 0;
                    iArrF[3] = 0;
                    i3--;
                } else {
                    i3++;
                }
                iArrF[i3] = 1;
                z4 = !z4;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int getNextSecondBar(BitArray bitArray, int i2) {
        return bitArray.get(i2) ? bitArray.getNextSet(bitArray.getNextUnset(i2)) : bitArray.getNextUnset(bitArray.getNextSet(i2));
    }

    private static boolean isNotA1left(FinderPattern finderPattern, boolean z2, boolean z3) {
        return (finderPattern.getValue() == 0 && z2 && z3) ? false : true;
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> iterable, Iterable<ExpandedRow> iterable2) {
        for (ExpandedRow expandedRow : iterable2) {
            for (ExpandedPair expandedPair : iterable) {
                Iterator it = expandedRow.a().iterator();
                while (it.hasNext()) {
                    if (expandedPair.equals((ExpandedPair) it.next())) {
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isValidSequence(List<ExpandedPair> list) {
        for (int[] iArr : FINDER_PATTERN_SEQUENCES) {
            if (list.size() <= iArr.length) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (list.get(i2).a().getValue() != iArr[i2]) {
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }

    static Result n(List list) throws NotFoundException, FormatException {
        String information = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.a(list)).parseInformation();
        ResultPoint[] resultPoints = ((ExpandedPair) list.get(0)).a().getResultPoints();
        ResultPoint[] resultPoints2 = ((ExpandedPair) list.get(list.size() - 1)).a().getResultPoints();
        return new Result(information, null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_EXPANDED);
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i2, boolean z2) {
        int i3;
        int i4;
        int i5;
        if (z2) {
            int i6 = this.startEnd[0] - 1;
            while (i6 >= 0 && !bitArray.get(i6)) {
                i6--;
            }
            int i7 = i6 + 1;
            int[] iArr = this.startEnd;
            i5 = iArr[0] - i7;
            i3 = iArr[1];
            i4 = i7;
        } else {
            int[] iArr2 = this.startEnd;
            int i8 = iArr2[0];
            int nextUnset = bitArray.getNextUnset(iArr2[1] + 1);
            i3 = nextUnset;
            i4 = i8;
            i5 = nextUnset - this.startEnd[1];
        }
        int[] iArrF = f();
        System.arraycopy(iArrF, 0, iArrF, 1, iArrF.length - 1);
        iArrF[0] = i5;
        try {
            return new FinderPattern(AbstractRSSReader.m(iArrF, FINDER_PATTERNS), new int[]{i4, i3}, i4, i3, i2);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    private static void removePartialRows(List<ExpandedPair> list, List<ExpandedRow> list2) {
        Iterator<ExpandedRow> it = list2.iterator();
        while (it.hasNext()) {
            ExpandedRow next = it.next();
            if (next.a().size() != list.size()) {
                Iterator it2 = next.a().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        it.remove();
                        break;
                    }
                    ExpandedPair expandedPair = (ExpandedPair) it2.next();
                    Iterator<ExpandedPair> it3 = list.iterator();
                    while (it3.hasNext()) {
                        if (expandedPair.equals(it3.next())) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void reverseCounters(int[] iArr) {
        int length = iArr.length;
        for (int i2 = 0; i2 < length / 2; i2++) {
            int i3 = iArr[i2];
            int i4 = (length - i2) - 1;
            iArr[i2] = iArr[i4];
            iArr[i4] = i3;
        }
    }

    private void storeRow(int i2, boolean z2) {
        boolean zC = false;
        int i3 = 0;
        boolean zC2 = false;
        while (true) {
            if (i3 >= this.rows.size()) {
                break;
            }
            ExpandedRow expandedRow = this.rows.get(i3);
            if (expandedRow.b() > i2) {
                zC = expandedRow.c(this.pairs);
                break;
            } else {
                zC2 = expandedRow.c(this.pairs);
                i3++;
            }
        }
        if (zC || zC2 || isPartialRow(this.pairs, this.rows)) {
            return;
        }
        this.rows.add(i3, new ExpandedRow(this.pairs, i2, z2));
        removePartialRows(this.pairs, this.rows);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            return n(p(i2, bitArray));
        } catch (NotFoundException unused) {
            this.pairs.clear();
            this.startFromEven = true;
            return n(p(i2, bitArray));
        }
    }

    DataCharacter o(BitArray bitArray, FinderPattern finderPattern, boolean z2, boolean z3) throws NotFoundException {
        int[] iArrE = e();
        for (int i2 = 0; i2 < iArrE.length; i2++) {
            iArrE[i2] = 0;
        }
        if (z3) {
            OneDReader.c(bitArray, finderPattern.getStartEnd()[0], iArrE);
        } else {
            OneDReader.b(bitArray, finderPattern.getStartEnd()[1], iArrE);
            int i3 = 0;
            for (int length = iArrE.length - 1; i3 < length; length--) {
                int i4 = iArrE[i3];
                iArrE[i3] = iArrE[length];
                iArrE[length] = i4;
                i3++;
            }
        }
        float fSum = MathUtils.sum(iArrE) / 17.0f;
        float f2 = (finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f;
        if (Math.abs(fSum - f2) / f2 > 0.3f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int[] iArrI = i();
        int[] iArrG = g();
        float[] fArrJ = j();
        float[] fArrH = h();
        for (int i5 = 0; i5 < iArrE.length; i5++) {
            float f3 = (iArrE[i5] * 1.0f) / fSum;
            int i6 = (int) (0.5f + f3);
            if (i6 <= 0) {
                if (f3 < 0.3f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i6 = 1;
            } else if (i6 > 8) {
                if (f3 > 8.7f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i6 = 8;
            }
            int i7 = i5 / 2;
            if ((i5 & 1) == 0) {
                iArrI[i7] = i6;
                fArrJ[i7] = f3 - i6;
            } else {
                iArrG[i7] = i6;
                fArrH[i7] = f3 - i6;
            }
        }
        adjustOddEvenCounts(17);
        int value = (((finderPattern.getValue() * 4) + (z2 ? 0 : 2)) + (!z3 ? 1 : 0)) - 1;
        int i8 = 0;
        int i9 = 0;
        for (int length2 = iArrI.length - 1; length2 >= 0; length2--) {
            if (isNotA1left(finderPattern, z2, z3)) {
                i8 += iArrI[length2] * WEIGHTS[value][length2 * 2];
            }
            i9 += iArrI[length2];
        }
        int i10 = 0;
        for (int length3 = iArrG.length - 1; length3 >= 0; length3--) {
            if (isNotA1left(finderPattern, z2, z3)) {
                i10 += iArrG[length3] * WEIGHTS[value][(length3 * 2) + 1];
            }
        }
        int i11 = i8 + i10;
        if ((i9 & 1) != 0 || i9 > 13 || i9 < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i12 = (13 - i9) / 2;
        int i13 = SYMBOL_WIDEST[i12];
        return new DataCharacter((RSSUtils.getRSSvalue(iArrI, i13, true) * EVEN_TOTAL_SUBSET[i12]) + RSSUtils.getRSSvalue(iArrG, 9 - i13, false) + GSUM[i12], i11);
    }

    List p(int i2, BitArray bitArray) throws NotFoundException {
        boolean z2 = false;
        while (!z2) {
            try {
                List<ExpandedPair> list = this.pairs;
                list.add(q(bitArray, list, i2));
            } catch (NotFoundException e2) {
                if (this.pairs.isEmpty()) {
                    throw e2;
                }
                z2 = true;
            }
        }
        if (checkChecksum()) {
            return this.pairs;
        }
        boolean z3 = !this.rows.isEmpty();
        storeRow(i2, false);
        if (z3) {
            List<ExpandedPair> listCheckRows = checkRows(false);
            if (listCheckRows != null) {
                return listCheckRows;
            }
            List<ExpandedPair> listCheckRows2 = checkRows(true);
            if (listCheckRows2 != null) {
                return listCheckRows2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    ExpandedPair q(BitArray bitArray, List list, int i2) throws NotFoundException {
        FinderPattern foundFinderPattern;
        DataCharacter dataCharacterO;
        boolean z2 = list.size() % 2 == 0;
        if (this.startFromEven) {
            z2 = !z2;
        }
        int nextSecondBar = -1;
        boolean z3 = true;
        do {
            findNextPair(bitArray, list, nextSecondBar);
            foundFinderPattern = parseFoundFinderPattern(bitArray, i2, z2);
            if (foundFinderPattern == null) {
                nextSecondBar = getNextSecondBar(bitArray, this.startEnd[0]);
            } else {
                z3 = false;
            }
        } while (z3);
        DataCharacter dataCharacterO2 = o(bitArray, foundFinderPattern, z2, true);
        if (!list.isEmpty() && ((ExpandedPair) list.get(list.size() - 1)).mustBeLast()) {
            throw NotFoundException.getNotFoundInstance();
        }
        try {
            dataCharacterO = o(bitArray, foundFinderPattern, z2, false);
        } catch (NotFoundException unused) {
            dataCharacterO = null;
        }
        return new ExpandedPair(dataCharacterO2, dataCharacterO, foundFinderPattern, true);
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    private List<ExpandedPair> checkRows(List<ExpandedRow> list, int i2) throws NotFoundException {
        while (i2 < this.rows.size()) {
            ExpandedRow expandedRow = this.rows.get(i2);
            this.pairs.clear();
            Iterator<ExpandedRow> it = list.iterator();
            while (it.hasNext()) {
                this.pairs.addAll(it.next().a());
            }
            this.pairs.addAll(expandedRow.a());
            if (isValidSequence(this.pairs)) {
                if (checkChecksum()) {
                    return this.pairs;
                }
                ArrayList arrayList = new ArrayList(list);
                arrayList.add(expandedRow);
                try {
                    return checkRows(arrayList, i2 + 1);
                } catch (NotFoundException unused) {
                    continue;
                }
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
