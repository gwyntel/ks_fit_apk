package com.vivo.push.util;

import android.content.Context;
import com.alibaba.ailabs.iot.aisbase.Constants;

/* loaded from: classes4.dex */
public final class w extends b {

    /* renamed from: b, reason: collision with root package name */
    private static w f23263b;

    public static synchronized w b() {
        try {
            if (f23263b == null) {
                f23263b = new w();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23263b;
    }

    public final synchronized void a(Context context) {
        if (this.f23225a == null) {
            this.f23225a = context;
            a(context, "com.vivo.push_preferences");
        }
    }

    public final byte[] c() {
        byte[] bArrC = c(b("com.vivo.push.secure_cache_iv", ""));
        return (bArrC == null || bArrC.length <= 0) ? new byte[]{Constants.CMD_TYPE.CMD_REQUEST_OTA, 32, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, 32, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, 41, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 32, 32, 32} : bArrC;
    }

    public final byte[] d() {
        byte[] bArrC = c(b("com.vivo.push.secure_cache_key", ""));
        return (bArrC == null || bArrC.length <= 0) ? new byte[]{Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 39, 40, 41, 32, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES} : bArrC;
    }

    private static byte[] c(String str) {
        int length;
        byte[] bArr = null;
        try {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                bArr = new byte[strArrSplit.length];
                length = strArrSplit.length;
            } else {
                length = 0;
            }
            for (int i2 = 0; i2 < length; i2++) {
                bArr[i2] = Byte.parseByte(strArrSplit[i2].trim());
            }
        } catch (Exception e2) {
            p.a("SharePreferenceManager", "getCodeBytes error:" + e2.getMessage());
        }
        return bArr;
    }
}
