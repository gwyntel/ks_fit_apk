package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class e {
    public static f a(Context context) {
        if (context == null) {
            return null;
        }
        String strA = com.alipay.apmobilesecuritysdk.f.a.a(context, "device_feature_prefs_name", "device_feature_prefs_key");
        if (com.alipay.sdk.m.z.a.a(strA)) {
            strA = com.alipay.apmobilesecuritysdk.f.a.a("device_feature_file_name", "device_feature_file_key");
        }
        if (com.alipay.sdk.m.z.a.a(strA)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(strA);
            f fVar = new f();
            fVar.a(jSONObject.getString(Constants.KEY_IMEI));
            fVar.b(jSONObject.getString(Constants.KEY_IMSI));
            fVar.c(jSONObject.getString(AlinkConstants.KEY_MAC));
            fVar.d(jSONObject.getString("bluetoothmac"));
            fVar.e(jSONObject.getString("gsi"));
            return fVar;
        } catch (Exception e2) {
            com.alipay.apmobilesecuritysdk.c.a.a(e2);
            return null;
        }
    }
}
