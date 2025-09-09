package com.xiaomi.push;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;

/* loaded from: classes4.dex */
class bc implements ar {

    /* renamed from: a, reason: collision with root package name */
    private static String f23480a = "content://com.vivo.vms.IdProvider/IdentifierId/";

    /* renamed from: b, reason: collision with root package name */
    private static String f23481b = f23480a + "OAID";

    /* renamed from: c, reason: collision with root package name */
    private static String f23482c = f23480a + "VAID_";

    /* renamed from: d, reason: collision with root package name */
    private static String f23483d = f23480a + "AAID_";

    /* renamed from: e, reason: collision with root package name */
    private static String f23484e = f23480a + com.alipay.sdk.m.p0.b.f9639h;

    /* renamed from: f, reason: collision with root package name */
    private static String f23485f = com.alipay.sdk.m.p0.c.f9647c;

    /* renamed from: a, reason: collision with other field name */
    private Context f206a;

    public bc(Context context) {
        this.f206a = context;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public boolean mo180a() {
        return "1".equals(q.a(f23485f, "0"));
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public String mo179a() {
        return a(f23481b);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002a A[PHI: r0 r10
      0x002a: PHI (r0v5 java.lang.String) = (r0v0 java.lang.String), (r0v9 java.lang.String) binds: [B:19:0x003b, B:11:0x0028] A[DONT_GENERATE, DONT_INLINE]
      0x002a: PHI (r10v6 android.database.Cursor) = (r10v5 android.database.Cursor), (r10v7 android.database.Cursor) binds: [B:19:0x003b, B:11:0x0028] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r10) throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            android.content.Context r1 = r9.f206a     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L33
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L33
            android.net.Uri r3 = android.net.Uri.parse(r10)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L33
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L33
            if (r10 == 0) goto L28
            boolean r1 = r10.moveToNext()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L3b
            if (r1 == 0) goto L28
            java.lang.String r1 = "value"
            int r1 = r10.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L3b
            java.lang.String r0 = r10.getString(r1)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L3b
            goto L28
        L26:
            r0 = move-exception
            goto L35
        L28:
            if (r10 == 0) goto L3e
        L2a:
            r10.close()
            goto L3e
        L2e:
            r10 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto L35
        L33:
            r10 = r0
            goto L3b
        L35:
            if (r10 == 0) goto L3a
            r10.close()
        L3a:
            throw r0
        L3b:
            if (r10 == 0) goto L3e
            goto L2a
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.bc.a(java.lang.String):java.lang.String");
    }

    public static boolean a(Context context) {
        try {
            ProviderInfo providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(Uri.parse(f23480a).getAuthority(), 128);
            if (providerInfoResolveContentProvider != null) {
                return (providerInfoResolveContentProvider.applicationInfo.flags & 1) != 0;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
