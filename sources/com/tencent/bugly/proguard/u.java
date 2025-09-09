package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class u {

    /* renamed from: a, reason: collision with root package name */
    public static final long f21152a = System.currentTimeMillis();

    /* renamed from: b, reason: collision with root package name */
    private static u f21153b;

    /* renamed from: c, reason: collision with root package name */
    private Context f21154c;

    /* renamed from: f, reason: collision with root package name */
    private SharedPreferences f21157f;

    /* renamed from: e, reason: collision with root package name */
    private Map<Integer, Map<String, t>> f21156e = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private String f21155d = aa.b().f20679d;

    private u(Context context) {
        this.f21154c = context;
        this.f21157f = context.getSharedPreferences("crashrecord", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0042 A[Catch: all -> 0x0031, Exception -> 0x0057, PHI: r6
      0x0042: PHI (r6v10 java.io.ObjectInputStream) = (r6v9 java.io.ObjectInputStream), (r6v11 java.io.ObjectInputStream) binds: [B:21:0x0040, B:26:0x004e] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x0057, blocks: (B:4:0x0003, B:10:0x002c, B:29:0x0053, B:30:0x0056, B:22:0x0042), top: B:38:0x0003, outer: #0 }] */
    /* JADX WARN: Type inference failed for: r6v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized <T extends java.util.List<?>> T d(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            android.content.Context r3 = r5.f21154c     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            java.lang.String r4 = "crashrecord"
            java.io.File r3 = r3.getDir(r4, r1)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            r2.<init>(r3, r6)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            boolean r6 = r2.exists()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            if (r6 != 0) goto L1c
            monitor-exit(r5)
            return r0
        L1c:
            java.io.ObjectInputStream r6 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L35 java.lang.ClassNotFoundException -> L38 java.io.IOException -> L46
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L35 java.lang.ClassNotFoundException -> L38 java.io.IOException -> L46
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L35 java.lang.ClassNotFoundException -> L38 java.io.IOException -> L46
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L35 java.lang.ClassNotFoundException -> L38 java.io.IOException -> L46
            java.lang.Object r2 = r6.readObject()     // Catch: java.lang.Throwable -> L33 java.lang.ClassNotFoundException -> L39 java.io.IOException -> L47
            java.util.List r2 = (java.util.List) r2     // Catch: java.lang.Throwable -> L33 java.lang.ClassNotFoundException -> L39 java.io.IOException -> L47
            r6.close()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            monitor-exit(r5)
            return r2
        L31:
            r6 = move-exception
            goto L60
        L33:
            r2 = move-exception
            goto L51
        L35:
            r2 = move-exception
            r6 = r0
            goto L51
        L38:
            r6 = r0
        L39:
            java.lang.String r2 = "get object error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L33
            com.tencent.bugly.proguard.al.a(r2, r3)     // Catch: java.lang.Throwable -> L33
            if (r6 == 0) goto L5e
        L42:
            r6.close()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
            goto L5e
        L46:
            r6 = r0
        L47:
            java.lang.String r2 = "open record file error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L33
            com.tencent.bugly.proguard.al.a(r2, r3)     // Catch: java.lang.Throwable -> L33
            if (r6 == 0) goto L5e
            goto L42
        L51:
            if (r6 == 0) goto L56
            r6.close()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
        L56:
            throw r2     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L57
        L57:
            java.lang.String r6 = "readCrashRecord error"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L31
            com.tencent.bugly.proguard.al.e(r6, r1)     // Catch: java.lang.Throwable -> L31
        L5e:
            monitor-exit(r5)
            return r0
        L60:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L31
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.d(int):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean c(int i2) {
        try {
            try {
                List<t> listD = d(i2);
                if (listD == null) {
                    return false;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (t tVar : listD) {
                    String str = tVar.f21146b;
                    if (str != null && str.equalsIgnoreCase(this.f21155d) && tVar.f21148d > 0) {
                        arrayList.add(tVar);
                    }
                    if (tVar.f21147c + 86400000 < jCurrentTimeMillis) {
                        arrayList2.add(tVar);
                    }
                }
                Collections.sort(arrayList);
                if (arrayList.size() < 2) {
                    listD.removeAll(arrayList2);
                    a(i2, (int) listD);
                    return false;
                }
                if (arrayList.size() <= 0 || ((t) arrayList.get(arrayList.size() - 1)).f21147c + 86400000 >= jCurrentTimeMillis) {
                    return true;
                }
                listD.clear();
                a(i2, (int) listD);
                return false;
            } catch (Exception unused) {
                al.e("isFrequentCrash failed", new Object[0]);
                return false;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized boolean b(final int i2) {
        boolean z2;
        z2 = true;
        try {
            z2 = this.f21157f.getBoolean(i2 + OpenAccountUIConstants.UNDER_LINE + this.f21155d, true);
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.u.2
                @Override // java.lang.Runnable
                public final void run() {
                    boolean zC = u.this.c(i2);
                    u.this.f21157f.edit().putBoolean(i2 + OpenAccountUIConstants.UNDER_LINE + u.this.f21155d, !zC).commit();
                }
            });
        } catch (Exception unused) {
            al.e("canInit error", new Object[0]);
            return z2;
        }
        return z2;
    }

    public static synchronized u a(Context context) {
        try {
            if (f21153b == null) {
                f21153b = new u(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f21153b;
    }

    public static synchronized u a() {
        return f21153b;
    }

    static /* synthetic */ boolean b(t tVar, t tVar2) {
        String str = tVar.f21149e;
        if (str != null && !str.equalsIgnoreCase(tVar2.f21149e)) {
            return true;
        }
        String str2 = tVar.f21150f;
        return !(str2 == null || str2.equalsIgnoreCase(tVar2.f21150f)) || tVar.f21148d <= 0;
    }

    public final void a(final int i2) {
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.u.1

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ int f21158a = 1004;

            @Override // java.lang.Runnable
            public final void run() {
                t tVar;
                try {
                    if (TextUtils.isEmpty(u.this.f21155d)) {
                        return;
                    }
                    List<t> listD = u.this.d(this.f21158a);
                    if (listD == null) {
                        listD = new ArrayList();
                    }
                    if (u.this.f21156e.get(Integer.valueOf(this.f21158a)) == null) {
                        u.this.f21156e.put(Integer.valueOf(this.f21158a), new HashMap());
                    }
                    if (((Map) u.this.f21156e.get(Integer.valueOf(this.f21158a))).get(u.this.f21155d) == null) {
                        tVar = new t();
                        tVar.f21145a = this.f21158a;
                        tVar.f21151g = u.f21152a;
                        tVar.f21146b = u.this.f21155d;
                        tVar.f21150f = aa.b().f20690o;
                        tVar.f21149e = aa.b().f20683h;
                        tVar.f21147c = System.currentTimeMillis();
                        tVar.f21148d = i2;
                        ((Map) u.this.f21156e.get(Integer.valueOf(this.f21158a))).put(u.this.f21155d, tVar);
                    } else {
                        tVar = (t) ((Map) u.this.f21156e.get(Integer.valueOf(this.f21158a))).get(u.this.f21155d);
                        tVar.f21148d = i2;
                    }
                    ArrayList arrayList = new ArrayList();
                    boolean z2 = false;
                    for (t tVar2 : listD) {
                        if (u.a(tVar2, tVar)) {
                            tVar2.f21148d = tVar.f21148d;
                            z2 = true;
                        }
                        if (u.b(tVar2, tVar)) {
                            arrayList.add(tVar2);
                        }
                    }
                    listD.removeAll(arrayList);
                    if (!z2) {
                        listD.add(tVar);
                    }
                    u.this.a(this.f21158a, (int) listD);
                } catch (Exception unused) {
                    al.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x004c A[Catch: all -> 0x002a, Exception -> 0x0050, TRY_ENTER, TryCatch #1 {Exception -> 0x0050, blocks: (B:7:0x0006, B:11:0x0025, B:32:0x004c, B:33:0x004f, B:26:0x0043), top: B:39:0x0006, outer: #3 }] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.ObjectOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized <T extends java.util.List<?>> void a(int r5, T r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 != 0) goto L5
            monitor-exit(r4)
            return
        L5:
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            android.content.Context r2 = r4.f21154c     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            java.lang.String r3 = "crashrecord"
            java.io.File r2 = r2.getDir(r3, r0)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            r1.<init>(r2, r5)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            r5 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L34
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L34
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L34
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L34
            r2.writeObject(r6)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L2e
            r2.close()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            monitor-exit(r4)
            return
        L2a:
            r5 = move-exception
            goto L59
        L2c:
            r5 = move-exception
            goto L4a
        L2e:
            r5 = move-exception
            goto L37
        L30:
            r6 = move-exception
            r2 = r5
            r5 = r6
            goto L4a
        L34:
            r6 = move-exception
            r2 = r5
            r5 = r6
        L37:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L2c
            java.lang.String r5 = "open record file error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L2c
            com.tencent.bugly.proguard.al.a(r5, r6)     // Catch: java.lang.Throwable -> L2c
            if (r2 == 0) goto L48
            r2.close()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
            monitor-exit(r4)
            return
        L48:
            monitor-exit(r4)
            return
        L4a:
            if (r2 == 0) goto L4f
            r2.close()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
        L4f:
            throw r5     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L50
        L50:
            java.lang.String r5 = "writeCrashRecord error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L2a
            com.tencent.bugly.proguard.al.e(r5, r6)     // Catch: java.lang.Throwable -> L2a
            monitor-exit(r4)
            return
        L59:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L2a
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.a(int, java.util.List):void");
    }

    static /* synthetic */ boolean a(t tVar, t tVar2) {
        String str;
        return tVar.f21151g == tVar2.f21151g && (str = tVar.f21146b) != null && str.equalsIgnoreCase(tVar2.f21146b);
    }
}
