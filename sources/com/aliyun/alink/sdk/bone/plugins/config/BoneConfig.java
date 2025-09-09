package com.aliyun.alink.sdk.bone.plugins.config;

import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneService;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.ServiceMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@BoneService(mode = ServiceMode.ALWAYS_NEW, name = BoneConfig.API_NAME)
/* loaded from: classes2.dex */
public class BoneConfig extends BaseBoneService {
    public static final String API_NAME = "BoneConfig";

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, String> f11474a = new ConcurrentHashMap();

    public static void setAll(Map<String, String> map) {
        f11474a.putAll(map);
    }

    @BoneMethod
    public void get(JSONArray jSONArray, BoneCallback boneCallback) throws JSONException {
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            strArr[i2] = jSONArray.optString(i2);
        }
        JSONObject jSONObject = new JSONObject();
        for (int i3 = 0; i3 < length; i3++) {
            String str = strArr[i3];
            try {
                String str2 = f11474a.get(str);
                if (str2 == null) {
                    str2 = "";
                }
                jSONObject.put(str, str2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        boneCallback.success(jSONObject);
    }

    @BoneMethod
    public void getAll(BoneCallback boneCallback) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : f11474a.keySet()) {
            try {
                String str2 = f11474a.get(str);
                if (str2 == null) {
                    str2 = "";
                }
                jSONObject.put(str, str2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        boneCallback.success(jSONObject);
    }

    @BoneMethod
    public void set(JSONObject jSONObject, BoneCallback boneCallback) {
        HashMap map = new HashMap(jSONObject.length());
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, jSONObject.optString(next));
        }
        f11474a.putAll(map);
        boneCallback.success(new JSONObject());
    }

    public static Map<String, String> getAll() {
        return new HashMap(f11474a);
    }

    public static String set(String str, String str2) {
        return f11474a.put(str, str2);
    }

    public static String get(String str) {
        return f11474a.get(str);
    }
}
