package com.umeng.ut.b.b;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.ut.a.b.g;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f23009a = new a();

    /* renamed from: c, reason: collision with root package name */
    private static long f23010c = 3000;

    /* renamed from: c, reason: collision with other field name */
    private String f73c = "";

    private a() {
    }

    public static a a() {
        return f23009a;
    }

    public static long b() {
        return f23010c;
    }

    private void d() {
        com.umeng.ut.a.c.e.c();
        if (TextUtils.isEmpty(this.f73c)) {
            return;
        }
        try {
            Context contextM82a = com.umeng.ut.a.a.a().m82a();
            if (com.umeng.ut.a.c.a.a(contextM82a)) {
                new Thread(new g(contextM82a)).start();
            }
        } catch (Throwable th) {
            com.umeng.ut.a.c.e.m85a("", th);
        }
    }

    synchronized String getUtdid(Context context) {
        if (!TextUtils.isEmpty(this.f73c)) {
            return this.f73c;
        }
        try {
            String value = d.a(context).getValue();
            if (TextUtils.isEmpty(value)) {
                return "ffffffffffffffffffffffff";
            }
            this.f73c = value;
            d();
            return this.f73c;
        } catch (Throwable th) {
            com.umeng.ut.a.c.e.a("AppUtdid", th, new Object[0]);
            return "ffffffffffffffffffffffff";
        }
    }

    public synchronized String i() {
        return this.f73c;
    }
}
