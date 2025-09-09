package com.aliyun.alink.business.devicecenter.log;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes2.dex */
public class PerformanceLog {
    public static JSONObject getJsonObject(String... strArr) throws JSONException {
        if (strArr == null) {
            return null;
        }
        try {
            if (strArr.length < 2) {
                return null;
            }
            JSONObject jSONObject = new JSONObject();
            int length = strArr.length / 2;
            for (int i2 = 0; i2 < length; i2 += 2) {
                try {
                    jSONObject.put(strArr[i2], strArr[i2 + 1]);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            return jSONObject;
        } catch (Exception e3) {
            e3.printStackTrace();
            return new JSONObject();
        }
    }

    public static void trace(String str, String str2) {
    }

    public static void trace(String str, String str2, String str3, String str4, JSONObject jSONObject) {
    }

    public static void trace(String str, String str2, JSONObject jSONObject) {
    }
}
