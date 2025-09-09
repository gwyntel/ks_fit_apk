package org.android.agoo.common;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.google.common.base.Ascii;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static byte[] f26509a = {82, Ascii.SYN, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, 44, -16, 124, -40, -114, -87, -40, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Ascii.ETB, -56, Ascii.ETB, -33, 75};

    /* renamed from: b, reason: collision with root package name */
    private static ThreadLocal<Cipher> f26510b = new ThreadLocal<>();

    /* renamed from: c, reason: collision with root package name */
    private static final AlgorithmParameterSpec f26511c = new IvParameterSpec(f26509a);

    public static final byte[] a(byte[] bArr, SecretKeySpec secretKeySpec, byte[] bArr2) throws IllegalArgumentException {
        try {
            return a(secretKeySpec, bArr2, 2).doFinal(bArr);
        } catch (BadPaddingException e2) {
            throw new IllegalArgumentException("AES decrypt error:" + e2.getMessage(), e2);
        } catch (IllegalBlockSizeException e3) {
            throw new IllegalArgumentException("AES decrypt error:" + e3.getMessage(), e3);
        }
    }

    private static final Cipher a(SecretKeySpec secretKeySpec, byte[] bArr, int i2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipherA = a();
        try {
            cipherA.init(i2, secretKeySpec, new IvParameterSpec(bArr));
            return cipherA;
        } catch (IllegalArgumentException e2) {
            throw new RuntimeException("init Chipher error:" + e2.getMessage(), e2);
        } catch (InvalidAlgorithmParameterException e3) {
            throw new RuntimeException("init Chipher error:" + e3.getMessage(), e3);
        } catch (InvalidKeyException e4) {
            throw new RuntimeException("init Chipher error:" + e4.getMessage(), e4);
        }
    }

    private static final Cipher a() throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = f26510b.get();
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
                f26510b.set(cipher);
            } catch (NoSuchAlgorithmException e2) {
                throw new RuntimeException("get Chipher error:" + e2.getMessage(), e2);
            } catch (NoSuchPaddingException e3) {
                throw new RuntimeException("get Chipher error:" + e3.getMessage(), e3);
            }
        }
        return cipher;
    }

    public static final byte[] a(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Throwable th) {
            throw new RuntimeException("md5 value Throwable", th);
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return mac.doFinal(bArr2);
        } catch (Throwable th) {
            throw new RuntimeException("HmacSHA1 Throwable", th);
        }
    }
}
