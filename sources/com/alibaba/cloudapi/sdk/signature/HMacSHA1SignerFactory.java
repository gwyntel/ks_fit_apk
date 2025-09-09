package com.alibaba.cloudapi.sdk.signature;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class HMacSHA1SignerFactory implements ISignerFactory {
    public static final String METHOD = "HmacSHA1";
    private static ISinger singer;

    private static class HMacSHA1Signer implements ISinger {
        private HMacSHA1Signer() {
        }

        @Override // com.alibaba.cloudapi.sdk.signature.ISinger
        public String sign(String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("strToSign can not be empty");
            }
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("secretKey can not be empty");
            }
            Mac mac = Mac.getInstance("HmacSHA1");
            Charset charset = SdkConstant.CLOUDAPI_ENCODING;
            byte[] bytes = str2.getBytes(charset);
            mac.init(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA1"));
            return new String(Base64.encode(mac.doFinal(str.getBytes(charset)), 0), charset);
        }
    }

    static String byte2String(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null) {
            for (byte b2 : bArr) {
                sb.append(String.format("%02x", Byte.valueOf(b2)));
            }
        }
        return sb.toString();
    }

    @Override // com.alibaba.cloudapi.sdk.signature.ISignerFactory
    public ISinger getSigner() {
        if (singer == null) {
            singer = new HMacSHA1Signer();
        }
        return singer;
    }
}
