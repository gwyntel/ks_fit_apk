package com.alibaba.sdk.android.openaccount.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.umeng.analytics.pro.bc;

/* loaded from: classes2.dex */
public final class TraceHelper {
    private static final String DEFAULT_CHANNEL = "0";
    private static final String TAG = "TraceHelper";
    public static String channel = "0";
    public static String clientTTID = null;
    public static int ttidVersion = 2;
    public static String webTTID;

    private static boolean checkChannel(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'a' || cCharAt > 'z') && (cCharAt < 'A' || cCharAt > 'Z'))) {
                return false;
            }
        }
        return true;
    }

    private static String getChannel(Context context) {
        String property;
        try {
            property = OpenAccountSDK.getProperty("channel");
        } catch (RuntimeException e2) {
            AliSDKLogger.e(TAG, "getChannel error: " + e2.getMessage(), e2);
        }
        if (property == null || property.length() <= 0) {
            String metaConfig = getMetaConfig(context, OpenAccountConstants.CHANNEL_META_CONFIG_KEY_ALISDK);
            if (metaConfig != null && metaConfig.length() > 0) {
                return "0" + metaConfig;
            }
            String metaConfig2 = getMetaConfig(context, OpenAccountConstants.CHANNEL_META_CONFIG_KEY_UMENG);
            if (metaConfig2 != null && metaConfig2.length() > 0) {
                return bc.aN + metaConfig2;
            }
            return "0";
        }
        String property2 = OpenAccountSDK.getProperty("channelType");
        if (property2 == null || property2.length() <= 0) {
            return property;
        }
        if (property2.equals("umeng")) {
            return bc.aN + property;
        }
        if (property2.equals("baidu")) {
            return "b" + property;
        }
        return "0" + property;
    }

    private static String getMetaConfig(Context context, String str) {
        Object obj;
        try {
            Bundle bundle = context.getPackageManager().getPackageInfo(context.getPackageName(), 16512).applicationInfo.metaData;
            return (bundle == null || (obj = bundle.get(str)) == null) ? "" : obj.toString();
        } catch (PackageManager.NameNotFoundException unused) {
            AliSDKLogger.d(OpenAccountConstants.LOG_TAG, "Meta config not found: " + str);
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001e A[Catch: all -> 0x000f, RuntimeException -> 0x0012, TryCatch #0 {RuntimeException -> 0x0012, blocks: (B:5:0x0008, B:13:0x0018, B:15:0x001e, B:16:0x003a, B:18:0x0040, B:21:0x0046, B:22:0x004b, B:23:0x005d, B:24:0x0069, B:12:0x0014), top: B:31:0x0008, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040 A[Catch: all -> 0x000f, RuntimeException -> 0x0012, TryCatch #0 {RuntimeException -> 0x0012, blocks: (B:5:0x0008, B:13:0x0018, B:15:0x001e, B:16:0x003a, B:18:0x0040, B:21:0x0046, B:22:0x004b, B:23:0x005d, B:24:0x0069, B:12:0x0014), top: B:31:0x0008, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void init(android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) {
        /*
            r0 = 0
            r1 = 2
            r2 = 1
            java.lang.Class<com.alibaba.sdk.android.openaccount.util.TraceHelper> r3 = com.alibaba.sdk.android.openaccount.util.TraceHelper.class
            monitor-enter(r3)
            if (r10 == 0) goto L14
            int r4 = r10.length()     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            if (r4 != 0) goto L18
            goto L14
        Lf:
            r8 = move-exception
            goto L8a
        L12:
            r8 = move-exception
            goto L6e
        L14:
            java.lang.String r10 = getChannel(r8)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
        L18:
            boolean r8 = checkChannel(r10)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            if (r8 != 0) goto L3a
            java.lang.String r8 = "oa"
            java.lang.String r4 = "kernel"
            java.lang.String r5 = "initChannel"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r6.<init>()     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r7 = "Channel chars must in [0-9][a-z][A-Z], now : "
            r6.append(r7)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r6.append(r10)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r10 = r6.toString()     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.e(r8, r4, r5, r10)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r10 = "0"
        L3a:
            com.alibaba.sdk.android.openaccount.util.TraceHelper.channel = r10     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r8 = com.alibaba.sdk.android.openaccount.util.TraceHelper.clientTTID     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            if (r8 != 0) goto L69
            int r8 = com.alibaba.sdk.android.openaccount.util.TraceHelper.ttidVersion     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            if (r8 == r2) goto L5d
            if (r8 == r1) goto L4b
            java.lang.String r8 = "2014@taobao_h5_3.0.0"
            com.alibaba.sdk.android.openaccount.util.TraceHelper.clientTTID = r8     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            goto L69
        L4b:
            java.lang.String r8 = "2014_%s_%s@openaccount_android_%s"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r4[r0] = r10     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r4[r2] = r9     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r4[r1] = r11     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r8 = java.lang.String.format(r8, r4)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            com.alibaba.sdk.android.openaccount.util.TraceHelper.clientTTID = r8     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            goto L69
        L5d:
            java.lang.String r8 = "2014@taobao_h5_3.0.0$%s"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            r10[r0] = r9     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            java.lang.String r8 = java.lang.String.format(r8, r10)     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            com.alibaba.sdk.android.openaccount.util.TraceHelper.clientTTID = r8     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
        L69:
            java.lang.String r8 = com.alibaba.sdk.android.openaccount.util.TraceHelper.clientTTID     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            com.alibaba.sdk.android.openaccount.util.TraceHelper.webTTID = r8     // Catch: java.lang.Throwable -> Lf java.lang.RuntimeException -> L12
            goto L88
        L6e:
            java.lang.String r9 = com.alibaba.sdk.android.openaccount.util.TraceHelper.TAG     // Catch: java.lang.Throwable -> Lf
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lf
            r10.<init>()     // Catch: java.lang.Throwable -> Lf
            java.lang.String r11 = "init trace info error: "
            r10.append(r11)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r11 = r8.getMessage()     // Catch: java.lang.Throwable -> Lf
            r10.append(r11)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> Lf
            com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.e(r9, r10, r8)     // Catch: java.lang.Throwable -> Lf
        L88:
            monitor-exit(r3)
            return
        L8a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lf
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.util.TraceHelper.init(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
