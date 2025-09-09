package com.alipay.sdk.m.l0;

/* loaded from: classes2.dex */
public class c {
    public static byte[] a(int i2) {
        return new byte[]{(byte) ((i2 >> 24) % 256), (byte) ((i2 >> 16) % 256), (byte) ((i2 >> 8) % 256), (byte) (i2 % 256)};
    }
}
