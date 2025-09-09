package com.huawei.hms.utils;

import com.huawei.hms.support.log.HMSLog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public abstract class SHA256 {
    public static byte[] digest(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bArr);
        } catch (NoSuchAlgorithmException e2) {
            HMSLog.e("SHA256", "NoSuchAlgorithmException" + e2.getMessage());
            return new byte[0];
        }
    }

    public static byte[] digest(File file) throws Throwable {
        BufferedInputStream bufferedInputStream;
        MessageDigest messageDigest;
        int i2;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                try {
                    byte[] bArr = new byte[4096];
                    i2 = 0;
                    while (true) {
                        int i3 = bufferedInputStream.read(bArr);
                        if (i3 == -1) {
                            break;
                        }
                        i2 += i3;
                        messageDigest.update(bArr, 0, i3);
                    }
                } catch (IOException | NoSuchAlgorithmException unused) {
                    bufferedInputStream2 = bufferedInputStream;
                    HMSLog.e("SHA256", "An exception occurred while computing file 'SHA-256'.");
                    IOUtils.closeQuietly((InputStream) bufferedInputStream2);
                    return new byte[0];
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly((InputStream) bufferedInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = bufferedInputStream2;
            }
        } catch (IOException | NoSuchAlgorithmException unused2) {
        }
        if (i2 > 0) {
            byte[] bArrDigest = messageDigest.digest();
            IOUtils.closeQuietly((InputStream) bufferedInputStream);
            return bArrDigest;
        }
        IOUtils.closeQuietly((InputStream) bufferedInputStream);
        return new byte[0];
    }
}
