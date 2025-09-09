package org.eclipse.paho.client.mqttv3.internal.security;

/* loaded from: classes5.dex */
public class SimpleBase64Encoder {
    private static final String PWDCHARS_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final char[] PWDCHARS_ARRAY = PWDCHARS_STRING.toCharArray();

    public static byte[] decode(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        byte[] bArr = new byte[(length * 3) / 4];
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (length < 4) {
                break;
            }
            long jFrom64 = from64(bytes, i2, 4);
            length -= 4;
            i2 += 4;
            for (int i4 = 2; i4 >= 0; i4--) {
                bArr[i3 + i4] = (byte) (jFrom64 & 255);
                jFrom64 >>= 8;
            }
            i3 += 3;
        }
        if (length == 3) {
            long jFrom642 = from64(bytes, i2, 3);
            for (int i5 = 1; i5 >= 0; i5--) {
                bArr[i3 + i5] = (byte) (jFrom642 & 255);
                jFrom642 >>= 8;
            }
        }
        if (length == 2) {
            bArr[i3] = (byte) (from64(bytes, i2, 2) & 255);
        }
        return bArr;
    }

    public static String encode(byte[] bArr) {
        int length = bArr.length;
        StringBuffer stringBuffer = new StringBuffer(((length + 2) / 3) * 4);
        int i2 = 0;
        while (length >= 3) {
            stringBuffer.append(to64(((bArr[i2] & 255) << 16) | ((bArr[i2 + 1] & 255) << 8) | (bArr[i2 + 2] & 255), 4));
            i2 += 3;
            length -= 3;
        }
        if (length == 2) {
            stringBuffer.append(to64(((bArr[i2] & 255) << 8) | (bArr[i2 + 1] & 255), 3));
        }
        if (length == 1) {
            stringBuffer.append(to64(bArr[i2] & 255, 2));
        }
        return stringBuffer.toString();
    }

    private static final long from64(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        long j2 = 0;
        while (i3 > 0) {
            i3--;
            int i5 = i2 + 1;
            byte b2 = bArr[i2];
            long j3 = b2 == 47 ? 1L : 0L;
            if (b2 >= 48 && b2 <= 57) {
                j3 = b2 - 46;
            }
            if (b2 >= 65 && b2 <= 90) {
                j3 = b2 - 53;
            }
            if (b2 >= 97 && b2 <= 122) {
                j3 = b2 - 59;
            }
            j2 += j3 << i4;
            i4 += 6;
            i2 = i5;
        }
        return j2;
    }

    private static final String to64(long j2, int i2) {
        StringBuffer stringBuffer = new StringBuffer(i2);
        while (i2 > 0) {
            i2--;
            stringBuffer.append(PWDCHARS_ARRAY[(int) (63 & j2)]);
            j2 >>= 6;
        }
        return stringBuffer.toString();
    }
}
