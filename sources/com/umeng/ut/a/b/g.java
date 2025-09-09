package com.umeng.ut.a.b;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f22992a = false;

    /* renamed from: a, reason: collision with other field name */
    private Context f69a;

    public g(Context context) {
        this.f69a = context;
    }

    private void a() {
        com.umeng.ut.a.c.e.c();
        if (com.umeng.ut.a.c.c.b(this.f69a) && !f22992a) {
            f22992a = true;
            if (com.umeng.ut.b.b.d.a(this.f69a).b()) {
                try {
                    m84b();
                } catch (Throwable unused) {
                }
                f22992a = false;
            }
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    private void m84b() {
        com.umeng.ut.a.c.e.c();
        String strB = b();
        if (TextUtils.isEmpty(strB)) {
            com.umeng.ut.a.c.e.m85a("postData is empty", new Object[0]);
        } else if (a(strB)) {
            com.umeng.ut.a.c.e.m85a("", "upload success");
        } else {
            com.umeng.ut.a.c.e.m85a("", "upload fail");
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                Thread.sleep(com.umeng.ut.b.b.a.b());
            } catch (Throwable th) {
                com.umeng.ut.a.c.e.a("", th, new Object[0]);
                return;
            }
        } catch (Exception unused) {
        }
        a();
    }

    private boolean a(String str) throws Throwable {
        a aVarA = b.a("https://audid.umeng.com/v3/a/audid/req", str, true);
        if (aVarA == null) {
            return false;
        }
        return com.umeng.ut.b.b.e.a(aVarA);
    }

    private String b() {
        String strI = com.umeng.ut.b.b.a.a().i();
        if (TextUtils.isEmpty(strI)) {
            return null;
        }
        String strA = com.umeng.ut.a.a.a.a(strI);
        if (com.umeng.ut.a.c.e.m86a()) {
            com.umeng.ut.a.c.e.b("", strA);
        }
        return com.umeng.ut.a.a.b.b(strA);
    }
}
