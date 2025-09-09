package com.huawei.hms.scankit.p;

import aisble.error.GattError;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class d4 {
    private static char a(char c2, int i2) {
        int i3 = c2 + ((i2 * 149) % GattError.GATT_CCCD_CFG_ERROR) + 1;
        if (i3 > 254) {
            i3 -= 254;
        }
        return (char) i3;
    }

    static boolean b(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    static boolean c(char c2) {
        return c2 >= 128 && c2 <= 255;
    }

    private static boolean d(char c2) {
        return c2 == ' ' || (c2 >= '0' && c2 <= '9') || (c2 >= 'A' && c2 <= 'Z');
    }

    private static boolean e(char c2) {
        return c2 >= ' ' && c2 <= '^';
    }

    private static boolean f(char c2) {
        return c2 == ' ' || (c2 >= '0' && c2 <= '9') || (c2 >= 'a' && c2 <= 'z');
    }

    private static boolean g(char c2) {
        return i(c2) || c2 == ' ' || (c2 >= '0' && c2 <= '9') || (c2 >= 'A' && c2 <= 'Z');
    }

    private static boolean h(char c2) {
        return false;
    }

    private static boolean i(char c2) {
        return c2 == '\r' || c2 == '*' || c2 == '>';
    }

    public static String a(String str, e7 e7Var, l2 l2Var, l2 l2Var2) {
        int iE = 0;
        v2[] v2VarArr = {new b(), new d0(), new g7(), new n8(), new s2(), new n()};
        y2 y2Var = new y2(str);
        y2Var.a(e7Var);
        y2Var.a(l2Var, l2Var2);
        if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
            y2Var.a((char) 236);
            y2Var.a(2);
            y2Var.f18021f += 7;
        } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
            y2Var.a((char) 237);
            y2Var.a(2);
            y2Var.f18021f += 7;
        }
        while (y2Var.i()) {
            if (iE >= 0 && iE < 6) {
                v2VarArr[iE].a(y2Var);
            }
            if (y2Var.e() >= 0) {
                iE = y2Var.e();
                y2Var.j();
            }
        }
        int iA = y2Var.a();
        y2Var.l();
        int iA2 = y2Var.g().a();
        if (iA < iA2 && iE != 0 && iE != 5 && iE != 4) {
            y2Var.a((char) 254);
        }
        StringBuilder sbB = y2Var.b();
        if (sbB.length() < iA2) {
            sbB.append((char) 129);
        }
        while (sbB.length() < iA2) {
            sbB.append(a((char) 129, sbB.length() + 1));
        }
        return y2Var.b().toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x020b, code lost:
    
        return 5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static int a(java.lang.CharSequence r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.d4.a(java.lang.CharSequence, int, int):int");
    }

    private static int a(float[] fArr, int[] iArr, int i2, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        for (int i3 = 0; i3 < 6; i3++) {
            int iCeil = (int) Math.ceil(fArr[i3]);
            iArr[i3] = iCeil;
            if (i2 > iCeil) {
                Arrays.fill(bArr, (byte) 0);
                i2 = iCeil;
            }
            if (i2 == iCeil) {
                bArr[i3] = (byte) (bArr[i3] + 1);
            }
        }
        return i2;
    }

    private static int a(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            i2 += bArr[i3];
        }
        return i2;
    }

    public static int a(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        if (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            while (b(cCharAt) && i2 < length) {
                i3++;
                i2++;
                if (i2 < length) {
                    cCharAt = charSequence.charAt(i2);
                }
            }
        }
        return i3;
    }

    static void a(char c2) {
        String hexString = Integer.toHexString(c2);
        throw new IllegalArgumentException("Illegal character: " + c2 + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }
}
