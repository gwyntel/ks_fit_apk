package com.umeng.analytics.pro;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class bl implements bk {

    /* renamed from: a, reason: collision with root package name */
    private static final String f21519a = "cache_domain";

    /* renamed from: b, reason: collision with root package name */
    private static volatile String f21520b = "";

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final bl f21521a = new bl();

        private a() {
        }
    }

    public static bl b() {
        return a.f21521a;
    }

    private void d() {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(UMGlobalContext.getAppContext());
        if (sharedPreferences != null) {
            f21520b = sharedPreferences.getString(f21519a, "");
        }
    }

    private void e() {
        try {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(UMGlobalContext.getAppContext());
            if (sharedPreferences != null) {
                sharedPreferences.edit().putString(f21519a, f21520b).commit();
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.umeng.analytics.pro.bk
    public void a() {
    }

    public String c() {
        return f21520b;
    }

    private bl() {
        d();
    }

    @Override // com.umeng.analytics.pro.bk
    public void a(Throwable th) {
    }

    @Override // com.umeng.analytics.pro.bk
    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("Status") && jSONObject.getInt("Status") == 0 && jSONObject.has("Answer")) {
                String strOptString = jSONObject.optString("Answer");
                String strOptString2 = "";
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                if (jSONObject.has("ip")) {
                    strOptString2 = jSONObject.optString("ip");
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> domain下发结果：" + strOptString);
                if (!TextUtils.isEmpty(strOptString2)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 对应domain下发请求ip：" + strOptString2);
                }
                f21520b = strOptString;
                e();
            }
        } catch (Throwable unused) {
        }
    }
}
