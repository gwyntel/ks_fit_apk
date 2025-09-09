package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.m.m.a;
import com.alipay.sdk.m.s.a;
import com.alipay.sdk.m.s.b;
import com.alipay.sdk.m.u.e;
import com.alipay.sdk.m.u.h;
import com.alipay.sdk.m.u.l;
import com.alipay.sdk.m.u.n;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AuthTask {

    /* renamed from: c, reason: collision with root package name */
    public static final Object f9109c = h.class;

    /* renamed from: a, reason: collision with root package name */
    public Activity f9110a;

    /* renamed from: b, reason: collision with root package name */
    public com.alipay.sdk.m.x.a f9111b;

    public class a implements h.e {
        public a() {
        }

        @Override // com.alipay.sdk.m.u.h.e
        public void a() {
            AuthTask.this.a();
        }

        @Override // com.alipay.sdk.m.u.h.e
        public void b() {
        }
    }

    public AuthTask(Activity activity) {
        this.f9110a = activity;
        b.d().a(this.f9110a);
        this.f9111b = new com.alipay.sdk.m.x.a(activity, com.alipay.sdk.m.x.a.f9851k);
    }

    private h.e b() {
        return new a();
    }

    private void c() {
        com.alipay.sdk.m.x.a aVar = this.f9111b;
        if (aVar != null) {
            aVar.d();
        }
    }

    public synchronized String auth(String str, boolean z2) {
        return innerAuth(new com.alipay.sdk.m.s.a(this.f9110a, str, "auth"), str, z2);
    }

    public synchronized Map<String, String> authV2(String str, boolean z2) {
        com.alipay.sdk.m.s.a aVar;
        aVar = new com.alipay.sdk.m.s.a(this.f9110a, str, "authV2");
        return l.a(aVar, innerAuth(aVar, str, z2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.app.Activity, android.content.Context] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    public synchronized String innerAuth(com.alipay.sdk.m.s.a aVar, String str, boolean z2) {
        String strA;
        if (z2) {
            try {
                c();
            } catch (Throwable th) {
                throw th;
            }
        }
        b.d().a(this.f9110a);
        strA = com.alipay.sdk.m.j.b.a();
        com.alipay.sdk.m.j.a.a("");
        int i2 = 1;
        i2 = 1;
        boolean z3 = 0;
        z3 = 0;
        try {
            try {
                strA = a(this.f9110a, str, aVar);
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
            } catch (Exception e2) {
                e.a(e2);
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
                com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
                if (!com.alipay.sdk.m.m.a.z().r()) {
                }
            }
            if (!com.alipay.sdk.m.m.a.z().r()) {
                com.alipay.sdk.m.m.a.z().a(aVar, this.f9110a, false, 1);
            }
            a();
            i2 = this.f9110a;
            z3 = aVar.f9727d;
            com.alipay.sdk.m.k.a.b((Context) i2, aVar, str, (String) z3);
        } catch (Throwable th2) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.V, "" + SystemClock.elapsedRealtime());
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.W, l.a(strA, l.f9812a) + "|" + l.a(strA, l.f9813b));
            if (!com.alipay.sdk.m.m.a.z().r()) {
                com.alipay.sdk.m.m.a.z().a(aVar, this.f9110a, z3, i2);
            }
            a();
            com.alipay.sdk.m.k.a.b(this.f9110a, aVar, str, aVar.f9727d);
            throw th2;
        }
        return strA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        com.alipay.sdk.m.x.a aVar = this.f9111b;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(android.app.Activity r4, java.lang.String r5, com.alipay.sdk.m.s.a r6) {
        /*
            r3 = this;
            r3.c()
            r0 = 0
            com.alipay.sdk.m.q.a r1 = new com.alipay.sdk.m.q.a     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            r1.<init>()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            com.alipay.sdk.m.p.b r4 = r1.a(r6, r4, r5)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            org.json.JSONObject r4 = r4.c()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            java.lang.String r5 = "form"
            org.json.JSONObject r4 = r4.optJSONObject(r5)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            java.lang.String r5 = "onload"
            org.json.JSONObject r4 = r4.optJSONObject(r5)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            java.util.List r4 = com.alipay.sdk.m.r.b.a(r4)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            r3.a()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            r5 = 0
        L25:
            int r1 = r4.size()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            if (r5 >= r1) goto L4e
            java.lang.Object r1 = r4.get(r5)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            com.alipay.sdk.m.r.b r1 = (com.alipay.sdk.m.r.b) r1     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            com.alipay.sdk.m.r.a r1 = r1.a()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            com.alipay.sdk.m.r.a r2 = com.alipay.sdk.m.r.a.WapPay     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            if (r1 != r2) goto L4b
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            com.alipay.sdk.m.r.b r4 = (com.alipay.sdk.m.r.b) r4     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            java.lang.String r4 = r3.a(r6, r4)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L49
            r3.a()
            return r4
        L47:
            r4 = move-exception
            goto L52
        L49:
            r4 = move-exception
            goto L5c
        L4b:
            int r5 = r5 + 1
            goto L25
        L4e:
            r3.a()
            goto L6e
        L52:
            java.lang.String r5 = "biz"
            java.lang.String r1 = "H5AuthDataAnalysisError"
            com.alipay.sdk.m.k.a.a(r6, r5, r1, r4)     // Catch: java.lang.Throwable -> L5a
            goto L6b
        L5a:
            r4 = move-exception
            goto L89
        L5c:
            com.alipay.sdk.m.j.c r5 = com.alipay.sdk.m.j.c.NETWORK_ERROR     // Catch: java.lang.Throwable -> L5a
            int r5 = r5.b()     // Catch: java.lang.Throwable -> L5a
            com.alipay.sdk.m.j.c r0 = com.alipay.sdk.m.j.c.b(r5)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r5 = "net"
            com.alipay.sdk.m.k.a.a(r6, r5, r4)     // Catch: java.lang.Throwable -> L5a
        L6b:
            r3.a()
        L6e:
            if (r0 != 0) goto L7a
            com.alipay.sdk.m.j.c r4 = com.alipay.sdk.m.j.c.FAILED
            int r4 = r4.b()
            com.alipay.sdk.m.j.c r0 = com.alipay.sdk.m.j.c.b(r4)
        L7a:
            int r4 = r0.b()
            java.lang.String r5 = r0.a()
            java.lang.String r6 = ""
            java.lang.String r4 = com.alipay.sdk.m.j.b.a(r4, r5, r6)
            return r4
        L89:
            r3.a()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.AuthTask.b(android.app.Activity, java.lang.String, com.alipay.sdk.m.s.a):java.lang.String");
    }

    private String a(Activity activity, String str, com.alipay.sdk.m.s.a aVar) {
        String strA = aVar.a(str);
        List<a.b> listL = com.alipay.sdk.m.m.a.z().l();
        if (!com.alipay.sdk.m.m.a.z().f9518g || listL == null) {
            listL = com.alipay.sdk.m.j.a.f9316d;
        }
        if (n.a(aVar, (Context) this.f9110a, listL, true)) {
            h hVar = new h(activity, aVar, b());
            String strA2 = hVar.a(strA, false);
            hVar.a();
            if (!TextUtils.equals(strA2, h.f9784j) && !TextUtils.equals(strA2, h.f9785k)) {
                return TextUtils.isEmpty(strA2) ? com.alipay.sdk.m.j.b.a() : strA2;
            }
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9357f0);
            return b(activity, strA, aVar);
        }
        com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9358g0);
        return b(activity, strA, aVar);
    }

    private String a(com.alipay.sdk.m.s.a aVar, com.alipay.sdk.m.r.b bVar) {
        String[] strArrC = bVar.c();
        Bundle bundle = new Bundle();
        bundle.putString("url", strArrC[0]);
        Intent intent = new Intent(this.f9110a, (Class<?>) H5AuthActivity.class);
        intent.putExtras(bundle);
        a.C0055a.a(aVar, intent);
        this.f9110a.startActivity(intent);
        Object obj = f9109c;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException unused) {
                return com.alipay.sdk.m.j.b.a();
            }
        }
        String strD = com.alipay.sdk.m.j.b.d();
        return TextUtils.isEmpty(strD) ? com.alipay.sdk.m.j.b.a() : strD;
    }
}
