package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class UPCEANReader extends OneDReader {
    private static final float MAX_AVG_VARIANCE = 0.48f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    /* renamed from: a, reason: collision with root package name */
    static final int[] f15419a = {1, 1, 1};

    /* renamed from: b, reason: collision with root package name */
    static final int[] f15420b = {1, 1, 1, 1, 1};

    /* renamed from: c, reason: collision with root package name */
    static final int[] f15421c = {1, 1, 1, 1, 1, 1};

    /* renamed from: d, reason: collision with root package name */
    static final int[][] f15422d;

    /* renamed from: e, reason: collision with root package name */
    static final int[][] f15423e;
    private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();
    private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();

    static {
        int[][] iArr = {new int[]{3, 2, 1, 1}, new int[]{2, 2, 2, 1}, new int[]{2, 1, 2, 2}, new int[]{1, 4, 1, 1}, new int[]{1, 1, 3, 2}, new int[]{1, 2, 3, 1}, new int[]{1, 1, 1, 4}, new int[]{1, 3, 1, 2}, new int[]{1, 2, 1, 3}, new int[]{3, 1, 1, 2}};
        f15422d = iArr;
        int[][] iArr2 = new int[20][];
        f15423e = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 10);
        for (int i2 = 10; i2 < 20; i2++) {
            int[] iArr3 = f15422d[i2 - 10];
            int[] iArr4 = new int[iArr3.length];
            for (int i3 = 0; i3 < iArr3.length; i3++) {
                iArr4[i3] = iArr3[(iArr3.length - i3) - 1];
            }
            f15423e[i2] = iArr4;
        }
    }

    protected UPCEANReader() {
    }

    static boolean e(CharSequence charSequence) {
        int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int i2 = length - 1;
        return l(charSequence.subSequence(0, i2)) == Character.digit(charSequence.charAt(i2), 10);
    }

    static int f(BitArray bitArray, int[] iArr, int i2, int[][] iArr2) throws NotFoundException {
        OneDReader.b(bitArray, i2, iArr);
        int length = iArr2.length;
        float f2 = MAX_AVG_VARIANCE;
        int i3 = -1;
        for (int i4 = 0; i4 < length; i4++) {
            float fA = OneDReader.a(iArr, iArr2[i4], 0.7f);
            if (fA < f2) {
                i3 = i4;
                f2 = fA;
            }
        }
        if (i3 >= 0) {
            return i3;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findGuardPattern(BitArray bitArray, int i2, boolean z2, int[] iArr, int[] iArr2) throws NotFoundException {
        int size = bitArray.getSize();
        int nextUnset = z2 ? bitArray.getNextUnset(i2) : bitArray.getNextSet(i2);
        int length = iArr.length;
        boolean z3 = z2;
        int i3 = 0;
        int i4 = nextUnset;
        while (nextUnset < size) {
            if (bitArray.get(nextUnset) != z3) {
                iArr2[i3] = iArr2[i3] + 1;
            } else {
                if (i3 != length - 1) {
                    i3++;
                } else {
                    if (OneDReader.a(iArr2, iArr, 0.7f) < MAX_AVG_VARIANCE) {
                        return new int[]{i4, nextUnset};
                    }
                    i4 += iArr2[0] + iArr2[1];
                    int i5 = i3 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i3] = 0;
                    i3--;
                }
                iArr2[i3] = 1;
                z3 = !z3;
            }
            nextUnset++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int[] i(BitArray bitArray, int i2, boolean z2, int[] iArr) {
        return findGuardPattern(bitArray, i2, z2, iArr, new int[iArr.length]);
    }

    static int[] j(BitArray bitArray) throws NotFoundException {
        int[] iArr = new int[f15419a.length];
        int[] iArrFindGuardPattern = null;
        boolean zIsRange = false;
        int i2 = 0;
        while (!zIsRange) {
            int[] iArr2 = f15419a;
            Arrays.fill(iArr, 0, iArr2.length, 0);
            iArrFindGuardPattern = findGuardPattern(bitArray, i2, false, iArr2, iArr);
            int i3 = iArrFindGuardPattern[0];
            int i4 = iArrFindGuardPattern[1];
            int i5 = i3 - (i4 - i3);
            if (i5 >= 0) {
                zIsRange = bitArray.isRange(i5, i3, false);
            }
            i2 = i4;
        }
        return iArrFindGuardPattern;
    }

    static int l(CharSequence charSequence) throws FormatException {
        int length = charSequence.length();
        int i2 = 0;
        for (int i3 = length - 1; i3 >= 0; i3 -= 2) {
            int iCharAt = charSequence.charAt(i3) - '0';
            if (iCharAt < 0 || iCharAt > 9) {
                throw FormatException.getFormatInstance();
            }
            i2 += iCharAt;
        }
        int i4 = i2 * 3;
        for (int i5 = length - 2; i5 >= 0; i5 -= 2) {
            int iCharAt2 = charSequence.charAt(i5) - '0';
            if (iCharAt2 < 0 || iCharAt2 > 9) {
                throw FormatException.getFormatInstance();
            }
            i4 += iCharAt2;
        }
        return (1000 - i4) % 10;
    }

    boolean d(String str) {
        return e(str);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        return decodeRow(i2, bitArray, j(bitArray), map);
    }

    int[] g(BitArray bitArray, int i2) {
        return i(bitArray, i2, false, f15419a);
    }

    protected abstract int h(BitArray bitArray, int[] iArr, StringBuilder sb);

    abstract BarcodeFormat k();

    public Result decodeRow(int i2, BitArray bitArray, int[] iArr, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int length;
        String strA;
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint((iArr[0] + iArr[1]) / 2.0f, i2));
        }
        StringBuilder sb = this.decodeRowStringBuffer;
        sb.setLength(0);
        int iH = h(bitArray, iArr, sb);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint(iH, i2));
        }
        int[] iArrG = g(bitArray, iH);
        if (resultPointCallback != null) {
            resultPointCallback.foundPossibleResultPoint(new ResultPoint((iArrG[0] + iArrG[1]) / 2.0f, i2));
        }
        int i3 = iArrG[1];
        int i4 = (i3 - iArrG[0]) + i3;
        if (i4 >= bitArray.getSize() || !bitArray.isRange(i3, i4, false)) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string = sb.toString();
        if (string.length() < 8) {
            throw FormatException.getFormatInstance();
        }
        if (!d(string)) {
            throw ChecksumException.getChecksumInstance();
        }
        BarcodeFormat barcodeFormatK = k();
        float f2 = i2;
        Result result = new Result(string, null, new ResultPoint[]{new ResultPoint((iArr[1] + iArr[0]) / 2.0f, f2), new ResultPoint((iArrG[1] + iArrG[0]) / 2.0f, f2)}, barcodeFormatK);
        try {
            Result resultA = this.extensionReader.a(i2, bitArray, iArrG[1]);
            result.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, resultA.getText());
            result.putAllMetadata(resultA.getResultMetadata());
            result.addResultPoints(resultA.getResultPoints());
            length = resultA.getText().length();
        } catch (ReaderException unused) {
            length = 0;
        }
        int[] iArr2 = map != null ? (int[]) map.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS) : null;
        if (iArr2 != null) {
            for (int i5 : iArr2) {
                if (length != i5) {
                }
            }
            throw NotFoundException.getNotFoundInstance();
        }
        if ((barcodeFormatK == BarcodeFormat.EAN_13 || barcodeFormatK == BarcodeFormat.UPC_A) && (strA = this.eanManSupport.a(string)) != null) {
            result.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, strA);
        }
        return result;
    }
}
