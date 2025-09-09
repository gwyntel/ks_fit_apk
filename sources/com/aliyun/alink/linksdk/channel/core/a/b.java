package com.aliyun.alink.linksdk.channel.core.a;

import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.id2.Id2Itls;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public class b extends InputStream {

    /* renamed from: a, reason: collision with root package name */
    private Id2Itls f10840a;

    /* renamed from: b, reason: collision with root package name */
    private long f10841b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f10842c = new Object();

    /* renamed from: e, reason: collision with root package name */
    private int f10844e = -1;

    /* renamed from: f, reason: collision with root package name */
    private int f10845f = 0;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f10843d = new byte[1024];

    public b(Id2Itls id2Itls, long j2) {
        this.f10840a = id2Itls;
        this.f10841b = j2;
    }

    private void a() throws IOException {
        try {
            com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSInputStream", "read dataLen=" + this.f10845f + ", byteIndex=" + this.f10844e + ",handleId=" + this.f10841b);
            this.f10844e = -1;
            this.f10845f = 0;
            Id2Itls id2Itls = this.f10840a;
            if (id2Itls != null) {
                this.f10845f = id2Itls.itlsRead(this.f10841b, this.f10843d, 1024, MqttConfigure.itlsReadTimeout);
            }
            com.aliyun.alink.linksdk.channel.core.b.a.a("ITLSInputStream", "read dataLen=" + this.f10845f + ", byteIndex=" + this.f10844e + ",handleId=" + this.f10841b);
        } catch (Exception e2) {
            this.f10845f = 0;
            e2.printStackTrace();
        }
        if (this.f10845f >= 0) {
            return;
        }
        throw new IOException(String.valueOf(32109), new Throwable("itlsReadErrorDataLen=" + this.f10845f));
    }

    @Override // java.io.InputStream
    public int available() {
        return super.available();
    }

    @Override // java.io.InputStream
    public synchronized int read() {
        int i2;
        synchronized (this.f10842c) {
            try {
                int i3 = this.f10844e;
                if (i3 < 0 || i3 >= this.f10845f - 1) {
                    this.f10844e = -1;
                    this.f10845f = 0;
                    a();
                }
                if (this.f10845f <= 0) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    throw new SocketTimeoutException("NoData");
                }
                int i4 = this.f10844e + 1;
                this.f10844e = i4;
                i2 = this.f10843d[i4] & 255;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }
}
