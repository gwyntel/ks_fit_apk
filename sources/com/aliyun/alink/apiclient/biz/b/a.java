package com.aliyun.alink.apiclient.biz.b;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/* loaded from: classes2.dex */
public class a {
    public static String a() {
        String str = "64" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10);
        String str2 = Build.SERIAL;
        if (TextUtils.isEmpty(str2)) {
            str2 = "serial";
        }
        return new UUID(str.hashCode(), str2.hashCode()).toString();
    }

    public static String b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str = Build.VERSION.RELEASE;
        ALog.d("ApiClient-PhoneUtils", "getSystemVersion=" + str);
        return str;
    }

    public static String b(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (String) telephonyManager.getClass().getMethod("getImei", null).invoke(telephonyManager, null);
        } catch (SecurityException e2) {
            ALog.w("ApiClient-PhoneUtils", "SecurityException " + e2);
            return null;
        } catch (Exception e3) {
            ALog.w("ApiClient-PhoneUtils", "Exception " + e3);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r4) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            java.lang.String r4 = b(r4)
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            java.lang.String r1 = "ApiClient-PhoneUtils"
            if (r0 == 0) goto L15
            java.lang.String r4 = "to get getPseudoUniqueID"
            com.aliyun.alink.linksdk.tools.ALog.d(r1, r4)
            java.lang.String r4 = a()
        L15:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L42
            java.lang.String r4 = "to get default, should not be here."
            com.aliyun.alink.linksdk.tools.ALog.d(r1, r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r0 = "default_"
            r4.append(r0)
            java.lang.String r0 = android.os.Build.MODEL
            r4.append(r0)
            java.lang.String r0 = android.os.Build.BRAND
            r4.append(r0)
            java.lang.String r0 = android.os.Build.MANUFACTURER
            r4.append(r0)
            java.lang.String r0 = android.os.Build.BOARD
            r4.append(r0)
            java.lang.String r4 = r4.toString()
        L42:
            java.lang.String r0 = "sha256"
            java.lang.String r0 = com.aliyun.alink.apiclient.biz.b.b.a(r4, r0)     // Catch: java.lang.Exception -> L49 java.io.UnsupportedEncodingException -> L4b java.security.NoSuchAlgorithmException -> L4d
            goto L8e
        L49:
            r0 = move-exception
            goto L4f
        L4b:
            r0 = move-exception
            goto L64
        L4d:
            r0 = move-exception
            goto L79
        L4f:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "exception "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.aliyun.alink.linksdk.tools.ALog.w(r1, r0)
            goto L8d
        L64:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "UnsupportedEncodingException "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.aliyun.alink.linksdk.tools.ALog.w(r1, r0)
            goto L8d
        L79:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "NoSuchAlgorithmException "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.aliyun.alink.linksdk.tools.ALog.w(r1, r0)
        L8d:
            r0 = 0
        L8e:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L95
            r0 = r4
        L95:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "origin="
            r2.append(r3)
            r2.append(r4)
            java.lang.String r4 = ", sha="
            r2.append(r4)
            r2.append(r0)
            java.lang.String r4 = r2.toString()
            com.aliyun.alink.linksdk.tools.ALog.d(r1, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.apiclient.biz.b.a.a(android.content.Context):java.lang.String");
    }
}
