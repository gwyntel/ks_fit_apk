package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.service.XMJobService;

/* loaded from: classes4.dex */
public final class fu {

    /* renamed from: a, reason: collision with other field name */
    private static a f437a;

    /* renamed from: a, reason: collision with other field name */
    private static final String f438a = XMJobService.class.getCanonicalName();

    /* renamed from: a, reason: collision with root package name */
    private static int f23768a = 0;

    interface a {
        void a();

        void a(boolean z2);

        /* renamed from: a, reason: collision with other method in class */
        boolean mo419a();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x005f A[EDGE_INSN: B:47:0x005f->B:20:0x005f BREAK  A[LOOP:0: B:10:0x002f->B:28:0x0078], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r9) {
        /*
            java.lang.String r0 = "android.permission.BIND_JOB_SERVICE"
            android.content.Context r9 = r9.getApplicationContext()
            java.lang.String r1 = r9.getPackageName()
            java.lang.String r2 = "com.xiaomi.xmsf"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L1b
            com.xiaomi.push.fv r0 = new com.xiaomi.push.fv
            r0.<init>(r9)
            com.xiaomi.push.fu.f437a = r0
            goto Lca
        L1b:
            android.content.pm.PackageManager r1 = r9.getPackageManager()
            r2 = 0
            java.lang.String r3 = r9.getPackageName()     // Catch: java.lang.Exception -> L7b
            r4 = 4
            android.content.pm.PackageInfo r1 = r1.getPackageInfo(r3, r4)     // Catch: java.lang.Exception -> L7b
            android.content.pm.ServiceInfo[] r1 = r1.services     // Catch: java.lang.Exception -> L7b
            if (r1 == 0) goto L94
            int r3 = r1.length     // Catch: java.lang.Exception -> L7b
            r4 = r2
        L2f:
            if (r2 >= r3) goto L5f
            r5 = r1[r2]     // Catch: java.lang.Exception -> L61
            java.lang.String r6 = r5.permission     // Catch: java.lang.Exception -> L61
            boolean r6 = r0.equals(r6)     // Catch: java.lang.Exception -> L61
            r7 = 1
            if (r6 == 0) goto L64
            java.lang.String r6 = com.xiaomi.push.fu.f438a     // Catch: java.lang.Exception -> L61
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L61
            boolean r8 = r6.equals(r8)     // Catch: java.lang.Exception -> L61
            if (r8 == 0) goto L48
        L46:
            r4 = r7
            goto L5d
        L48:
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L5d
            java.lang.Class r8 = com.xiaomi.push.C0472r.a(r9, r8)     // Catch: java.lang.Exception -> L5d
            java.lang.Class r8 = r8.getSuperclass()     // Catch: java.lang.Exception -> L5d
            java.lang.String r8 = r8.getCanonicalName()     // Catch: java.lang.Exception -> L5d
            boolean r6 = r6.equals(r8)     // Catch: java.lang.Exception -> L5d
            if (r6 == 0) goto L5d
            goto L46
        L5d:
            if (r4 != r7) goto L64
        L5f:
            r2 = r4
            goto L94
        L61:
            r1 = move-exception
            r2 = r4
            goto L7c
        L64:
            java.lang.String r6 = com.xiaomi.push.fu.f438a     // Catch: java.lang.Exception -> L61
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L61
            boolean r6 = r6.equals(r8)     // Catch: java.lang.Exception -> L61
            if (r6 == 0) goto L78
            java.lang.String r5 = r5.permission     // Catch: java.lang.Exception -> L61
            boolean r5 = r0.equals(r5)     // Catch: java.lang.Exception -> L61
            if (r5 == 0) goto L78
            r2 = r7
            goto L94
        L78:
            int r2 = r2 + 1
            goto L2f
        L7b:
            r1 = move-exception
        L7c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "check service err : "
            r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            com.xiaomi.channel.commonutils.logger.b.m91a(r1)
        L94:
            if (r2 != 0) goto Lc3
            boolean r1 = com.xiaomi.push.C0472r.m687a(r9)
            if (r1 != 0) goto L9d
            goto Lc3
        L9d:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Should export service: "
            r1.append(r2)
            java.lang.String r2 = com.xiaomi.push.fu.f438a
            r1.append(r2)
            java.lang.String r2 = " with permission "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = " in AndroidManifest.xml file"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r9.<init>(r0)
            throw r9
        Lc3:
            com.xiaomi.push.fv r0 = new com.xiaomi.push.fv
            r0.<init>(r9)
            com.xiaomi.push.fu.f437a = r0
        Lca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.fu.a(android.content.Context):void");
    }

    public static synchronized void a(Context context, int i2) {
        try {
            int i3 = f23768a;
            if (!"com.xiaomi.xmsf".equals(context.getPackageName())) {
                if (i2 == 2) {
                    f23768a = 2;
                } else {
                    f23768a = 0;
                }
            }
            int i4 = f23768a;
            if (i3 != i4 && i4 == 2) {
                a();
                f437a = new fx(context);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void a(boolean z2) {
        if (f437a == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("timer is not initialized");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] register alarm. (" + z2 + ")");
        f437a.a(z2);
    }

    public static synchronized void a() {
        if (f437a == null) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] stop alarm.");
        f437a.a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized boolean m418a() {
        a aVar = f437a;
        if (aVar == null) {
            return false;
        }
        return aVar.mo419a();
    }
}
