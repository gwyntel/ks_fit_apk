package com.meizu.cloud.pushsdk.e.d;

import androidx.browser.trusted.sharing.ShareTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes4.dex */
public final class h extends j {

    /* renamed from: a, reason: collision with root package name */
    public static final g f19418a = g.a("multipart/mixed");

    /* renamed from: b, reason: collision with root package name */
    public static final g f19419b = g.a("multipart/alternative");

    /* renamed from: c, reason: collision with root package name */
    public static final g f19420c = g.a("multipart/digest");

    /* renamed from: d, reason: collision with root package name */
    public static final g f19421d = g.a("multipart/parallel");

    /* renamed from: e, reason: collision with root package name */
    public static final g f19422e = g.a(ShareTarget.ENCODING_TYPE_MULTIPART);

    /* renamed from: f, reason: collision with root package name */
    private static final byte[] f19423f = {58, 32};

    /* renamed from: g, reason: collision with root package name */
    private static final byte[] f19424g = {13, 10};

    /* renamed from: h, reason: collision with root package name */
    private static final byte[] f19425h = {45, 45};

    /* renamed from: i, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.e.h.e f19426i;

    /* renamed from: j, reason: collision with root package name */
    private final g f19427j;

    /* renamed from: k, reason: collision with root package name */
    private final g f19428k;

    /* renamed from: l, reason: collision with root package name */
    private final List<b> f19429l;

    /* renamed from: m, reason: collision with root package name */
    private long f19430m = -1;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final com.meizu.cloud.pushsdk.e.h.e f19431a;

        /* renamed from: b, reason: collision with root package name */
        private g f19432b;

        /* renamed from: c, reason: collision with root package name */
        private final List<b> f19433c;

        public a() {
            this(UUID.randomUUID().toString());
        }

        public a a(c cVar, j jVar) {
            return a(b.a(cVar, jVar));
        }

        public a(String str) {
            this.f19432b = h.f19418a;
            this.f19433c = new ArrayList();
            this.f19431a = com.meizu.cloud.pushsdk.e.h.e.b(str);
        }

        public a a(g gVar) {
            if (gVar == null) {
                throw new NullPointerException("type == null");
            }
            if ("multipart".equals(gVar.b())) {
                this.f19432b = gVar;
                return this;
            }
            throw new IllegalArgumentException("multipart != " + gVar);
        }

        public a a(b bVar) {
            if (bVar == null) {
                throw new NullPointerException("part == null");
            }
            this.f19433c.add(bVar);
            return this;
        }

        public h a() {
            if (this.f19433c.isEmpty()) {
                throw new IllegalStateException("Multipart body must have at least one part.");
            }
            return new h(this.f19431a, this.f19432b, this.f19433c);
        }
    }

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        private final c f19434a;

        /* renamed from: b, reason: collision with root package name */
        private final j f19435b;

        private b(c cVar, j jVar) {
            this.f19434a = cVar;
            this.f19435b = jVar;
        }

        public static b a(c cVar, j jVar) {
            if (jVar == null) {
                throw new NullPointerException("body == null");
            }
            if (cVar != null && cVar.a("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            }
            if (cVar == null || cVar.a("Content-Length") == null) {
                return new b(cVar, jVar);
            }
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    h(com.meizu.cloud.pushsdk.e.h.e eVar, g gVar, List<b> list) {
        this.f19426i = eVar;
        this.f19427j = gVar;
        this.f19428k = g.a(gVar + "; boundary=" + eVar.d());
        this.f19429l = m.a(list);
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public long a() throws IOException {
        long j2 = this.f19430m;
        if (j2 != -1) {
            return j2;
        }
        long jA = a((com.meizu.cloud.pushsdk.e.h.c) null, true);
        this.f19430m = jA;
        return jA;
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public g b() {
        return this.f19428k;
    }

    private long a(com.meizu.cloud.pushsdk.e.h.c cVar, boolean z2) throws IOException {
        com.meizu.cloud.pushsdk.e.h.c cVar2;
        com.meizu.cloud.pushsdk.e.h.b bVar;
        if (z2) {
            bVar = new com.meizu.cloud.pushsdk.e.h.b();
            cVar2 = bVar;
        } else {
            cVar2 = cVar;
            bVar = null;
        }
        int size = this.f19429l.size();
        long j2 = 0;
        for (int i2 = 0; i2 < size; i2++) {
            b bVar2 = this.f19429l.get(i2);
            c cVar3 = bVar2.f19434a;
            j jVar = bVar2.f19435b;
            cVar2.a(f19425h);
            cVar2.a(this.f19426i);
            cVar2.a(f19424g);
            if (cVar3 != null) {
                int iC = cVar3.c();
                for (int i3 = 0; i3 < iC; i3++) {
                    cVar2.a(cVar3.a(i3)).a(f19423f).a(cVar3.b(i3)).a(f19424g);
                }
            }
            g gVarB = jVar.b();
            if (gVarB != null) {
                cVar2.a("Content-Type: ").a(gVarB.toString()).a(f19424g);
            }
            long jA = jVar.a();
            if (jA != -1) {
                cVar2.a("Content-Length: ").a(jA).a(f19424g);
            } else if (z2) {
                bVar.e();
                return -1L;
            }
            byte[] bArr = f19424g;
            cVar2.a(bArr);
            if (z2) {
                j2 += jA;
            } else {
                jVar.a(cVar2);
            }
            cVar2.a(bArr);
        }
        byte[] bArr2 = f19425h;
        cVar2.a(bArr2);
        cVar2.a(this.f19426i);
        cVar2.a(bArr2);
        cVar2.a(f19424g);
        if (!z2) {
            return j2;
        }
        long jK = j2 + bVar.k();
        bVar.e();
        return jK;
    }

    @Override // com.meizu.cloud.pushsdk.e.d.j
    public void a(com.meizu.cloud.pushsdk.e.h.c cVar) throws IOException {
        a(cVar, false);
    }
}
