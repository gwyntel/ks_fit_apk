package com.facebook.appevents.ml;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.restrictivedatafilter.AddressFilterManager;
import com.facebook.appevents.suggestedevents.SuggestedEventsManager;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.Utility;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes3.dex */
public final class ModelManager {
    private static final String CACHE_KEY_MODELS = "models";
    public static final String MODEL_ADDRESS_DETECTION = "DATA_DETECTION_ADDRESS";
    private static final String MODEL_ASSERT_STORE = "com.facebook.internal.MODEL_STORE";
    public static final String MODEL_SUGGESTED_EVENTS = "SUGGEST_EVENT";
    private static final String SDK_MODEL_ASSET = "%s/model_asset";
    private static SharedPreferences shardPreferences;
    private static final ConcurrentMap<String, Model> models = new ConcurrentHashMap();
    private static final String[] APP_SETTING_FIELDS = {"version_id", "asset_uri", "use_case", "thresholds", "rules_uri"};

    /* JADX INFO: Access modifiers changed from: private */
    public static void addModelsFromModelJson(JSONObject jSONObject) {
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            try {
                String next = itKeys.next();
                Model modelJsonObjectToModel = jsonObjectToModel(jSONObject.getJSONObject(next));
                if (modelJsonObjectToModel != null) {
                    models.put(next, modelJsonObjectToModel);
                }
            } catch (JSONException unused) {
                return;
            }
        }
    }

    public static void enable() {
        initialize();
    }

    public static void enablePIIFiltering() {
        if (models.containsKey(MODEL_ADDRESS_DETECTION)) {
            FeatureManager.checkFeature(FeatureManager.Feature.PIIFiltering, new FeatureManager.Callback() { // from class: com.facebook.appevents.ml.ModelManager.3
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        ((Model) ModelManager.models.get(ModelManager.MODEL_ADDRESS_DETECTION)).initialize(new Runnable() { // from class: com.facebook.appevents.ml.ModelManager.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AddressFilterManager.enable();
                            }
                        });
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enableSuggestedEvents() {
        if (models.containsKey(MODEL_SUGGESTED_EVENTS)) {
            Locale resourceLocale = Utility.getResourceLocale();
            if (resourceLocale == null || resourceLocale.getLanguage().contains("en")) {
                FeatureManager.checkFeature(FeatureManager.Feature.SuggestedEvents, new FeatureManager.Callback() { // from class: com.facebook.appevents.ml.ModelManager.2
                    @Override // com.facebook.internal.FeatureManager.Callback
                    public void onCompleted(boolean z2) {
                        if (z2) {
                            ((Model) ModelManager.models.get(ModelManager.MODEL_SUGGESTED_EVENTS)).initialize(new Runnable() { // from class: com.facebook.appevents.ml.ModelManager.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SuggestedEventsManager.enable();
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static JSONObject fetchFromServer() {
        ArrayList arrayList = new ArrayList(Arrays.asList(APP_SETTING_FIELDS));
        Bundle bundle = new Bundle();
        bundle.putString(GraphRequest.FIELDS_PARAM, TextUtils.join(",", arrayList));
        GraphRequest graphRequestNewGraphPathRequest = GraphRequest.newGraphPathRequest(null, String.format(SDK_MODEL_ASSET, FacebookSdk.getApplicationId()), null);
        graphRequestNewGraphPathRequest.setSkipClientToken(true);
        graphRequestNewGraphPathRequest.setParameters(bundle);
        JSONObject jSONObject = graphRequestNewGraphPathRequest.executeAndWait().getJSONObject();
        if (jSONObject == null) {
            return null;
        }
        return parseRawJsonObject(jSONObject);
    }

    @Nullable
    public static File getRuleFile(String str) {
        ConcurrentMap<String, Model> concurrentMap = models;
        if (concurrentMap.containsKey(str)) {
            return concurrentMap.get(str).getRuleFile();
        }
        return null;
    }

    public static void initialize() {
        shardPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(MODEL_ASSERT_STORE, 0);
        initializeModels();
    }

    private static void initializeModels() {
        Utility.runOnNonUiThread(new Runnable() { // from class: com.facebook.appevents.ml.ModelManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    JSONObject jSONObjectFetchFromServer = ModelManager.fetchFromServer();
                    if (jSONObjectFetchFromServer != null) {
                        ModelManager.shardPreferences.edit().putString(ModelManager.CACHE_KEY_MODELS, jSONObjectFetchFromServer.toString()).apply();
                    } else {
                        jSONObjectFetchFromServer = new JSONObject(ModelManager.shardPreferences.getString(ModelManager.CACHE_KEY_MODELS, ""));
                    }
                    ModelManager.addModelsFromModelJson(jSONObjectFetchFromServer);
                    ModelManager.enableSuggestedEvents();
                    ModelManager.enablePIIFiltering();
                } catch (Exception unused) {
                }
            }
        });
    }

    @Nullable
    private static Model jsonObjectToModel(JSONObject jSONObject) throws JSONException {
        try {
            return new Model(jSONObject.getString("use_case"), Integer.parseInt(jSONObject.getString("version_id")), jSONObject.getString("asset_uri"), jSONObject.optString("rules_uri", null), parseJsonArray(jSONObject.getJSONArray("thresholds")));
        } catch (JSONException unused) {
            return null;
        }
    }

    private static float[] parseJsonArray(JSONArray jSONArray) {
        float[] fArr = new float[jSONArray.length()];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                fArr[i2] = Float.parseFloat(jSONArray.getString(i2));
            } catch (JSONException unused) {
            }
        }
        return fArr;
    }

    private static JSONObject parseRawJsonObject(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("data");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("version_id", jSONObject3.getString("version_id"));
                jSONObject4.put("use_case", jSONObject3.getString("use_case"));
                jSONObject4.put("thresholds", jSONObject3.getJSONArray("thresholds"));
                jSONObject4.put("asset_uri", jSONObject3.getString("asset_uri"));
                if (jSONObject3.has("rules_uri")) {
                    jSONObject4.put("rules_uri", jSONObject3.getString("rules_uri"));
                }
                jSONObject2.put(jSONObject3.getString("use_case"), jSONObject4);
            }
            return jSONObject2;
        } catch (JSONException unused) {
            return new JSONObject();
        }
    }

    @Nullable
    public static String predict(String str, float[] fArr, String str2) {
        ConcurrentMap<String, Model> concurrentMap = models;
        if (concurrentMap.containsKey(str)) {
            return concurrentMap.get(str).predict(fArr, str2);
        }
        return null;
    }
}
