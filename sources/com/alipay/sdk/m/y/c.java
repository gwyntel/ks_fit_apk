package com.alipay.sdk.m.y;

import com.yc.utesdk.utils.close.AESUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public static String f9933a = "idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#";

    public static String a() {
        String str = new String();
        for (int i2 = 0; i2 < f9933a.length() - 1; i2 += 4) {
            str = str + f9933a.charAt(i2);
        }
        return str;
    }

    public static String b(String str, String str2) throws Exception {
        byte[] bArrDoFinal;
        try {
            PBEKeySpec pBEKeySpecA = a(str);
            int length = str2.length() / 2;
            byte[] bArr = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                bArr[i2] = Integer.valueOf(str2.substring(i3, i3 + 2), 16).byteValue();
            }
            byte[] bArrB = b();
            if (length <= 16) {
                bArrDoFinal = null;
            } else {
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(pBEKeySpecA.getPassword(), Arrays.copyOf(bArr, 16), 10, 128)).getEncoded(), AESUtils.AES);
                Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
                cipher.init(2, secretKeySpec, new IvParameterSpec(bArrB));
                bArrDoFinal = cipher.doFinal(bArr, 16, length - 16);
            }
        } catch (Exception unused) {
        }
        if (bArrDoFinal == null) {
            throw new Exception();
        }
        String str3 = new String(bArrDoFinal);
        if (com.alipay.sdk.m.z.a.c(str3)) {
            return str3;
        }
        return null;
    }

    public static String a(String str, String str2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException {
        try {
            PBEKeySpec pBEKeySpecA = a(str);
            byte[] bytes = str2.getBytes();
            byte[] bArrB = b();
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpecA).getEncoded(), AESUtils.AES);
            Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
            cipher.init(1, secretKeySpec, new IvParameterSpec(bArrB));
            byte[] salt = pBEKeySpecA.getSalt();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(salt.length + cipher.getOutputSize(bytes.length));
            byteBufferAllocate.put(salt);
            cipher.doFinal(ByteBuffer.wrap(bytes), byteBufferAllocate);
            return a(byteBufferAllocate.array());
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b() {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < 48; i2 += 2) {
                sb.append("AsAgAtA5A6AdAgABABACADAfAsAdAfAsAgAaAgA3A5A6=8=0".charAt(i2));
            }
            return a.a(sb.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b2 : bArr) {
            stringBuffer.append("0123456789ABCDEF".charAt((b2 >> 4) & 15));
            stringBuffer.append("0123456789ABCDEF".charAt(b2 & 15));
        }
        return stringBuffer.toString();
    }

    public static PBEKeySpec a(String str) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = Class.forName(new String(a.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object objNewInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", bArr.getClass());
        method.setAccessible(true);
        method.invoke(objNewInstance, bArr);
        return new PBEKeySpec(str.toCharArray(), bArr, 10, 128);
    }
}
