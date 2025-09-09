package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 241;
    private static final char ESCAPE_FNC_2 = 242;
    private static final char ESCAPE_FNC_3 = 243;
    private static final char ESCAPE_FNC_4 = 244;

    private enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    private static int chooseCode(CharSequence charSequence, int i2, int i3) {
        CType cTypeFindCType;
        CType cTypeFindCType2;
        char cCharAt;
        CType cTypeFindCType3 = findCType(charSequence, i2);
        CType cType = CType.ONE_DIGIT;
        if (cTypeFindCType3 == cType) {
            return 100;
        }
        CType cType2 = CType.UNCODABLE;
        if (cTypeFindCType3 == cType2) {
            return (i2 >= charSequence.length() || ((cCharAt = charSequence.charAt(i2)) >= ' ' && (i3 != 101 || cCharAt >= '`'))) ? 100 : 101;
        }
        if (i3 == 99) {
            return 99;
        }
        if (i3 != 100) {
            if (cTypeFindCType3 == CType.FNC_1) {
                cTypeFindCType3 = findCType(charSequence, i2 + 1);
            }
            return cTypeFindCType3 == CType.TWO_DIGITS ? 99 : 100;
        }
        CType cType3 = CType.FNC_1;
        if (cTypeFindCType3 == cType3 || (cTypeFindCType = findCType(charSequence, i2 + 2)) == cType2 || cTypeFindCType == cType) {
            return 100;
        }
        if (cTypeFindCType == cType3) {
            return findCType(charSequence, i2 + 3) == CType.TWO_DIGITS ? 99 : 100;
        }
        int i4 = i2 + 4;
        while (true) {
            cTypeFindCType2 = findCType(charSequence, i4);
            if (cTypeFindCType2 != CType.TWO_DIGITS) {
                break;
            }
            i4 += 2;
        }
        return cTypeFindCType2 == CType.ONE_DIGIT ? 100 : 99;
    }

    private static CType findCType(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        if (i2 >= length) {
            return CType.UNCODABLE;
        }
        char cCharAt = charSequence.charAt(i2);
        if (cCharAt == 241) {
            return CType.FNC_1;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            return CType.UNCODABLE;
        }
        int i3 = i2 + 1;
        if (i3 >= length) {
            return CType.ONE_DIGIT;
        }
        char cCharAt2 = charSequence.charAt(i3);
        return (cCharAt2 < '0' || cCharAt2 > '9') ? CType.ONE_DIGIT : CType.TWO_DIGITS;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got ".concat(String.valueOf(barcodeFormat)));
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        int length = str.length();
        if (length > 0 && length <= 80) {
            int iA = 0;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                switch (cCharAt) {
                    case 241:
                    case 242:
                    case 243:
                    case 244:
                        break;
                    default:
                        if (cCharAt > 127) {
                            throw new IllegalArgumentException("Bad character in input: ".concat(String.valueOf(cCharAt)));
                        }
                        break;
                }
            }
            ArrayList<int[]> arrayList = new ArrayList();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 1;
            while (true) {
                int i7 = 103;
                if (i3 < length) {
                    int iChooseCode = chooseCode(str, i3, i5);
                    int iCharAt = 100;
                    if (iChooseCode == i5) {
                        switch (str.charAt(i3)) {
                            case 241:
                                iCharAt = 102;
                                break;
                            case 242:
                                iCharAt = 97;
                                break;
                            case 243:
                                iCharAt = 96;
                                break;
                            case 244:
                                if (i5 == 101) {
                                    iCharAt = 101;
                                    break;
                                }
                                break;
                            default:
                                if (i5 != 100) {
                                    if (i5 != 101) {
                                        iCharAt = Integer.parseInt(str.substring(i3, i3 + 2));
                                        i3++;
                                        break;
                                    } else {
                                        char cCharAt2 = str.charAt(i3);
                                        iCharAt = cCharAt2 - ' ';
                                        if (iCharAt < 0) {
                                            iCharAt = cCharAt2 + '@';
                                            break;
                                        }
                                    }
                                } else {
                                    iCharAt = str.charAt(i3) - ' ';
                                    break;
                                }
                                break;
                        }
                        i3++;
                    } else {
                        if (i5 != 0) {
                            i7 = iChooseCode;
                        } else if (iChooseCode == 100) {
                            i7 = 104;
                        } else if (iChooseCode != 101) {
                            i7 = 105;
                        }
                        iCharAt = i7;
                        i5 = iChooseCode;
                    }
                    arrayList.add(Code128Reader.f15414a[iCharAt]);
                    i4 += iCharAt * i6;
                    if (i3 != 0) {
                        i6++;
                    }
                } else {
                    int[][] iArr = Code128Reader.f15414a;
                    arrayList.add(iArr[i4 % 103]);
                    arrayList.add(iArr[106]);
                    int i8 = 0;
                    for (int[] iArr2 : arrayList) {
                        for (int i9 : iArr2) {
                            i8 += i9;
                        }
                    }
                    boolean[] zArr = new boolean[i8];
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        iA += OneDimensionalCodeWriter.a(zArr, iA, (int[]) it.next(), true);
                    }
                    return zArr;
                }
            }
        } else {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got ".concat(String.valueOf(length)));
        }
    }
}
