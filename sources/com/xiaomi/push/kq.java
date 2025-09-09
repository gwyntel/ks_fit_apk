package com.xiaomi.push;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class kq extends kt {

    /* renamed from: a, reason: collision with root package name */
    protected InputStream f24393a;

    /* renamed from: a, reason: collision with other field name */
    protected OutputStream f926a;

    protected kq() {
        this.f24393a = null;
        this.f926a = null;
    }

    @Override // com.xiaomi.push.kt
    public int a(byte[] bArr, int i2, int i3) throws ku, IOException {
        InputStream inputStream = this.f24393a;
        if (inputStream == null) {
            throw new ku(1, "Cannot read from null inputStream");
        }
        try {
            int i4 = inputStream.read(bArr, i2, i3);
            if (i4 >= 0) {
                return i4;
            }
            throw new ku(4);
        } catch (IOException e2) {
            throw new ku(0, e2);
        }
    }

    public kq(OutputStream outputStream) {
        this.f24393a = null;
        this.f926a = outputStream;
    }

    @Override // com.xiaomi.push.kt
    /* renamed from: a, reason: collision with other method in class */
    public void mo681a(byte[] bArr, int i2, int i3) throws ku, IOException {
        OutputStream outputStream = this.f926a;
        if (outputStream != null) {
            try {
                outputStream.write(bArr, i2, i3);
                return;
            } catch (IOException e2) {
                throw new ku(0, e2);
            }
        }
        throw new ku(1, "Cannot write to null outputStream");
    }
}
