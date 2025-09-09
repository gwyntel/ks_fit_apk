package com.umeng.analytics.process;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f21990a;

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<String, a> f21991b = new ConcurrentHashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private Context f21992c;

    private c() {
    }

    static c a(Context context) {
        if (f21990a == null) {
            synchronized (c.class) {
                try {
                    if (f21990a == null) {
                        f21990a = new c();
                    }
                } finally {
                }
            }
        }
        c cVar = f21990a;
        cVar.f21992c = context;
        return cVar;
    }

    private a c(String str) {
        if (this.f21991b.get(str) != null) {
            return this.f21991b.get(str);
        }
        a aVarA = a.a(this.f21992c, str);
        this.f21991b.put(str, aVarA);
        return aVarA;
    }

    synchronized void b(String str) {
        c(str).b();
    }

    static class a {

        /* renamed from: a, reason: collision with root package name */
        private AtomicInteger f21993a = new AtomicInteger();

        /* renamed from: b, reason: collision with root package name */
        private SQLiteOpenHelper f21994b;

        /* renamed from: c, reason: collision with root package name */
        private SQLiteDatabase f21995c;

        private a() {
        }

        static a a(Context context, String str) {
            Context appContext = UMGlobalContext.getAppContext(context);
            a aVar = new a();
            aVar.f21994b = b.a(appContext, str);
            return aVar;
        }

        synchronized void b() {
            try {
                if (this.f21993a.decrementAndGet() == 0) {
                    this.f21995c.close();
                }
            } catch (Throwable unused) {
            }
        }

        synchronized SQLiteDatabase a() {
            try {
                if (this.f21993a.incrementAndGet() == 1) {
                    this.f21995c = this.f21994b.getWritableDatabase();
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.f21995c;
        }
    }

    synchronized SQLiteDatabase a(String str) {
        return c(str).a();
    }
}
