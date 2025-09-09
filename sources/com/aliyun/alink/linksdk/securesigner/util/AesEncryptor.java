package com.aliyun.alink.linksdk.securesigner.util;

import android.util.Base64;
import com.yc.utesdk.utils.close.AESUtils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class AesEncryptor {
    private static final byte[] IV = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static String decrypt(String str, String str2) throws Exception {
        Charset charset = StandardCharsets.UTF_8;
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(charset), AESUtils.AES);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(IV));
        return new String(cipher.doFinal(Base64.decode(str2, 0)), charset);
    }

    public static String encrypt(String str, String str2) throws Exception {
        Charset charset = StandardCharsets.UTF_8;
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(charset), AESUtils.AES);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(IV));
        return Base64.encodeToString(cipher.doFinal(str2.getBytes(charset)), 0);
    }
}
