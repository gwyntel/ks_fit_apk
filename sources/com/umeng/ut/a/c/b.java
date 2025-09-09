package com.umeng.ut.a.c;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f22993a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String a(String str, String str2) {
        try {
            byte[] bArrA = a(str.getBytes(), str2.getBytes());
            return bArrA != null ? toHexString(bArrA) : "0000000000000000";
        } catch (Exception e2) {
            e.m85a("", e2);
            return "0000000000000000";
        }
    }

    public static String c(String str) {
        return a(c(), str);
    }

    private static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            char[] cArr = f22993a;
            sb.append(cArr[(bArr[i2] & 240) >>> 4]);
            sb.append(cArr[bArr[i2] & 15]);
        }
        return sb.toString();
    }

    private static String c() {
        byte[] bytes = "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn".getBytes();
        for (byte b2 = 0; b2 < 32; b2 = (byte) (b2 + 1)) {
            try {
                bytes[b2] = (byte) (bytes[b2] + b2);
            } catch (Exception unused) {
                return null;
            }
        }
        return toHexString(bytes);
    }

    private static byte[] a(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception e2) {
            e.a("", e2, new Object[0]);
            return null;
        }
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        byte[] bArr3 = new byte[64];
        byte[] bArr4 = new byte[64];
        for (int i2 = 0; i2 < 64; i2++) {
            bArr3[i2] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS;
            bArr4[i2] = 92;
        }
        byte[] bArr5 = new byte[64];
        if (bArr.length > 64) {
            bArr = a(bArr);
        }
        for (int i3 = 0; i3 < bArr.length; i3++) {
            bArr5[i3] = bArr[i3];
        }
        if (bArr.length < 64) {
            for (int length = bArr.length; length < 64; length++) {
                bArr5[length] = 0;
            }
        }
        byte[] bArr6 = new byte[64];
        for (int i4 = 0; i4 < 64; i4++) {
            bArr6[i4] = (byte) (bArr5[i4] ^ bArr3[i4]);
        }
        byte[] bArr7 = new byte[bArr2.length + 64];
        for (int i5 = 0; i5 < 64; i5++) {
            bArr7[i5] = bArr6[i5];
        }
        for (int i6 = 0; i6 < bArr2.length; i6++) {
            bArr7[i6 + 64] = bArr2[i6];
        }
        byte[] bArrA = a(bArr7);
        byte[] bArr8 = new byte[64];
        for (int i7 = 0; i7 < 64; i7++) {
            bArr8[i7] = (byte) (bArr5[i7] ^ bArr4[i7]);
        }
        byte[] bArr9 = new byte[bArrA.length + 64];
        for (int i8 = 0; i8 < 64; i8++) {
            bArr9[i8] = bArr8[i8];
        }
        for (int i9 = 0; i9 < bArrA.length; i9++) {
            bArr9[i9 + 64] = bArrA[i9];
        }
        return a(bArr9);
    }
}
