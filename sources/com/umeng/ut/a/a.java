package com.umeng.ut.a;

import android.content.Context;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f22983a = new a();

    /* renamed from: a, reason: collision with other field name */
    private Context f67a = null;

    /* renamed from: a, reason: collision with other field name */
    private long f66a = 0;

    private a() {
    }

    public static a a() {
        return f22983a;
    }

    public synchronized void a(Context context) {
        try {
            if (this.f67a == null) {
                if (context == null) {
                    return;
                }
                if (context.getApplicationContext() != null) {
                    this.f67a = context.getApplicationContext();
                } else {
                    this.f67a = context;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public Context m82a() {
        return this.f67a;
    }

    public void a(long j2) {
        this.f66a = j2 - System.currentTimeMillis();
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m81a() {
        return System.currentTimeMillis() + this.f66a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m83a() {
        return "" + m81a();
    }
}
