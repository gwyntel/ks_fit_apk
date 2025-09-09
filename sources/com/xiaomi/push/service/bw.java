package com.xiaomi.push.service;

import android.content.SharedPreferences;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.ak;
import com.xiaomi.push.ew;
import com.xiaomi.push.ex;
import com.xiaomi.push.ie;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class bw {

    /* renamed from: a, reason: collision with root package name */
    private static bw f24564a = new bw();

    /* renamed from: a, reason: collision with other field name */
    private static String f1057a;

    /* renamed from: a, reason: collision with other field name */
    private ak.b f1058a;

    /* renamed from: a, reason: collision with other field name */
    private ew.a f1059a;

    /* renamed from: a, reason: collision with other field name */
    private List<a> f1060a = new ArrayList();

    public static abstract class a {
        public void a(ew.a aVar) {
        }

        public void a(ex.b bVar) {
        }
    }

    private bw() {
    }

    private void b() throws Throwable {
        if (this.f1059a == null) {
            d();
        }
    }

    private void c() {
        if (this.f1058a != null) {
            return;
        }
        bx bxVar = new bx(this);
        this.f1058a = bxVar;
        ie.a(bxVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void d() throws java.lang.Throwable {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.xiaomi.push.C0472r.m684a()     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L29
            java.lang.String r2 = "XMCloudCfg"
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L29
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L29
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L29
            com.xiaomi.push.b r0 = com.xiaomi.push.b.a(r2)     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L23
            com.xiaomi.push.ew$a r0 = com.xiaomi.push.ew.a.b(r0)     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L23
            r4.f1059a = r0     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L23
            r2.close()     // Catch: java.lang.Throwable -> L21 java.lang.Exception -> L23
        L1d:
            com.xiaomi.push.x.a(r2)
            goto L45
        L21:
            r0 = move-exception
            goto L51
        L23:
            r0 = move-exception
            goto L2c
        L25:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L51
        L29:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L2c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L21
            r1.<init>()     // Catch: java.lang.Throwable -> L21
            java.lang.String r3 = "load config failure: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L21
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L21
            r1.append(r0)     // Catch: java.lang.Throwable -> L21
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L21
            com.xiaomi.channel.commonutils.logger.b.m91a(r0)     // Catch: java.lang.Throwable -> L21
            goto L1d
        L45:
            com.xiaomi.push.ew$a r0 = r4.f1059a
            if (r0 != 0) goto L50
            com.xiaomi.push.ew$a r0 = new com.xiaomi.push.ew$a
            r0.<init>()
            r4.f1059a = r0
        L50:
            return
        L51:
            com.xiaomi.push.x.a(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.bw.d():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() throws IOException {
        try {
            if (this.f1059a != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(C0472r.m684a().openFileOutput("XMCloudCfg", 0));
                com.xiaomi.push.c cVarA = com.xiaomi.push.c.a(bufferedOutputStream);
                this.f1059a.a(cVarA);
                cVarA.m225a();
                bufferedOutputStream.close();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("save config failure: " + e2.getMessage());
        }
    }

    public static bw a() {
        return f24564a;
    }

    public synchronized void a(a aVar) {
        this.f1060a.add(aVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    synchronized void m772a() {
        this.f1060a.clear();
    }

    /* renamed from: a, reason: collision with other method in class */
    int m770a() throws Throwable {
        b();
        ew.a aVar = this.f1059a;
        if (aVar != null) {
            return aVar.c();
        }
        return 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public ew.a m771a() throws Throwable {
        b();
        return this.f1059a;
    }

    void a(ex.b bVar) {
        a[] aVarArr;
        if (bVar.m343d() && bVar.d() > m770a()) {
            c();
        }
        synchronized (this) {
            List<a> list = this.f1060a;
            aVarArr = (a[]) list.toArray(new a[list.size()]);
        }
        for (a aVar : aVarArr) {
            aVar.a(bVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized String m767a() {
        try {
            if (f1057a == null) {
                SharedPreferences sharedPreferences = C0472r.m684a().getSharedPreferences("XMPushServiceConfig", 0);
                String string = sharedPreferences.getString("DeviceUUID", null);
                f1057a = string;
                if (string == null) {
                    String strA = com.xiaomi.push.i.a(C0472r.m684a(), false);
                    f1057a = strA;
                    if (strA != null) {
                        sharedPreferences.edit().putString("DeviceUUID", f1057a).commit();
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return f1057a;
    }
}
