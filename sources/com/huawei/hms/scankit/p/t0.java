package com.huawei.hms.scankit.p;

import com.google.common.base.Ascii;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.yc.utesdk.ble.close.KeyType;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public final class t0 extends g5 {

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f17802e = {52, 289, 97, 352, 49, 304, 112, 37, 292, 100, KeyType.QUERY_SEDENTARY_REMIND, 73, 328, 25, KeyType.SET_AI_WATCH_VOICE_CONTENT, 88, 13, KeyType.QUERY_SOS_CONTACT, 76, 28, 259, 67, 322, 19, KeyType.QUERY_MENSTRUAL_DATA_FOR_MONTH, 82, 7, KeyType.QUERY_FRK_DEVICE_ERASE_COMMAND, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 168, 162, 138, 42};

    /* renamed from: a, reason: collision with root package name */
    private final boolean f17803a;

    /* renamed from: b, reason: collision with root package name */
    private final boolean f17804b;

    /* renamed from: c, reason: collision with root package name */
    private final StringBuilder f17805c;

    /* renamed from: d, reason: collision with root package name */
    private final int[] f17806d;

    public t0() {
        this(false);
    }

    private static boolean b(int[] iArr) {
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        for (int i4 : iArr) {
            if (i4 < i2) {
                i2 = i4;
            }
            if (i4 > i3) {
                i3 = i4;
            }
        }
        return i3 / i2 > 6;
    }

    private static int c(int[] iArr) {
        int length = iArr.length;
        if (b(iArr)) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            int i3 = Integer.MAX_VALUE;
            for (int i4 : iArr) {
                if (i4 < i3 && i4 > i2) {
                    i3 = i4;
                }
            }
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            for (int i8 = 0; i8 < length; i8++) {
                int i9 = iArr[i8];
                if (i9 > i3) {
                    i7 |= 1 << ((length - 1) - i8);
                    i5++;
                    i6 += i9;
                }
            }
            if (i5 == 3) {
                for (int i10 = 0; i10 < length && i5 > 0; i10++) {
                    int i11 = iArr[i10];
                    if (i11 > i3) {
                        i5--;
                        if (i11 * 2 >= i6) {
                            return -1;
                        }
                    }
                }
                return i7;
            }
            if (i5 <= 3) {
                return -1;
            }
            i2 = i3;
        }
    }

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        int[] iArr = this.f17806d;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.f17805c;
        sb.setLength(0);
        int[] iArrA = a(rVar, iArr);
        int iC = rVar.c(iArrA[1]);
        int iE = rVar.e();
        while (true) {
            g5.a(rVar, iC, iArr);
            int iC2 = c(iArr);
            if (iC2 < 0) {
                throw a.a();
            }
            char cA = a(iC2);
            sb.append(cA);
            int i3 = iC;
            for (int i4 : iArr) {
                i3 += i4;
            }
            int iC3 = rVar.c(i3);
            if (cA == '*') {
                sb.setLength(sb.length() - 1);
                int i5 = 0;
                for (int i6 : iArr) {
                    i5 += i6;
                }
                int i7 = (iC3 - iC) - i5;
                if (iC3 == iE || i7 * 5 >= i5) {
                    return a(sb, iArrA, iC, i5, i2);
                }
                throw a.a();
            }
            iC = iC3;
        }
    }

    public t0(boolean z2) {
        this(z2, false);
    }

    public t0(boolean z2, boolean z3) {
        this.f17803a = z2;
        this.f17804b = z3;
        this.f17805c = new StringBuilder(20);
        this.f17806d = new int[9];
    }

    private s6 a(StringBuilder sb, int[] iArr, int i2, int i3, int i4) throws a {
        String string;
        if (this.f17803a) {
            int length = sb.length() - 1;
            int iIndexOf = 0;
            for (int i5 = 0; i5 < length; i5++) {
                iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(this.f17805c.charAt(i5));
            }
            if (sb.charAt(length) == "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".charAt(iIndexOf % 43)) {
                sb.setLength(length);
            } else {
                throw a.a();
            }
        }
        if (sb.length() != 0) {
            if (this.f17804b) {
                string = a(sb);
            } else {
                string = sb.toString();
            }
            float f2 = i4;
            return new s6(string, null, new u6[]{new u6(iArr[0], f2), new u6(i2 + i3, f2)}, BarcodeFormat.CODE_39);
        }
        throw a.a();
    }

    private static int[] a(r rVar, int[] iArr) throws a {
        int iE = rVar.e();
        int iC = rVar.c(0);
        int length = iArr.length;
        boolean z2 = false;
        int i2 = 0;
        int i3 = iC;
        while (iC < iE) {
            if (rVar.b(iC) != z2) {
                if (i2 >= 0 && i2 < iArr.length) {
                    iArr[i2] = iArr[i2] + 1;
                } else {
                    throw a.a();
                }
            } else {
                if (i2 != length - 1) {
                    i2++;
                } else {
                    if (c(iArr) == 148 && rVar.a(Math.max(0, i3 - ((iC - i3) / 5)), i3, false, true)) {
                        return new int[]{i3, iC};
                    }
                    i3 += iArr[0] + iArr[1];
                    int i4 = i2 - 1;
                    System.arraycopy(iArr, 2, iArr, 0, i4);
                    iArr[i4] = 0;
                    iArr[i2] = 0;
                    i2--;
                }
                iArr[i2] = 1;
                z2 = !z2;
            }
            iC++;
        }
        throw a.a();
    }

    private static char a(int i2) throws a {
        int i3 = 0;
        while (true) {
            int[] iArr = f17802e;
            if (i3 >= iArr.length) {
                if (i2 == 148) {
                    return '*';
                }
                throw a.a();
            }
            if (iArr[i3] == i2) {
                return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".charAt(i3);
            }
            i3++;
        }
    }

    private static String a(CharSequence charSequence) throws a {
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt != '+' && cCharAt != '$' && cCharAt != '%' && cCharAt != '/') {
                sb.append(cCharAt);
            } else {
                i2++;
                sb.append(a(cCharAt, charSequence.charAt(i2)));
            }
            i2++;
        }
        return sb.toString();
    }

    private static char a(char c2, char c3) throws a {
        int i2;
        if (c2 != '$') {
            if (c2 != '%') {
                if (c2 != '+') {
                    if (c2 == '/') {
                        if (c3 < 'A' || c3 > 'O') {
                            if (c3 == 'Z') {
                                return ':';
                            }
                            throw a.a();
                        }
                        i2 = c3 - ' ';
                    }
                    return (char) 0;
                }
                if (c3 < 'A' || c3 > 'Z') {
                    throw a.a();
                }
                i2 = c3 + ' ';
            } else if (c3 >= 'A' && c3 <= 'E') {
                i2 = c3 - '&';
            } else if (c3 >= 'F' && c3 <= 'J') {
                i2 = c3 - 11;
            } else if (c3 >= 'K' && c3 <= 'O') {
                i2 = c3 + 16;
            } else {
                if (c3 < 'P' || c3 > 'T') {
                    if (c3 != 'U') {
                        if (c3 == 'V') {
                            return '@';
                        }
                        if (c3 == 'W') {
                            return '`';
                        }
                        if (c3 == 'X' || c3 == 'Y' || c3 == 'Z') {
                            return Ascii.MAX;
                        }
                        throw a.a();
                    }
                    return (char) 0;
                }
                i2 = c3 + '+';
            }
        } else {
            if (c3 < 'A' || c3 > 'Z') {
                throw a.a();
            }
            i2 = c3 - '@';
        }
        return (char) i2;
    }
}
