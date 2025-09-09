package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public class ce {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ce f23526a;

    /* renamed from: a, reason: collision with other field name */
    private Context f227a;

    private ce(Context context) {
        this.f227a = context;
    }

    public static ce a(Context context) {
        if (f23526a == null) {
            synchronized (ce.class) {
                try {
                    if (f23526a == null) {
                        f23526a = new ce(context);
                    }
                } finally {
                }
            }
        }
        return f23526a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m250a(String str, String str2, String str3) {
        SharedPreferences.Editor editorEdit = this.f227a.getSharedPreferences(str, 4).edit();
        editorEdit.putString(str2, str3);
        editorEdit.commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m249a(String str, String str2, long j2) {
        SharedPreferences.Editor editorEdit = this.f227a.getSharedPreferences(str, 4).edit();
        editorEdit.putLong(str2, j2);
        editorEdit.commit();
    }

    public synchronized String a(String str, String str2, String str3) {
        try {
        } catch (Throwable unused) {
            return str3;
        }
        return this.f227a.getSharedPreferences(str, 4).getString(str2, str3);
    }

    public synchronized long a(String str, String str2, long j2) {
        try {
        } catch (Throwable unused) {
            return j2;
        }
        return this.f227a.getSharedPreferences(str, 4).getLong(str2, j2);
    }
}
