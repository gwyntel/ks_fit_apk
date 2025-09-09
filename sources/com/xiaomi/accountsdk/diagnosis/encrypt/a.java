package com.xiaomi.accountsdk.diagnosis.encrypt;

import android.util.Base64;
import android.util.Log;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private SecretKeySpec f23315a;

    public a(byte[] bArr) {
        if (bArr == null) {
            throw new SecurityException("aes key is null");
        }
        if (bArr.length != 16) {
            Log.e("AESCoder", "aesKey is invalid");
        }
        this.f23315a = new SecretKeySpec(bArr, AESUtils.AES);
    }

    public String a(String str) throws b {
        try {
            return Base64.encodeToString(a(str.getBytes("UTF-8")), 2);
        } catch (Exception e2) {
            throw new b("fail to encrypt by aescoder", e2);
        }
    }

    protected byte[] a() {
        return "0102030405060708".getBytes();
    }

    public byte[] a(byte[] bArr) throws b, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance(AESUtils.AES_ALGORITHM);
            cipher.init(1, this.f23315a, new IvParameterSpec(a()));
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            throw new b("fail to encrypt by aescoder", e2);
        }
    }
}
