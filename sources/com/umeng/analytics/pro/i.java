package com.umeng.analytics.pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
class i {

    /* renamed from: b, reason: collision with root package name */
    private static SQLiteOpenHelper f21775b;

    /* renamed from: d, reason: collision with root package name */
    private static Context f21776d;

    /* renamed from: a, reason: collision with root package name */
    private AtomicInteger f21777a;

    /* renamed from: c, reason: collision with root package name */
    private SQLiteDatabase f21778c;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final i f21779a = new i();

        private a() {
        }
    }

    public static i a(Context context) {
        if (f21776d == null && context != null) {
            Context applicationContext = context.getApplicationContext();
            f21776d = applicationContext;
            f21775b = h.a(applicationContext);
        }
        return a.f21779a;
    }

    public synchronized void b() {
        try {
            if (this.f21777a.decrementAndGet() == 0) {
                this.f21778c.close();
            }
        } catch (Throwable unused) {
        }
    }

    private i() {
        this.f21777a = new AtomicInteger();
    }

    public synchronized SQLiteDatabase a() {
        try {
            if (this.f21777a.incrementAndGet() == 1) {
                this.f21778c = f21775b.getWritableDatabase();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f21778c;
    }
}
