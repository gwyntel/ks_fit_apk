package com.facebook.appevents.codeless;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.codeless.ViewIndexingTrigger;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import com.umeng.analytics.pro.bc;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class CodelessManager {

    @Nullable
    private static String deviceSessionID;
    private static Boolean isAppIndexingEnabled;
    private static volatile Boolean isCheckingSession;

    @Nullable
    private static SensorManager sensorManager;

    @Nullable
    private static ViewIndexer viewIndexer;
    private static final ViewIndexingTrigger viewIndexingTrigger = new ViewIndexingTrigger();
    private static final AtomicBoolean isCodelessEnabled = new AtomicBoolean(true);

    static {
        Boolean bool = Boolean.FALSE;
        isAppIndexingEnabled = bool;
        isCheckingSession = bool;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkCodelessSession(final String str) {
        if (isCheckingSession.booleanValue()) {
            return;
        }
        isCheckingSession = Boolean.TRUE;
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.CodelessManager.2
            @Override // java.lang.Runnable
            public void run() {
                GraphRequest graphRequestNewPostRequest = GraphRequest.newPostRequest(null, String.format(Locale.US, "%s/app_indexing_session", str), null, null);
                Bundle parameters = graphRequestNewPostRequest.getParameters();
                if (parameters == null) {
                    parameters = new Bundle();
                }
                AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                JSONArray jSONArray = new JSONArray();
                String str2 = Build.MODEL;
                if (str2 == null) {
                    str2 = "";
                }
                jSONArray.put(str2);
                if (attributionIdentifiers == null || attributionIdentifiers.getAndroidAdvertiserId() == null) {
                    jSONArray.put("");
                } else {
                    jSONArray.put(attributionIdentifiers.getAndroidAdvertiserId());
                }
                jSONArray.put("0");
                jSONArray.put(AppEventUtility.isEmulator() ? "1" : "0");
                Locale currentLocale = Utility.getCurrentLocale();
                jSONArray.put(currentLocale.getLanguage() + OpenAccountUIConstants.UNDER_LINE + currentLocale.getCountry());
                String string = jSONArray.toString();
                parameters.putString(Constants.DEVICE_SESSION_ID, CodelessManager.getCurrentDeviceSessionID());
                parameters.putString(Constants.EXTINFO, string);
                graphRequestNewPostRequest.setParameters(parameters);
                JSONObject jSONObject = graphRequestNewPostRequest.executeAndWait().getJSONObject();
                Boolean unused = CodelessManager.isAppIndexingEnabled = Boolean.valueOf(jSONObject != null && jSONObject.optBoolean(Constants.APP_INDEXING_ENABLED, false));
                if (!CodelessManager.isAppIndexingEnabled.booleanValue()) {
                    String unused2 = CodelessManager.deviceSessionID = null;
                } else if (CodelessManager.viewIndexer != null) {
                    CodelessManager.viewIndexer.schedule();
                }
                Boolean unused3 = CodelessManager.isCheckingSession = Boolean.FALSE;
            }
        });
    }

    public static void disable() {
        isCodelessEnabled.set(false);
    }

    public static void enable() {
        isCodelessEnabled.set(true);
    }

    static String getCurrentDeviceSessionID() {
        if (deviceSessionID == null) {
            deviceSessionID = UUID.randomUUID().toString();
        }
        return deviceSessionID;
    }

    static boolean getIsAppIndexingEnabled() {
        return isAppIndexingEnabled.booleanValue();
    }

    public static void onActivityDestroyed(Activity activity) {
        CodelessMatcher.getInstance().destroy(activity);
    }

    public static void onActivityPaused(Activity activity) {
        if (isCodelessEnabled.get()) {
            CodelessMatcher.getInstance().remove(activity);
            ViewIndexer viewIndexer2 = viewIndexer;
            if (viewIndexer2 != null) {
                viewIndexer2.unschedule();
            }
            SensorManager sensorManager2 = sensorManager;
            if (sensorManager2 != null) {
                sensorManager2.unregisterListener(viewIndexingTrigger);
            }
        }
    }

    public static void onActivityResumed(Activity activity) {
        if (isCodelessEnabled.get()) {
            CodelessMatcher.getInstance().add(activity);
            Context applicationContext = activity.getApplicationContext();
            final String applicationId = FacebookSdk.getApplicationId();
            final FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(applicationId);
            if (appSettingsWithoutQuery == null || !appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                return;
            }
            SensorManager sensorManager2 = (SensorManager) applicationContext.getSystemService(bc.ac);
            sensorManager = sensorManager2;
            if (sensorManager2 == null) {
                return;
            }
            Sensor defaultSensor = sensorManager2.getDefaultSensor(1);
            viewIndexer = new ViewIndexer(activity);
            ViewIndexingTrigger viewIndexingTrigger2 = viewIndexingTrigger;
            viewIndexingTrigger2.setOnShakeListener(new ViewIndexingTrigger.OnShakeListener() { // from class: com.facebook.appevents.codeless.CodelessManager.1
                @Override // com.facebook.appevents.codeless.ViewIndexingTrigger.OnShakeListener
                public void onShake() {
                    FetchedAppSettings fetchedAppSettings = appSettingsWithoutQuery;
                    boolean z2 = fetchedAppSettings != null && fetchedAppSettings.getCodelessEventsEnabled();
                    boolean codelessSetupEnabled = FacebookSdk.getCodelessSetupEnabled();
                    if (z2 && codelessSetupEnabled) {
                        CodelessManager.checkCodelessSession(applicationId);
                    }
                }
            });
            sensorManager.registerListener(viewIndexingTrigger2, defaultSensor, 2);
            if (appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                viewIndexer.schedule();
            }
        }
    }

    static void updateAppIndexing(Boolean bool) {
        isAppIndexingEnabled = bool;
    }
}
