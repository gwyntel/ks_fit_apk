package com.huawei.hms.support.hianalytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.android.SystemUtils;
import com.huawei.hms.common.internal.RequestHeader;
import com.huawei.hms.common.internal.ResponseHeader;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HiAnalyticsUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f18113a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static HiAnalyticsUtil f18114b;

    /* renamed from: c, reason: collision with root package name */
    private static HiAnalyticsUtils f18115c;

    private String a(Context context, String str) {
        return "01||" + context.getPackageName() + "|" + Util.getAppId(context) + "|60800300|" + str;
    }

    public static HiAnalyticsUtil getInstance() {
        HiAnalyticsUtil hiAnalyticsUtil;
        synchronized (f18113a) {
            try {
                if (f18114b == null) {
                    f18114b = new HiAnalyticsUtil();
                    f18115c = HiAnalyticsUtils.getInstance();
                }
                hiAnalyticsUtil = f18114b;
            } catch (Throwable th) {
                throw th;
            }
        }
        return hiAnalyticsUtil;
    }

    public static Map<String, String> getMapFromForegroundResponseHeader(ResponseHeader responseHeader) {
        HashMap map = new HashMap();
        if (responseHeader == null) {
            return map;
        }
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, responseHeader.getTransactionId());
        map.put("appid", responseHeader.getActualAppID());
        map.put("service", responseHeader.getSrvName());
        map.put("apiName", responseHeader.getApiName());
        map.put("package", responseHeader.getPkgName());
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(responseHeader.getStatusCode()));
        map.put("result", String.valueOf(responseHeader.getErrorCode()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_ERRORREASON, responseHeader.getErrorReason());
        map.put("callTime", String.valueOf(System.currentTimeMillis()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.8.0.300");
        return map;
    }

    public static Map<String, String> getMapFromRequestHeader(ResponseHeader responseHeader) {
        HashMap map = new HashMap();
        if (responseHeader == null) {
            return map;
        }
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, responseHeader.getTransactionId());
        map.put("appid", responseHeader.getActualAppID());
        map.put("service", responseHeader.getSrvName());
        String apiName = responseHeader.getApiName();
        if (!TextUtils.isEmpty(apiName)) {
            String[] strArrSplit = apiName.split("\\.");
            if (strArrSplit.length >= 2) {
                map.put("apiName", strArrSplit[1]);
            }
        }
        map.put("package", responseHeader.getPkgName());
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, String.valueOf(responseHeader.getStatusCode()));
        map.put("result", String.valueOf(responseHeader.getErrorCode()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_ERRORREASON, responseHeader.getErrorReason());
        map.put("callTime", String.valueOf(System.currentTimeMillis()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.8.0.300");
        return map;
    }

    public static String versionCodeToName(String str) {
        return HiAnalyticsUtils.versionCodeToName(str);
    }

    @Deprecated
    public Map<String, String> getMapForBi(Context context, String str) {
        HashMap map = new HashMap();
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length >= 2) {
            String str2 = strArrSplit[0];
            String str3 = strArrSplit[1];
            String appId = Util.getAppId(context);
            map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, TransactionIdCreater.getId(appId, str));
            map.put("appid", appId);
            map.put("service", str2);
            map.put("apiName", str3);
            map.put("package", context.getPackageName());
            map.put("version", "6.8.0.300");
            map.put("callTime", String.valueOf(System.currentTimeMillis()));
        }
        return map;
    }

    public Map<String, String> getMapFromForegroundRequestHeader(RequestHeader requestHeader) {
        HashMap map = new HashMap();
        if (requestHeader == null) {
            return map;
        }
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, requestHeader.getTransactionId());
        map.put("appid", requestHeader.getActualAppID());
        map.put("service", requestHeader.getSrvName());
        map.put("apiName", requestHeader.getApiName());
        map.put("package", requestHeader.getPkgName());
        map.put("callTime", String.valueOf(System.currentTimeMillis()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.8.0.300");
        return map;
    }

    @Deprecated
    public boolean hasError() {
        if (SystemUtils.isChinaROM()) {
            return false;
        }
        HMSLog.i("HiAnalyticsUtil", "not ChinaROM ");
        return true;
    }

    public void onBuoyEvent(Context context, String str, String str2) {
        f18115c.onBuoyEvent(context, str, str2);
    }

    public void onEvent(Context context, String str, Map<String, String> map) {
        f18115c.onEvent(context, str, map);
    }

    public void onEvent2(Context context, String str, String str2) {
        f18115c.onEvent2(context, str, str2);
    }

    public void onNewEvent(Context context, String str, Map map) {
        f18115c.onNewEvent(context, str, map);
    }

    public void onEvent(Context context, String str, String str2) {
        if (context != null) {
            onEvent2(context, str, a(context, str2));
        }
    }

    public boolean hasError(Context context) {
        return f18115c.hasError(context);
    }

    public Map<String, String> getMapFromRequestHeader(RequestHeader requestHeader) {
        HashMap map = new HashMap();
        if (requestHeader == null) {
            return map;
        }
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, requestHeader.getTransactionId());
        map.put("appid", requestHeader.getActualAppID());
        map.put("service", requestHeader.getSrvName());
        String apiName = requestHeader.getApiName();
        if (!TextUtils.isEmpty(apiName)) {
            String[] strArrSplit = apiName.split("\\.");
            if (strArrSplit.length >= 2) {
                map.put("apiName", strArrSplit[1]);
            }
        }
        map.put("package", requestHeader.getPkgName());
        map.put("callTime", String.valueOf(System.currentTimeMillis()));
        map.put(HiAnalyticsConstant.HaKey.BI_KEY_BASE_VERSION, "6.8.0.300");
        return map;
    }
}
