package com.aliyun.alink.linksdk.tmp.utils;

/* loaded from: classes2.dex */
public class ByteUtils {
    public static String byte2hex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null) {
            for (byte b2 : bArr) {
                sb.append(String.format("%02X", Integer.valueOf(b2 & 255)));
            }
        }
        return sb.toString();
    }

    public static byte[] int32ToByte(int i2) {
        return new byte[]{(byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }
}
