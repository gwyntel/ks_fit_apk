package com.meizu.cloud.pushsdk.e.d;

import anet.channel.request.Request;
import com.meizu.cloud.pushsdk.e.d.c;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private final f f19436a;

    /* renamed from: b, reason: collision with root package name */
    private final String f19437b;

    /* renamed from: c, reason: collision with root package name */
    private final c f19438c;

    /* renamed from: d, reason: collision with root package name */
    private final j f19439d;

    /* renamed from: e, reason: collision with root package name */
    private final Object f19440e;

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private f f19441a;

        /* renamed from: b, reason: collision with root package name */
        private String f19442b = "GET";

        /* renamed from: c, reason: collision with root package name */
        private c.b f19443c = new c.b();

        /* renamed from: d, reason: collision with root package name */
        private j f19444d;

        /* renamed from: e, reason: collision with root package name */
        private Object f19445e;

        public b b() {
            return a("GET", (j) null);
        }

        public b d(j jVar) {
            return a(Request.Method.PUT, jVar);
        }

        public b a(c cVar) {
            this.f19443c = cVar.b();
            return this;
        }

        public b b(j jVar) {
            return a("PATCH", jVar);
        }

        public b c() {
            return a(Request.Method.HEAD, (j) null);
        }

        public b a(f fVar) {
            if (fVar == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.f19441a = fVar;
            return this;
        }

        public b c(j jVar) {
            return a("POST", jVar);
        }

        public b a(j jVar) {
            return a("DELETE", jVar);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.meizu.cloud.pushsdk.e.d.i.b a(java.lang.String r7) {
            /*
                r6 = this;
                if (r7 == 0) goto L61
                r4 = 0
                r5 = 3
                r1 = 1
                r2 = 0
                java.lang.String r3 = "ws:"
                r0 = r7
                boolean r0 = r0.regionMatches(r1, r2, r3, r4, r5)
                if (r0 == 0) goto L26
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "http:"
                r0.append(r1)
                r1 = 3
            L1a:
                java.lang.String r7 = r7.substring(r1)
                r0.append(r7)
                java.lang.String r7 = r0.toString()
                goto L3f
            L26:
                r4 = 0
                r5 = 4
                r1 = 1
                r2 = 0
                java.lang.String r3 = "wss:"
                r0 = r7
                boolean r0 = r0.regionMatches(r1, r2, r3, r4, r5)
                if (r0 == 0) goto L3f
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "https:"
                r0.append(r1)
                r1 = 4
                goto L1a
            L3f:
                com.meizu.cloud.pushsdk.e.d.f r0 = com.meizu.cloud.pushsdk.e.d.f.b(r7)
                if (r0 == 0) goto L4a
                com.meizu.cloud.pushsdk.e.d.i$b r7 = r6.a(r0)
                return r7
            L4a:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "unexpected url: "
                r1.append(r2)
                r1.append(r7)
                java.lang.String r7 = r1.toString()
                r0.<init>(r7)
                throw r0
            L61:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "url == null"
                r7.<init>(r0)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.e.d.i.b.a(java.lang.String):com.meizu.cloud.pushsdk.e.d.i$b");
        }

        public b a(String str, j jVar) {
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            }
            if (jVar != null && !d.a(str)) {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            }
            if (jVar != null || !d.b(str)) {
                this.f19442b = str;
                this.f19444d = jVar;
                return this;
            }
            throw new IllegalArgumentException("method " + str + " must have a request body.");
        }

        public b a(String str, String str2) {
            this.f19443c.a(str, str2);
            return this;
        }

        public i a() {
            if (this.f19441a != null) {
                return new i(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    private i(b bVar) {
        this.f19436a = bVar.f19441a;
        this.f19437b = bVar.f19442b;
        this.f19438c = bVar.f19443c.a();
        this.f19439d = bVar.f19444d;
        this.f19440e = bVar.f19445e != null ? bVar.f19445e : this;
    }

    public j a() {
        return this.f19439d;
    }

    public int b() {
        if ("POST".equals(d())) {
            return 1;
        }
        if (Request.Method.PUT.equals(d())) {
            return 2;
        }
        if ("DELETE".equals(d())) {
            return 3;
        }
        if (Request.Method.HEAD.equals(d())) {
            return 4;
        }
        return "PATCH".equals(d()) ? 5 : 0;
    }

    public c c() {
        return this.f19438c;
    }

    public String d() {
        return this.f19437b;
    }

    public f e() {
        return this.f19436a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{method=");
        sb.append(this.f19437b);
        sb.append(", url=");
        sb.append(this.f19436a);
        sb.append(", tag=");
        Object obj = this.f19440e;
        if (obj == this) {
            obj = null;
        }
        sb.append(obj);
        sb.append('}');
        return sb.toString();
    }

    public String a(String str) {
        return this.f19438c.a(str);
    }
}
