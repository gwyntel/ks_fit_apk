package com.alipay.sdk.m.l0;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.yc.utesdk.utils.close.AESUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class a {
    public static String a(String str) {
        byte[] bArrA;
        try {
            bArrA = a(a(), str.getBytes());
        } catch (Exception unused) {
            bArrA = null;
        }
        if (bArrA != null) {
            return a(bArrA);
        }
        return null;
    }

    public static String b(String str) {
        try {
            return new String(b(a(), m55a(str)));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] a() throws Exception {
        return e.a(new byte[]{Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 83, -50, -89, -84, -114, 80, 99, 10, 63, Ascii.SYN, -65, -11, Ascii.RS, 101, -118});
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, AESUtils.AES);
        Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
        cipher.init(2, secretKeySpec, new IvParameterSpec(b()));
        return cipher.doFinal(bArr2);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, AESUtils.AES);
        Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
        cipher.init(1, secretKeySpec, new IvParameterSpec(b()));
        return cipher.doFinal(bArr2);
    }

    public static byte[] b() {
        try {
            byte[] bArrA = b.a("IUQSvE6r1TfFPdPEjfklLw==".getBytes("UTF-8"), 2);
            if (bArrA != null) {
                return e.a(bArrA);
            }
        } catch (Exception unused) {
        }
        return new byte[16];
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m55a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = Integer.valueOf(str.substring(i3, i3 + 2), 16).byteValue();
        }
        return bArr;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b2 : bArr) {
            a(stringBuffer, b2);
        }
        return stringBuffer.toString();
    }

    public static void a(StringBuffer stringBuffer, byte b2) {
        stringBuffer.append("0123456789ABCDEF".charAt((b2 >> 4) & 15));
        stringBuffer.append("0123456789ABCDEF".charAt(b2 & 15));
    }
}
