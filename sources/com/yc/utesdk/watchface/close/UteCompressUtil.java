package com.yc.utesdk.watchface.close;

import com.yc.utesdk.log.LogUtils;
import java.util.Arrays;
import net.jpountz.lz4.LZ4Factory;

/* loaded from: classes4.dex */
public class UteCompressUtil {
    private static UteCompressUtil instance;

    public static synchronized UteCompressUtil getInstance() {
        try {
            if (instance == null) {
                instance = new UteCompressUtil();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public byte[] uteCompress(byte[] bArr) {
        LZ4Factory lZ4FactoryFastestInstance = LZ4Factory.fastestInstance();
        LogUtils.i("压缩前 length =" + bArr.length);
        byte[] bArrCompress = lZ4FactoryFastestInstance.fastCompressor().compress(bArr);
        LogUtils.i("压缩后 length =" + bArrCompress.length);
        return bArrCompress;
    }

    public byte[] uteDecompress(byte[] bArr, int i2) {
        LogUtils.i("uteDecompress =" + i2);
        byte[] bArr2 = new byte[i2];
        int iDecompress = LZ4Factory.fastestInstance().safeDecompressor().decompress(bArr, bArr2);
        byte[] bArr3 = new byte[iDecompress];
        LogUtils.i("解压前 length =" + bArr.length);
        byte[] bArrCopyOf = Arrays.copyOf(bArr2, iDecompress);
        LogUtils.i("解压后 length =" + iDecompress + ", =" + bArrCopyOf.length);
        return bArrCopyOf;
    }
}
