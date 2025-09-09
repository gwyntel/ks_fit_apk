package com.huawei.secure.android.common.encrypt.hash;

import android.os.Build;
import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/* loaded from: classes4.dex */
public abstract class PBKDF2 {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18289a = "PBKDF2";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18290b = "PBKDF2WithHmacSHA1";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18291c = "PBKDF2WithHmacSHA256";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18292d = "";

    /* renamed from: e, reason: collision with root package name */
    private static final int f18293e = 8;

    /* renamed from: f, reason: collision with root package name */
    private static final int f18294f = 16;

    /* renamed from: g, reason: collision with root package name */
    private static final int f18295g = 32;

    /* renamed from: h, reason: collision with root package name */
    private static final int f18296h = 10000;

    /* renamed from: i, reason: collision with root package name */
    private static final int f18297i = 1000;

    private static byte[] a(char[] cArr, byte[] bArr, int i2, int i3, boolean z2) {
        try {
            return (z2 ? SecretKeyFactory.getInstance(f18291c) : SecretKeyFactory.getInstance(f18290b)).generateSecret(new PBEKeySpec(cArr, bArr, i2, i3)).getEncoded();
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            b.b(f18289a, "pbkdf exception : " + e.getMessage());
            return new byte[0];
        } catch (InvalidKeySpecException e3) {
            e = e3;
            b.b(f18289a, "pbkdf exception : " + e.getMessage());
            return new byte[0];
        }
    }

    public static byte[] pbkdf2(char[] cArr, byte[] bArr, int i2, int i3) {
        return a(cArr, bArr, i2, i3, false);
    }

    @Deprecated
    public static String pbkdf2Encrypt(String str) {
        return pbkdf2Encrypt(str, 10000);
    }

    public static String pbkdf2EncryptNew(String str) {
        return pbkdf2EncryptNew(str, 10000);
    }

    public static byte[] pbkdf2SHA256(char[] cArr, byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[0];
        if (Build.VERSION.SDK_INT >= 26) {
            return a(cArr, bArr, i2, i3, true);
        }
        b.b(f18289a, "system version not high than 26");
        return bArr2;
    }

    @Deprecated
    public static boolean validatePassword(String str, String str2) {
        return validatePassword(str, str2, 10000);
    }

    public static boolean validatePasswordNew(String str, String str2) {
        return validatePasswordNew(str, str2, 10000);
    }

    @Deprecated
    public static String pbkdf2Encrypt(String str, int i2) {
        return pbkdf2Encrypt(str, EncryptUtil.generateSecureRandom(8), i2, 32);
    }

    public static String pbkdf2EncryptNew(String str, int i2) {
        return pbkdf2EncryptNew(str, EncryptUtil.generateSecureRandom(16), i2, 32);
    }

    @Deprecated
    public static boolean validatePassword(String str, String str2, int i2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str2.length() < 16) {
            return false;
        }
        return a(pbkdf2(str.toCharArray(), HexUtil.hexStr2ByteArray(str2.substring(0, 16)), i2, 256), HexUtil.hexStr2ByteArray(str2.substring(16)));
    }

    public static boolean validatePasswordNew(String str, String str2, int i2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str2.length() < 32) {
            return false;
        }
        String strSubstring = str2.substring(0, 32);
        return a(Build.VERSION.SDK_INT < 26 ? pbkdf2(str.toCharArray(), HexUtil.hexStr2ByteArray(strSubstring), i2, 256) : pbkdf2SHA256(str.toCharArray(), HexUtil.hexStr2ByteArray(strSubstring), i2, 256), HexUtil.hexStr2ByteArray(str2.substring(32)));
    }

    @Deprecated
    public static String pbkdf2Encrypt(String str, byte[] bArr, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            b.b(f18289a, "pwd is null.");
            return "";
        }
        if (i2 < 1000) {
            b.b(f18289a, "iterations times is not enough.");
            return "";
        }
        if (bArr == null || bArr.length < 8) {
            b.b(f18289a, "salt parameter is null or length is not enough");
            return "";
        }
        if (i3 < 32) {
            b.b(f18289a, "cipherLen length is not enough");
            return "";
        }
        return HexUtil.byteArray2HexStr(bArr) + HexUtil.byteArray2HexStr(pbkdf2(str.toCharArray(), bArr, i2, i3 * 8));
    }

    public static String pbkdf2EncryptNew(String str, byte[] bArr, int i2, int i3) {
        byte[] bArrPbkdf2SHA256;
        if (TextUtils.isEmpty(str)) {
            b.b(f18289a, "pwd is null.");
            return "";
        }
        if (i2 < 1000) {
            b.b(f18289a, "iterations times is not enough.");
            return "";
        }
        if (bArr == null || bArr.length < 16) {
            b.b(f18289a, "salt parameter is null or length is not enough");
            return "";
        }
        if (i3 < 32) {
            b.b(f18289a, "cipherLen length is not enough");
            return "";
        }
        if (Build.VERSION.SDK_INT < 26) {
            b.c(f18289a, "sha 1");
            bArrPbkdf2SHA256 = pbkdf2(str.toCharArray(), bArr, i2, i3 * 8);
        } else {
            b.c(f18289a, "sha 256");
            bArrPbkdf2SHA256 = pbkdf2SHA256(str.toCharArray(), bArr, i2, i3 * 8);
        }
        return HexUtil.byteArray2HexStr(bArr) + HexUtil.byteArray2HexStr(bArrPbkdf2SHA256);
    }

    private static boolean a(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return false;
        }
        int length = bArr.length ^ bArr2.length;
        for (int i2 = 0; i2 < bArr.length && i2 < bArr2.length; i2++) {
            length |= bArr[i2] ^ bArr2[i2];
        }
        return length == 0;
    }
}
