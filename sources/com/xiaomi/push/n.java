package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static volatile n f24405a;

    /* renamed from: a, reason: collision with other field name */
    private Context f931a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f932a = new Handler(Looper.getMainLooper());

    /* renamed from: a, reason: collision with other field name */
    private Map<String, Map<String, String>> f933a = new HashMap();

    private n(Context context) {
        this.f931a = context;
    }

    private synchronized void b(String str, String str2, String str3) {
        try {
            if (this.f933a == null) {
                this.f933a = new HashMap();
            }
            Map<String, String> map = this.f933a.get(str);
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(str2, str3);
            this.f933a.put(str, map);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static n a(Context context) {
        if (f24405a == null) {
            synchronized (n.class) {
                try {
                    if (f24405a == null) {
                        f24405a = new n(context);
                    }
                } finally {
                }
            }
        }
        return f24405a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m683a(String str, String str2, String str3) {
        b(str, str2, str3);
        this.f932a.post(new o(this, str, str2, str3));
    }

    private synchronized String a(String str, String str2) {
        if (this.f933a != null && !TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(str2)) {
                try {
                    Map<String, String> map = this.f933a.get(str);
                    if (map != null) {
                        return map.get(str2);
                    }
                    return "";
                } catch (Throwable unused) {
                    return "";
                }
            }
        }
        return "";
    }

    public synchronized String a(String str, String str2, String str3) {
        String strA = a(str, str2);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        return this.f931a.getSharedPreferences(str, 4).getString(str2, str3);
    }
}
