package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.xiaomi.push.is;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TargetApi(24)
/* loaded from: classes4.dex */
class at {

    /* renamed from: a, reason: collision with root package name */
    private static at f24468a = new at();

    private class a {

        /* renamed from: a, reason: collision with other field name */
        List<b> f1014a;

        /* renamed from: b, reason: collision with root package name */
        List<b> f24470b;

        private a() {
            this.f1014a = new ArrayList();
            this.f24470b = new ArrayList();
        }
    }

    private class b {

        /* renamed from: a, reason: collision with root package name */
        int f24471a;

        /* renamed from: a, reason: collision with other field name */
        Notification f1015a;

        public b(int i2, Notification notification) {
            this.f24471a = i2;
            this.f1015a = notification;
        }

        public String toString() {
            return "id:" + this.f24471a;
        }
    }

    private at() {
    }

    public static at a() {
        return f24468a;
    }

    private boolean b(Context context) {
        return az.a(context).a(is.NotificationAutoGroupSwitch.a(), true);
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m736a() {
        return Build.VERSION.SDK_INT >= 24;
    }

    private boolean a(Context context) {
        if (b(context) && aw.m741a(context)) {
            return az.a(context).a(is.LatestNotificationNotIntoGroupSwitch.a(), false);
        }
        return false;
    }

    private String b(Notification notification) {
        if (notification == null) {
            return null;
        }
        return m738b(notification) ? a(notification) : notification.getGroup();
    }

    /* renamed from: b, reason: collision with other method in class */
    private boolean m738b(Notification notification) {
        Bundle bundle;
        if (notification == null || notification.getGroup() == null || (bundle = notification.extras) == null) {
            return false;
        }
        return notification.getGroup().equals(String.format("pushmask_%s_%s", Long.valueOf(bundle.getLong("push_src_group_time")), a(notification)));
    }

    private String a(Notification notification) {
        Bundle bundle;
        if (notification == null || (bundle = notification.extras) == null) {
            return null;
        }
        return bundle.getString("push_src_group_name");
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m737a(Notification notification) {
        if (notification == null) {
            return false;
        }
        Object objA = com.xiaomi.push.bk.a((Object) notification, "isGroupSummary", (Object[]) null);
        if (objA instanceof Boolean) {
            return ((Boolean) objA).booleanValue();
        }
        return false;
    }

    private void b(Context context, int i2, Notification notification) {
        String strC = ax.c(notification);
        if (TextUtils.isEmpty(strC)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("group restore not extract pkg from notification:" + i2);
            return;
        }
        aw awVarA = aw.a(context, strC);
        List<StatusBarNotification> listA = a(awVarA);
        if (listA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("group restore not get notifications");
            return;
        }
        for (StatusBarNotification statusBarNotification : listA) {
            Notification notification2 = statusBarNotification.getNotification();
            if (notification2 != null && m738b(notification2) && statusBarNotification.getId() != i2) {
                Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(context, statusBarNotification.getNotification());
                builderRecoverBuilder.setGroup(a(notification2));
                ax.a(builderRecoverBuilder, m737a(notification2));
                awVarA.a(statusBarNotification.getId(), builderRecoverBuilder.build());
                com.xiaomi.channel.commonutils.logger.b.b("group restore notification:" + statusBarNotification.getId());
            }
        }
    }

    public String a(Context context, Notification.Builder builder, String str) {
        if (!m736a() || !a(context)) {
            return str;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Bundle extras = builder.getExtras();
        extras.putString("push_src_group_name", str);
        extras.putLong("push_src_group_time", jCurrentTimeMillis);
        return String.format("pushmask_%s_%s", Long.valueOf(jCurrentTimeMillis), str);
    }

    public void a(Context context, int i2, Notification notification) {
        if (m736a()) {
            if (a(context)) {
                try {
                    b(context, i2, notification);
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("group notify handle restore error " + e2);
                }
            }
            if (b(context)) {
                try {
                    a(context, i2, notification, true);
                } catch (Exception e3) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("group notify handle auto error " + e3);
                }
            }
        }
    }

    private void a(Context context, int i2, Notification notification, boolean z2) {
        Notification notification2;
        String strC = ax.c(notification);
        if (TextUtils.isEmpty(strC)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("group auto not extract pkg from notification:" + i2);
            return;
        }
        List<StatusBarNotification> listA = a(aw.a(context, strC));
        if (listA == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("group auto not get notifications");
            return;
        }
        String strB = b(notification);
        HashMap map = new HashMap();
        for (StatusBarNotification statusBarNotification : listA) {
            if (statusBarNotification.getNotification() != null && statusBarNotification.getId() != i2) {
                a(map, statusBarNotification);
            }
        }
        for (Map.Entry<String, a> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!TextUtils.isEmpty(key)) {
                a value = entry.getValue();
                if (z2 && key.equals(strB) && !m738b(notification)) {
                    b bVar = new b(i2, notification);
                    if (m737a(notification)) {
                        value.f24470b.add(bVar);
                    } else {
                        value.f1014a.add(bVar);
                    }
                }
                int size = value.f1014a.size();
                if (value.f24470b.size() <= 0) {
                    if (z2 && size >= 2) {
                        a(context, strC, key, value.f1014a.get(0).f1015a);
                    }
                } else if (size <= 0) {
                    a(context, strC, key);
                } else if (az.a(context).a(is.NotificationGroupUpdateTimeSwitch.a(), false) && (notification2 = value.f24470b.get(0).f1015a) != null) {
                    notification2.when = System.currentTimeMillis();
                    a(context, strC, key, notification2);
                }
            }
        }
    }

    private void a(Map<String, a> map, StatusBarNotification statusBarNotification) {
        String strB = b(statusBarNotification.getNotification());
        a aVar = map.get(strB);
        if (aVar == null) {
            aVar = new a();
            map.put(strB, aVar);
        }
        b bVar = new b(statusBarNotification.getId(), statusBarNotification.getNotification());
        if (m737a(statusBarNotification.getNotification())) {
            aVar.f24470b.add(bVar);
        } else {
            aVar.f1014a.add(bVar);
        }
    }

    private void a(Context context, String str, String str2, Notification notification) {
        Notification.Builder defaults;
        try {
            if (TextUtils.isEmpty(str2)) {
                com.xiaomi.channel.commonutils.logger.b.m91a("group show summary group is null");
                return;
            }
            int iA = ax.a(context, str);
            if (iA == 0) {
                com.xiaomi.channel.commonutils.logger.b.m91a("group show summary not get icon from " + str);
                return;
            }
            aw awVarA = aw.a(context, str);
            if (Build.VERSION.SDK_INT >= 26) {
                String strB = awVarA.b(notification.getChannelId(), "groupSummary");
                NotificationChannel notificationChannelM743a = awVarA.m743a(strB);
                if ("groupSummary".equals(strB) && notificationChannelM743a == null) {
                    androidx.media3.common.util.j.a();
                    awVarA.a(androidx.browser.trusted.g.a(strB, "group_summary", 3));
                }
                f0.a();
                defaults = com.vivo.push.util.b0.a(context, strB);
            } else {
                defaults = new Notification.Builder(context).setPriority(0).setDefaults(-1);
            }
            ax.a(defaults, true);
            Notification notificationBuild = defaults.setContentTitle("GroupSummary").setContentText("GroupSummary").setSmallIcon(Icon.createWithResource(str, iA)).setAutoCancel(true).setGroup(str2).setGroupSummary(true).build();
            if (!com.xiaomi.push.j.m554c() && "com.xiaomi.xmsf".equals(context.getPackageName())) {
                ax.m750a(notificationBuild, str);
            }
            int iA2 = a(str, str2);
            awVarA.a(iA2, notificationBuild);
            com.xiaomi.channel.commonutils.logger.b.b("group show summary notify:" + iA2);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("group show summary error " + e2);
        }
    }

    private void a(Context context, String str, String str2) {
        com.xiaomi.channel.commonutils.logger.b.b("group cancel summary:" + str2);
        aw.a(context, str).a(a(str, str2));
    }

    private int a(String str, String str2) {
        return ("GroupSummary" + str + str2).hashCode();
    }

    private List<StatusBarNotification> a(aw awVar) {
        List<StatusBarNotification> listM749b = awVar != null ? awVar.m749b() : null;
        if (listM749b == null || listM749b.size() == 0) {
            return null;
        }
        return listM749b;
    }
}
