package com.umeng.message.proguard;

import android.app.Application;
import android.os.Build;
import anetwork.channel.util.RequestConstant;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class e {
    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            Application applicationA = x.a();
            jSONObject.put("appkey", PushAgent.getInstance(applicationA).getMessageAppkey());
            jSONObject.put("channel", PushAgent.getInstance(applicationA).getMessageChannel());
            jSONObject.put(com.umeng.analytics.pro.bc.f21408g, d.k(applicationA));
            jSONObject.put("din", d.c(applicationA));
            jSONObject.put("device_id", d.d(applicationA));
            jSONObject.put(com.umeng.analytics.pro.bc.f21423v, d.f(applicationA));
            jSONObject.put(com.umeng.analytics.pro.bc.A, d.c());
            if (d.e(applicationA) != null) {
                jSONObject.put("android_id", d.e(applicationA));
            }
            if (d.b() != null) {
                jSONObject.put("serial_number", d.b());
            }
            String strP = d.p(applicationA);
            if (RequestConstant.FALSE.equals(strP)) {
                UMLog.aq(ab.f22708b, 0, "\\|");
            }
            jSONObject.put("push_switch", strP);
            jSONObject.put("sdk_type", AnalyticsConstants.SDK_TYPE);
            jSONObject.put("sdk_version", MsgConstant.SDK_VERSION);
            String[] strArrG = d.g(applicationA);
            jSONObject.put(com.umeng.analytics.pro.bc.Q, strArrG[0]);
            jSONObject.put(com.umeng.analytics.pro.bc.R, strArrG[1]);
            jSONObject.put("carrier", d.m(applicationA));
            jSONObject.put("device_model", d.d());
            jSONObject.put("os", AnalyticsConstants.SDK_TYPE);
            jSONObject.put(com.umeng.analytics.pro.bc.f21426y, Build.VERSION.RELEASE);
            jSONObject.put("app_version", d.b(applicationA));
            jSONObject.put("version_code", d.a(applicationA));
            jSONObject.put("package_name", applicationA.getPackageName());
            jSONObject.put("resolution", d.l(applicationA));
            jSONObject.put(com.umeng.analytics.pro.bc.f21424w, d.a());
            jSONObject.put(com.umeng.analytics.pro.bc.M, d.i(applicationA));
            String[] strArrJ = d.j(applicationA);
            jSONObject.put("country", strArrJ[0]);
            jSONObject.put(com.umeng.analytics.pro.bc.N, strArrJ[1]);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return jSONObject;
    }
}
