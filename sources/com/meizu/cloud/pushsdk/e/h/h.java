package com.meizu.cloud.pushsdk.e.h;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;

/* loaded from: classes4.dex */
final class h implements c {

    /* renamed from: a, reason: collision with root package name */
    private final b f19500a;

    /* renamed from: b, reason: collision with root package name */
    private final l f19501b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f19502c;

    public h(l lVar) {
        this(lVar, new b());
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public long a(m mVar) throws IOException {
        if (mVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long jB = mVar.b(this.f19500a, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
            if (jB == -1) {
                return j2;
            }
            j2 += jB;
            e();
        }
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public b c() {
        return this.f19500a;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.f19502c) {
            return;
        }
        try {
            b bVar = this.f19500a;
            long j2 = bVar.f19487c;
            if (j2 > 0) {
                this.f19501b.a(bVar, j2);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.f19501b.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.f19502c = true;
        if (th != null) {
            o.a(th);
        }
    }

    public c e() throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        long jG = this.f19500a.g();
        if (jG > 0) {
            this.f19501b.a(this.f19500a, jG);
        }
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l, java.io.Flushable
    public void flush() throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        b bVar = this.f19500a;
        long j2 = bVar.f19487c;
        if (j2 > 0) {
            this.f19501b.a(bVar, j2);
        }
        this.f19501b.flush();
    }

    public String toString() {
        return "buffer(" + this.f19501b + ")";
    }

    public h(l lVar, b bVar) {
        if (lVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.f19500a = bVar;
        this.f19501b = lVar;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public c a(long j2) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(j2);
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public c a(e eVar) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(eVar);
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public c a(String str) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(str);
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public c a(byte[] bArr) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(bArr);
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public c a(byte[] bArr, int i2, int i3) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(bArr, i2, i3);
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l
    public void a(b bVar, long j2) throws IOException {
        if (this.f19502c) {
            throw new IllegalStateException("closed");
        }
        this.f19500a.a(bVar, j2);
        e();
    }
}
