package com.xiaomi.push;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.push.kf;
import com.xiaomi.push.kp;

/* loaded from: classes4.dex */
public class jx {

    /* renamed from: a, reason: collision with root package name */
    static Boolean f24365a;

    public static <T extends jy<T, ?>> byte[] a(T t2) {
        if (t2 == null) {
            return null;
        }
        try {
            return new ke(new kf.a()).a(t2);
        } catch (kd e2) {
            com.xiaomi.channel.commonutils.logger.b.a("convertThriftObjectToBytes catch TException.", e2);
            return null;
        }
    }

    private static int b(Context context, String str, String str2) {
        com.xiaomi.push.service.aw awVarA;
        NotificationChannel notificationChannelM743a;
        if (Build.VERSION.SDK_INT < 26 || context == null || TextUtils.isEmpty(str) || (awVarA = com.xiaomi.push.service.aw.a(context, str)) == null || (notificationChannelM743a = awVarA.m743a(awVarA.m746a(str2))) == null) {
            return 0;
        }
        return notificationChannelM743a.getImportance() != 0 ? 32 : 64;
    }

    public static <T extends jy<T, ?>> void a(T t2, byte[] bArr) {
        if (bArr != null) {
            new kc(new kp.a(true, true, bArr.length)).a(t2, bArr);
            return;
        }
        throw new kd("the message byte is empty.");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static short m659a(Context context, jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        return m661a(context, jjVar.f748b, (jaVarM593a == null || jaVarM593a.m560a() == null) ? null : jaVarM593a.m560a().get("channel_id"));
    }

    public static int a(Context context, jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        return a(context, jjVar.f748b, (jaVarM593a == null || jaVarM593a.m560a() == null) ? null : jaVarM593a.m560a().get("channel_id"));
    }

    public static void a() {
        f24365a = null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m662a() {
        Bundle bundleM777a;
        if (f24365a == null) {
            if (Build.VERSION.SDK_INT >= 30 && (bundleM777a = com.xiaomi.push.service.f.m777a("com.xiaomi.xmsf", (String) null)) != null && !bundleM777a.isEmpty()) {
                f24365a = new Boolean(true);
            } else {
                f24365a = new Boolean(false);
            }
        }
        return f24365a.booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r8, java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.jx.a(android.content.Context, java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r5, java.lang.String r6, android.app.NotificationChannel r7) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 26
            if (r0 < r1) goto L98
            if (r5 == 0) goto L98
            boolean r5 = android.text.TextUtils.isEmpty(r6)
            if (r5 != 0) goto L98
            if (r7 == 0) goto L98
            int r5 = androidx.browser.trusted.c.a(r7)
            r0 = 1
            if (r5 == 0) goto L19
            r5 = r0
            goto L1a
        L19:
            r5 = 2
        L1a:
            boolean r1 = m662a()
            r2 = 16
            r3 = 8
            r4 = 4
            if (r1 == 0) goto L56
            java.lang.String r0 = com.umeng.message.a.a(r7)
            android.os.Bundle r6 = com.xiaomi.push.service.f.m777a(r6, r0)
            java.lang.String r0 = "canShowFloat"
            boolean r1 = r6.containsKey(r0)
            if (r1 == 0) goto L43
            boolean r0 = r6.getBoolean(r0)
            if (r0 == 0) goto L42
            int r0 = androidx.browser.trusted.c.a(r7)
            if (r0 < r4) goto L42
            r3 = r4
        L42:
            r5 = r5 | r3
        L43:
            java.lang.String r0 = "canShowOnKeyguard"
            boolean r1 = r6.containsKey(r0)
            if (r1 == 0) goto L80
            boolean r6 = r6.getBoolean(r0)
            if (r6 == 0) goto L52
            goto L54
        L52:
            r2 = 32
        L54:
            r5 = r5 | r2
            goto L80
        L56:
            java.lang.String r1 = com.umeng.message.a.a(r7)
            int r1 = com.xiaomi.push.service.f.a(r6, r1, r3)
            if (r1 != r0) goto L6c
            int r1 = androidx.browser.trusted.c.a(r7)
            if (r1 < r4) goto L69
            r5 = r5 | 4
            goto L6f
        L69:
            r5 = r5 | 8
            goto L6f
        L6c:
            if (r1 != 0) goto L6f
            goto L69
        L6f:
            java.lang.String r1 = com.umeng.message.a.a(r7)
            int r6 = com.xiaomi.push.service.f.a(r6, r1, r2)
            if (r6 != r0) goto L7c
            r5 = r5 | 16
            goto L80
        L7c:
            if (r6 != 0) goto L80
            r5 = r5 | 32
        L80:
            android.net.Uri r6 = com.xiaomi.push.a0.a(r7)
            if (r6 == 0) goto L89
            r5 = r5 | 64
            goto L8b
        L89:
            r5 = r5 | 128(0x80, float:1.8E-43)
        L8b:
            boolean r6 = com.xiaomi.push.b0.a(r7)
            if (r6 == 0) goto L94
            r6 = 256(0x100, float:3.59E-43)
            goto L96
        L94:
            r6 = 512(0x200, float:7.17E-43)
        L96:
            r5 = r5 | r6
            goto L99
        L98:
            r5 = 0
        L99:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.jx.a(android.content.Context, java.lang.String, android.app.NotificationChannel):int");
    }

    private static int a(Context context, String str, String str2) {
        com.xiaomi.push.service.aw awVarA;
        NotificationChannel notificationChannelM743a;
        if (Build.VERSION.SDK_INT < 26 || context == null || TextUtils.isEmpty(str) || (awVarA = com.xiaomi.push.service.aw.a(context, str)) == null || (notificationChannelM743a = awVarA.m743a(awVarA.m746a(str2))) == null) {
            return 0;
        }
        int i2 = notificationChannelM743a.getImportance() != 0 ? 1 : 2;
        int iA = com.xiaomi.push.service.f.a(str, notificationChannelM743a.getId(), 8);
        if (iA == 1) {
            i2 += 4;
        } else if (iA == 0) {
            i2 += 8;
        }
        int iA2 = com.xiaomi.push.service.f.a(str, notificationChannelM743a.getId(), 16);
        return iA2 == 1 ? i2 + 16 : iA2 == 0 ? i2 + 32 : i2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static short m660a(Context context, String str) {
        return m661a(context, str, (String) null);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static short m661a(Context context, String str, String str2) {
        return (short) (g.a(context, str, false).a() + (ag.b(context) ? 4 : 0) + (ag.a(context) ? 8 : 0) + (com.xiaomi.push.service.aw.m741a(context) ? 16 : 0) + b(context, str, str2));
    }
}
