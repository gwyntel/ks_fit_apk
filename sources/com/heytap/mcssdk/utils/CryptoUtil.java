package com.heytap.mcssdk.utils;

import android.text.TextUtils;
import com.heytap.mcssdk.base.Base64;
import com.heytap.msp.push.encrypt.AESEncrypt;
import com.yc.utesdk.utils.close.AESUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* loaded from: classes3.dex */
public class CryptoUtil {
    public static String DES_KEY = "";
    public static final String DES_KEY_BASE64 = "Y29tLm5lYXJtZS5tY3M=";
    public static String mDecryptTag;

    public static String aesDecrypt(String str) {
        boolean z2;
        String strDecrypt = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            strDecrypt = AESEncrypt.decrypt(AESEncrypt.SDK_APP_SECRET, str);
            LogUtil.d("sdkDecrypt aesDecrypt aes data " + strDecrypt);
            z2 = true;
        } catch (Exception e2) {
            LogUtil.d("sdkDecrypt AES excepiton " + e2.toString());
            z2 = false;
        }
        if (TextUtils.isEmpty(strDecrypt) ? false : z2) {
            return strDecrypt;
        }
        try {
            strDecrypt = DESUtil.decrypt(str, getDesKey());
            mDecryptTag = "DES";
            SharedPreferenceManager.getInstance().saveDecryptTag(mDecryptTag);
            LogUtil.d("sdkDecrypt aesDecrypt des data " + strDecrypt);
            return strDecrypt;
        } catch (Exception e3) {
            LogUtil.d("sdkDecrypt DES excepiton " + e3.toString());
            return strDecrypt;
        }
    }

    public static String desDecrypt(String str) {
        boolean z2;
        String strDecrypt = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            strDecrypt = DESUtil.decrypt(str, getDesKey());
            LogUtil.d("sdkDecrypt desDecrypt des data " + strDecrypt);
            z2 = true;
        } catch (Exception e2) {
            LogUtil.d("sdkDecrypt DES excepiton " + e2.toString());
            z2 = false;
        }
        if (TextUtils.isEmpty(strDecrypt) ? false : z2) {
            return strDecrypt;
        }
        try {
            strDecrypt = AESEncrypt.decrypt(AESEncrypt.SDK_APP_SECRET, str);
            mDecryptTag = AESUtils.AES;
            SharedPreferenceManager.getInstance().saveDecryptTag(mDecryptTag);
            LogUtil.d("sdkDecrypt desDecrypt aes data " + strDecrypt);
            return strDecrypt;
        } catch (Exception e3) {
            LogUtil.d("sdkDecrypt AES excepiton " + e3.toString());
            return strDecrypt;
        }
    }

    private static String getDesKey() {
        if (TextUtils.isEmpty(DES_KEY)) {
            DES_KEY = new String(Base64.decodeBase64(DES_KEY_BASE64));
        }
        byte[] bArrSwapBytes = swapBytes(getUTF8Bytes(DES_KEY));
        return bArrSwapBytes != null ? new String(bArrSwapBytes, Charset.forName("UTF-8")) : "";
    }

    public static byte[] getUTF8Bytes(String str) {
        if (str == null) {
            return new byte[0];
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return new byte[0];
        }
    }

    public static String sdkDecrypt(String str) {
        LogUtil.d("sdkDecrypt start data " + str);
        if (TextUtils.isEmpty(mDecryptTag)) {
            mDecryptTag = SharedPreferenceManager.getInstance().getDecryptTag();
        }
        if ("DES".equals(mDecryptTag)) {
            LogUtil.d("sdkDecrypt start DES");
            return desDecrypt(str);
        }
        LogUtil.d("sdkDecrypt start AES");
        return aesDecrypt(str);
    }

    public static byte[] swapBytes(byte[] bArr) {
        int length = bArr.length % 2 == 0 ? bArr.length : bArr.length - 1;
        for (int i2 = 0; i2 < length; i2 += 2) {
            byte b2 = bArr[i2];
            int i3 = i2 + 1;
            bArr[i2] = bArr[i3];
            bArr[i3] = b2;
        }
        return bArr;
    }
}
