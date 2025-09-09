package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public final class by implements aq {

    /* renamed from: a, reason: collision with root package name */
    private static volatile by f24566a;

    /* renamed from: a, reason: collision with other field name */
    private long f1062a;

    /* renamed from: a, reason: collision with other field name */
    Context f1063a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f1064a;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f1066a = false;

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, a> f1065a = new ConcurrentHashMap<>();

    public static abstract class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        long f24567a;

        /* renamed from: a, reason: collision with other field name */
        String f1067a;

        a(String str, long j2) {
            this.f1067a = str;
            this.f24567a = j2;
        }

        abstract void a(by byVar);

        @Override // java.lang.Runnable
        public void run() {
            if (by.f24566a != null) {
                Context context = by.f24566a.f1063a;
                if (com.xiaomi.push.bg.d(context)) {
                    if (System.currentTimeMillis() - by.f24566a.f1064a.getLong(":ts-" + this.f1067a, 0L) > this.f24567a || com.xiaomi.push.ae.a(context)) {
                        com.xiaomi.push.p.a(by.f24566a.f1064a.edit().putLong(":ts-" + this.f1067a, System.currentTimeMillis()));
                        a(by.f24566a);
                    }
                }
            }
        }
    }

    private by(Context context) {
        this.f1063a = context.getApplicationContext();
        this.f1064a = context.getSharedPreferences(InvokeServiceData.CALL_TYPE_SYNC, 0);
    }

    public static by a(Context context) {
        if (f24566a == null) {
            synchronized (by.class) {
                try {
                    if (f24566a == null) {
                        f24566a = new by(context);
                    }
                } finally {
                }
            }
        }
        return f24566a;
    }

    @Override // com.xiaomi.push.service.aq
    /* renamed from: a, reason: collision with other method in class */
    public void mo774a() {
        if (this.f1066a) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f1062a < 3600000) {
            return;
        }
        this.f1062a = jCurrentTimeMillis;
        this.f1066a = true;
        com.xiaomi.push.ah.a(this.f1063a).a(new bz(this), (int) (Math.random() * 10.0d));
    }

    public String a(String str, String str2) {
        return this.f1064a.getString(str + ":" + str2, "");
    }

    public void a(String str, String str2, String str3) {
        com.xiaomi.push.p.a(f24566a.f1064a.edit().putString(str + ":" + str2, str3));
    }

    public void a(a aVar) {
        if (this.f1065a.putIfAbsent(aVar.f1067a, aVar) == null) {
            com.xiaomi.push.ah.a(this.f1063a).a(aVar, ((int) (Math.random() * 30.0d)) + 10);
        }
    }
}
