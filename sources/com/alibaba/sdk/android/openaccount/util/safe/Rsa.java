package com.alibaba.sdk.android.openaccount.util.safe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes2.dex */
public class Rsa {
    private static final String ALGORITHM = "RSA";
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x006d -> B:44:0x007b). Please report as a decompilation issue!!! */
    public static String decrypt(String str, String str2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            try {
                try {
                    PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str2)));
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(2, privateKeyGeneratePrivate);
                    byte[] bArrDecode = Base64.decode(str);
                    int blockSize = cipher.getBlockSize();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int i2 = 0; i2 < bArrDecode.length; i2 += blockSize) {
                        try {
                            byteArrayOutputStream.write(cipher.doFinal(bArrDecode, i2, bArrDecode.length - i2 < blockSize ? bArrDecode.length - i2 : blockSize));
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            th.printStackTrace();
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return null;
                        }
                    }
                    String str3 = new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8"));
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return str3;
                } catch (Exception e4) {
                    e = e4;
                    byteArrayOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = null;
                }
            } catch (Throwable th3) {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th3;
            }
        } catch (IOException e6) {
            e6.printStackTrace();
        }
    }

    public static boolean doCheck(String str, String str2, String str3) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str3)));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKeyGeneratePublic);
            signature.update(str.getBytes("utf-8"));
            return signature.verify(Base64.decode(str2));
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0060 -> B:48:0x006e). Please report as a decompilation issue!!! */
    public static String encrypt(String str, String str2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            try {
                try {
                    PublicKey publicKeyFromX509 = getPublicKeyFromX509("RSA", str2);
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(1, publicKeyFromX509);
                    byte[] bytes = str.getBytes("UTF-8");
                    int blockSize = cipher.getBlockSize();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int i2 = 0; i2 < bytes.length; i2 += blockSize) {
                        try {
                            byteArrayOutputStream.write(cipher.doFinal(bytes, i2, bytes.length - i2 < blockSize ? bytes.length - i2 : blockSize));
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            th.printStackTrace();
                            if (byteArrayOutputStream != null) {
                                byteArrayOutputStream.close();
                            }
                            return null;
                        }
                    }
                    String str3 = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return str3;
                } catch (Exception e4) {
                    e = e4;
                    byteArrayOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = null;
                }
            } catch (Throwable th3) {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th3;
            }
        } catch (IOException e6) {
            e6.printStackTrace();
        }
    }

    private static PublicKey getPublicKeyFromX509(String str, String str2) throws Exception {
        KeyFactory keyFactory;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(str2));
        try {
            try {
                keyFactory = KeyFactory.getInstance(str);
            } catch (Throwable unused) {
                keyFactory = KeyFactory.getInstance(str, "BC");
            }
        } catch (Throwable unused2) {
            keyFactory = KeyFactory.getInstance(str);
        }
        if (keyFactory == null) {
            keyFactory = KeyFactory.getInstance(str);
        }
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public static String sign(String str, String str2) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str2)));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes("utf-8"));
            return Base64.encode(signature.sign());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
