package com.huawei.hms.scankit.p;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.common.base.Ascii;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.xiaomi.infra.galaxy.fds.Common;
import com.yc.utesdk.ble.close.KeyType;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public final class v0 extends g5 {

    /* renamed from: c, reason: collision with root package name */
    private static final char[] f17869c = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f17870d;

    /* renamed from: e, reason: collision with root package name */
    private static final int f17871e;

    /* renamed from: a, reason: collision with root package name */
    private final StringBuilder f17872a = new StringBuilder(20);

    /* renamed from: b, reason: collision with root package name */
    private final int[] f17873b = new int[6];

    static {
        int[] iArr = {276, 328, 324, 322, 296, 292, 290, 336, KeyType.QUERY_MENSTRUAL_DATA_FOR_MONTH, KeyType.QUERY_DRINK_WATER_REMIND, 424, 420, 418, Common.HTTP_STATUS_NOT_FOUND, TypedValues.CycleType.TYPE_VISIBILITY, 394, 360, 356, 354, 308, KeyType.SET_AI_AGENT_TYPE, 344, 332, 326, 300, KeyType.SET_AI_WATCH_ENABLE, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, FacebookRequestErrorClassification.ESC_APP_NOT_INSTALLED, 366, 374, 430, 294, 474, 470, 306, 350};
        f17870d = iArr;
        f17871e = iArr[47];
    }

    private static int b(int[] iArr) {
        int i2 = 0;
        for (int i3 : iArr) {
            i2 += i3;
        }
        int length = iArr.length;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            int iRound = Math.round((iArr[i5] * 9.0f) / i2);
            if (iRound < 1 || iRound > 4) {
                return -1;
            }
            if ((i5 & 1) == 0) {
                for (int i6 = 0; i6 < iRound; i6++) {
                    i4 = (i4 << 1) | 1;
                }
            } else {
                i4 <<= iRound;
            }
        }
        return i4;
    }

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        int iC = rVar.c(a(rVar)[1]);
        int iE = rVar.e();
        int[] iArr = this.f17873b;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.f17872a;
        sb.setLength(0);
        while (true) {
            g5.a(rVar, iC, iArr);
            int iB = b(iArr);
            if (iB < 0) {
                throw a.a();
            }
            char cA = a(iB);
            sb.append(cA);
            int i3 = iC;
            for (int i4 : iArr) {
                i3 += i4;
            }
            int iC2 = rVar.c(i3);
            if (cA == '*') {
                sb.deleteCharAt(sb.length() - 1);
                int i5 = 0;
                for (int i6 : iArr) {
                    i5 += i6;
                }
                if (iC2 == iE || !rVar.b(iC2)) {
                    throw a.a();
                }
                if (sb.length() < 2) {
                    throw a.a();
                }
                a(sb);
                sb.setLength(sb.length() - 2);
                float f2 = i2;
                return new s6(b(sb), null, new u6[]{new u6(r0[0], f2), new u6(iC + ((i5 * 10) / 9), f2)}, BarcodeFormat.CODE_93);
            }
            iC = iC2;
        }
    }

    private static String b(CharSequence charSequence) throws a {
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 'a' || cCharAt > 'd') {
                sb.append(cCharAt);
            } else if (i2 < length - 1) {
                i2++;
                sb.append(a(cCharAt, charSequence.charAt(i2)));
            } else {
                throw a.a();
            }
            i2++;
        }
        return sb.toString();
    }

    private int[] a(r rVar) throws a {
        int iE = rVar.e();
        int iC = rVar.c(0);
        Arrays.fill(this.f17873b, 0);
        int[] iArr = this.f17873b;
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
                    if (b(iArr) == f17871e) {
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
            int[] iArr = f17870d;
            if (i3 < iArr.length) {
                if (iArr[i3] == i2) {
                    return f17869c[i3];
                }
                i3++;
            } else {
                throw a.a();
            }
        }
    }

    private static char a(char c2, char c3) throws a {
        int i2;
        switch (c2) {
            case 'a':
                if (c3 >= 'A' && c3 <= 'Z') {
                    i2 = c3 - '@';
                    break;
                } else {
                    throw a.a();
                }
            case 'b':
                if (c3 >= 'A' && c3 <= 'E') {
                    i2 = c3 - '&';
                    break;
                } else if (c3 >= 'F' && c3 <= 'J') {
                    i2 = c3 - 11;
                    break;
                } else if (c3 >= 'K' && c3 <= 'O') {
                    i2 = c3 + 16;
                    break;
                } else if (c3 >= 'P' && c3 <= 'S') {
                    i2 = c3 + '+';
                    break;
                } else {
                    if (c3 < 'T' || c3 > 'Z') {
                        throw a.a();
                    }
                    return Ascii.MAX;
                }
                break;
            case 'c':
                if (c3 >= 'A' && c3 <= 'O') {
                    i2 = c3 - ' ';
                    break;
                } else {
                    if (c3 == 'Z') {
                        return ':';
                    }
                    throw a.a();
                }
            case 'd':
                if (c3 >= 'A' && c3 <= 'Z') {
                    i2 = c3 + ' ';
                    break;
                } else {
                    throw a.a();
                }
            default:
                return (char) 0;
        }
        return (char) i2;
    }

    private static void a(CharSequence charSequence) throws a {
        int length = charSequence.length();
        a(charSequence, length - 2, 20);
        a(charSequence, length - 1, 15);
    }

    private static void a(CharSequence charSequence, int i2, int i3) throws a {
        int iIndexOf = 0;
        int i4 = 1;
        for (int i5 = i2 - 1; i5 >= 0; i5--) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(charSequence.charAt(i5)) * i4;
            i4++;
            if (i4 > i3) {
                i4 = 1;
            }
        }
        if (charSequence.charAt(i2) != f17869c[iIndexOf % 47]) {
            throw a.a();
        }
    }
}
