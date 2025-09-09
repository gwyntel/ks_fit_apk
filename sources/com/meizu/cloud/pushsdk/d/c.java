package com.meizu.cloud.pushsdk.d;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static String f19208a;

    /* renamed from: b, reason: collision with root package name */
    private static String f19209b;

    public static String a(Context context) {
        if (!TextUtils.isEmpty(f19208a)) {
            return f19208a;
        }
        String strC = c(context);
        f19208a = strC;
        if (!TextUtils.isEmpty(strC)) {
            return f19208a;
        }
        try {
            f19208a = (String) Class.forName("com.meizu.cloud.utils.AppDeviceUtils").getDeclaredMethod("getId", Context.class).invoke(null, context);
        } catch (Exception e2) {
            DebugLogger.e("DeviceUtils", "getDeviceId error " + e2.getMessage());
            f19208a = "";
        }
        if (f19208a == null) {
            f19208a = "";
        }
        return f19208a;
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(f19209b)) {
            return f19209b;
        }
        try {
            f19209b = (String) Class.forName("com.meizu.cloud.utils.AppDeviceUtils").getDeclaredMethod("getFdId", Context.class).invoke(null, context);
        } catch (Exception e2) {
            DebugLogger.e("DeviceUtils", "getFdId error " + e2.getMessage());
            f19209b = "";
        }
        if (f19209b == null) {
            f19209b = "";
        }
        return f19209b;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.SuppressLint({"MissingPermission", "HardwareIds"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String c(android.content.Context r4) {
        /*
            java.lang.String r0 = "android.telephony.MzTelephonyManager"
            com.meizu.cloud.pushsdk.d.l.a r0 = com.meizu.cloud.pushsdk.d.l.a.a(r0)     // Catch: java.lang.Exception -> L28
            java.lang.String r1 = "getDeviceId"
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch: java.lang.Exception -> L28
            com.meizu.cloud.pushsdk.d.l.c r0 = r0.a(r1, r3)     // Catch: java.lang.Exception -> L28
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch: java.lang.Exception -> L28
            com.meizu.cloud.pushsdk.d.l.d r0 = r0.a(r1)     // Catch: java.lang.Exception -> L28
            boolean r1 = r0.f19257a     // Catch: java.lang.Exception -> L28
            if (r1 == 0) goto L2a
            T r1 = r0.f19258b     // Catch: java.lang.Exception -> L28
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch: java.lang.Exception -> L28
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Exception -> L28
            if (r1 != 0) goto L2a
            T r4 = r0.f19258b     // Catch: java.lang.Exception -> L28
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Exception -> L28
            goto L54
        L28:
            r4 = move-exception
            goto L39
        L2a:
            java.lang.String r0 = "phone"
            java.lang.Object r4 = r4.getSystemService(r0)     // Catch: java.lang.Exception -> L28
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4     // Catch: java.lang.Exception -> L28
            if (r4 == 0) goto L53
            java.lang.String r4 = r4.getDeviceId()     // Catch: java.lang.Exception -> L28
            goto L54
        L39:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "getDeviceId error "
            r0.append(r1)
            java.lang.String r4 = r4.getMessage()
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            java.lang.String r0 = "DeviceUtils"
            com.meizu.cloud.pushinternal.DebugLogger.e(r0, r4)
        L53:
            r4 = 0
        L54:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.d.c.c(android.content.Context):java.lang.String");
    }
}
