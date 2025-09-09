package com.aliyun.alink.linksdk.tmp.utils;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ResponseUtils {
    protected static final String TAG = "[Tmp]ResponseUtils";

    public static String getRspJson(int i2, String str, JSONObject jSONObject) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("code", i2);
            jSONObject2.put("message", str);
            jSONObject2.put("data", jSONObject);
            ALog.d(TAG, "rsp bone json = " + jSONObject2.toString());
            return jSONObject2.toString();
        } catch (Exception e2) {
            ALog.d(TAG, "getRspJson, e = " + e2.toString());
            return null;
        }
    }

    public static JSONObject getSuccessRspJson(JSONArray jSONArray) {
        return getRspJson(200, "success", jSONArray);
    }

    public static String getSuccessRspJson(JSONObject jSONObject) {
        return getRspJson(200, "success", jSONObject);
    }

    public static String getSuccessRspJson(com.alibaba.fastjson.JSONObject jSONObject) {
        return getRspJson(200, "success", jSONObject);
    }

    public static JSONObject getRspJson(int i2, String str, JSONArray jSONArray) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", i2);
            jSONObject.put("message", str);
            jSONObject.put("data", jSONArray);
            return jSONObject;
        } catch (Exception e2) {
            ALog.d(TAG, "getRspJson, e = " + e2.toString());
            return null;
        }
    }

    public static String getRspJson(int i2, String str, com.alibaba.fastjson.JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            com.alibaba.fastjson.JSONObject jSONObject2 = new com.alibaba.fastjson.JSONObject();
            jSONObject2.put("code", (Object) Integer.valueOf(i2));
            jSONObject2.put("message", (Object) str);
            jSONObject2.put("data", (Object) jSONObject);
            ALog.d(TAG, "rsp bone json = " + jSONObject2.toString());
            return jSONObject2.toString();
        } catch (Exception e2) {
            ALog.d(TAG, "getRspJson, e = " + e2.toString());
            return null;
        }
    }
}
