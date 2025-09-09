package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    private static w f24630a;

    /* renamed from: a, reason: collision with other field name */
    private Context f1113a;

    /* renamed from: a, reason: collision with other field name */
    private List<String> f1114a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private final List<String> f24631b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private final List<String> f24632c = new ArrayList();

    private w(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f1113a = applicationContext;
        if (applicationContext == null) {
            this.f1113a = context;
        }
        SharedPreferences sharedPreferences = this.f1113a.getSharedPreferences("mipush_app_info", 0);
        for (String str : sharedPreferences.getString("unregistered_pkg_names", "").split(",")) {
            if (TextUtils.isEmpty(str)) {
                this.f1114a.add(str);
            }
        }
        for (String str2 : sharedPreferences.getString("disable_push_pkg_names", "").split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                this.f24631b.add(str2);
            }
        }
        for (String str3 : sharedPreferences.getString("disable_push_pkg_names_cache", "").split(",")) {
            if (!TextUtils.isEmpty(str3)) {
                this.f24632c.add(str3);
            }
        }
    }

    public static w a(Context context) {
        if (f24630a == null) {
            f24630a = new w(context);
        }
        return f24630a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m807b(String str) {
        boolean zContains;
        synchronized (this.f24631b) {
            zContains = this.f24631b.contains(str);
        }
        return zContains;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m808c(String str) {
        boolean zContains;
        synchronized (this.f24632c) {
            zContains = this.f24632c.contains(str);
        }
        return zContains;
    }

    public void d(String str) {
        synchronized (this.f1114a) {
            try {
                if (this.f1114a.contains(str)) {
                    this.f1114a.remove(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", com.xiaomi.push.bp.a(this.f1114a, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void e(String str) {
        synchronized (this.f24631b) {
            try {
                if (this.f24631b.contains(str)) {
                    this.f24631b.remove(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", com.xiaomi.push.bp.a(this.f24631b, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void f(String str) {
        synchronized (this.f24632c) {
            try {
                if (this.f24632c.contains(str)) {
                    this.f24632c.remove(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", com.xiaomi.push.bp.a(this.f24632c, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m806a(String str) {
        boolean zContains;
        synchronized (this.f1114a) {
            zContains = this.f1114a.contains(str);
        }
        return zContains;
    }

    public void b(String str) {
        synchronized (this.f24631b) {
            try {
                if (!this.f24631b.contains(str)) {
                    this.f24631b.add(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", com.xiaomi.push.bp.a(this.f24631b, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void c(String str) {
        synchronized (this.f24632c) {
            try {
                if (!this.f24632c.contains(str)) {
                    this.f24632c.add(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", com.xiaomi.push.bp.a(this.f24632c, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(String str) {
        synchronized (this.f1114a) {
            try {
                if (!this.f1114a.contains(str)) {
                    this.f1114a.add(str);
                    this.f1113a.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", com.xiaomi.push.bp.a(this.f1114a, ",")).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
