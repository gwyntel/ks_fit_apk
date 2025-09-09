package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
final class UserSettingsManager {
    private static final String ADVERTISERID_COLLECTION_FALSE_WARNING = "The value for AdvertiserIDCollectionEnabled is currently set to FALSE so you're sending app events without collecting Advertiser ID. This can affect the quality of your advertising and analytics results.";
    private static final String ADVERTISERID_COLLECTION_NOT_SET_WARNING = "You haven't set a value for AdvertiserIDCollectionEnabled. Set the flag to TRUE if you want to collect Advertiser ID for better advertising and analytics results. To request user consent before collecting data, set the flag value to FALSE, then change to TRUE once user consent is received. Learn more: https://developers.facebook.com/docs/app-events/getting-started-app-events-android#disable-auto-events.";
    private static final String ADVERTISER_ID_KEY = "advertiser_id";
    private static final String APPLICATION_FIELDS = "fields";
    private static final String AUTOLOG_APPEVENT_NOT_SET_WARNING = "Please set a value for AutoLogAppEventsEnabled. Set the flag to TRUE if you want to collect app install, app launch and in-app purchase events automatically. To request user consent before collecting data, set the flag value to FALSE, then change to TRUE once user consent is received. Learn more: https://developers.facebook.com/docs/app-events/getting-started-app-events-android#disable-auto-events.";
    private static final String AUTO_APP_LINK_WARNING = "You haven't set the Auto App Link URL scheme: fb<YOUR APP ID> in AndroidManifest";
    private static final String LAST_TIMESTAMP = "last_timestamp";
    private static final String TAG = "com.facebook.UserSettingsManager";
    private static final long TIMEOUT_7D = 604800000;
    private static final String USER_SETTINGS = "com.facebook.sdk.USER_SETTINGS";
    private static final String USER_SETTINGS_BITMASK = "com.facebook.sdk.USER_SETTINGS_BITMASK";
    private static final String VALUE = "value";
    private static SharedPreferences userSettingPref;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static AtomicBoolean isFetchingCodelessStatus = new AtomicBoolean(false);
    private static UserSetting autoInitEnabled = new UserSetting(true, FacebookSdk.AUTO_INIT_ENABLED_PROPERTY);
    private static UserSetting autoLogAppEventsEnabled = new UserSetting(true, FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY);
    private static UserSetting advertiserIDCollectionEnabled = new UserSetting(true, FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY);
    private static final String EVENTS_CODELESS_SETUP_ENABLED = "auto_event_setup_enabled";
    private static UserSetting codelessSetupEnabled = new UserSetting(false, EVENTS_CODELESS_SETUP_ENABLED);

    private static class UserSetting {
        boolean defaultVal;
        String key;
        long lastTS;
        Boolean value;

        UserSetting(boolean z2, String str) {
            this.defaultVal = z2;
            this.key = str;
        }

        boolean getValue() {
            Boolean bool = this.value;
            return bool == null ? this.defaultVal : bool.booleanValue();
        }
    }

    UserSettingsManager() {
    }

    public static boolean getAdvertiserIDCollectionEnabled() throws JSONException, PackageManager.NameNotFoundException {
        initializeIfNotInitialized();
        return advertiserIDCollectionEnabled.getValue();
    }

    public static boolean getAutoInitEnabled() throws JSONException, PackageManager.NameNotFoundException {
        initializeIfNotInitialized();
        return autoInitEnabled.getValue();
    }

    public static boolean getAutoLogAppEventsEnabled() throws JSONException, PackageManager.NameNotFoundException {
        initializeIfNotInitialized();
        return autoLogAppEventsEnabled.getValue();
    }

    public static boolean getCodelessSetupEnabled() throws JSONException, PackageManager.NameNotFoundException {
        initializeIfNotInitialized();
        return codelessSetupEnabled.getValue();
    }

