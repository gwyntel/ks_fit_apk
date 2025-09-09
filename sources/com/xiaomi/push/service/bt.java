package com.xiaomi.push.service;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes4.dex */
public class bt {

    /* renamed from: a, reason: collision with root package name */
    private static RSAPublicKey f24561a;

    /* renamed from: a, reason: collision with other field name */
    private static final byte[] f1055a;

    static {
        byte[] bArr = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, -127, -97, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 13, 6, 9, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, -127, -119, 2, -127, -127, 0, -109, -38, -114, Ascii.SUB, -72, 78, 16, 70, -90, 113, -30, Constants.CMD_TYPE.CMD_OTA_RES, 85, -3, -43, 123, 61, -98, 4, -16, 67, 19, -90, -73, -5, -89, Constants.CMD_TYPE.CMD_OTA_RES, 44, -27, 59, -123, 72, -73, -48, Constants.CMD_TYPE.CMD_STATUS_REPORT, 13, 16, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, -27, -82, 18, -28, 84, 0, -41, 16, 69, -39, 7, 82, 56, 79, -37, 40, 85, 107, 98, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 123, -34, -49, 111, -11, Constants.CMD_TYPE.CMD_STATUS_REPORT, Ascii.FS, 117, -74, 114, -122, -29, -84, 82, Ascii.SYN, -122, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -40, -79, 18, -116, -42, 101, -70, 44, 11, 62, -49, -3, -22, -2, 66, 90, -116, -75, -99, Constants.CMD_TYPE.CMD_REQUEST_OTA, 121, 69, 10, -81, -57, 89, -23, -36, -60, -81, 67, -114, 10, 79, 100, Ascii.GS, Constants.CMD_TYPE.CMD_OTA, -24, 110, -66, -7, 87, 16, -125, -91, -43, -103, 67, -20, 41, 117, -37, -11, 2, 3, 1, 0, 1};
        f1055a = bArr;
        try {
            f24561a = (RSAPublicKey) KeyFactory.getInstance(com.alipay.sdk.m.n.d.f9568a).generatePublic(new X509EncodedKeySpec(bArr));
        } catch (Throwable unused) {
            com.xiaomi.channel.commonutils.logger.b.d("rsa key pair init failure!!!");
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, f24561a);
            return Base64.encodeToString(a(cipher, 1, str.getBytes("UTF-8"), f24561a.getModulus().bitLength()), 2);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static byte[] a(Cipher cipher, int i2, byte[] bArr, int i3) throws BadPaddingException, IllegalBlockSizeException {
        int i4;
        byte[] bArrDoFinal;
        if (cipher == null || bArr == null) {
            return null;
        }
        if (i2 == 2) {
            i4 = i3 / 8;
        } else {
            i4 = (i3 / 8) - 11;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i5 = 0;
            int i6 = 0;
            while (bArr.length > i5) {
                if (bArr.length - i5 > i4) {
                    bArrDoFinal = cipher.doFinal(bArr, i5, i4);
                } else {
                    bArrDoFinal = cipher.doFinal(bArr, i5, bArr.length - i5);
                }
                byteArrayOutputStream.write(bArrDoFinal, 0, bArrDoFinal.length);
                i6++;
                i5 = i6 * i4;
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
