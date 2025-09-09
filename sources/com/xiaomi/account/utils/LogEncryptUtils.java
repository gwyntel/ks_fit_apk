package com.xiaomi.account.utils;

import com.xiaomi.accountsdk.diagnosis.encrypt.PassportEnvEncryptUtils;

/* loaded from: classes4.dex */
public class LogEncryptUtils {
    public static String generateEncryptMessageLine(String str) {
        try {
            PassportEnvEncryptUtils.EncryptResult encryptResultEncrypt = PassportEnvEncryptUtils.encrypt(str);
            return String.format("#&^%s!!%s^&#", encryptResultEncrypt.encryptedKey, encryptResultEncrypt.content);
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }
}
