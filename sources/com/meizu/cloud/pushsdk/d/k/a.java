package com.meizu.cloud.pushsdk.d.k;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.core.app.NotificationCompat;
import com.meizu.cloud.pushinternal.DebugLogger;

@SuppressLint({"MissingPermission"})
/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private AlarmManager f19237a;

    /* renamed from: b, reason: collision with root package name */
    private Context f19238b;

    /* renamed from: c, reason: collision with root package name */
    private Runnable f19239c;

    /* renamed from: d, reason: collision with root package name */
    private long f19240d;

    /* renamed from: e, reason: collision with root package name */
    private int f19241e;

    /* renamed from: f, reason: collision with root package name */
    private b f19242f;

    /* renamed from: g, reason: collision with root package name */
    private PendingIntent f19243g;

    /* renamed from: h, reason: collision with root package name */
    private String f19244h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f19245i;

    private class b extends BroadcastReceiver {
        private b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !intent.getAction().equals("alarm.util")) {
                return;
            }
            DebugLogger.i("AlarmUtils", "on receive delayed task, keyword: " + a.this.f19244h);
            a.this.f19245i = true;
            a.this.b();
            a.this.f19239c.run();
        }
    }

    public a(Context context, Runnable runnable, long j2) {
        this(context, runnable, j2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            b bVar = this.f19242f;
            if (bVar != null) {
                this.f19238b.unregisterReceiver(bVar);
                this.f19242f = null;
            }
        } catch (Exception e2) {
            DebugLogger.e("AlarmUtils", "clean error, " + e2.getMessage());
        }
    }

    public a(Context context, Runnable runnable, long j2, boolean z2) {
        Context applicationContext = context.getApplicationContext();
        this.f19238b = applicationContext;
        this.f19239c = runnable;
        this.f19240d = j2;
        this.f19241e = !z2 ? 1 : 0;
        this.f19237a = (AlarmManager) applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.f19245i = true;
    }

    public void a() {
        if (this.f19237a != null && this.f19243g != null && !this.f19245i) {
            DebugLogger.i("AlarmUtils", "cancel  delayed task, keyword: " + this.f19244h);
            this.f19237a.cancel(this.f19243g);
        }
        b();
    }

    public boolean c() {
        if (!this.f19245i) {
            DebugLogger.e("AlarmUtils", "last task not completed");
            return false;
        }
        this.f19245i = false;
        b bVar = new b();
        this.f19242f = bVar;
        this.f19238b.registerReceiver(bVar, new IntentFilter("alarm.util"));
        this.f19244h = String.valueOf(System.currentTimeMillis());
        this.f19243g = PendingIntent.getBroadcast(this.f19238b, 0, new Intent("alarm.util"), 1140850688);
        this.f19237a.setExactAndAllowWhileIdle(this.f19241e, System.currentTimeMillis() + this.f19240d, this.f19243g);
        DebugLogger.i("AlarmUtils", "start delayed task, keyword: " + this.f19244h);
        return true;
    }
}
