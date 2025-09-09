package com.huawei.secure.android.common.encrypt.rsa;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Arrays;

/* loaded from: classes4.dex */
public abstract class RSASign {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18334a = "SHA256WithRSA";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18335b = "SHA256WithRSA/PSS";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18336c = "RSASign";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18337d = "UTF-8";

    /* renamed from: e, reason: collision with root package name */
    private static final String f18338e = "";

    private static String a(String str, String str2, boolean z2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            b.b(f18336c, "sign content or key is null");
            return "";
        }
        PrivateKey privateKey = EncryptUtil.getPrivateKey(str2);
        return z2 ? newSign(str, privateKey) : sign(str, privateKey);
    }

    public static boolean isBuildVersionHigherThan23() {
        return Build.VERSION.SDK_INT > 23;
    }

    public static String newSign(String str, String str2) {
        if (isBuildVersionHigherThan23()) {
            return a(str, str2, true);
        }
        b.b(f18336c, "sdk version is too low");
        return "";
    }

    public static boolean newVerifySign(String str, String str2, String str3) {
        if (isBuildVersionHigherThan23()) {
            return a(str, str2, str3, true);
        }
        b.b(f18336c, "sdk version is too low");
        return false;
    }

    @Deprecated
    public static String sign(String str, String str2) {
        return a(str, str2, false);
    }

    @Deprecated
    public static boolean verifySign(String str, String str2, String str3) {
        return a(str, str2, str3, false);
    }

    @Deprecated
    public static String sign(String str, PrivateKey privateKey) {
        return a(str, privateKey, false);
    }

    @Deprecated
    public static boolean verifySign(String str, String str2, PublicKey publicKey) {
        return a(str, str2, publicKey, false);
    }

    public static byte[] sign(byte[] bArr, PrivateKey privateKey, boolean z2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        Signature signature;
        byte[] bArr2 = new byte[0];
        if (bArr != null && privateKey != null && RSAEncrypt.isPrivateKeyLengthRight((RSAPrivateKey) privateKey)) {
            try {
                if (z2) {
                    signature = Signature.getInstance(f18335b);
                    signature.setParameter(new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                } else {
                    signature = Signature.getInstance(f18334a);
                }
                signature.initSign(privateKey);
                signature.update(bArr);
                return signature.sign();
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(f18336c, "sign InvalidAlgorithmParameterException: " + e2.getMessage());
                return bArr2;
            } catch (InvalidKeyException e3) {
                b.b(f18336c, "sign InvalidKeyException: " + e3.getMessage());
                return bArr2;
            } catch (NoSuchAlgorithmException e4) {
                b.b(f18336c, "sign NoSuchAlgorithmException: " + e4.getMessage());
                return bArr2;
            } catch (SignatureException e5) {
                b.b(f18336c, "sign SignatureException: " + e5.getMessage());
                return bArr2;
            } catch (Exception e6) {
                b.b(f18336c, "sign Exception: " + e6.getMessage());
                return bArr2;
            }
        }
        b.b(f18336c, "content or privateKey is null , or length is too short");
        return bArr2;
    }

    public static boolean verifySign(byte[] bArr, byte[] bArr2, PublicKey publicKey, boolean z2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        Signature signature;
        if (bArr != null && publicKey != null && bArr2 != null && RSAEncrypt.isPublicKeyLengthRight((RSAPublicKey) publicKey)) {
            try {
                if (z2) {
                    signature = Signature.getInstance(f18335b);
                    signature.setParameter(new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                } else {
                    signature = Signature.getInstance(f18334a);
                }
                signature.initVerify(publicKey);
                signature.update(bArr);
                return signature.verify(bArr2);
            } catch (GeneralSecurityException e2) {
                b.b(f18336c, "check sign exception: " + e2.getMessage());
                return false;
            } catch (Exception e3) {
                b.b(f18336c, "exception : " + e3.getMessage());
                return false;
            }
        }
        b.b(f18336c, "content or publicKey is null , or length is too short");
        return false;
    }

    public static String newSign(String str, PrivateKey privateKey) {
        if (!isBuildVersionHigherThan23()) {
            b.b(f18336c, "sdk version is too low");
            return "";
        }
        return a(str, privateKey, true);
    }

    public static boolean newVerifySign(String str, String str2, PublicKey publicKey) {
        if (!isBuildVersionHigherThan23()) {
            b.b(f18336c, "sdk version is too low");
            return false;
        }
        return a(str, str2, publicKey, true);
    }

    private static String a(String str, PrivateKey privateKey, boolean z2) {
        try {
            return Base64.encodeToString(sign(str.getBytes("UTF-8"), privateKey, z2), 0);
        } catch (UnsupportedEncodingException e2) {
            b.b(f18336c, "sign UnsupportedEncodingException: " + e2.getMessage());
            return "";
        }
    }

    private static boolean a(String str, String str2, String str3, boolean z2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2)) {
            RSAPublicKey publicKey = EncryptUtil.getPublicKey(str3);
            if (z2) {
                return newVerifySign(str, str2, publicKey);
            }
            return verifySign(str, str2, publicKey);
        }
        b.b(f18336c, "content or public key or sign value is null");
        return false;
    }

    private static boolean a(String str, String str2, PublicKey publicKey, boolean z2) {
        try {
            return verifySign(str.getBytes("UTF-8"), Base64.decode(str2, 0), publicKey, z2);
        } catch (UnsupportedEncodingException e2) {
            b.b(f18336c, "verifySign UnsupportedEncodingException: " + e2.getMessage());
            return false;
        } catch (Exception e3) {
            b.b(f18336c, "base64 decode Exception : " + e3.getMessage());
            return false;
        }
    }

    public static boolean verifySign(ByteBuffer byteBuffer, byte[] bArr, PublicKey publicKey, boolean z2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        Signature signature;
        if (byteBuffer != null && publicKey != null && bArr != null && RSAEncrypt.isPublicKeyLengthRight((RSAPublicKey) publicKey)) {
            try {
                if (z2) {
                    signature = Signature.getInstance(f18335b);
                    signature.setParameter(new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                } else {
                    signature = Signature.getInstance(f18334a);
                }
                signature.initVerify(publicKey);
                signature.update(byteBuffer);
                return signature.verify(bArr);
            } catch (GeneralSecurityException e2) {
                b.b(f18336c, "check sign exception: " + e2.getMessage());
                return false;
            } catch (Exception e3) {
                b.b(f18336c, "exception : " + e3.getMessage());
                return false;
            }
        }
        b.b(f18336c, "content or publicKey is null , or length is too short");
        return false;
    }

    public static byte[] sign(ByteBuffer byteBuffer, PrivateKey privateKey, boolean z2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        Signature signature;
        byte[] bArrSign = new byte[0];
        if (byteBuffer != null && privateKey != null && RSAEncrypt.isPrivateKeyLengthRight((RSAPrivateKey) privateKey)) {
            try {
                if (z2) {
                    signature = Signature.getInstance(f18335b);
                    signature.setParameter(new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                } else {
                    signature = Signature.getInstance(f18334a);
                }
                signature.initSign(privateKey);
                signature.update(byteBuffer);
                bArrSign = signature.sign();
                b.c(f18336c, "result is : " + Arrays.toString(bArrSign));
                return bArrSign;
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(f18336c, "sign InvalidAlgorithmParameterException: " + e2.getMessage());
                return bArrSign;
            } catch (InvalidKeyException e3) {
                b.b(f18336c, "sign InvalidKeyException: " + e3.getMessage());
                return bArrSign;
            } catch (NoSuchAlgorithmException e4) {
                b.b(f18336c, "sign NoSuchAlgorithmException: " + e4.getMessage());
                return bArrSign;
            } catch (SignatureException e5) {
                b.b(f18336c, "sign SignatureException: " + e5.getMessage());
                return bArrSign;
            } catch (Exception e6) {
                b.b(f18336c, "sign Exception: " + e6.getMessage());
                return bArrSign;
            }
        }
        b.b(f18336c, "content or privateKey is null , or length is too short");
        return bArrSign;
    }
}
