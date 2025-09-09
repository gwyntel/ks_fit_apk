package com.aliyun.alink.apiclient.utils;

import com.alipay.sdk.m.n.a;
import org.apache.commons.io.IOUtils;

/* loaded from: classes2.dex */
public class DatatypeConverter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char[] encodeMap = initEncodeMap();

    public static String _printBase64Binary(byte[] bArr) {
        return _printBase64Binary(bArr, 0, bArr.length);
    }

    public static char encode(int i2) {
        return encodeMap[i2 & 63];
    }

    private static char[] initEncodeMap() {
        int i2;
        int i3;
        char[] cArr = new char[64];
        int i4 = 0;
        while (true) {
            i2 = 26;
            if (i4 >= 26) {
                break;
            }
            cArr[i4] = (char) (i4 + 65);
            i4++;
        }
        while (true) {
            if (i2 >= 52) {
                break;
            }
            cArr[i2] = (char) (i2 + 71);
            i2++;
        }
        for (i3 = 52; i3 < 62; i3++) {
            cArr[i3] = (char) (i3 - 4);
        }
        cArr[62] = '+';
        cArr[63] = IOUtils.DIR_SEPARATOR_UNIX;
        return cArr;
    }

    public static byte[] parseBase64Binary(String str) {
        return new byte[0];
    }

    public static String printBase64Binary(byte[] bArr) {
        return _printBase64Binary(bArr);
    }

    public static String _printBase64Binary(byte[] bArr, int i2, int i3) {
        char[] cArr = new char[((i3 + 2) / 3) * 4];
        _printBase64Binary(bArr, i2, i3, cArr, 0);
        return new String(cArr);
    }

    public static int _printBase64Binary(byte[] bArr, int i2, int i3, char[] cArr, int i4) {
        while (i3 >= 3) {
            cArr[i4] = encode(bArr[i2] >> 2);
            int i5 = i2 + 1;
            cArr[i4 + 1] = encode(((bArr[i2] & 3) << 4) | ((bArr[i5] >> 4) & 15));
            int i6 = i4 + 3;
            int i7 = i2 + 2;
            cArr[i4 + 2] = encode((3 & (bArr[i7] >> 6)) | ((bArr[i5] & 15) << 2));
            i4 += 4;
            cArr[i6] = encode(bArr[i7] & 63);
            i3 -= 3;
            i2 += 3;
        }
        if (i3 == 1) {
            cArr[i4] = encode(bArr[i2] >> 2);
            cArr[i4 + 1] = encode((bArr[i2] & 3) << 4);
            int i8 = i4 + 3;
            cArr[i4 + 2] = a.f9565h;
            i4 += 4;
            cArr[i8] = a.f9565h;
        }
        if (i3 != 2) {
            return i4;
        }
        cArr[i4] = encode(bArr[i2] >> 2);
        int i9 = (3 & bArr[i2]) << 4;
        int i10 = i2 + 1;
        cArr[i4 + 1] = encode(i9 | ((bArr[i10] >> 4) & 15));
        int i11 = i4 + 3;
        cArr[i4 + 2] = encode((bArr[i10] & 15) << 2);
        int i12 = i4 + 4;
        cArr[i11] = a.f9565h;
        return i12;
    }
}
