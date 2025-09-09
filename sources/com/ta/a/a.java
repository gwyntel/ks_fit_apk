package com.ta.a;

import android.content.Context;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f20005a = new a();
    private Context mContext = null;

    /* renamed from: a, reason: collision with other field name */
    private long f56a = 0;

    private a() {
    }

    public static a a() {
        return f20005a;
    }

    public Context getContext() {
        return this.mContext;
    }

    public synchronized void a(Context context) {
        try {
            if (this.mContext == null) {
                if (context == null) {
                    return;
                }
                if (context.getApplicationContext() != null) {
                    this.mContext = context.getApplicationContext();
                } else {
                    this.mContext = context;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void a(long j2) {
        this.f56a = j2 - System.currentTimeMillis();
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m73a() {
        return System.currentTimeMillis() + this.f56a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m74a() {
        return "" + m73a();
    }
}
