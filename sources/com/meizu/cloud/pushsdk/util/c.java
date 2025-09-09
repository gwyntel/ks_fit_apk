package com.meizu.cloud.pushsdk.util;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final Charset f19827a = Charset.forName("UTF-8");

    public static String a(String str, String str2) {
        if (str != null) {
            try {
                if (!TextUtils.isEmpty(str2)) {
                    return new String(a(b(str), a(str2)), f19827a);
                }
            } catch (Exception e2) {
                DebugLogger.e("RSAUtils", "decrypt " + e2.getMessage());
            }
        }
        return null;
    }

    private static RSAPublicKey b(String str) {
        StringBuilder sb;
        String str2;
        try {
            return (RSAPublicKey) KeyFactory.getInstance(com.alipay.sdk.m.n.d.f9568a).generatePublic(new X509EncodedKeySpec(a(str)));
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            sb = new StringBuilder();
            str2 = "loadPublicKey NoSuchAlgorithmException ";
            sb.append(str2);
            sb.append(e.getMessage());
            DebugLogger.e("RSAUtils", sb.toString());
            return null;
        } catch (InvalidKeySpecException e3) {
            e = e3;
            sb = new StringBuilder();
            str2 = "loadPublicKey InvalidKeySpecException ";
            sb.append(str2);
            sb.append(e.getMessage());
            DebugLogger.e("RSAUtils", sb.toString());
            return null;
        }
    }

    private static byte[] a(String str) {
        return com.meizu.cloud.pushsdk.e.h.a.a(str);
    }

    private static byte[] a(PublicKey publicKey, byte[] bArr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, publicKey);
        return cipher.doFinal(bArr);
    }
}
