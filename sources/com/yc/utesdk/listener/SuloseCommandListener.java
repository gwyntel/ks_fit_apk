package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface SuloseCommandListener {
    public static final int UUID_TYPE_60 = 60;
    public static final int UUID_TYPE_61 = 61;

    void onSuloseChanged(int i2, byte[] bArr);

    void onSuloseWrite(int i2, int i3);
}
