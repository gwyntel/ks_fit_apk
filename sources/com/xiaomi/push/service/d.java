package com.xiaomi.push.service;

import android.app.Notification;
import android.content.Context;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static List<a> f24593a = new CopyOnWriteArrayList();

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public final int f24594a;

        /* renamed from: a, reason: collision with other field name */
        public final long f1082a;

        /* renamed from: a, reason: collision with other field name */
        public final String f1083a;

        /* renamed from: a, reason: collision with other field name */
        public final Notification.Action[] f1084a;

        a(String str, long j2, int i2, Notification.Action[] actionArr) {
            this.f1083a = str;
            this.f1082a = j2;
            this.f24594a = i2;
            this.f1084a = actionArr;
        }
    }

    protected static void a(Context context, StatusBarNotification statusBarNotification, int i2) {
        if (!com.xiaomi.push.j.m550a(context) || i2 <= 0 || statusBarNotification == null) {
            return;
        }
        a(new a(statusBarNotification.getKey(), SystemClock.elapsedRealtime(), i2, ax.m752a(statusBarNotification.getNotification())));
    }

    private static void a(a aVar) {
        f24593a.add(aVar);
        a();
    }

    private static void a() {
        for (int size = f24593a.size() - 1; size >= 0; size--) {
            a aVar = f24593a.get(size);
            if (SystemClock.elapsedRealtime() - aVar.f1082a > 5000) {
                f24593a.remove(aVar);
            }
        }
        if (f24593a.size() > 10) {
            f24593a.remove(0);
        }
    }
}
