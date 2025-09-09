package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class s0 extends h5 {

    private enum a {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    @Override // com.huawei.hms.scankit.p.h5, com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.a(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.huawei.hms.scankit.p.h5
    public boolean[] a(String str) throws NumberFormatException {
        int length = str.length();
        if (length >= 1 && length <= 80) {
            int iA = 0;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt < ' ' || cCharAt > '~') {
                    switch (cCharAt) {
                        case 241:
                        case 242:
                        case 243:
                        case 244:
                            break;
                        default:
                            throw new IllegalArgumentException("Bad character in input: " + cCharAt);
                    }
                }
            }
            ArrayList<int[]> arrayList = new ArrayList();
            int i3 = 1;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i4 < length) {
                int iA2 = a(str, i4, i6);
                int iCharAt = 100;
                if (iA2 == i6) {
                    switch (str.charAt(i4)) {
                        case 241:
                            iCharAt = 102;
                            i4++;
                            break;
                        case 242:
                            iCharAt = 97;
                            i4++;
                            break;
                        case 243:
                            iCharAt = 96;
                            i4++;
                            break;
                        case 244:
                            i4++;
                            break;
                        default:
                            if (i6 == 100) {
                                iCharAt = str.charAt(i4) - ' ';
                            } else {
                                try {
                                    iCharAt = Integer.parseInt(str.substring(i4, i4 + 2));
                                    i4++;
                                } catch (NumberFormatException unused) {
                                    throw new IllegalArgumentException("contents substring can not format integer");
                                }
                            }
                            i4++;
                            break;
                    }
                } else {
                    iCharAt = i6 == 0 ? iA2 == 100 ? 104 : 105 : iA2;
                    i6 = iA2;
                }
                arrayList.add(r0.f17711a[iCharAt]);
                i5 += iCharAt * i3;
                if (i4 != 0) {
                    i3++;
                }
            }
            int[][] iArr = r0.f17711a;
            arrayList.add(iArr[i5 % 103]);
            arrayList.add(iArr[106]);
            int i7 = 0;
            for (int[] iArr2 : arrayList) {
                for (int i8 : iArr2) {
                    i7 += i8;
                }
            }
            boolean[] zArr = new boolean[i7];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                iA += h5.a(zArr, iA, (int[]) it.next(), true);
            }
            return zArr;
        }
        throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
    }

    private static a a(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        if (i2 >= length) {
            return a.UNCODABLE;
        }
        char cCharAt = charSequence.charAt(i2);
        if (cCharAt == 241) {
            return a.FNC_1;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            return a.UNCODABLE;
        }
        int i3 = i2 + 1;
        if (i3 >= length) {
            return a.ONE_DIGIT;
        }
        char cCharAt2 = charSequence.charAt(i3);
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            return a.TWO_DIGITS;
        }
        return a.ONE_DIGIT;
    }

    private static int a(CharSequence charSequence, int i2, int i3) {
        a aVar;
        a aVarA;
        a aVarA2;
        a aVarA3 = a(charSequence, i2);
        a aVar2 = a.UNCODABLE;
        if (aVarA3 != aVar2 && aVarA3 != (aVar = a.ONE_DIGIT)) {
            if (i3 == 99) {
                return 99;
            }
            if (i3 == 100) {
                a aVar3 = a.FNC_1;
                if (aVarA3 == aVar3 || (aVarA = a(charSequence, i2 + 2)) == aVar2 || aVarA == aVar) {
                    return 100;
                }
                if (aVarA == aVar3) {
                    return a(charSequence, i2 + 3) == a.TWO_DIGITS ? 99 : 100;
                }
                int i4 = i2 + 4;
                while (true) {
                    aVarA2 = a(charSequence, i4);
                    if (aVarA2 != a.TWO_DIGITS) {
                        break;
                    }
                    i4 += 2;
                }
                return aVarA2 == a.ONE_DIGIT ? 100 : 99;
            }
            if (aVarA3 == a.FNC_1) {
                aVarA3 = a(charSequence, i2 + 1);
            }
            if (aVarA3 == a.TWO_DIGITS) {
                return 99;
            }
        }
        return 100;
    }
}
