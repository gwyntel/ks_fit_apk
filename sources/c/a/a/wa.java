package c.a.a;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class wa extends za {

    /* renamed from: c, reason: collision with root package name */
    public int f7985c;

    /* renamed from: d, reason: collision with root package name */
    public int f7986d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f7987e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f7988f;

    public wa(InputStream inputStream, int i2) throws IOException {
        super(inputStream, i2);
        this.f7987e = false;
        this.f7988f = true;
        this.f7985c = inputStream.read();
        int i3 = inputStream.read();
        this.f7986d = i3;
        if (i3 < 0) {
            throw new EOFException();
        }
        b();
    }

    public void b(boolean z2) {
        this.f7988f = z2;
        b();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this.f7988f || i3 < 3) {
            return super.read(bArr, i2, i3);
        }
        if (this.f7987e) {
            return -1;
        }
        int i4 = this.f7996a.read(bArr, i2 + 2, i3 - 2);
        if (i4 < 0) {
            throw new EOFException();
        }
        bArr[i2] = (byte) this.f7985c;
        bArr[i2 + 1] = (byte) this.f7986d;
        this.f7985c = this.f7996a.read();
        int i5 = this.f7996a.read();
        this.f7986d = i5;
        if (i5 >= 0) {
            return i4 + 2;
        }
        throw new EOFException();
    }

    public final boolean b() {
        if (!this.f7987e && this.f7988f && this.f7985c == 0 && this.f7986d == 0) {
            this.f7987e = true;
            a(true);
        }
        return this.f7987e;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (b()) {
            return -1;
        }
        int i2 = this.f7996a.read();
        if (i2 >= 0) {
            int i3 = this.f7985c;
            this.f7985c = this.f7986d;
            this.f7986d = i2;
            return i3;
        }
        throw new EOFException();
    }
}
