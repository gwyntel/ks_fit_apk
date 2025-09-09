package com.yc.utesdk.utils.close;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class RSAUtils {
    private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC57TJWQJc+vSp+Lq5ctZIhT3tZAWJMsgHLexnPUkix6ci52M9lIKglwthipBlExFs8TN/1eY6Y56oJce+AMlIakVEg+UZJ1kHFRDwzJdpG/Lb8hi61fknkhROu6sB3xcG9jUWpWTLRl3KTAd4AGiEgPdDS3bdA+oFr6lygvj9SlQIDAQAB";
    private static final String RSA = "RSA";
    private static String TAG = "RSAUtils";

    public static String encryptByPublicKeyForSplit(String str) {
        float length = str.length();
        float f2 = length / 117;
        int i2 = (int) f2;
        String str2 = "";
        for (int i3 = 0; i3 < i2 + 1; i3++) {
            if (i3 != i2) {
                str2 = str2 + utendo(str.substring(i3 * 117, (i3 + 1) * 117));
            } else if (f2 > i2) {
                try {
                    str2 = str2 + utendo(str.substring(i3 * 117, (int) length));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return str2.replace("\n", "");
    }

    public static String utendo(String str) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] bytes = str.getBytes();
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(PUCLIC_KEY, 0)));
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(1, publicKeyGeneratePublic);
        return Base64.encodeToString(cipher.doFinal(bytes), 0);
    }
}
