package com.xiaomi.push;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f23859a = {100, Ascii.ETB, 84, 114, 72, 0, 4, 97, 73, 97, 2, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, 84, 102, 18, 32};

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr, 2).doFinal(bArr2);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        return a(bArr, 1).doFinal(bArr2);
    }

    private static Cipher a(byte[] bArr, int i2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, AESUtils.AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(f23859a);
        Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
        cipher.init(i2, secretKeySpec, ivParameterSpec);
        return cipher;
    }
}