    private static void initializeCodelessSetupEnabledAsync() {
        readSettingFromCache(codelessSetupEnabled);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        UserSetting userSetting = codelessSetupEnabled;
        if (userSetting.value == null || jCurrentTimeMillis - userSetting.lastTS >= TIMEOUT_7D) {
            userSetting.value = null;
            userSetting.lastTS = 0L;
            if (isFetchingCodelessStatus.compareAndSet(false, true)) {
                FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.UserSettingsManager.1
                    @Override // java.lang.Runnable
                    public void run() throws JSONException {
                        FetchedAppSettings fetchedAppSettingsQueryAppSettings;
                        if (UserSettingsManager.advertiserIDCollectionEnabled.getValue() && (fetchedAppSettingsQueryAppSettings = FetchedAppSettingsManager.queryAppSettings(FacebookSdk.getApplicationId(), false)) != null && fetchedAppSettingsQueryAppSettings.getCodelessEventsEnabled()) {
                            AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                            if (((attributionIdentifiers == null || attributionIdentifiers.getAndroidAdvertiserId() == null) ? null : attributionIdentifiers.getAndroidAdvertiserId()) != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString(UserSettingsManager.ADVERTISER_ID_KEY, attributionIdentifiers.getAndroidAdvertiserId());
                                bundle.putString("fields", UserSettingsManager.EVENTS_CODELESS_SETUP_ENABLED);
                                GraphRequest graphRequestNewGraphPathRequest = GraphRequest.newGraphPathRequest(null, FacebookSdk.getApplicationId(), null);
                                graphRequestNewGraphPathRequest.setSkipClientToken(true);
                                graphRequestNewGraphPathRequest.setParameters(bundle);
                                JSONObject jSONObject = graphRequestNewGraphPathRequest.executeAndWait().getJSONObject();
                                if (jSONObject != null) {
                                    UserSettingsManager.codelessSetupEnabled.value = Boolean.valueOf(jSONObject.optBoolean(UserSettingsManager.EVENTS_CODELESS_SETUP_ENABLED, false));
                                    UserSettingsManager.codelessSetupEnabled.lastTS = jCurrentTimeMillis;
                                    UserSettingsManager.writeSettingToCache(UserSettingsManager.codelessSetupEnabled);
                                }
                            }
                        }
                        UserSettingsManager.isFetchingCodelessStatus.set(false);
                    }
                });
            }
        }
    }

    public static void initializeIfNotInitialized() throws JSONException, PackageManager.NameNotFoundException {
        if (FacebookSdk.isInitialized() && isInitialized.compareAndSet(false, true)) {
            userSettingPref = FacebookSdk.getApplicationContext().getSharedPreferences(USER_SETTINGS, 0);
            initializeUserSetting(autoLogAppEventsEnabled, advertiserIDCollectionEnabled, autoInitEnabled);
            initializeCodelessSetupEnabledAsync();
            logWarnings();
            logIfSDKSettingsChanged();
        }
    }

    private static void initializeUserSetting(UserSetting... userSettingArr) throws JSONException, PackageManager.NameNotFoundException {
        for (UserSetting userSetting : userSettingArr) {
            if (userSetting == codelessSetupEnabled) {
                initializeCodelessSetupEnabledAsync();
            } else if (userSetting.value == null) {
                readSettingFromCache(userSetting);
                if (userSetting.value == null) {
                    loadSettingFromManifest(userSetting);
                }
            } else {
                writeSettingToCache(userSetting);
            }
        }
    }

    private static void loadSettingFromManifest(UserSetting userSetting) throws PackageManager.NameNotFoundException {
        Bundle bundle;
        validateInitialized();
        try {
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || !bundle.containsKey(userSetting.key)) {
                return;
            }
            userSetting.value = Boolean.valueOf(applicationInfo.metaData.getBoolean(userSetting.key, userSetting.defaultVal));
        } catch (PackageManager.NameNotFoundException e2) {
            Utility.logd(TAG, e2);
        }
    }

    static void logIfAutoAppLinkEnabled() throws PackageManager.NameNotFoundException {
        Bundle bundle;
        try {
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null || !bundle.getBoolean("com.facebook.sdk.AutoAppLinkEnabled", false)) {
                return;
            }
            InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(applicationContext);
            Bundle bundle2 = new Bundle();
            if (!Utility.isAutoAppLinkSetup()) {
                bundle2.putString("SchemeWarning", AUTO_APP_LINK_WARNING);
                Log.w(TAG, AUTO_APP_LINK_WARNING);
            }
            internalAppEventsLogger.logEvent("fb_auto_applink", bundle2);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    private static void logIfSDKSettingsChanged() throws PackageManager.NameNotFoundException {
        int i2;
        ApplicationInfo applicationInfo;
        if (isInitialized.get() && FacebookSdk.isInitialized()) {
            Context applicationContext = FacebookSdk.getApplicationContext();
            int i3 = (autoInitEnabled.getValue() ? 1 : 0) | ((autoLogAppEventsEnabled.getValue() ? 1 : 0) << 1) | ((advertiserIDCollectionEnabled.getValue() ? 1 : 0) << 2);
            int i4 = 0;
            int i5 = userSettingPref.getInt(USER_SETTINGS_BITMASK, 0);
            if (i5 != i3) {
                userSettingPref.edit().putInt(USER_SETTINGS_BITMASK, i3).commit();
                try {
                    applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
                } catch (PackageManager.NameNotFoundException unused) {
                }
                if (applicationInfo == null || applicationInfo.metaData == null) {
                    i2 = 0;
                } else {
                    String[] strArr = {FacebookSdk.AUTO_INIT_ENABLED_PROPERTY, FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY, FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY};
                    boolean[] zArr = {true, true, true};
                    int i6 = 0;
                    i2 = 0;
                    while (i4 < 3) {
                        try {
                            i2 |= (applicationInfo.metaData.containsKey(strArr[i4]) ? 1 : 0) << i4;
                            i6 |= (applicationInfo.metaData.getBoolean(strArr[i4], zArr[i4]) ? 1 : 0) << i4;
                            i4++;
                        } catch (PackageManager.NameNotFoundException unused2) {
                        }
                    }
                    i4 = i6;
                }
                InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(applicationContext);
                Bundle bundle = new Bundle();
                bundle.putInt("usage", i2);
                bundle.putInt("initial", i4);
                bundle.putInt("previous", i5);
                bundle.putInt("current", i3);
                internalAppEventsLogger.logEventImplicitly("fb_sdk_settings_changed", bundle);
            }
        }
    }

    private static void logWarnings() throws PackageManager.NameNotFoundException {
        Bundle bundle;
        try {
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null) {
                return;
            }
            if (!bundle.containsKey(FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY)) {
                Log.w(TAG, AUTOLOG_APPEVENT_NOT_SET_WARNING);
            }
            if (!applicationInfo.metaData.containsKey(FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY)) {
                Log.w(TAG, ADVERTISERID_COLLECTION_NOT_SET_WARNING);
            }
            if (getAdvertiserIDCollectionEnabled()) {
                return;
            }
            Log.w(TAG, ADVERTISERID_COLLECTION_FALSE_WARNING);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    private static void readSettingFromCache(UserSetting userSetting) {
        validateInitialized();
        try {
            String string = userSettingPref.getString(userSetting.key, "");
            if (string.isEmpty()) {
                return;
            }
            JSONObject jSONObject = new JSONObject(string);
            userSetting.value = Boolean.valueOf(jSONObject.getBoolean("value"));
            userSetting.lastTS = jSONObject.getLong(LAST_TIMESTAMP);
        } catch (JSONException e2) {
            Utility.logd(TAG, e2);
        }
    }

    public static void setAdvertiserIDCollectionEnabled(boolean z2) {
        advertiserIDCollectionEnabled.value = Boolean.valueOf(z2);
        advertiserIDCollectionEnabled.lastTS = System.currentTimeMillis();
        if (isInitialized.get()) {
            writeSettingToCache(advertiserIDCollectionEnabled);
        } else {
            initializeIfNotInitialized();
        }
    }

    public static void setAutoInitEnabled(boolean z2) {
        autoInitEnabled.value = Boolean.valueOf(z2);
        autoInitEnabled.lastTS = System.currentTimeMillis();
        if (isInitialized.get()) {
            writeSettingToCache(autoInitEnabled);
        } else {
            initializeIfNotInitialized();
        }
    }

    public static void setAutoLogAppEventsEnabled(boolean z2) {
        autoLogAppEventsEnabled.value = Boolean.valueOf(z2);
        autoLogAppEventsEnabled.lastTS = System.currentTimeMillis();
        if (isInitialized.get()) {
            writeSettingToCache(autoLogAppEventsEnabled);
        } else {
            initializeIfNotInitialized();
        }
    }

    private static void validateInitialized() {
        if (!isInitialized.get()) {
            throw new FacebookSdkNotInitializedException("The UserSettingManager has not been initialized successfully");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeSettingToCache(UserSetting userSetting) throws JSONException {
        validateInitialized();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("value", userSetting.value);
            jSONObject.put(LAST_TIMESTAMP, userSetting.lastTS);
            userSettingPref.edit().putString(userSetting.key, jSONObject.toString()).commit();
            logIfSDKSettingsChanged();
        } catch (Exception e2) {
            Utility.logd(TAG, e2);
        }
    }
}
