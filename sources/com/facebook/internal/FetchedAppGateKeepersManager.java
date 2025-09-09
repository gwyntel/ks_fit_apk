package com.facebook.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.kingsmith.miot.KsProperty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class FetchedAppGateKeepersManager {
    private static final String APPLICATION_FIELDS = "fields";
    private static final long APPLICATION_GATEKEEPER_CACHE_TIMEOUT = 3600000;
    private static final String APPLICATION_GATEKEEPER_EDGE = "mobile_sdk_gk";
    private static final String APPLICATION_GATEKEEPER_FIELD = "gatekeepers";
    private static final String APPLICATION_GRAPH_DATA = "data";
    private static final String APPLICATION_PLATFORM = "platform";
    private static final String APPLICATION_SDK_VERSION = "sdk_version";
    private static final String APP_GATEKEEPERS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_GATEKEEPERS.%s";
    private static final String APP_GATEKEEPERS_PREFS_STORE = "com.facebook.internal.preferences.APP_GATEKEEPERS";
    private static final String APP_PLATFORM = "android";
    private static final String TAG = "com.facebook.internal.FetchedAppGateKeepersManager";

    @Nullable
    private static Long timestamp;
    private static final AtomicBoolean isLoading = new AtomicBoolean(false);
    private static final ConcurrentLinkedQueue<Callback> callbacks = new ConcurrentLinkedQueue<>();
    private static final Map<String, JSONObject> fetchedAppGateKeepers = new ConcurrentHashMap();

    public interface Callback {
        void onCompleted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static JSONObject getAppGateKeepersQueryResponse(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("platform", "android");
        bundle.putString("sdk_version", FacebookSdk.getSdkVersion());
        bundle.putString("fields", APPLICATION_GATEKEEPER_FIELD);
        GraphRequest graphRequestNewGraphPathRequest = GraphRequest.newGraphPathRequest(null, String.format("%s/%s", str, APPLICATION_GATEKEEPER_EDGE), null);
        graphRequestNewGraphPathRequest.setSkipClientToken(true);
        graphRequestNewGraphPathRequest.setParameters(bundle);
        return graphRequestNewGraphPathRequest.executeAndWait().getJSONObject();
    }

    public static boolean getGateKeeperForKey(String str, String str2, boolean z2) {
        loadAppGateKeepersAsync();
        if (str2 != null) {
            Map<String, JSONObject> map = fetchedAppGateKeepers;
            if (map.containsKey(str2)) {
                return map.get(str2).optBoolean(str, z2);
            }
        }
        return z2;
    }

    private static boolean isTimestampValid(@Nullable Long l2) {
        return l2 != null && System.currentTimeMillis() - l2.longValue() < 3600000;
    }

    static void loadAppGateKeepersAsync() {
        loadAppGateKeepersAsync(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized JSONObject parseAppGateKeepersFromJSON(String str, JSONObject jSONObject) {
        JSONObject jSONObject2;
        try {
            Map<String, JSONObject> map = fetchedAppGateKeepers;
            jSONObject2 = map.containsKey(str) ? map.get(str) : new JSONObject();
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray != null ? jSONArrayOptJSONArray.optJSONObject(0) : null;
            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optJSONArray(APPLICATION_GATEKEEPER_FIELD) != null) {
                JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(APPLICATION_GATEKEEPER_FIELD);
                for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                    try {
                        JSONObject jSONObject3 = jSONArrayOptJSONArray2.getJSONObject(i2);
                        jSONObject2.put(jSONObject3.getString(KsProperty.Key), jSONObject3.getBoolean("value"));
                    } catch (JSONException e2) {
                        Utility.logd("FacebookSDK", e2);
                    }
                }
            }
            fetchedAppGateKeepers.put(str, jSONObject2);
        } catch (Throwable th) {
            throw th;
        }
        return jSONObject2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pollCallbacks() {
        Handler handler = new Handler(Looper.getMainLooper());
        while (true) {
            ConcurrentLinkedQueue<Callback> concurrentLinkedQueue = callbacks;
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            final Callback callbackPoll = concurrentLinkedQueue.poll();
            if (callbackPoll != null) {
                handler.post(new Runnable() { // from class: com.facebook.internal.FetchedAppGateKeepersManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callbackPoll.onCompleted();
                    }
                });
            }
        }
    }

    @Nullable
    static JSONObject queryAppGateKeepers(String str, boolean z2) {
        if (!z2) {
            Map<String, JSONObject> map = fetchedAppGateKeepers;
            if (map.containsKey(str)) {
                return map.get(str);
            }
        }
        JSONObject appGateKeepersQueryResponse = getAppGateKeepersQueryResponse(str);
        if (appGateKeepersQueryResponse == null) {
            return null;
        }
        FacebookSdk.getApplicationContext().getSharedPreferences(APP_GATEKEEPERS_PREFS_STORE, 0).edit().putString(String.format(APP_GATEKEEPERS_PREFS_KEY_FORMAT, str), appGateKeepersQueryResponse.toString()).apply();
        return parseAppGateKeepersFromJSON(str, appGateKeepersQueryResponse);
    }

    static synchronized void loadAppGateKeepersAsync(@Nullable Callback callback) {
        if (callback != null) {
            try {
                callbacks.add(callback);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (isTimestampValid(timestamp)) {
            pollCallbacks();
            return;
        }
        final Context applicationContext = FacebookSdk.getApplicationContext();
        final String applicationId = FacebookSdk.getApplicationId();
        final String str = String.format(APP_GATEKEEPERS_PREFS_KEY_FORMAT, applicationId);
        if (applicationContext == null) {
            return;
        }
        JSONObject jSONObject = null;
        String string = applicationContext.getSharedPreferences(APP_GATEKEEPERS_PREFS_STORE, 0).getString(str, null);
        if (!Utility.isNullOrEmpty(string)) {
            try {
                jSONObject = new JSONObject(string);
            } catch (JSONException e2) {
                Utility.logd("FacebookSDK", e2);
            }
            if (jSONObject != null) {
                parseAppGateKeepersFromJSON(applicationId, jSONObject);
            }
        }
        Executor executor = FacebookSdk.getExecutor();
        if (executor == null) {
            return;
        }
        if (isLoading.compareAndSet(false, true)) {
            executor.execute(new Runnable() { // from class: com.facebook.internal.FetchedAppGateKeepersManager.1
                @Override // java.lang.Runnable
                public void run() {
                    JSONObject appGateKeepersQueryResponse = FetchedAppGateKeepersManager.getAppGateKeepersQueryResponse(applicationId);
                    if (appGateKeepersQueryResponse != null) {
                        FetchedAppGateKeepersManager.parseAppGateKeepersFromJSON(applicationId, appGateKeepersQueryResponse);
                        applicationContext.getSharedPreferences(FetchedAppGateKeepersManager.APP_GATEKEEPERS_PREFS_STORE, 0).edit().putString(str, appGateKeepersQueryResponse.toString()).apply();
                        Long unused = FetchedAppGateKeepersManager.timestamp = Long.valueOf(System.currentTimeMillis());
                    }
                    FetchedAppGateKeepersManager.pollCallbacks();
                    FetchedAppGateKeepersManager.isLoading.set(false);
                }
            });
        }
    }
}
