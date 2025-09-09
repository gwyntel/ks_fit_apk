package com.xiaomi.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import com.xiaomi.push.fu;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
class fv implements fu.a {

    /* renamed from: a, reason: collision with other field name */
    protected Context f440a;

    /* renamed from: a, reason: collision with other field name */
    private PendingIntent f439a = null;

    /* renamed from: a, reason: collision with root package name */
    private volatile long f23769a = 0;

    public fv(Context context) {
        this.f440a = null;
        this.f440a = context;
    }

    public void a(Intent intent, long j2) {
        AlarmManager alarmManager = (AlarmManager) this.f440a.getSystemService(NotificationCompat.CATEGORY_ALARM);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            this.f439a = PendingIntent.getBroadcast(this.f440a, 0, intent, DiskLruHelper.DEFAULT_MAXSIZE);
        } else {
            this.f439a = PendingIntent.getBroadcast(this.f440a, 0, intent, 0);
        }
        if (i2 < 31 || j.m550a(this.f440a)) {
            bk.a((Object) alarmManager, "setExactAndAllowWhileIdle", 2, Long.valueOf(j2), this.f439a);
        } else {
            alarmManager.set(2, j2, this.f439a);
        }
        com.xiaomi.channel.commonutils.logger.b.c("[Alarm] register timer " + j2);
    }

    private void a(AlarmManager alarmManager, long j2, PendingIntent pendingIntent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            AlarmManager.class.getMethod("setExact", Integer.TYPE, Long.TYPE, PendingIntent.class).invoke(alarmManager, 2, Long.valueOf(j2), pendingIntent);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("[Alarm] invoke setExact method meet error. " + e2);
        }
    }

    @Override // com.xiaomi.push.fu.a
    public void a(boolean z2) {
        long jM784a = com.xiaomi.push.service.p.a(this.f440a).m784a();
        if (z2 || this.f23769a != 0) {
            if (z2) {
                a();
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (z2 || this.f23769a == 0) {
                this.f23769a = jElapsedRealtime + (jM784a - (jElapsedRealtime % jM784a));
            } else if (this.f23769a <= jElapsedRealtime) {
                this.f23769a += jM784a;
                if (this.f23769a < jElapsedRealtime) {
                    this.f23769a = jElapsedRealtime + jM784a;
                }
            }
            Intent intent = new Intent(com.xiaomi.push.service.bj.f24533q);
            intent.setPackage(this.f440a.getPackageName());
            a(intent, this.f23769a);
        }
    }

    @Override // com.xiaomi.push.fu.a
    public void a() {
        if (this.f439a != null) {
            try {
                ((AlarmManager) this.f440a.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(this.f439a);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f439a = null;
                com.xiaomi.channel.commonutils.logger.b.c("[Alarm] unregister timer");
                this.f23769a = 0L;
                throw th;
            }
            this.f439a = null;
            com.xiaomi.channel.commonutils.logger.b.c("[Alarm] unregister timer");
            this.f23769a = 0L;
        }
        this.f23769a = 0L;
    }

    @Override // com.xiaomi.push.fu.a
    /* renamed from: a */
    public boolean mo419a() {
        return this.f23769a != 0;
    }
}
