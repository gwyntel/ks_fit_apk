package com.huawei.hms.scankit.p;

import com.google.zxing.common.StringUtils;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes4.dex */
public final class c7 {

    /* renamed from: a, reason: collision with root package name */
    private static final String f17074a;

    /* renamed from: b, reason: collision with root package name */
    private static final boolean f17075b;

    static {
        String strName = Charset.defaultCharset().name();
        f17074a = strName;
        f17075b = StringUtils.SHIFT_JIS.equalsIgnoreCase(strName) || "EUC_JP".equalsIgnoreCase(strName);
    }

    public static String a(byte[] bArr, Map<l1, ?> map) {
        int i2;
        if (map != null) {
            l1 l1Var = l1.f17494h;
            if (map.containsKey(l1Var)) {
                return map.get(l1Var).toString();
            }
        }
        int[] iArr = new int[15];
        iArr[0] = bArr.length;
        iArr[1] = 1;
        iArr[2] = 1;
        iArr[3] = 1;
        boolean z2 = bArr.length > 3 && bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65;
        int i3 = 0;
        while (true) {
            i2 = iArr[0];
            if (i3 >= i2 || !(iArr[1] == 1 || iArr[2] == 1 || iArr[3] == 1)) {
                break;
            }
            int i4 = bArr[i3] & 255;
            c(i4, iArr);
            b(i4, iArr);
            a(i4, iArr);
            i3++;
        }
        return a(bArr, iArr[3] == 1, iArr[2] == 1, iArr[1] == 1, iArr[4], iArr[8], z2, iArr[5], iArr[6], iArr[7], iArr[12], iArr[13], iArr[9], iArr[14], i2);
    }

    private static void b(int i2, int[] iArr) {
        if (iArr[2] == 1) {
            int i3 = iArr[8];
            if (i3 > 0) {
                if (i2 < 64 || i2 == 127 || i2 > 252) {
                    iArr[2] = 0;
                    return;
                } else {
                    iArr[8] = i3 - 1;
                    return;
                }
            }
            if (i2 == 128 || i2 == 160 || i2 > 239) {
                iArr[2] = 0;
                return;
            }
            if (i2 > 160 && i2 < 224) {
                iArr[9] = iArr[9] + 1;
                iArr[11] = 0;
                int i4 = iArr[10] + 1;
                iArr[10] = i4;
                if (i4 > iArr[12]) {
                    iArr[12] = i4;
                    return;
                }
                return;
            }
            if (i2 <= 127) {
                iArr[10] = 0;
                iArr[11] = 0;
                return;
            }
            iArr[8] = i3 + 1;
            iArr[10] = 0;
            int i5 = iArr[11] + 1;
            iArr[11] = i5;
            if (i5 > iArr[13]) {
                iArr[13] = i5;
            }
        }
    }

    private static void c(int i2, int[] iArr) {
        if (iArr[3] == 1) {
            int i3 = iArr[4];
            if (i3 > 0) {
                if ((i2 & 128) == 0) {
                    iArr[3] = 0;
                    return;
                } else {
                    iArr[4] = i3 - 1;
                    return;
                }
            }
            if ((i2 & 128) != 0) {
                if ((i2 & 64) == 0) {
                    iArr[3] = 0;
                    return;
                }
                iArr[4] = i3 + 1;
                if ((i2 & 32) == 0) {
                    iArr[5] = iArr[5] + 1;
                    return;
                }
                iArr[4] = i3 + 2;
                if ((i2 & 16) == 0) {
                    iArr[6] = iArr[6] + 1;
                    return;
                }
                iArr[4] = i3 + 3;
                if ((i2 & 8) == 0) {
                    iArr[7] = iArr[7] + 1;
                } else {
                    iArr[3] = 0;
                }
            }
        }
    }

    private static void a(int i2, int[] iArr) {
        if (iArr[1] == 1) {
            if (i2 > 127 && i2 < 160) {
                iArr[1] = 0;
                return;
            }
            if (i2 > 159) {
                if (i2 < 192 || i2 == 215 || i2 == 247) {
                    iArr[14] = iArr[14] + 1;
                }
            }
        }
    }

    public static String a(byte[] bArr, boolean z2, boolean z3, boolean z4, int i2, int i3, boolean z5, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        if (z2 && i2 > 0) {
            z2 = false;
        }
        if (z3 && i3 > 0) {
            z3 = false;
        }
        if (z2 && (z5 || i4 + i5 + i6 > 0)) {
            return "UTF8";
        }
        if (a(bArr).booleanValue()) {
            return "GBK";
        }
        if (z3 && (f17075b || i7 >= 3 || i8 >= 3)) {
            return StringUtils.SHIFT_JIS;
        }
        if (z4 && z3) {
            return (!(i7 == 2 && i9 == 2) && i10 * 10 < i11) ? "ISO8859_1" : StringUtils.SHIFT_JIS;
        }
        if (z4 && i10 * 10 < i11) {
            return "ISO8859_1";
        }
        if (z3) {
            return StringUtils.SHIFT_JIS;
        }
        if (z2) {
            return "UTF8";
        }
        if (!z2 && "UTF-8".equals(f17074a)) {
            return StringUtils.GB2312;
        }
        return f17074a;
    }

    public static Boolean a(byte[] bArr) {
        int length = bArr.length;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z2 = true;
                break;
            }
            byte b2 = bArr[i2];
            if ((b2 & 128) != 0) {
                int i3 = b2 & 255;
                if ((i3 < 170 && i3 > 160) || (i3 < 248 && i3 > 175)) {
                    i2++;
                    if (i2 >= length) {
                        break;
                    }
                    byte b3 = bArr[i2];
                    if ((b3 & 255) >= 255 || (b3 & 255) <= 160 || (b3 & 255) == 127) {
                        break;
                    }
                } else if (i3 < 161 && i3 > 128) {
                    i2++;
                    if (i2 >= length) {
                        break;
                    }
                    byte b4 = bArr[i2];
                    if ((b4 & 255) >= 255 || (b4 & 255) <= 63 || (b4 & 255) == 127) {
                        break;
                    }
                } else {
                    if (((i3 >= 255 || i3 <= 169) && (i3 >= 170 || i3 <= 167)) || (i2 = i2 + 1) >= length) {
                        break;
                    }
                    byte b5 = bArr[i2];
                    if ((b5 & 255) >= 161 || (b5 & 255) <= 63 || (b5 & 255) == 127) {
                        break;
                    }
                }
            }
            i2++;
        }
        return Boolean.valueOf(z2);
    }
}
