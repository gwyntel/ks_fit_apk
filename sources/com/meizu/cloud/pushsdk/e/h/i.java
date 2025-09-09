package com.meizu.cloud.pushsdk.e.h;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
final class i implements d {

    /* renamed from: a, reason: collision with root package name */
    private final b f19503a;

    /* renamed from: b, reason: collision with root package name */
    private final m f19504b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f19505c;

    class a extends InputStream {
        a() {
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            if (i.this.f19505c) {
                throw new IOException("closed");
            }
            return (int) Math.min(i.this.f19503a.f19487c, 2147483647L);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            i.this.close();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (i.this.f19505c) {
                throw new IOException("closed");
            }
            if (i.this.f19503a.f19487c == 0 && i.this.f19504b.b(i.this.f19503a, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) == -1) {
                return -1;
            }
            return i.this.f19503a.i() & 255;
        }

        public String toString() {
            return i.this + ".inputStream()";
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            if (i.this.f19505c) {
                throw new IOException("closed");
            }
            o.a(bArr.length, i2, i3);
            if (i.this.f19503a.f19487c == 0 && i.this.f19504b.b(i.this.f19503a, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) == -1) {
                return -1;
            }
            return i.this.f19503a.b(bArr, i2, i3);
        }
    }

    public i(m mVar) {
        this(mVar, new b());
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public String a() throws IOException {
        this.f19503a.a(this.f19504b);
        return this.f19503a.a();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.m
    public long b(b bVar, long j2) throws IOException {
        if (bVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f19505c) {
            throw new IllegalStateException("closed");
        }
        b bVar2 = this.f19503a;
        if (bVar2.f19487c == 0 && this.f19504b.b(bVar2, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) == -1) {
            return -1L;
        }
        return this.f19503a.b(bVar, Math.min(j2, this.f19503a.f19487c));
    }

    @Override // com.meizu.cloud.pushsdk.e.h.m, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.e.h.l
    public void close() throws IOException {
        if (this.f19505c) {
            return;
        }
        this.f19505c = true;
        this.f19504b.close();
        this.f19503a.e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public InputStream d() {
        return new a();
    }

    public String toString() {
        return "buffer(" + this.f19504b + ")";
    }

    public i(m mVar, b bVar) {
        if (mVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.f19503a = bVar;
        this.f19504b = mVar;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public byte[] b() throws IOException {
        this.f19503a.a(this.f19504b);
        return this.f19503a.b();
    }
}
