package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class AESUtil {
    public static final String PKCS7PADDING_CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /* renamed from: a, reason: collision with root package name */
    public static final String f8260a = "AESUtil";

    /* renamed from: b, reason: collision with root package name */
    public static AESUtil f8261b;

    /* renamed from: c, reason: collision with root package name */
    public final String f8262c = "AES/CBC/NoPadding";

    /* renamed from: d, reason: collision with root package name */
    public SecretKeySpec f8263d = null;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f8264e = "123aqwed#*$!(4ju".getBytes();

    /* renamed from: g, reason: collision with root package name */
    public String f8266g = "";

    /* renamed from: f, reason: collision with root package name */
    public IvParameterSpec f8265f = new IvParameterSpec(this.f8264e);

    public static AESUtil getInstance() {
        if (f8261b == null) {
            synchronized (AESUtil.class) {
                try {
                    if (f8261b == null) {
                        f8261b = new AESUtil();
                    }
                } finally {
                }
            }
        }
        return f8261b;
    }

    public final byte[] a(String str, SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        LogUtils.d(f8260a, "Decrypt  cmp: " + str + ", secret key: " + ConvertUtils.bytes2HexString(secretKey.getEncoded()) + ", cipher text: " + ConvertUtils.bytes2HexString(bArr));
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(2, secretKey, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String decrypt(String str) {
        return ConvertUtils.bytes2HexString(a("AES/CBC/NoPadding", this.f8263d, this.f8265f, ConvertUtils.hexString2Bytes(str)));
    }

    public byte[] encrypt(byte[] bArr) {
        return encrypt("AES/CBC/NoPadding", this.f8263d, this.f8265f, bArr);
    }

    public void setKey(String str) {
        this.f8266g = str;
        this.f8263d = new SecretKeySpec(ConvertUtils.hexString2Bytes(this.f8266g), AESUtils.AES);
    }

    public byte[] encrypt(String str, byte[] bArr) {
        return encrypt(str, this.f8263d, this.f8265f, bArr);
    }

    public byte[] decrypt(byte[] bArr) {
        return a(PKCS7PADDING_CIPHER_ALGORITHM, this.f8263d, this.f8265f, bArr);
    }

    public byte[] encrypt(String str, SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(1, secretKey, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void setKey(byte[] bArr) {
        this.f8266g = ConvertUtils.bytes2HexString(bArr);
        this.f8263d = new SecretKeySpec(bArr, AESUtils.AES);
    }
}
