package com.meizu.cloud.pushsdk.e.h;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
public final class g {

    /* renamed from: a, reason: collision with root package name */
    private static final Logger f19495a = Logger.getLogger(g.class.getName());

    class a implements l {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ n f19496a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ OutputStream f19497b;

        a(n nVar, OutputStream outputStream) {
            this.f19496a = nVar;
            this.f19497b = outputStream;
        }

        @Override // com.meizu.cloud.pushsdk.e.h.l
        public void a(com.meizu.cloud.pushsdk.e.h.b bVar, long j2) throws IOException {
            o.a(bVar.f19487c, 0L, j2);
            while (j2 > 0) {
                this.f19496a.a();
                j jVar = bVar.f19486b;
                int iMin = (int) Math.min(j2, jVar.f19509c - jVar.f19508b);
                this.f19497b.write(jVar.f19507a, jVar.f19508b, iMin);
                int i2 = jVar.f19508b + iMin;
                jVar.f19508b = i2;
                long j3 = iMin;
                j2 -= j3;
                bVar.f19487c -= j3;
                if (i2 == jVar.f19509c) {
                    bVar.f19486b = jVar.b();
                    k.a(jVar);
                }
            }
        }

        @Override // com.meizu.cloud.pushsdk.e.h.l, java.lang.AutoCloseable
        public void close() throws IOException {
            this.f19497b.close();
        }

        @Override // com.meizu.cloud.pushsdk.e.h.l, java.io.Flushable
        public void flush() throws IOException {
            this.f19497b.flush();
        }

        public String toString() {
            return "sink(" + this.f19497b + ")";
        }
    }

    class b implements m {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ n f19498a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ InputStream f19499b;

        b(n nVar, InputStream inputStream) {
            this.f19498a = nVar;
            this.f19499b = inputStream;
        }

        @Override // com.meizu.cloud.pushsdk.e.h.m
        public long b(com.meizu.cloud.pushsdk.e.h.b bVar, long j2) throws IOException {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            if (j2 == 0) {
                return 0L;
            }
            this.f19498a.a();
            j jVarA = bVar.a(1);
            int i2 = this.f19499b.read(jVarA.f19507a, jVarA.f19509c, (int) Math.min(j2, 2048 - jVarA.f19509c));
            if (i2 == -1) {
                return -1L;
            }
            jVarA.f19509c += i2;
            long j3 = i2;
            bVar.f19487c += j3;
            return j3;
        }

        @Override // com.meizu.cloud.pushsdk.e.h.m, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.e.h.l
        public void close() throws IOException {
            this.f19499b.close();
        }

        public String toString() {
            return "source(" + this.f19499b + ")";
        }
    }

    private g() {
    }

    public static c a(l lVar) {
        if (lVar != null) {
            return new h(lVar);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static d a(m mVar) {
        if (mVar != null) {
            return new i(mVar);
        }
        throw new IllegalArgumentException("source == null");
    }

    public static l a(OutputStream outputStream) {
        return a(outputStream, new n());
    }

    private static l a(OutputStream outputStream, n nVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (nVar != null) {
            return new a(nVar, outputStream);
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static m a(File file) throws FileNotFoundException {
        if (file != null) {
            return a(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static m a(InputStream inputStream) {
        return a(inputStream, new n());
    }

    private static m a(InputStream inputStream, n nVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (nVar != null) {
            return new b(nVar, inputStream);
        }
        throw new IllegalArgumentException("timeout == null");
    }
}
