package com.umeng.message.proguard;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.UTrack;
import com.umeng.message.common.UPLog;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class an extends c {

    /* renamed from: b, reason: collision with root package name */
    private static final Object f22775b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private final ac f22776c;

    /* renamed from: d, reason: collision with root package name */
    private long f22777d;

    public an(ac acVar) {
        this.f22776c = acVar;
    }

    private StatusBarNotification a(NotificationManager notificationManager) {
        ac acVar = this.f22776c;
        if (acVar == null || acVar.f22710b == null) {
            return null;
        }
        try {
            StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
            if (activeNotifications != null && activeNotifications.length != 0) {
                for (StatusBarNotification statusBarNotification : activeNotifications) {
                    if (TextUtils.equals(statusBarNotification.getTag(), "um") && statusBarNotification.getId() == this.f22776c.f22709a) {
                        return statusBarNotification;
                    }
                }
                return null;
            }
            return null;
        } catch (Throwable th) {
            UPLog.e("RePop", th);
            return null;
        }
    }

    @Override // com.umeng.message.proguard.c
    public final Future<?> b() throws ClassNotFoundException {
        boolean z2;
        if (d()) {
            return this.f22826a;
        }
        ac acVar = this.f22776c;
        if (acVar == null) {
            return this.f22826a;
        }
        if (acVar.f22710b == null) {
            return this.f22826a;
        }
        long j2 = this.f22777d;
        if (j2 == 0) {
            this.f22777d = System.currentTimeMillis();
            z2 = true;
        } else {
            if (!f.a(j2)) {
                return this.f22826a;
            }
            z2 = false;
        }
        Application applicationA = x.a();
        if (MessageSharedPrefs.getInstance(applicationA).o() >= MessageSharedPrefs.getInstance(applicationA).n()) {
            return this.f22826a;
        }
        ScheduledFuture<?> scheduledFutureA = b.a(this, z2 ? r0.getRepostStart() : r0.getRepostInterval(), TimeUnit.MINUTES);
        this.f22826a = scheduledFutureA;
        return scheduledFutureA;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            synchronized (f22775b) {
                try {
                    if (this.f22776c == null) {
                        this.f22826a = null;
                        return;
                    }
                    if (!f.a(this.f22777d)) {
                        this.f22826a = null;
                        return;
                    }
                    Application applicationA = x.a();
                    NotificationManager notificationManager = (NotificationManager) applicationA.getSystemService("notification");
                    if (notificationManager == null) {
                        this.f22826a = null;
                        UPLog.d("RePop", "mgr null!");
                        return;
                    }
                    StatusBarNotification statusBarNotificationA = a(notificationManager);
                    if (statusBarNotificationA == null) {
                        this.f22826a = null;
                        UPLog.d("RePop", "sbn null! msgId:", this.f22776c.f22710b.getMsgId());
                        return;
                    }
                    int iN = MessageSharedPrefs.getInstance(applicationA).n();
                    int iO = MessageSharedPrefs.getInstance(applicationA).o();
                    UPLog.d("RePop", "task total times:", Integer.valueOf(iO), "config:", Integer.valueOf(iN));
                    if (iO >= iN) {
                        return;
                    }
                    Notification notification = statusBarNotificationA.getNotification();
                    if (notification != null) {
                        notificationManager.cancel("um", this.f22776c.f22709a);
                        notification.when = System.currentTimeMillis();
                        notificationManager.notify("um", this.f22776c.f22709a, notification);
                        this.f22826a = null;
                        this.f22776c.f22712d++;
                        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA);
                        Calendar calendar = Calendar.getInstance();
                        messageSharedPrefs.f22601b.a("re_pop_times", String.format(Locale.getDefault(), "%d.%d.%d", Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(6)), Integer.valueOf(iO + 1)));
                        UTrack.getInstance().trackMsgRepost(this.f22776c.f22710b, notification);
                        UPLog.d("RePop", "show msgId:", this.f22776c.f22710b.getMsgId(), "count:", Integer.valueOf(this.f22776c.f22712d));
                        a();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            UPLog.e("RePop", th2);
        }
    }
}
