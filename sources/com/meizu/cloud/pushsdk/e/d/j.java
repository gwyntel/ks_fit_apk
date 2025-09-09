package com.meizu.cloud.pushsdk.e.d;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public abstract class j {

    class a extends j {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ g f19446a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f19447b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ byte[] f19448c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ int f19449d;

        a(g gVar, int i2, byte[] bArr, int i3) {
            this.f19446a = gVar;
            this.f19447b = i2;
            this.f19448c = bArr;
            this.f19449d = i3;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public long a() {
            return this.f19447b;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public g b() {
            return this.f19446a;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public void a(com.meizu.cloud.pushsdk.e.h.c cVar) throws IOException {
            cVar.a(this.f19448c, this.f19449d, this.f19447b);
        }
    }

    class b extends j {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ g f19450a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ File f19451b;

        b(g gVar, File file) {
            this.f19450a = gVar;
            this.f19451b = file;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public long a() {
            return this.f19451b.length();
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public g b() {
            return this.f19450a;
        }

        @Override // com.meizu.cloud.pushsdk.e.d.j
        public void a(com.meizu.cloud.pushsdk.e.h.c cVar) throws IOException {
            com.meizu.cloud.pushsdk.e.h.m mVarA = null;
            try {
                mVarA = com.meizu.cloud.pushsdk.e.h.g.a(this.f19451b);
                cVar.a(mVarA);
            } finally {
                m.a(mVarA);
            }
        }
    }

    public static j a(g gVar, File file) {
        if (file != null) {
            return new b(gVar, file);
        }
        throw new NullPointerException("content == null");
    }

    public abstract long a() throws IOException;

    public abstract void a(com.meizu.cloud.pushsdk.e.h.c cVar) throws IOException;

    public abstract g b();

    public static j a(g gVar, String str) {
        Charset charset = m.f19470c;
        if (gVar != null) {
            Charset charsetA = gVar.a();
            if (charsetA == null) {
                gVar = g.a(gVar + "; charset=utf-8");
            } else {
                charset = charsetA;
            }
        }
        return a(gVar, str.getBytes(charset));
    }

    public static j a(g gVar, byte[] bArr) {
        return a(gVar, bArr, 0, bArr.length);
    }

    public static j a(g gVar, byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        m.a(bArr.length, i2, i3);
        return new a(gVar, i3, bArr, i2);
    }
}
