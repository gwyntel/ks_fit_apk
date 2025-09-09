package com.alipay.sdk.m.s;

import android.content.Context;
import java.io.File;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    public static b f9737b;

    /* renamed from: a, reason: collision with root package name */
    public Context f9738a;

    public static b d() {
        if (f9737b == null) {
            f9737b = new b();
        }
        return f9737b;
    }

    public static boolean e() {
        String[] strArr = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (int i2 = 0; i2 < 10; i2++) {
            if (new File(strArr[i2]).exists()) {
                return true;
            }
        }
        return false;
    }

    public void a(Context context) {
        com.alipay.sdk.m.m.b.b();
        this.f9738a = context.getApplicationContext();
    }

    public Context b() {
        return this.f9738a;
    }

    public String c() {
        return com.alipay.sdk.m.w.b.c(null, this.f9738a);
    }

    public com.alipay.sdk.m.m.b a() {
        return com.alipay.sdk.m.m.b.b();
    }
}
