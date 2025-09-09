package com.aliyun.iot.aep.sdk.log;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class UpLoadRuleEngine {
    public static boolean needUpload(JSONObject jSONObject) {
        try {
            return RuleManager.hasTag("default_ruleset", jSONObject.getString("tag"));
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void setRule(String str) {
    }
}
