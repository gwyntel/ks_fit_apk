package com.alipay.sdk.m.n;

import android.text.TextUtils;
import java.security.SecureRandom;
import javax.crypto.Cipher;

/* loaded from: classes2.dex */
public class c {
    public static byte[] a(Cipher cipher, String str) {
        SecureRandom secureRandom = new SecureRandom();
        int blockSize = cipher.getBlockSize();
        if (TextUtils.isEmpty(str)) {
            str = String.valueOf(secureRandom.nextDouble());
        }
        int i2 = blockSize * 2;
        byte[] bArr = new byte[i2];
        byte[] bArr2 = new byte[blockSize];
        secureRandom.nextBytes(bArr2);
        for (int i3 = 1; i3 < i2; i3++) {
            byte bCodePointAt = (byte) (str.codePointAt(i3 % str.length()) & 127);
            bArr[i3] = bCodePointAt;
            if (i3 >= blockSize) {
                bArr[i3] = (byte) (bArr[0] & bCodePointAt);
            }
        }
        System.arraycopy(bArr, blockSize, bArr2, 0, blockSize);
        return bArr2;
    }
}
