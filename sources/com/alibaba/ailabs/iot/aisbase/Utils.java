package com.alibaba.ailabs.iot.aisbase;

import android.text.TextUtils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public class Utils {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8340a = "Utils";

    /* renamed from: b, reason: collision with root package name */
    public static char[] f8341b = "0123456789ABCDEF".toCharArray();

    public static int adapterToAisVersion(String str) {
        try {
            if (!TextUtils.isEmpty(str) && str.length() >= 5 && str.length() <= 8) {
                String[] strArrSplit = str.split("\\.");
                if (strArrSplit.length != 3) {
                    return 0;
                }
                int[] iArr = {Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2])};
                return iArr[2] | (iArr[0] << 16) | (iArr[1] << 8);
            }
        } catch (NumberFormatException unused) {
        }
        return 0;
    }

    public static String adapterToOsUpdateVersion(int i2) {
        byte[] bArrInt2ByteArrayByBigEndian = int2ByteArrayByBigEndian(i2);
        LogUtils.d(f8340a, "osUpdate version: " + ConvertUtils.bytes2HexString(bArrInt2ByteArrayByBigEndian));
        return (bArrInt2ByteArrayByBigEndian == null || bArrInt2ByteArrayByBigEndian.length < 4) ? "" : String.format("%d.%d.%d-S-00000000.0000", Integer.valueOf(bArrInt2ByteArrayByBigEndian[1] & 255), Integer.valueOf(bArrInt2ByteArrayByBigEndian[2] & 255), Integer.valueOf(bArrInt2ByteArrayByBigEndian[3] & 255));
    }

    public static String byte2String(byte b2, boolean z2) {
        StringBuilder sb;
        String str;
        char[] cArr = f8341b;
        char[] cArr2 = {cArr[(b2 & 255) >>> 4], cArr[b2 & 15]};
        if (z2) {
            sb = new StringBuilder();
            str = "0x";
        } else {
            sb = new StringBuilder();
            str = "";
        }
        sb.append(str);
        sb.append(String.valueOf(cArr2));
        return sb.toString();
    }

    public static int byteArray2Int(byte[] bArr) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.BIG_ENDIAN);
        return byteBufferWrap.getInt();
    }

    public static int byteArray2IntByLittleEndian(byte[] bArr) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferWrap.getInt();
    }

    public static String formatMacAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder(18);
        for (byte b2 : bArr) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(String.format("%02x", Byte.valueOf(b2)));
        }
        return sb.toString().toUpperCase();
    }

    public static int genCrc16CCITT(byte[] bArr, int i2, int i3) {
        int i4 = 65535;
        while (i2 < i3) {
            int i5 = (((i4 << 8) | (i4 >>> 8)) & 65535) ^ (bArr[i2] & 255);
            int i6 = i5 ^ ((i5 & 255) >> 4);
            int i7 = i6 ^ ((i6 << 12) & 65535);
            i4 = i7 ^ (((i7 & 255) << 5) & 65535);
            i2++;
        }
        return i4 & 65535;
    }

    public static byte[] int2ByteArrayByBigEndian(int i2) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(i2).array();
    }

    public static byte[] int2ByteArrayByLittleEndian(int i2) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i2).array();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String md5(java.io.File r10) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "MD5"
            r1 = 0
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a java.security.NoSuchAlgorithmException -> L3c
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a java.security.NoSuchAlgorithmException -> L3c
            r2.<init>(r10)     // Catch: java.lang.Throwable -> L38 java.io.IOException -> L3a java.security.NoSuchAlgorithmException -> L3c
            java.nio.channels.FileChannel r9 = r2.getChannel()     // Catch: java.io.IOException -> L33 java.security.NoSuchAlgorithmException -> L36 java.lang.Throwable -> L5b
            java.nio.channels.FileChannel$MapMode r4 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            long r7 = r10.length()     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            r5 = 0
            r3 = r9
            java.nio.MappedByteBuffer r10 = r3.map(r4, r5, r7)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            r0.update(r10)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            byte[] r10 = r0.digest()     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e java.security.NoSuchAlgorithmException -> L30
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r2)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r9)
            goto L4c
        L2b:
            r10 = move-exception
            r1 = r9
            goto L5c
        L2e:
            r10 = move-exception
            goto L42
        L30:
            r10 = move-exception
            r1 = r9
            goto L55
        L33:
            r10 = move-exception
            r9 = r1
            goto L42
        L36:
            r10 = move-exception
            goto L55
        L38:
            r10 = move-exception
            goto L3e
        L3a:
            r10 = move-exception
            goto L40
        L3c:
            r10 = move-exception
            goto L54
        L3e:
            r2 = r1
            goto L5c
        L40:
            r2 = r1
            r9 = r2
        L42:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L2b
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r2)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r9)
            r10 = r1
        L4c:
            if (r10 != 0) goto L4f
            return r1
        L4f:
            java.lang.String r10 = com.alibaba.ailabs.tg.utils.ConvertUtils.bytes2HexString(r10)
            return r10
        L54:
            r2 = r1
        L55:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L5b
            r0.<init>(r10)     // Catch: java.lang.Throwable -> L5b
            throw r0     // Catch: java.lang.Throwable -> L5b
        L5b:
            r10 = move-exception
        L5c:
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r2)
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r1)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.aisbase.Utils.md5(java.io.File):java.lang.String");
    }

    public static String adapterToOsUpdateVersion(byte[] bArr) {
        return (bArr == null || bArr.length < 4) ? "" : String.format("%d.%d.%d-S-00000000.0000", Integer.valueOf(bArr[2] & 255), Integer.valueOf(bArr[1] & 255), Integer.valueOf(bArr[0] & 255));
    }
}
