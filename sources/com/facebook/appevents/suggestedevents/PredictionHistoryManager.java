package com.facebook.appevents.suggestedevents;

import android.content.SharedPreferences;
import android.view.View;
import androidx.annotation.Nullable;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.internal.Utility;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
final class PredictionHistoryManager {
    private static final String CLICKED_PATH_STORE = "com.facebook.internal.SUGGESTED_EVENTS_HISTORY";
    private static final String SUGGESTED_EVENTS_HISTORY = "SUGGESTED_EVENTS_HISTORY";
    private static final Map<String, String> clickedViewPaths = new HashMap();
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static SharedPreferences shardPreferences;

    PredictionHistoryManager() {
    }

    static void addPrediction(String str, String str2) {
        if (!initialized.get()) {
            initAndWait();
        }
        Map<String, String> map = clickedViewPaths;
        map.put(str, str2);
        shardPreferences.edit().putString(SUGGESTED_EVENTS_HISTORY, Utility.mapToJsonStr(map)).apply();
    }

    @Nullable
    static String getPathID(View view) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        while (view != null) {
            SuggestedEventViewHierarchy.updateBasicInfo(view, jSONObject);
            view = ViewHierarchy.getParentOfView(view);
        }
        return Utility.sha256hash(jSONObject.toString());
    }

    private static void initAndWait() {
        AtomicBoolean atomicBoolean = initialized;
        if (atomicBoolean.get()) {
            return;
        }
        SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(CLICKED_PATH_STORE, 0);
        shardPreferences = sharedPreferences;
        clickedViewPaths.putAll(Utility.JsonStrToMap(sharedPreferences.getString(SUGGESTED_EVENTS_HISTORY, "")));
        atomicBoolean.set(true);
    }

    @Nullable
    static String queryEvent(String str) {
        Map<String, String> map = clickedViewPaths;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        return null;
    }
}
