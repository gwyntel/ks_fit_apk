package com.huawei.secure.android.common.encrypt.utils;

import android.annotation.SuppressLint;
import com.huawei.secure.android.common.encrypt.hash.PBKDF2;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class BaseKeyUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18339a = "BaseKeyUtil";

    /* renamed from: b, reason: collision with root package name */
    private static final int f18340b = 16;

    /* renamed from: c, reason: collision with root package name */
    private static final int f18341c = 16;

    /* renamed from: d, reason: collision with root package name */
    private static final int f18342d = 10000;

    /* renamed from: e, reason: collision with root package name */
    private static final int f18343e = 32;

    /* renamed from: f, reason: collision with root package name */
    private static final int f18344f = 1;

    private static int a(int i2, int i3, int i4) {
        if (i3 < i2) {
            i2 = i3;
        }
        return i4 < i2 ? i4 : i2;
    }

    public static String exportHexRootKey(String str, String str2, String str3, byte[] bArr, int i2, boolean z2) {
        return HexUtil.byteArray2HexStr(exportRootKey(str, str2, str3, bArr, i2, z2));
    }

    @SuppressLint({"NewApi"})
    public static byte[] exportRootKey(String str, String str2, String str3, byte[] bArr, boolean z2) {
        return exportRootKey(str, str2, str3, bArr, 16, z2);
    }

    public static byte[] exportRootKey32(String str, String str2, String str3, byte[] bArr, boolean z2) {
        return exportRootKey(str, str2, str3, bArr, 32, z2);
    }

    public static byte[] exportRootKey32Iteration1(String str, String str2, String str3, byte[] bArr, boolean z2) {
        return exportRootKey(str, str2, str3, bArr, 1, 32, z2);
    }

    @SuppressLint({"NewApi"})
    public static byte[] exportRootKeyIteration1(String str, String str2, String str3, byte[] bArr, boolean z2) {
        return exportRootKey(str, str2, str3, bArr, 1, 16, z2);
    }

    private static boolean a(int i2) {
        return i2 >= 16;
    }

    @SuppressLint({"NewApi"})
    public static byte[] exportRootKey(String str, String str2, String str3, byte[] bArr, int i2, boolean z2) {
        return exportRootKey(str, str2, str3, bArr, 10000, i2, z2);
    }

    private static boolean a(int i2, byte[] bArr) {
        return a(i2) & a(bArr);
    }

    public static byte[] exportRootKey(String str, String str2, String str3, byte[] bArr, int i2, int i3, boolean z2) throws UnsupportedEncodingException {
        byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str);
        byte[] bArrHexStr2ByteArray2 = HexUtil.hexStr2ByteArray(str2);
        byte[] bArrHexStr2ByteArray3 = HexUtil.hexStr2ByteArray(str3);
        int iA = a(bArrHexStr2ByteArray.length, bArrHexStr2ByteArray2.length, bArrHexStr2ByteArray3.length);
        if (a(iA, bArr)) {
            char[] cArr = new char[iA];
            for (int i4 = 0; i4 < iA; i4++) {
                cArr[i4] = (char) ((bArrHexStr2ByteArray[i4] ^ bArrHexStr2ByteArray2[i4]) ^ bArrHexStr2ByteArray3[i4]);
            }
            if (!z2) {
                b.c(f18339a, "exportRootKey: sha1");
                return PBKDF2.pbkdf2(cArr, bArr, i2, i3 * 8);
            }
            b.c(f18339a, "exportRootKey: sha256");
            return PBKDF2.pbkdf2SHA256(cArr, bArr, i2, i3 * 8);
        }
        throw new IllegalArgumentException("key length must be more than 128bit.");
    }

    private static boolean a(byte[] bArr) {
        return bArr.length >= 16;
    }

    public static byte[] exportRootKey(String str, String str2, String str3, String str4, int i2, boolean z2) {
        return exportRootKey(str, str2, str3, HexUtil.hexStr2ByteArray(str4), i2, z2);
    }
}
