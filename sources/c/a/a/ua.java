package c.a.a;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ua extends za {

    /* renamed from: c, reason: collision with root package name */
    public static final byte[] f7979c = new byte[0];

    /* renamed from: d, reason: collision with root package name */
    public final int f7980d;

    /* renamed from: e, reason: collision with root package name */
    public int f7981e;

    public ua(InputStream inputStream, int i2) {
        super(inputStream, i2);
        if (i2 < 0) {
            throw new IllegalArgumentException("negative lengths not allowed");
        }
        this.f7980d = i2;
        this.f7981e = i2;
        if (i2 == 0) {
            a(true);
        }
    }

    @Override // c.a.a.za
    public int a() {
        return this.f7981e;
    }

    public byte[] b() throws EOFException {
        int i2 = this.f7981e;
        if (i2 == 0) {
            return f7979c;
        }
        byte[] bArr = new byte[i2];
        int iA = i2 - c.a.d.b.a.a(this.f7996a, bArr);
        this.f7981e = iA;
        if (iA == 0) {
            a(true);
            return bArr;
        }
        throw new EOFException("DEF length " + this.f7980d + " object truncated by " + this.f7981e);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.f7981e == 0) {
            return -1;
        }
        int i2 = this.f7996a.read();
        if (i2 >= 0) {
            int i3 = this.f7981e - 1;
            this.f7981e = i3;
            if (i3 == 0) {
                a(true);
            }
            return i2;
        }
        throw new EOFException("DEF length " + this.f7980d + " object truncated by " + this.f7981e);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.f7981e;
        if (i4 == 0) {
            return -1;
        }
        int i5 = this.f7996a.read(bArr, i2, Math.min(i3, i4));
        if (i5 >= 0) {
            int i6 = this.f7981e - i5;
            this.f7981e = i6;
            if (i6 == 0) {
                a(true);
            }
            return i5;
        }
        throw new EOFException("DEF length " + this.f7980d + " object truncated by " + this.f7981e);
    }
}
