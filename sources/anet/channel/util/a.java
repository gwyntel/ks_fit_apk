package anet.channel.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class a extends InputStream {

    /* renamed from: a, reason: collision with root package name */
    private InputStream f7075a;

    /* renamed from: b, reason: collision with root package name */
    private long f7076b = 0;

    public a(InputStream inputStream) {
        this.f7075a = null;
        if (inputStream == null) {
            throw new NullPointerException("input stream cannot be null");
        }
        this.f7075a = inputStream;
    }

    public long a() {
        return this.f7076b;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        this.f7076b++;
        return this.f7075a.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.f7075a.read(bArr, i2, i3);
        if (i4 != -1) {
            this.f7076b += i4;
        }
        return i4;
    }
}
