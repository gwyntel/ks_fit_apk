package com.xiaomi.push;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    private final int f23523a;

    /* renamed from: a, reason: collision with other field name */
    private final OutputStream f224a;

    /* renamed from: a, reason: collision with other field name */
    private final byte[] f225a;

    /* renamed from: b, reason: collision with root package name */
    private int f23524b;

    public static class a extends IOException {
        a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    private c(byte[] bArr, int i2, int i3) {
        this.f224a = null;
        this.f225a = bArr;
        this.f23524b = i2;
        this.f23523a = i2 + i3;
    }

    public static int a(boolean z2) {
        return 1;
    }

    public static int c(long j2) {
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (((-16384) & j2) == 0) {
            return 2;
        }
        if (((-2097152) & j2) == 0) {
            return 3;
        }
        if (((-268435456) & j2) == 0) {
            return 4;
        }
        if (((-34359738368L) & j2) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j2) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j2) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j2) == 0) {
            return 8;
        }
        return (j2 & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static int d(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m241b(int i2, long j2) throws IOException {
        c(i2, 0);
        m242b(j2);
    }

    public static c a(OutputStream outputStream) {
        return a(outputStream, 4096);
    }

    private void c() throws IOException {
        OutputStream outputStream = this.f224a;
        if (outputStream == null) {
            throw new a();
        }
        outputStream.write(this.f225a, 0, this.f23524b);
        this.f23524b = 0;
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m245d(int i2) throws IOException {
        while ((i2 & (-128)) != 0) {
            m243c((i2 & 127) | 128);
            i2 >>>= 7;
        }
        m243c(i2);
    }

    public static c a(OutputStream outputStream, int i2) {
        return new c(outputStream, new byte[i2]);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m240b(int i2, int i3) throws IOException {
        c(i2, 0);
        m239b(i3);
    }

    public static c a(byte[] bArr, int i2, int i3) {
        return new c(bArr, i2, i3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m228a(int i2, long j2) throws IOException {
        c(i2, 0);
        m233a(j2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m242b(long j2) throws IOException {
        m244c(j2);
    }

    private c(OutputStream outputStream, byte[] bArr) {
        this.f224a = outputStream;
        this.f225a = bArr;
        this.f23524b = 0;
        this.f23523a = bArr.length;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m239b(int i2) throws IOException {
        m245d(i2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m243c(int i2) throws IOException {
        a((byte) i2);
    }

    public static int b(int i2, long j2) {
        return c(i2) + b(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m227a(int i2, int i3) throws IOException {
        c(i2, 0);
        m226a(i3);
    }

    public void c(int i2, int i3) throws IOException {
        m245d(f.a(i2, i3));
    }

    public static int b(int i2, int i3) {
        return c(i2) + b(i3);
    }

    public static int c(int i2) {
        return d(f.a(i2, 0));
    }

    public static int b(long j2) {
        return c(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m232a(int i2, boolean z2) throws IOException {
        c(i2, 0);
        m237a(z2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m244c(long j2) throws IOException {
        while (((-128) & j2) != 0) {
            m243c((((int) j2) & 127) | 128);
            j2 >>>= 7;
        }
        m243c((int) j2);
    }

    public static int b(int i2) {
        return d(i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m231a(int i2, String str) throws IOException {
        c(i2, 2);
        m236a(str);
    }

    public void b() {
        if (a() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m230a(int i2, e eVar) throws IOException {
        c(i2, 2);
        m235a(eVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m229a(int i2, com.xiaomi.push.a aVar) throws IOException {
        c(i2, 2);
        m234a(aVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m233a(long j2) throws IOException {
        m244c(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m226a(int i2) throws IOException {
        if (i2 >= 0) {
            m245d(i2);
        } else {
            m244c(i2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m237a(boolean z2) throws IOException {
        m243c(z2 ? 1 : 0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m236a(String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        m245d(bytes.length);
        a(bytes);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m235a(e eVar) throws IOException {
        m245d(eVar.a());
        eVar.a(this);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m234a(com.xiaomi.push.a aVar) throws IOException {
        byte[] bArrM169a = aVar.m169a();
        m245d(bArrM169a.length);
        a(bArrM169a);
    }

    public static int a(int i2, long j2) {
        return c(i2) + a(j2);
    }

    public static int a(int i2, int i3) {
        return c(i2) + a(i3);
    }

    public static int a(int i2, boolean z2) {
        return c(i2) + a(z2);
    }

    public static int a(int i2, String str) {
        return c(i2) + a(str);
    }

    public static int a(int i2, e eVar) {
        return c(i2) + a(eVar);
    }

    public static int a(int i2, com.xiaomi.push.a aVar) {
        return c(i2) + a(aVar);
    }

    public static int a(long j2) {
        return c(j2);
    }

    public static int a(int i2) {
        if (i2 >= 0) {
            return d(i2);
        }
        return 10;
    }

    public static int a(String str) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return d(bytes.length) + bytes.length;
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int a(e eVar) {
        int iB = eVar.b();
        return d(iB) + iB;
    }

    public static int a(com.xiaomi.push.a aVar) {
        return d(aVar.a()) + aVar.a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m225a() throws IOException {
        if (this.f224a != null) {
            c();
        }
    }

    public int a() {
        if (this.f224a == null) {
            return this.f23523a - this.f23524b;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    public void a(byte b2) throws IOException {
        if (this.f23524b == this.f23523a) {
            c();
        }
        byte[] bArr = this.f225a;
        int i2 = this.f23524b;
        this.f23524b = i2 + 1;
        bArr[i2] = b2;
    }

    public void a(byte[] bArr) throws IOException {
        m238a(bArr, 0, bArr.length);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m238a(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.f23523a;
        int i5 = this.f23524b;
        if (i4 - i5 >= i3) {
            System.arraycopy(bArr, i2, this.f225a, i5, i3);
            this.f23524b += i3;
            return;
        }
        int i6 = i4 - i5;
        System.arraycopy(bArr, i2, this.f225a, i5, i6);
        int i7 = i2 + i6;
        int i8 = i3 - i6;
        this.f23524b = this.f23523a;
        c();
        if (i8 <= this.f23523a) {
            System.arraycopy(bArr, i7, this.f225a, 0, i8);
            this.f23524b = i8;
        } else {
            this.f224a.write(bArr, i7, i8);
        }
    }
}
