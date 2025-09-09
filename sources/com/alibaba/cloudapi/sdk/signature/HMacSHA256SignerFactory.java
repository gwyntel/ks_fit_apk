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
public class HMacSHA256SignerFactory implements ISignerFactory {
    public static final String METHOD = "HmacSHA256";
    private static ISinger singer;

    private static class HMacSHA256Signer implements ISinger {
        private HMacSHA256Signer() {
        }

        @Override // com.alibaba.cloudapi.sdk.signature.ISinger
        public String sign(String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("strToSign can not be empty");
            }
            if (TextUtils.isEmpty(str2)) {
                throw new IllegalArgumentException("secretKey can not be empty");
            }
            Mac mac = Mac.getInstance("HmacSHA256");
            Charset charset = SdkConstant.CLOUDAPI_ENCODING;
            byte[] bytes = str2.getBytes(charset);
            mac.init(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256"));
            return new String(Base64.encode(mac.doFinal(str.getBytes(charset)), 0), charset);
        }
    }

    @Override // com.alibaba.cloudapi.sdk.signature.ISignerFactory
    public ISinger getSigner() {
        if (singer == null) {
            singer = new HMacSHA256Signer();
        }
        return singer;
    }
}
