package com.ta.utdid2.a.a;

/* loaded from: classes4.dex */
public class b {
    public static byte[] getBytes(int i2) {
        return new byte[]{(byte) ((i2 >> 24) % 256), (byte) ((i2 >> 16) % 256), (byte) ((i2 >> 8) % 256), (byte) (i2 % 256)};
    }
}
