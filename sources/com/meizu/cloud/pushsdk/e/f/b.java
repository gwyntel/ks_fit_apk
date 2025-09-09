package com.meizu.cloud.pushsdk.e.f;

import com.meizu.cloud.pushsdk.e.d.g;
import com.meizu.cloud.pushsdk.e.d.j;
import com.meizu.cloud.pushsdk.e.h.f;
import com.meizu.cloud.pushsdk.e.h.l;
import java.io.IOException;

/* loaded from: classes4.dex */
public class b extends j {

    /* renamed from: a, reason: collision with root package name */
    private final j f19474a;

    /* renamed from: b, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.e.h.c f19475b;

    /* renamed from: c, reason: collision with root package name */
    private d f19476c;

    class a extends f {

        /* renamed from: b, reason: collision with root package name */
        long f19477b;

        /* renamed from: c, reason: collision with root package name */
        long f19478c;

        a(l lVar) {
            super(lVar);
            this.f19477b = 0L;
            this.f19478c = 0L;
        }

        @Override // com.meizu.cloud.pushsdk.e.h.f, com.meizu.cloud.pushsdk.e.h.l
        public void a(com.meizu.cloud.pushsdk.e.h.b bVar, long j2) throws IOException {
            super.a(bVar, j2);
            if (this.f19478c == 0) {
                this.f19478c = b.this.a();
            }
            this.f19477b += j2;
            if (b.this.f19476c != null) {
                b.this.f19476c.obtainMessage(1, new com.meizu.cloud.pushsdk.e.g.a(this.f19477b, this.f19478c)).sendToTarget();
            }
        }
    }

    public b(j jVar, com.meizu.cloud.pushsdk.e.e.a aVar) {
        this.f19474a = jVar;
        if (aVar != null) {
            this.f19476c = new d(aVar);
        }
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public long a() throws IOException {
        return this.f19474a.a();
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public g b() {
        return this.f19474a.b();
    }

    private l a(l lVar) {
        return new a(lVar);
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public void a(com.meizu.cloud.pushsdk.e.h.c cVar) throws IOException {
        if (this.f19475b == null) {
            this.f19475b = com.meizu.cloud.pushsdk.e.h.g.a(a((l) cVar));
        }
        this.f19474a.a(this.f19475b);
        this.f19475b.flush();
    }
}
