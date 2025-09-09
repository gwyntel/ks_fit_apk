package com.alibaba.ailabs.iot.aisbase;

import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/* renamed from: com.alibaba.ailabs.iot.aisbase.t, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final class C0426t {

    /* renamed from: a, reason: collision with root package name */
    public static final ParcelUuid f8573a = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");

    public static ParcelUuid a(byte[] bArr) {
        long j2;
        if (bArr == null) {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
        int length = bArr.length;
        if (length != 2 && length != 4 && length != 16) {
            throw new IllegalArgumentException("uuidBytes length invalid - " + length);
        }
        if (length == 16) {
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            return new ParcelUuid(new UUID(byteBufferOrder.getLong(8), byteBufferOrder.getLong(0)));
        }
        if (length == 2) {
            j2 = (bArr[0] & 255) + ((bArr[1] & 255) << 8);
        } else {
            j2 = ((bArr[3] & 255) << 24) + (bArr[0] & 255) + ((bArr[1] & 255) << 8) + ((bArr[2] & 255) << 16);
        }
        ParcelUuid parcelUuid = f8573a;
        return new ParcelUuid(new UUID(parcelUuid.getUuid().getMostSignificantBits() + (j2 << 32), parcelUuid.getUuid().getLeastSignificantBits()));
    }
}
