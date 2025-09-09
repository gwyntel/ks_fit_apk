package com.facebook.appevents.internal;

import androidx.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/* loaded from: classes3.dex */
final class HashUtils {
    private static final String MD5 = "MD5";

    HashUtils() {
    }

    @Nullable
    public static final String computeChecksum(String str) throws Exception {
        return computeFileMd5(new File(str));
    }

    @Nullable
    private static String computeFileMd5(File file) throws Exception {
        int i2;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 1024);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[1024];
            do {
                i2 = bufferedInputStream.read(bArr);
                if (i2 > 0) {
                    messageDigest.update(bArr, 0, i2);
                }
            } while (i2 != -1);
            String string = new BigInteger(1, messageDigest.digest()).toString(16);
            bufferedInputStream.close();
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }
}
