package com.xiaomi.accountsdk.diagnosis.encrypt;

import android.util.Log;
import com.xiaomi.accountsdk.diagnosis.encrypt.PassportEnvEncryptUtils;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
class e {
    private e() {
    }

    private static int a(int i2, String str, String str2) throws Throwable {
        com.xiaomi.accountsdk.diagnosis.b.a.a(com.xiaomi.accountsdk.diagnosis.b.b.a(com.xiaomi.accountsdk.diagnosis.b.c.a(i2), str) + str2);
        return Log.println(i2, str, str2);
    }

    int a(int i2, String str, String str2, Throwable th) throws Throwable {
        int iA = a(i2, str, a(str2));
        return th != null ? iA + a(i2, str, a(Log.getStackTraceString(th))) : iA;
    }

    public static e a() {
        return new e();
    }

    private String a(String str) throws NoSuchAlgorithmException {
        try {
            PassportEnvEncryptUtils.EncryptResult encryptResultEncrypt = PassportEnvEncryptUtils.encrypt(str);
            return String.format("#&^%s!!%s^&#", encryptResultEncrypt.encryptedKey, encryptResultEncrypt.content);
        } catch (PassportEnvEncryptUtils.EncryptException e2) {
            e2.printStackTrace();
            return str;
        }
    }
}
