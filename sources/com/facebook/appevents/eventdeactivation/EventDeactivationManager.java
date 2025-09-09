package com.facebook.appevents.eventdeactivation;

import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEvent;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class EventDeactivationManager {
    private static boolean enabled = false;
    private static final List<DeprecatedParamFilter> deprecatedParamFilters = new ArrayList();
    private static final Set<String> deprecatedEvents = new HashSet();

    static class DeprecatedParamFilter {
        List<String> deprecateParams;
        String eventName;

        DeprecatedParamFilter(String str, List<String> list) {
            this.eventName = str;
            this.deprecateParams = list;
        }
    }

    public static void enable() {
        enabled = true;
        initialize();
    }

    private static synchronized void initialize() {
        FetchedAppSettings fetchedAppSettingsQueryAppSettings;
        try {
            fetchedAppSettingsQueryAppSettings = FetchedAppSettingsManager.queryAppSettings(FacebookSdk.getApplicationId(), false);
        } catch (Exception unused) {
        } catch (Throwable th) {
            throw th;
        }
        if (fetchedAppSettingsQueryAppSettings == null) {
            return;
        }
        String restrictiveDataSetting = fetchedAppSettingsQueryAppSettings.getRestrictiveDataSetting();
        if (!restrictiveDataSetting.isEmpty()) {
            JSONObject jSONObject = new JSONObject(restrictiveDataSetting);
            deprecatedParamFilters.clear();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                if (jSONObject2 != null) {
                    if (jSONObject2.optBoolean("is_deprecated_event")) {
                        deprecatedEvents.add(next);
                    } else {
                        JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("deprecated_param");
                        DeprecatedParamFilter deprecatedParamFilter = new DeprecatedParamFilter(next, new ArrayList());
                        if (jSONArrayOptJSONArray != null) {
                            deprecatedParamFilter.deprecateParams = Utility.convertJSONArrayToList(jSONArrayOptJSONArray);
                        }
                        deprecatedParamFilters.add(deprecatedParamFilter);
                    }
                }
            }
        }
    }

    public static void processDeprecatedParameters(Map<String, String> map, String str) {
        if (enabled) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            for (DeprecatedParamFilter deprecatedParamFilter : new ArrayList(deprecatedParamFilters)) {
                if (deprecatedParamFilter.eventName.equals(str)) {
                    for (String str2 : arrayList) {
                        if (deprecatedParamFilter.deprecateParams.contains(str2)) {
                            map.remove(str2);
                        }
                    }
                }
            }
        }
    }

    public static void processEvents(List<AppEvent> list) {
        if (enabled) {
            Iterator<AppEvent> it = list.iterator();
            while (it.hasNext()) {
                if (deprecatedEvents.contains(it.next().getName())) {
                    it.remove();
                }
            }
        }
    }
}
