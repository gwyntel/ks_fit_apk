package com.facebook.appevents.restrictivedatafilter;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class RestrictiveDataManager {
    private static final String TAG = "com.facebook.appevents.restrictivedatafilter.RestrictiveDataManager";
    private static boolean enabled = false;
    private static final List<RestrictiveParamFilter> restrictiveParamFilters = new ArrayList();

    static class RestrictiveParamFilter {
        String eventName;
        Map<String, String> restrictiveParams;

        RestrictiveParamFilter(String str, Map<String, String> map) {
            this.eventName = str;
            this.restrictiveParams = map;
        }
    }

    public static void enable() throws JSONException {
        enabled = true;
        initialize();
    }

    @Nullable
    private static String getMatchedRuleType(String str, String str2) {
        try {
            for (RestrictiveParamFilter restrictiveParamFilter : new ArrayList(restrictiveParamFilters)) {
                if (restrictiveParamFilter != null && str.equals(restrictiveParamFilter.eventName)) {
                    for (String str3 : restrictiveParamFilter.restrictiveParams.keySet()) {
                        if (str2.equals(str3)) {
                            return restrictiveParamFilter.restrictiveParams.get(str3);
                        }
                    }
                }
            }
            return null;
        } catch (Exception e2) {
            Log.w(TAG, "getMatchedRuleType failed", e2);
            return null;
        }
    }

    private static void initialize() throws JSONException {
        String restrictiveDataSetting;
        try {
            FetchedAppSettings fetchedAppSettingsQueryAppSettings = FetchedAppSettingsManager.queryAppSettings(FacebookSdk.getApplicationId(), false);
            if (fetchedAppSettingsQueryAppSettings == null || (restrictiveDataSetting = fetchedAppSettingsQueryAppSettings.getRestrictiveDataSetting()) == null || restrictiveDataSetting.isEmpty()) {
                return;
            }
            JSONObject jSONObject = new JSONObject(restrictiveDataSetting);
            restrictiveParamFilters.clear();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                if (jSONObject2 != null) {
                    JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("restrictive_param");
                    RestrictiveParamFilter restrictiveParamFilter = new RestrictiveParamFilter(next, new HashMap());
                    if (jSONObjectOptJSONObject != null) {
                        restrictiveParamFilter.restrictiveParams = Utility.convertJSONObjectToStringMap(jSONObjectOptJSONObject);
                        restrictiveParamFilters.add(restrictiveParamFilter);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public static void processParameters(Map<String, String> map, String str) throws JSONException {
        if (enabled) {
            HashMap map2 = new HashMap();
            for (String str2 : new ArrayList(map.keySet())) {
                String matchedRuleType = getMatchedRuleType(str, str2);
                if (matchedRuleType != null) {
                    map2.put(str2, matchedRuleType);
                    map.remove(str2);
                }
            }
            if (map2.size() > 0) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    for (Map.Entry entry : map2.entrySet()) {
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    }
                    map.put("_restrictedParams", jSONObject.toString());
                } catch (JSONException unused) {
                }
            }
        }
    }
}
