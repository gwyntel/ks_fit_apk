package com.yc.utesdk.utils.open;

/* loaded from: classes4.dex */
public class ByteDataUtil {
    private static ByteDataUtil instance;

    public static ByteDataUtil getInstance() {
        if (instance == null) {
            instance = new ByteDataUtil();
        }
        return instance;
    }

    public byte[] copyTwoArrays(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
