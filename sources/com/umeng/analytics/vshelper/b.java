package com.umeng.analytics.vshelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.bm;
import com.umeng.analytics.pro.q;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements bm {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21997a = "RealTimeDebugSwitch";

    /* renamed from: b, reason: collision with root package name */
    private static volatile int f21998b;

    @Override // com.umeng.analytics.pro.bm
    public void a() {
    }

    @Override // com.umeng.analytics.pro.bm
    public void b() {
    }

    @Override // com.umeng.analytics.pro.bm
    public void c() {
    }

    @Override // com.umeng.analytics.pro.bm
    public void d(Activity activity) {
        f21998b--;
    }

    @Override // com.umeng.analytics.pro.bm
    public void e(Activity activity) {
    }

    public static boolean d() {
        return f21998b > 0;
    }

    @Override // com.umeng.analytics.pro.bm
    public void a(Activity activity) {
    }

    @Override // com.umeng.analytics.pro.bm
    public void b(Activity activity) {
    }

    @Override // com.umeng.analytics.pro.bm
    public void c(Activity activity) {
        f21998b++;
    }

    @Override // com.umeng.analytics.pro.bm
    public void a(Activity activity, Bundle bundle) {
        long jLongValue;
        if (f21998b == 0) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null) {
                    String scheme = data.getScheme();
                    if (scheme != null && scheme.startsWith("um.")) {
                        String queryParameter = data.getQueryParameter(AnalyticsConfig.DEBUG_KEY);
                        String queryParameter2 = data.getQueryParameter(AnalyticsConfig.DEBUG_MODE_PERIOD);
                        if (TextUtils.isEmpty(queryParameter)) {
                            return;
                        }
                        if (TextUtils.isEmpty(queryParameter2)) {
                            jLongValue = 0;
                        } else {
                            try {
                                jLongValue = Long.valueOf(queryParameter2).longValue();
                            } catch (Throwable unused) {
                            }
                        }
                        HashMap map = new HashMap();
                        map.put(AnalyticsConfig.DEBUG_KEY, queryParameter);
                        if (jLongValue < 0) {
                            if (AnalyticsConfig.isRealTimeDebugMode()) {
                                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> call turnOffRealTimeDebug because sendaging < 0");
                                AnalyticsConfig.turnOffRealTimeDebug();
                                Context applicationContext = activity.getApplicationContext();
                                UMWorkDispatch.sendEvent(applicationContext, q.a.G, CoreProtocol.getInstance(applicationContext), null);
                                return;
                            }
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> Not currently in RealTimeDebug mode and doing nothing.");
                            return;
                        }
                        if (jLongValue > 0) {
                            AnalyticsConfig.turnOnRealTimeDebug(map);
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put("startTime", System.currentTimeMillis());
                                if (jLongValue < 60) {
                                    jLongValue = 60;
                                }
                                if (jLongValue > 240) {
                                    jLongValue = 240;
                                }
                                jSONObject.put("period", jLongValue);
                                jSONObject.put(AnalyticsConfig.DEBUG_KEY, queryParameter);
                            } catch (Throwable unused2) {
                            }
                            Context applicationContext2 = activity.getApplicationContext();
                            UMWorkDispatch.sendEvent(applicationContext2, q.a.F, CoreProtocol.getInstance(applicationContext2), jSONObject);
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> turnOnRealTimeDebug[persistent] dk: " + queryParameter + "; period: " + jLongValue);
                            return;
                        }
                        AnalyticsConfig.turnOnRealTimeDebug(map);
                        Context applicationContext3 = activity.getApplicationContext();
                        UMWorkDispatch.sendEvent(applicationContext3, q.a.G, CoreProtocol.getInstance(applicationContext3), null);
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> turnOnRealTimeDebug[non-persistent] dk: " + queryParameter);
                        return;
                    }
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> scheme: " + scheme);
                    return;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> uri: " + data);
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> intent: " + intent);
        }
    }

    @Override // com.umeng.analytics.pro.bm
    public void b(Activity activity, Bundle bundle) {
    }
}
