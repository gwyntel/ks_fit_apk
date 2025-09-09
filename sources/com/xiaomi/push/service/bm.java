package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

/* loaded from: classes4.dex */
public class bm {

    /* renamed from: a, reason: collision with root package name */
    private static bm f24544a;

    /* renamed from: a, reason: collision with other field name */
    private int f1050a = 0;

    /* renamed from: a, reason: collision with other field name */
    private Context f1051a;

    private bm(Context context) {
        this.f1051a = context.getApplicationContext();
    }

    public static bm a(Context context) {
        if (f24544a == null) {
            f24544a = new bm(context);
        }
        return f24544a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m763a() {
        String str = com.xiaomi.push.aa.f161a;
        return str.contains("xmsf") || str.contains("xiaomi") || str.contains("miui");
    }

    @SuppressLint({"NewApi"})
    public int a() {
        int i2 = this.f1050a;
        if (i2 != 0) {
            return i2;
        }
        try {
            this.f1050a = Settings.Global.getInt(this.f1051a.getContentResolver(), "device_provisioned", 0);
        } catch (Exception unused) {
        }
        return this.f1050a;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a, reason: collision with other method in class */
    public Uri m762a() {
        return Settings.Global.getUriFor("device_provisioned");
    }
}
