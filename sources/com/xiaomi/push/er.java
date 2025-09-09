package com.xiaomi.push;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
public class er {

    /* renamed from: a, reason: collision with root package name */
    private static volatile er f23667a;

    /* renamed from: a, reason: collision with other field name */
    private Context f336a;

    /* renamed from: a, reason: collision with other field name */
    private a f337a;

    public interface a {
        void a();
    }

    private er(Context context) {
        this.f336a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        a aVar;
        ah ahVarA = ah.a(this.f336a);
        com.xiaomi.push.service.az azVarA = com.xiaomi.push.service.az.a(this.f336a);
        SharedPreferences sharedPreferences = this.f336a.getSharedPreferences("mipush_extra", 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = sharedPreferences.getLong("first_try_ts", jCurrentTimeMillis);
        if (j2 == jCurrentTimeMillis) {
            sharedPreferences.edit().putLong("first_try_ts", jCurrentTimeMillis).commit();
        }
        if (Math.abs(jCurrentTimeMillis - j2) < 172800000) {
            return;
        }
        a(azVarA, ahVarA, false);
        if (azVarA.a(is.StorageCollectionSwitch.a(), true)) {
            int iA = a(azVarA.a(is.StorageCollectionFrequency.a(), 86400));
            ahVarA.a(new eu(this.f336a, iA), iA, 0);
        }
        if (j.m550a(this.f336a) && (aVar = this.f337a) != null) {
            aVar.a();
        }
        if (azVarA.a(is.ActivityTSSwitch.a(), false)) {
            a();
        }
        a(azVarA, ahVarA, true);
    }

    public static er a(Context context) {
        if (f23667a == null) {
            synchronized (er.class) {
                try {
                    if (f23667a == null) {
                        f23667a = new er(context);
                    }
                } finally {
                }
            }
        }
        return f23667a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m313a() {
        ah.a(this.f336a).a(new es(this));
    }

    private void a(com.xiaomi.push.service.az azVar, ah ahVar, boolean z2) {
        if (azVar.a(is.UploadSwitch.a(), true)) {
            ev evVar = new ev(this.f336a);
            if (z2) {
                ahVar.a((ah.a) evVar, a(azVar.a(is.UploadFrequency.a(), 86400)));
            } else {
                ahVar.m173a((ah.a) evVar);
            }
        }
    }

    public static int a(int i2) {
        return Math.max(60, i2);
    }

    private boolean a() {
        Application application;
        try {
            Context context = this.f336a;
            if (context instanceof Application) {
                application = (Application) context;
            } else {
                application = (Application) context.getApplicationContext();
            }
            application.registerActivityLifecycleCallbacks(new el(this.f336a, String.valueOf(System.currentTimeMillis() / 1000)));
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }
}
