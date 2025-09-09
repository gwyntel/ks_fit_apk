package com.alipay.sdk.m.i0;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: g, reason: collision with root package name */
    public static volatile f f9305g = null;

    /* renamed from: h, reason: collision with root package name */
    public static boolean f9306h = false;

    /* renamed from: f, reason: collision with root package name */
    public BroadcastReceiver f9312f;

    /* renamed from: a, reason: collision with root package name */
    public a f9307a = new a("udid");

    /* renamed from: b, reason: collision with root package name */
    public a f9308b = new a("oaid");

    /* renamed from: d, reason: collision with root package name */
    public a f9310d = new a("vaid");

    /* renamed from: c, reason: collision with root package name */
    public a f9309c = new a("aaid");

    /* renamed from: e, reason: collision with root package name */
    public c f9311e = new c();

    public static d a(Cursor cursor) {
        String str;
        d dVar = new d(null, 0);
        if (cursor == null) {
            str = "parseValue fail, cursor is null.";
        } else {
            if (!cursor.isClosed()) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex("value");
                if (columnIndex >= 0) {
                    dVar.f9302a = cursor.getString(columnIndex);
                } else {
                    a("parseValue fail, index < 0.");
                }
                int columnIndex2 = cursor.getColumnIndex("code");
                if (columnIndex2 >= 0) {
                    dVar.f9303b = cursor.getInt(columnIndex2);
                } else {
                    a("parseCode fail, index < 0.");
                }
                int columnIndex3 = cursor.getColumnIndex("expired");
                if (columnIndex3 >= 0) {
                    dVar.f9304c = cursor.getLong(columnIndex3);
                } else {
                    a("parseExpired fail, index < 0.");
                }
                return dVar;
            }
            str = "parseValue fail, cursor is closed.";
        }
        a(str);
        return dVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(android.content.Context r8, com.alipay.sdk.m.i0.a r9) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "queryId : "
            r0.<init>(r1)
            java.lang.String r1 = r9.f9296c
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            a(r0)
            java.lang.String r0 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r2 = android.net.Uri.parse(r0)
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            java.lang.String r3 = r9.f9296c     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            java.lang.String[] r5 = new java.lang.String[]{r3}     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            r6 = 0
            r3 = 0
            r4 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L82
            com.alipay.sdk.m.i0.d r4 = a(r1)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r0 = r4.f9302a     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r9.a(r0)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            long r5 = r4.f9304c     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r9.a(r5)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            int r5 = r4.f9303b     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r9.a(r5)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r5.<init>()     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r6 = r9.f9296c     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r5.append(r6)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r6 = " errorCode : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            int r9 = r9.f9297d     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r5.append(r9)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r9 = r5.toString()     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            a(r9)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            int r9 = r4.f9303b     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r9 == r4) goto L96
            r7.b(r8)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            boolean r9 = r7.a(r8, r3)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            if (r9 != 0) goto L96
            boolean r8 = r7.a(r8, r2)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r9 = "not support, forceQuery isSupported: "
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
        L76:
            java.lang.String r8 = r9.concat(r8)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            goto L93
        L7b:
            r8 = move-exception
            r0 = r1
            goto Lbc
        L7e:
            r8 = move-exception
            r9 = r0
            r0 = r1
            goto L9d
        L82:
            boolean r9 = r7.a(r8, r3)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            if (r9 == 0) goto L96
            boolean r8 = r7.a(r8, r2)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.lang.String r9 = "forceQuery isSupported : "
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            goto L76
        L93:
            a(r8)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
        L96:
            if (r1 == 0) goto Lbb
            goto Lb6
        L99:
            r8 = move-exception
            goto Lbc
        L9b:
            r8 = move-exception
            r9 = r0
        L9d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L99
            java.lang.String r2 = "queryId, Exception : "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L99
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L99
            r1.append(r8)     // Catch: java.lang.Throwable -> L99
            java.lang.String r8 = r1.toString()     // Catch: java.lang.Throwable -> L99
            a(r8)     // Catch: java.lang.Throwable -> L99
            if (r0 == 0) goto Lba
            r1 = r0
            r0 = r9
        Lb6:
            r1.close()
            goto Lbb
        Lba:
            r0 = r9
        Lbb:
            return r0
        Lbc:
            if (r0 == 0) goto Lc1
            r0.close()
        Lc1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.i0.f.b(android.content.Context, com.alipay.sdk.m.i0.a):java.lang.String");
    }

    public static final f a() {
        if (f9305g == null) {
            synchronized (f.class) {
                try {
                    if (f9305g == null) {
                        f9305g = new f();
                    }
                } finally {
                }
            }
        }
        return f9305g;
    }

    public static String b(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            a("getAppVersion, Exception : " + e2.getMessage());
            return null;
        }
    }

    private synchronized void b(Context context) {
        if (this.f9312f != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.meizu.flyme.openid.ACTION_OPEN_ID_CHANGE");
        e eVar = new e();
        this.f9312f = eVar;
        context.registerReceiver(eVar, intentFilter, "com.meizu.flyme.openid.permission.OPEN_ID_CHANGE", null);
    }

    public final String a(Context context, a aVar) {
        String str;
        if (aVar == null) {
            str = "getId, openId = null.";
        } else {
            if (aVar.a()) {
                return aVar.f9295b;
            }
            if (a(context, true)) {
                return b(context, aVar);
            }
            str = "getId, isSupported = false.";
        }
        a(str);
        return null;
    }

    public static String a(PackageManager packageManager, String str) {
        ProviderInfo providerInfoResolveContentProvider;
        if (packageManager == null || (providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0)) == null || (providerInfoResolveContentProvider.applicationInfo.flags & 1) == 0) {
            return null;
        }
        return providerInfoResolveContentProvider.packageName;
    }

    public static void a(String str) {
        if (f9306h) {
            Log.d("OpenIdManager", str);
        }
    }

    public static void a(boolean z2) {
        f9306h = z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(android.content.Context r8) {
        /*
            java.lang.String r0 = "querySupport version : 1.0.8"
            a(r0)
            java.lang.String r0 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r2 = android.net.Uri.parse(r0)
            r0 = 0
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            java.lang.String r8 = "supported"
            java.lang.String[] r5 = new java.lang.String[]{r8}     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r6 = 0
            r3 = 0
            r4 = 0
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            if (r7 == 0) goto L3e
            com.alipay.sdk.m.i0.d r8 = a(r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            int r1 = r8.f9303b     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r2 != r1) goto L39
            java.lang.String r1 = "0"
            java.lang.String r8 = r8.f9302a     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            boolean r8 = r1.equals(r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L37
            if (r8 == 0) goto L3a
            goto L39
        L35:
            r8 = move-exception
            goto L5c
        L37:
            r8 = move-exception
            goto L41
        L39:
            r0 = 1
        L3a:
            r7.close()
            return r0
        L3e:
            if (r7 == 0) goto L5b
            goto L58
        L41:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
            java.lang.String r2 = "querySupport, Exception : "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L35
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L35
            r1.append(r8)     // Catch: java.lang.Throwable -> L35
            java.lang.String r8 = r1.toString()     // Catch: java.lang.Throwable -> L35
            a(r8)     // Catch: java.lang.Throwable -> L35
            if (r7 == 0) goto L5b
        L58:
            r7.close()
        L5b:
            return r0
        L5c:
            if (r7 == 0) goto L61
            r7.close()
        L61:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.i0.f.a(android.content.Context):boolean");
    }

    public final boolean a(Context context, boolean z2) throws PackageManager.NameNotFoundException {
        if (this.f9311e.a() && !z2) {
            return this.f9311e.b();
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        String strA = a(packageManager, "com.meizu.flyme.openidsdk");
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        String strB = b(packageManager, strA);
        if (this.f9311e.a() && this.f9311e.a(strB)) {
            a("use same version cache, safeVersion : ".concat(String.valueOf(strB)));
            return this.f9311e.b();
        }
        this.f9311e.b(strB);
        boolean zA = a(context);
        a("query support, result : ".concat(String.valueOf(zA)));
        this.f9311e.a(zA);
        return zA;
    }
}
