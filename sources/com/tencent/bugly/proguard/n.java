package com.tencent.bugly.proguard;

import com.alibaba.ailabs.iot.aisbase.Constants;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f21108a;

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f21109b;

    static {
        byte[] bArr = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] bArr2 = new byte[256];
        byte[] bArr3 = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            bArr2[i2] = bArr[i2 >>> 4];
            bArr3[i2] = bArr[i2 & 15];
        }
        f21108a = bArr2;
        f21109b = bArr3;
    }

    public static boolean a(int i2, int i3) {
        return i2 == i3;
    }

    public static boolean a(long j2, long j3) {
        return j2 == j3;
    }

    public static boolean a(boolean z2, boolean z3) {
        return z2 == z3;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        byte[] bArr = new byte[iPosition];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, iPosition);
        return bArr;
    }
}
