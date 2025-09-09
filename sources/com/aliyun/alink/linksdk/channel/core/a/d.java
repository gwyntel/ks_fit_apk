package com.aliyun.alink.linksdk.channel.core.a;

import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.id2.Id2Itls;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class d extends OutputStream {

    /* renamed from: a, reason: collision with root package name */
    private Id2Itls f10851a;

    /* renamed from: b, reason: collision with root package name */
    private long f10852b;

    /* renamed from: c, reason: collision with root package name */
    private byte[] f10853c = new byte[1024];

    public d(Id2Itls id2Itls, long j2) {
        this.f10851a = id2Itls;
        this.f10852b = j2;
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i2) {
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] bArr, int i2, int i3) {
        int i4;
        if (bArr == null) {
            return;
        }
        try {
            if (i2 >= 0) {
                try {
                    try {
                        try {
                            try {
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                            }
                        } catch (Exception e3) {
                            com.aliyun.alink.linksdk.channel.core.b.a.d("ITLSOutputStream", "itls write exception " + e3);
                        }
                    } catch (IOException e4) {
                        throw e4;
                    }
                } catch (IndexOutOfBoundsException e5) {
                    e5.printStackTrace();
                }
                if (i2 <= bArr.length && i3 >= 0 && (i4 = i2 + i3) <= bArr.length && i4 >= 0) {
                    if (i3 == 0) {
                        return;
                    }
                    com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSOutputStream", "b.len=" + bArr.length + ", off=" + i2 + ", len=" + i3);
                    for (int i5 = 0; i5 < i3; i5 += 1024) {
                        int iMin = Math.min(1024, i3 - i5);
                        System.arraycopy(bArr, i5, this.f10853c, 0, iMin);
                        int iItlsWrite = this.f10851a.itlsWrite(this.f10852b, this.f10853c, iMin, MqttConfigure.itlsWriteTimeout);
                        com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSOutputStream", "result=" + iItlsWrite + ", length=" + iMin);
                        if (iItlsWrite < iMin) {
                            throw new IOException(String.valueOf(32109), new Throwable("itlsWriteErrorDataLen=" + iItlsWrite));
                        }
                    }
                    super.write(bArr, i2, i3);
                    return;
                }
            }
            throw new IndexOutOfBoundsException();
        } catch (Throwable th) {
            throw th;
        }
    }
}
