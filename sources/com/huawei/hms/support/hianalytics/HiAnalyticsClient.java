package com.huawei.hms.support.hianalytics;

import android.content.Context;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;
import java.util.Map;

/* loaded from: classes4.dex */
public class HiAnalyticsClient extends HiAnalyticsBase {
    private static final String TAG = "HiAnalyticsClient";

    @Deprecated
    public static String reportEntry(Context context, String str) {
        if (context != null) {
            return reportEntry(context, str, Util.getAppId(context), 0);
        }
        HMSLog.e(TAG, "<reportEntry 2 param> context is null.");
        return "";
    }

    public static void reportExit(Context context, String str, String str2, int i2, int i3) {
        if (context == null) {
            HMSLog.e(TAG, "<reportExit 5 param> context is null.");
        } else {
            reportExit(context, str, str2, Util.getAppId(context), i2, i3, 0);
        }
    }

    public static void reportExit(Context context, String str, String str2, int i2, int i3, int i4) {
        if (context == null) {
            HMSLog.e(TAG, "<reportExit 6 param> context is null.");
        } else {
            reportExit(context, str, str2, Util.getAppId(context), i2, i3, i4);
        }
    }

    public static String reportEntry(Context context, String str, int i2) {
        if (context == null) {
            HMSLog.e(TAG, "<reportEntry 3 param> context is null.");
            return "";
        }
        return reportEntry(context, str, Util.getAppId(context), i2);
    }

    public static void reportExit(Context context, String str, String str2, String str3, int i2, int i3, int i4) {
        if (context == null) {
            HMSLog.e(TAG, "<reportExit 7 param> context is null.");
            return;
        }
        Map<String, String> mapForBi = HiAnalyticsBase.getMapForBi(context, str);
        mapForBi.put("appid", str3);
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, str2);
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, HiAnalyticsConstant.Direction.RESPONSE);
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(i2));
        mapForBi.put("result", String.valueOf(i3));
        mapForBi.put("version", HiAnalyticsUtil.versionCodeToName(String.valueOf(i4)));
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_PHONETYPE, Util.getSystemProperties("ro.logsystem.usertype", ""));
        HiAnalyticsUtil.getInstance().onNewEvent(context, HiAnalyticsConstant.HMS_SDK_KIT_API_CALLED, mapForBi);
    }

    public static String reportEntry(Context context, String str, String str2, int i2) {
        if (context == null) {
            HMSLog.e(TAG, "<reportEntry 4 param> context is null.");
            return "";
        }
        String id = TransactionIdCreater.getId(str2, str);
        Map<String, String> mapForBi = HiAnalyticsBase.getMapForBi(context, str);
        mapForBi.put("appid", str2);
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, id);
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "req");
        mapForBi.put("version", HiAnalyticsUtil.versionCodeToName(String.valueOf(i2)));
        mapForBi.put(HiAnalyticsConstant.HaKey.BI_KEY_PHONETYPE, Util.getSystemProperties("ro.logsystem.usertype", ""));
        HiAnalyticsUtil.getInstance().onNewEvent(context, HiAnalyticsConstant.HMS_SDK_KIT_API_CALLED, mapForBi);
        return id;
    }
}
