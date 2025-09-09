package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.google.firebase.messaging.Constants;
import com.xiaomi.push.ah;
import com.xiaomi.push.fq;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class cd {
    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(19)
    public static void c(Context context, String str, int i2, String str2, Notification notification) {
        aw awVarA;
        Notification notificationA;
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || Build.VERSION.SDK_INT < 26 || (notificationA = a(notification, i2, str2, (awVarA = aw.a(context, str)))) == null) {
            return;
        }
        boolean z2 = notification != null;
        if (notificationA.getGroupAlertBehavior() != 1) {
            com.xiaomi.push.bk.a((Object) notificationA, "mGroupAlertBehavior", (Object) 1);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = notificationA.extras.getLong("mipush_org_when", 0L);
        int i3 = notificationA.extras.getInt("mipush_n_top_fre", 0);
        int i4 = notificationA.extras.getInt("mipush_n_top_prd", 0);
        if (i4 <= 0 || i4 < i3) {
            return;
        }
        long j3 = (i4 * 1000) + j2;
        int iMin = (j2 >= jCurrentTimeMillis || jCurrentTimeMillis >= j3) ? 0 : i3 > 0 ? (int) Math.min((j3 - jCurrentTimeMillis) / 1000, i3) : i4;
        if (!z2) {
            if (iMin > 0) {
                notificationA.when = jCurrentTimeMillis;
                com.xiaomi.channel.commonutils.logger.b.m91a("update top notification: " + str2);
                awVarA.a(i2, notificationA);
            } else {
                Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(context, notificationA);
                builderRecoverBuilder.setPriority(0);
                builderRecoverBuilder.setWhen(jCurrentTimeMillis);
                Bundle extras = builderRecoverBuilder.getExtras();
                if (extras != null) {
                    extras.remove("mipush_n_top_flag");
                    extras.remove("mipush_org_when");
                    extras.remove("mipush_n_top_fre");
                    extras.remove("mipush_n_top_prd");
                    builderRecoverBuilder.setExtras(extras);
                }
                com.xiaomi.channel.commonutils.logger.b.m91a("update top notification to common: " + str2);
                awVarA.a(i2, builderRecoverBuilder.build());
            }
        }
        if (iMin > 0) {
            com.xiaomi.channel.commonutils.logger.b.m91a("schedule top notification next update delay: " + iMin);
            com.xiaomi.push.ah.a(context).m174a(b(i2, str2));
            com.xiaomi.push.ah.a(context).b(a(context, str, i2, str2, (Notification) null), iMin);
        }
    }

    static void a(Context context, Map<String, String> map, fq fqVar, long j2) {
        if (map == null || fqVar == null || !com.xiaomi.push.j.m550a(context) || !m776a(map)) {
            return;
        }
        int iA = a(map);
        int iB = b(map);
        if (iA <= 0 || iB > iA) {
            com.xiaomi.channel.commonutils.logger.b.d("set top notification failed - period:" + iA + " frequency:" + iB);
            return;
        }
        fqVar.setPriority(2);
        Bundle bundle = new Bundle();
        bundle.putLong("mipush_org_when", j2);
        bundle.putBoolean("mipush_n_top_flag", true);
        if (iB > 0) {
            bundle.putInt("mipush_n_top_fre", iB);
        }
        bundle.putInt("mipush_n_top_prd", iA);
        fqVar.addExtras(bundle);
    }

    private static int b(Map<String, String> map) {
        return Math.max(0, com.xiaomi.push.s.a(map.get("notification_top_frequency"), 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(int i2, String str) {
        return "n_top_update_" + i2 + OpenAccountUIConstants.UNDER_LINE + str;
    }

    @TargetApi(19)
    /* renamed from: a, reason: collision with other method in class */
    static void m775a(Context context, String str, int i2, String str2, Notification notification) {
        if (com.xiaomi.push.j.m550a(context) && notification != null && notification.extras.getBoolean("mipush_n_top_flag", false)) {
            c(context, str, i2, str2, notification);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m776a(Map<String, String> map) {
        String str = map.get("notification_top_repeat");
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean z2 = Boolean.parseBoolean(str);
        com.xiaomi.channel.commonutils.logger.b.c("top notification' repeat is " + z2);
        return z2;
    }

    private static int a(Map<String, String> map) {
        return Math.max(0, com.xiaomi.push.s.a(map.get("notification_top_period"), 0));
    }

    @TargetApi(19)
    private static Notification a(Notification notification, int i2, String str, aw awVar) {
        if (notification != null) {
            if (!str.equals(notification.extras.getString(Constants.MessagePayloadKeys.MSGID_SERVER))) {
                notification = null;
            }
            return notification;
        }
        List<StatusBarNotification> listM749b = awVar.m749b();
        if (listM749b == null) {
            return null;
        }
        for (StatusBarNotification statusBarNotification : listM749b) {
            Notification notification2 = statusBarNotification.getNotification();
            String string = notification2.extras.getString(Constants.MessagePayloadKeys.MSGID_SERVER);
            if (i2 == statusBarNotification.getId() && str.equals(string)) {
                return notification2;
            }
        }
        return null;
    }

    private static ah.a a(Context context, String str, int i2, String str2, Notification notification) {
        return new ce(i2, str2, context, str, notification);
    }
}
