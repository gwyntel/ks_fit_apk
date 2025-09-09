package com.alibaba.ailabs.tg.utils;

import android.util.Base64;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.tg.storage.IOUtils;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class EncryptUtils {
    private EncryptUtils() {
    }

    public static byte[] encryptMD5(byte[] bArr) {
        return hashTemplate(bArr, Utils.MD5);
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x002b: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:44), block:B:13:0x002b */
    public static byte[] encryptMD5File(File file) throws Throwable {
        Closeable closeable;
        FileInputStream fileInputStream;
        Closeable closeable2 = null;
        try {
            if (file == null) {
                return null;
            }
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, MessageDigest.getInstance(Utils.MD5));
                    while (digestInputStream.read(new byte[262144]) > 0) {
                    }
                    byte[] bArrDigest = digestInputStream.getMessageDigest().digest();
                    IOUtils.closeQuietly(fileInputStream);
                    return bArrDigest;
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    IOUtils.closeQuietly(fileInputStream);
                    return null;
                } catch (NoSuchAlgorithmException e3) {
                    e = e3;
                    e.printStackTrace();
                    IOUtils.closeQuietly(fileInputStream);
                    return null;
                }
            } catch (IOException e4) {
                e = e4;
                fileInputStream = null;
                e.printStackTrace();
                IOUtils.closeQuietly(fileInputStream);
                return null;
            } catch (NoSuchAlgorithmException e5) {
                e = e5;
                fileInputStream = null;
                e.printStackTrace();
                IOUtils.closeQuietly(fileInputStream);
                return null;
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly(closeable2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable2 = closeable;
        }
    }

    public static String encryptMD5File2String(File file) {
        return ConvertUtils.bytes2HexString(encryptMD5File(file));
    }

    public static String encryptMD5ToString(String str, String str2) {
        return ConvertUtils.bytes2HexString(encryptMD5((str + str2).getBytes()));
    }

    public static byte[] encryptSHA1(byte[] bArr) {
        return hashTemplate(bArr, "SHA1");
    }

    public static String encryptSHA1ToString(byte[] bArr) {
        return ConvertUtils.bytes2HexString(encryptSHA1(bArr));
    }

    public static byte[] encryptSHA256(byte[] bArr) {
        return hashTemplate(bArr, "SHA256");
    }

    public static String encryptSHA256ToString(byte[] bArr) {
        return ConvertUtils.bytes2HexString(encryptSHA256(bArr));
    }

    public static String getMD5ForString(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] bArrDigest = MessageDigest.getInstance(Utils.MD5).digest(str.getBytes());
            for (int i2 = 0; i2 < bArrDigest.length; i2++) {
                int i3 = bArrDigest[i2];
                if (i3 < 0) {
                    i3 += 256;
                }
                if (i3 < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i3));
            }
            return sb.toString();
        } catch (Exception unused) {
            return Integer.toString(str.hashCode());
        }
    }

    public static String getSecureRandom() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return Base64.encodeToString(bArr, 11);
    }

    private static byte[] hashTemplate(byte[] bArr, String str) throws NoSuchAlgorithmException {
        if (bArr != null && bArr.length > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                messageDigest.update(bArr);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0052 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0053  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String md5(java.io.File r10) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r1 = "MD5"
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f java.security.NoSuchAlgorithmException -> L43
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f java.security.NoSuchAlgorithmException -> L43
            r2.<init>(r10)     // Catch: java.lang.Throwable -> L3c java.io.IOException -> L3f java.security.NoSuchAlgorithmException -> L43
            java.nio.channels.FileChannel r9 = r2.getChannel()     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L36 java.security.NoSuchAlgorithmException -> L39
            java.nio.channels.FileChannel$MapMode r4 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            long r7 = r10.length()     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            r5 = 0
            r3 = r9
            java.nio.MappedByteBuffer r10 = r3.map(r4, r5, r7)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            r1.update(r10)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            byte[] r10 = r1.digest()     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r2)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r9)
            goto L50
        L2b:
            r10 = move-exception
        L2c:
            r0 = r2
            goto L5f
        L2e:
            r10 = move-exception
            goto L46
        L30:
            r10 = move-exception
        L31:
            r0 = r2
            goto L58
        L33:
            r10 = move-exception
            r9 = r0
            goto L2c
        L36:
            r10 = move-exception
            r9 = r0
            goto L46
        L39:
            r10 = move-exception
            r9 = r0
            goto L31
        L3c:
            r10 = move-exception
            r9 = r0
            goto L5f
        L3f:
            r10 = move-exception
            r2 = r0
            r9 = r2
            goto L46
        L43:
            r10 = move-exception
            r9 = r0
            goto L58
        L46:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L2b
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r2)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r9)
            r10 = r0
        L50:
            if (r10 != 0) goto L53
            return r0
        L53:
            java.lang.String r10 = com.alibaba.ailabs.tg.utils.ConvertUtils.bytes2HexString(r10)
            return r10
        L58:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L5e
            r1.<init>(r10)     // Catch: java.lang.Throwable -> L5e
            throw r1     // Catch: java.lang.Throwable -> L5e
        L5e:
            r10 = move-exception
        L5f:
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r0)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.tg.utils.EncryptUtils.md5(java.io.File):java.lang.String");
    }

    public static String encryptMD5ToString(@NonNull String str) {
        return encryptMD5ToString(str.getBytes());
    }

    public static String encryptMD5ToString(byte[] bArr) {
        return ConvertUtils.bytes2HexString(encryptMD5(bArr));
    }

    public static String encryptMD5ToString(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return ConvertUtils.bytes2HexString(encryptMD5(bArr3));
    }
}
