package com.alipay.sdk.m.p0;

import android.content.Context;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    public static final String f9633b = "VMS_IDLG_SDK_DB";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9634c = "content://com.vivo.vms.IdProvider/IdentifierId";

    /* renamed from: d, reason: collision with root package name */
    public static final String f9635d = "value";

    /* renamed from: e, reason: collision with root package name */
    public static final String f9636e = "OAID";

    /* renamed from: f, reason: collision with root package name */
    public static final String f9637f = "AAID";

    /* renamed from: g, reason: collision with root package name */
    public static final String f9638g = "VAID";

    /* renamed from: h, reason: collision with root package name */
    public static final String f9639h = "OAIDSTATUS";

    /* renamed from: i, reason: collision with root package name */
    public static final int f9640i = 0;

    /* renamed from: j, reason: collision with root package name */
    public static final int f9641j = 1;

    /* renamed from: k, reason: collision with root package name */
    public static final int f9642k = 2;

    /* renamed from: l, reason: collision with root package name */
    public static final int f9643l = 4;

    /* renamed from: a, reason: collision with root package name */
    public Context f9644a;

    public b(Context context) {
        this.f9644a = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(int r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 == 0) goto L42
            r1 = 1
            if (r8 == r1) goto L2c
            r1 = 2
            if (r8 == r1) goto L16
            r9 = 4
            if (r8 == r9) goto Le
            r2 = r0
            goto L49
        Le:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAIDSTATUS"
            android.net.Uri r8 = android.net.Uri.parse(r8)
        L14:
            r2 = r8
            goto L49
        L16:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/AAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L14
        L2c:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/VAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L14
        L42:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L14
        L49:
            android.content.Context r8 = r7.f9644a
            android.content.ContentResolver r1 = r8.getContentResolver()
            r5 = 0
            r6 = 0
            r3 = 0
            r4 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)
            if (r8 == 0) goto L6d
            boolean r9 = r8.moveToNext()
            if (r9 == 0) goto L69
            java.lang.String r9 = "value"
            int r9 = r8.getColumnIndex(r9)
            java.lang.String r0 = r8.getString(r9)
        L69:
            r8.close()
            goto L74
        L6d:
            java.lang.String r8 = "VMS_IDLG_SDK_DB"
            java.lang.String r9 = "return cursor is null,return"
            android.util.Log.d(r8, r9)
        L74:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.p0.b.a(int, java.lang.String):java.lang.String");
    }
}
