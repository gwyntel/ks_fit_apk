package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class v {

    public static class a extends RuntimeException {
        public a(String str) {
            super(str);
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public String f23423a;

        /* renamed from: a, reason: collision with other field name */
        public boolean f155a;

        /* renamed from: b, reason: collision with root package name */
        public String f23424b;

        /* renamed from: b, reason: collision with other field name */
        public boolean f156b;

        public b(String str, boolean z2, boolean z3, String str2) {
            this.f23423a = str;
            this.f155a = z2;
            this.f156b = z3;
            this.f23424b = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, PackageInfo packageInfo) {
        HashSet hashSet = new HashSet();
        String str = context.getPackageName() + ".permission.MIPUSH_RECEIVE";
        hashSet.addAll(Arrays.asList("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", str, "android.permission.ACCESS_WIFI_STATE", "android.permission.VIBRATE"));
        PermissionInfo[] permissionInfoArr = packageInfo.permissions;
        if (permissionInfoArr != null) {
            for (PermissionInfo permissionInfo : permissionInfoArr) {
                if (str.equals(permissionInfo.name)) {
                    String[] strArr = packageInfo.requestedPermissions;
                    if (strArr != null) {
                        for (String str2 : strArr) {
                            if (!TextUtils.isEmpty(str2) && hashSet.contains(str2)) {
                                hashSet.remove(str2);
                                if (hashSet.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }
                    if (!hashSet.isEmpty()) {
                        throw new a(String.format("<uses-permission android:name=\"%1$s\"/> is missing in AndroidManifest.", hashSet.iterator().next()));
                    }
                    return;
                }
            }
        }
        throw new a(String.format("<permission android:name=\"%1$s\" .../> is undefined in AndroidManifest.", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, PackageInfo packageInfo) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int i2 = 1;
        map2.put(PushMessageHandler.class.getCanonicalName(), new b(PushMessageHandler.class.getCanonicalName(), true, true, ""));
        map2.put(MessageHandleService.class.getCanonicalName(), new b(MessageHandleService.class.getCanonicalName(), true, false, ""));
        if (!MiPushClient.shouldUseMIUIPush(context) || a(packageInfo, new String[]{"com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"})) {
            map2.put("com.xiaomi.push.service.XMJobService", new b("com.xiaomi.push.service.XMJobService", true, false, "android.permission.BIND_JOB_SERVICE"));
            map2.put("com.xiaomi.push.service.XMPushService", new b("com.xiaomi.push.service.XMPushService", true, false, ""));
        }
        if (MiPushClient.getOpenFCMPush(context)) {
            map2.put("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", new b("com.xiaomi.assemble.control.MiFireBaseInstanceIdService", true, false, ""));
            map2.put("com.xiaomi.assemble.control.MiFirebaseMessagingService", new b("com.xiaomi.assemble.control.MiFirebaseMessagingService", true, false, ""));
        }
        if (MiPushClient.getOpenOPPOPush(context)) {
            map2.put("com.xiaomi.assemble.control.COSPushMessageService", new b("com.xiaomi.assemble.control.COSPushMessageService", true, true, "com.coloros.mcs.permission.SEND_MCS_MESSAGE"));
        }
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            int length = serviceInfoArr.length;
            for (int i3 = 0; i3 < length; i3 += i2) {
                ServiceInfo serviceInfo = serviceInfoArr[i3];
                if (!TextUtils.isEmpty(serviceInfo.name) && map2.containsKey(serviceInfo.name)) {
                    b bVar = (b) map2.remove(serviceInfo.name);
                    boolean z2 = bVar.f155a;
                    boolean z3 = bVar.f156b;
                    String str = bVar.f23424b;
                    if (z2 != serviceInfo.enabled) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", serviceInfo.name, Boolean.valueOf(z2)));
                    }
                    if (z3 != serviceInfo.exported) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", serviceInfo.name, Boolean.valueOf(z3)));
                    }
                    if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, serviceInfo.permission)) {
                        throw new a(String.format("<service android:name=\"%1$s\" .../> in AndroidManifest had the wrong permission attribute, which should be android:permission=\"%2$s\".", serviceInfo.name, str));
                    }
                    map.put(serviceInfo.name, serviceInfo.processName);
                    if (map2.isEmpty()) {
                        break;
                    } else {
                        i2 = 1;
                    }
                }
            }
        }
        if (!map2.isEmpty()) {
            throw new a(String.format("<service android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", map2.keySet().iterator().next()));
        }
        if (!TextUtils.equals((CharSequence) map.get(PushMessageHandler.class.getCanonicalName()), (CharSequence) map.get(MessageHandleService.class.getCanonicalName()))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", PushMessageHandler.class.getCanonicalName(), MessageHandleService.class.getCanonicalName()));
        }
        if (map.containsKey("com.xiaomi.push.service.XMJobService") && map.containsKey("com.xiaomi.push.service.XMPushService") && !TextUtils.equals((CharSequence) map.get("com.xiaomi.push.service.XMJobService"), (CharSequence) map.get("com.xiaomi.push.service.XMPushService"))) {
            throw new a(String.format("\"%1$s\" and \"%2$s\" must be running in the same process.", "com.xiaomi.push.service.XMJobService", "com.xiaomi.push.service.XMPushService"));
        }
    }

    public static void a(Context context) {
        new Thread(new w(context)).start();
    }

    private static boolean a(PackageInfo packageInfo, String[] strArr) {
        for (ServiceInfo serviceInfo : packageInfo.services) {
            if (a(strArr, serviceInfo.name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String[] strArr, String str) {
        if (strArr != null && str != null) {
            for (String str2 : strArr) {
                if (TextUtils.equals(str2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ActivityInfo a(PackageManager packageManager, Intent intent, Class<?> cls) {
        Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(intent, 16384).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && cls.getCanonicalName().equals(activityInfo.name)) {
                return activityInfo;
            }
        }
        return null;
    }

    private static void a(ActivityInfo activityInfo, Boolean[] boolArr) {
        if (boolArr[0].booleanValue() == activityInfo.enabled) {
            if (boolArr[1].booleanValue() != activityInfo.exported) {
                throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong exported attribute, which should be android:exported=%2$b.", activityInfo.name, boolArr[1]));
            }
            return;
        }
        throw new a(String.format("<receiver android:name=\"%1$s\" .../> in AndroidManifest had the wrong enabled attribute, which should be android:enabled=%2$b.", activityInfo.name, boolArr[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a3 A[EDGE_INSN: B:45:0x00a3->B:31:0x00a3 BREAK  A[LOOP:0: B:16:0x006f->B:48:0x006f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x006f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r8) {
        /*
            r0 = 2
            r1 = 1
            r2 = 0
            java.lang.String r3 = "com.xiaomi.push.service.receivers.PingReceiver"
            android.content.pm.PackageManager r4 = r8.getPackageManager()
            java.lang.String r5 = r8.getPackageName()
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r7 = com.xiaomi.push.service.bj.f24533q
            r6.<init>(r7)
            r6.setPackage(r5)
            java.lang.Class r7 = com.xiaomi.push.C0472r.a(r8, r3)     // Catch: java.lang.ClassNotFoundException -> L35
            android.content.pm.ActivityInfo r6 = a(r4, r6, r7)     // Catch: java.lang.ClassNotFoundException -> L35
            boolean r7 = com.xiaomi.mipush.sdk.MiPushClient.shouldUseMIUIPush(r8)     // Catch: java.lang.ClassNotFoundException -> L35
            if (r7 != 0) goto L47
            if (r6 == 0) goto L37
            java.lang.Boolean[] r0 = new java.lang.Boolean[r0]     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.Boolean r3 = java.lang.Boolean.TRUE     // Catch: java.lang.ClassNotFoundException -> L35
            r0[r2] = r3     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.Boolean r3 = java.lang.Boolean.FALSE     // Catch: java.lang.ClassNotFoundException -> L35
            r0[r1] = r3     // Catch: java.lang.ClassNotFoundException -> L35
            a(r6, r0)     // Catch: java.lang.ClassNotFoundException -> L35
            goto L5a
        L35:
            r0 = move-exception
            goto L57
        L37:
            com.xiaomi.mipush.sdk.v$a r0 = new com.xiaomi.mipush.sdk.v$a     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.String r6 = "<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest."
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch: java.lang.ClassNotFoundException -> L35
            r7[r2] = r3     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.String r3 = java.lang.String.format(r6, r7)     // Catch: java.lang.ClassNotFoundException -> L35
            r0.<init>(r3)     // Catch: java.lang.ClassNotFoundException -> L35
            throw r0     // Catch: java.lang.ClassNotFoundException -> L35
        L47:
            if (r6 == 0) goto L5a
            java.lang.Boolean[] r0 = new java.lang.Boolean[r0]     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.Boolean r3 = java.lang.Boolean.TRUE     // Catch: java.lang.ClassNotFoundException -> L35
            r0[r2] = r3     // Catch: java.lang.ClassNotFoundException -> L35
            java.lang.Boolean r3 = java.lang.Boolean.FALSE     // Catch: java.lang.ClassNotFoundException -> L35
            r0[r1] = r3     // Catch: java.lang.ClassNotFoundException -> L35
            a(r6, r0)     // Catch: java.lang.ClassNotFoundException -> L35
            goto L5a
        L57:
            com.xiaomi.channel.commonutils.logger.b.a(r0)
        L5a:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r3 = "com.xiaomi.mipush.RECEIVE_MESSAGE"
            r0.<init>(r3)
            r0.setPackage(r5)
            r3 = 16384(0x4000, float:2.2959E-41)
            java.util.List r0 = r4.queryBroadcastReceivers(r0, r3)
            java.util.Iterator r0 = r0.iterator()
            r3 = r2
        L6f:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto La3
            java.lang.Object r4 = r0.next()
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ActivityInfo r4 = r4.activityInfo
            if (r4 == 0) goto La0
            java.lang.String r5 = r4.name     // Catch: java.lang.ClassNotFoundException -> L9b
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.ClassNotFoundException -> L9b
            if (r5 != 0) goto La0
            java.lang.Class<com.xiaomi.mipush.sdk.PushMessageReceiver> r5 = com.xiaomi.mipush.sdk.PushMessageReceiver.class
            java.lang.String r6 = r4.name     // Catch: java.lang.ClassNotFoundException -> L9b
            java.lang.Class r6 = com.xiaomi.push.C0472r.a(r8, r6)     // Catch: java.lang.ClassNotFoundException -> L9b
            boolean r5 = r5.isAssignableFrom(r6)     // Catch: java.lang.ClassNotFoundException -> L9b
            if (r5 == 0) goto La0
            boolean r3 = r4.enabled     // Catch: java.lang.ClassNotFoundException -> L9b
            if (r3 == 0) goto La0
            r3 = r1
            goto La1
        L9b:
            r4 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r4)
            goto L6f
        La0:
            r3 = r2
        La1:
            if (r3 == 0) goto L6f
        La3:
            if (r3 == 0) goto Lc7
            boolean r0 = com.xiaomi.mipush.sdk.MiPushClient.getOpenHmsPush(r8)
            if (r0 == 0) goto Lb9
            java.lang.String r0 = "com.huawei.android.push.intent.RECEIVE"
            java.lang.String r1 = "com.xiaomi.assemble.control.HmsPushReceiver"
            a(r8, r0, r1)
            java.lang.String r0 = "com.huawei.intent.action.PUSH"
            java.lang.String r1 = "com.huawei.hms.support.api.push.PushEventReceiver"
            a(r8, r0, r1)
        Lb9:
            boolean r0 = com.xiaomi.mipush.sdk.MiPushClient.getOpenVIVOPush(r8)
            if (r0 == 0) goto Lc6
            java.lang.String r0 = "com.vivo.pushclient.action.RECEIVE"
            java.lang.String r1 = "com.xiaomi.assemble.control.FTOSPushMessageReceiver"
            a(r8, r0, r1)
        Lc6:
            return
        Lc7:
            com.xiaomi.mipush.sdk.v$a r8 = new com.xiaomi.mipush.sdk.v$a
            java.lang.String r0 = "Receiver: none of the subclasses of PushMessageReceiver is enabled or defined."
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.v.c(android.content.Context):void");
    }

    private static void a(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        Intent intent = new Intent(str);
        intent.setPackage(packageName);
        Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(intent, 16384).iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            z2 = (activityInfo == null || TextUtils.isEmpty(activityInfo.name) || !activityInfo.name.equals(str2)) ? false : true;
            if (z2) {
                break;
            }
        }
        if (!z2) {
            throw new a(String.format("<receiver android:name=\"%1$s\" .../> is missing or disabled in AndroidManifest.", str2));
        }
    }
}
