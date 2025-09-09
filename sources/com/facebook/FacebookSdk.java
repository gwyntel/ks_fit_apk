package com.facebook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.AppEventsManager;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.LockOnGetVariable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.instrument.InstrumentManager;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;

/* loaded from: classes3.dex */
public final class FacebookSdk {
    public static final String ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY = "com.facebook.sdk.AdvertiserIDCollectionEnabled";
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    public static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    public static final String AUTO_INIT_ENABLED_PROPERTY = "com.facebook.sdk.AutoInitEnabled";
    public static final String AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY = "com.facebook.sdk.AutoLogAppEventsEnabled";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized. Call FacebookSdk.setCallbackRequestCodeOffset inside your Application.onCreate method";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CALLBACK_OFFSET_PROPERTY = "com.facebook.sdk.CallbackOffset";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    public static final String CODELESS_DEBUG_LOG_ENABLED_PROPERTY = "com.facebook.sdk.CodelessDebugLogEnabled";
    private static final String FB_GG = "fb.gg";
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = "com.facebook.FacebookSdk";
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;

    @Nullable
    private static volatile String applicationName;
    private static LockOnGetVariable<File> cacheDir;
    private static volatile Boolean codelessDebugLogEnabled;
    private static Executor executor;
    private static Boolean sdkFullyInitialized;
    private static Boolean sdkInitialized;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet<>(Arrays.asList(LoggingBehavior.DEVELOPER_ERRORS));
    private static final String FACEBOOK_COM = "facebook.com";
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static AtomicLong onProgressThreshold = new AtomicLong(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final int DEFAULT_CALLBACK_REQUEST_CODE_OFFSET = 64206;
    private static int callbackRequestCodeOffset = DEFAULT_CALLBACK_REQUEST_CODE_OFFSET;
    private static final Object LOCK = new Object();
    private static String graphApiVersion = ServerProtocol.getDefaultAPIVersion();

    public interface InitializeCallback {
        void onInitialized();
    }

    static {
        Boolean bool = Boolean.FALSE;
        sdkInitialized = bool;
        sdkFullyInitialized = bool;
    }

    public static void addLoggingBehavior(LoggingBehavior loggingBehavior) {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            hashSet.add(loggingBehavior);
            updateGraphDebugBehavior();
        }
    }

    public static void clearLoggingBehaviors() {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            hashSet.clear();
        }
    }

    public static void fullyInitialize() {
        sdkFullyInitialized = Boolean.TRUE;
    }

    public static boolean getAdvertiserIDCollectionEnabled() {
        return UserSettingsManager.getAdvertiserIDCollectionEnabled();
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    @Nullable
    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static String getApplicationSignature(Context context) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageManager packageManager;
        Validate.sdkInitialized();
        if (context == null || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr != null && signatureArr.length != 0) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(packageInfo.signatures[0].toByteArray());
                return Base64.encodeToString(messageDigest.digest(), 9);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
        }
        return null;
    }

    public static boolean getAutoInitEnabled() {
        return UserSettingsManager.getAutoInitEnabled();
    }

    public static boolean getAutoLogAppEventsEnabled() {
        return UserSettingsManager.getAutoLogAppEventsEnabled();
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return cacheDir.getValue();
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static boolean getCodelessDebugLogEnabled() {
        Validate.sdkInitialized();
        return codelessDebugLogEnabled.booleanValue();
    }

    public static boolean getCodelessSetupEnabled() {
        return UserSettingsManager.getCodelessSetupEnabled();
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            try {
                if (executor == null) {
                    executor = AsyncTask.THREAD_POOL_EXECUTOR;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return executor;
    }

    public static String getFacebookDomain() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        String graphDomain = currentAccessToken != null ? currentAccessToken.getGraphDomain() : null;
        return graphDomain == null ? facebookDomain : graphDomain.equals("gaming") ? facebookDomain.replace(FACEBOOK_COM, FB_GG) : facebookDomain;
    }

    public static String getGraphApiVersion() {
        Utility.logd(TAG, String.format("getGraphApiVersion: %s", graphApiVersion));
        return graphApiVersion;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> setUnmodifiableSet;
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(hashSet));
        }
        return setUnmodifiableSet;
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static String getSdkVersion() {
        return FacebookSdkVersion.BUILD;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static boolean isFacebookRequestCode(int i2) {
        int i3 = callbackRequestCodeOffset;
        return i2 >= i3 && i2 < i3 + 100;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static synchronized boolean isFullyInitialized() {
        return sdkFullyInitialized.booleanValue();
    }

    public static synchronized boolean isInitialized() {
        return sdkInitialized.booleanValue();
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior loggingBehavior) {
        boolean z2;
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            try {
                z2 = isDebugEnabled() && hashSet.contains(loggingBehavior);
            } finally {
            }
        }
        return z2;
    }

    static void loadDefaultsFromMetadata(Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData == null) {
                return;
            }
            if (applicationId == null) {
                Object obj = applicationInfo.metaData.get(APPLICATION_ID_PROPERTY);
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.toLowerCase(Locale.ROOT).startsWith("fb")) {
                        applicationId = str.substring(2);
                    } else {
                        applicationId = str;
                    }
                } else if (obj instanceof Integer) {
                    throw new FacebookException("App Ids cannot be directly placed in the manifest.They must be prefixed by 'fb' or be placed in the string resource file.");
                }
            }
            if (applicationName == null) {
                applicationName = applicationInfo.metaData.getString(APPLICATION_NAME_PROPERTY);
            }
            if (appClientToken == null) {
                appClientToken = applicationInfo.metaData.getString(CLIENT_TOKEN_PROPERTY);
            }
            if (callbackRequestCodeOffset == DEFAULT_CALLBACK_REQUEST_CODE_OFFSET) {
                callbackRequestCodeOffset = applicationInfo.metaData.getInt(CALLBACK_OFFSET_PROPERTY, DEFAULT_CALLBACK_REQUEST_CODE_OFFSET);
            }
            if (codelessDebugLogEnabled == null) {
                codelessDebugLogEnabled = Boolean.valueOf(applicationInfo.metaData.getBoolean(CODELESS_DEBUG_LOG_ENABLED_PROPERTY, false));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    static void publishInstallAndWaitForResponse(Context context, String str) {
        try {
            if (context == null || str == null) {
                throw new IllegalArgumentException("Both context and applicationId must be non-null");
            }
            AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
            String str2 = str + "ping";
            long j2 = sharedPreferences.getLong(str2, 0L);
            try {
                GraphRequest graphRequestNewPostRequest = GraphRequest.newPostRequest(null, String.format(PUBLISH_ACTIVITY_PATH, str), AppEventsLoggerUtility.getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT, attributionIdentifiers, AppEventsLogger.getAnonymousAppDeviceGUID(context), getLimitEventAndDataUsage(context), context), null);
                if (j2 == 0 && graphRequestNewPostRequest.executeAndWait().getError() == null) {
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putLong(str2, System.currentTimeMillis());
                    editorEdit.apply();
                }
            } catch (JSONException e2) {
                throw new FacebookException("An error occurred while publishing install.", e2);
            }
        } catch (Exception e3) {
            Utility.logd("Facebook-publish", e3);
        }
    }

    public static void publishInstallAsync(Context context, final String str) {
        final Context applicationContext2 = context.getApplicationContext();
        getExecutor().execute(new Runnable() { // from class: com.facebook.FacebookSdk.5
            @Override // java.lang.Runnable
            public void run() {
                FacebookSdk.publishInstallAndWaitForResponse(applicationContext2, str);
            }
        });
    }

    public static void removeLoggingBehavior(LoggingBehavior loggingBehavior) {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        synchronized (hashSet) {
            hashSet.remove(loggingBehavior);
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context context, int i2) {
        sdkInitialize(context, i2, null);
    }

    public static void setAdvertiserIDCollectionEnabled(boolean z2) {
        UserSettingsManager.setAdvertiserIDCollectionEnabled(z2);
    }

    public static void setApplicationId(String str) {
        applicationId = str;
    }

    public static void setApplicationName(String str) {
        applicationName = str;
    }

    public static void setAutoInitEnabled(boolean z2) {
        UserSettingsManager.setAutoInitEnabled(z2);
        if (z2) {
            fullyInitialize();
        }
    }

    public static void setAutoLogAppEventsEnabled(boolean z2) {
        UserSettingsManager.setAutoLogAppEventsEnabled(z2);
        if (z2) {
            ActivityLifecycleTracker.startTracking((Application) applicationContext, applicationId);
        }
    }

    public static void setCacheDir(File file) {
        cacheDir = new LockOnGetVariable<>(file);
    }

    public static void setClientToken(String str) {
        appClientToken = str;
    }

    public static void setCodelessDebugLogEnabled(boolean z2) {
        codelessDebugLogEnabled = Boolean.valueOf(z2);
    }

    public static void setExecutor(Executor executor2) {
        Validate.notNull(executor2, "executor");
        synchronized (LOCK) {
            executor = executor2;
        }
    }

    public static void setFacebookDomain(String str) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = str;
    }

    public static void setGraphApiVersion(String str) {
        Log.w(TAG, "WARNING: Calling setGraphApiVersion from non-DEBUG code.");
        if (Utility.isNullOrEmpty(str) || graphApiVersion.equals(str)) {
            return;
        }
        graphApiVersion = str;
    }

    public static void setIsDebugEnabled(boolean z2) {
        isDebugEnabled = z2;
    }

    public static void setLegacyTokenUpgradeSupported(boolean z2) {
        isLegacyTokenUpgradeSupported = z2;
    }

    public static void setLimitEventAndDataUsage(Context context, boolean z2) {
        context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", z2).apply();
    }

    public static void setOnProgressThreshold(long j2) {
        onProgressThreshold.set(j2);
    }

    private static void updateGraphDebugBehavior() {
        HashSet<LoggingBehavior> hashSet = loggingBehaviors;
        if (hashSet.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            LoggingBehavior loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
            if (hashSet.contains(loggingBehavior)) {
                return;
            }
            hashSet.add(loggingBehavior);
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context context, int i2, InitializeCallback initializeCallback) {
        try {
            if (sdkInitialized.booleanValue() && i2 != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            }
            if (i2 >= 0) {
                callbackRequestCodeOffset = i2;
                sdkInitialize(context, initializeCallback);
            } else {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            }
        } finally {
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context context) {
        sdkInitialize(context, (InitializeCallback) null);
    }

    @Deprecated
    public static synchronized void sdkInitialize(final Context context, final InitializeCallback initializeCallback) {
        if (sdkInitialized.booleanValue()) {
            if (initializeCallback != null) {
                initializeCallback.onInitialized();
            }
            return;
        }
        Validate.notNull(context, "applicationContext");
        Validate.hasFacebookActivity(context, false);
        Validate.hasInternetPermissions(context, false);
        applicationContext = context.getApplicationContext();
        AppEventsLogger.getAnonymousAppDeviceGUID(context);
        loadDefaultsFromMetadata(applicationContext);
        if (!Utility.isNullOrEmpty(applicationId)) {
            sdkInitialized = Boolean.TRUE;
            if (getAutoInitEnabled()) {
                fullyInitialize();
            }
            if ((applicationContext instanceof Application) && UserSettingsManager.getAutoLogAppEventsEnabled()) {
                ActivityLifecycleTracker.startTracking((Application) applicationContext, applicationId);
            }
            FetchedAppSettingsManager.loadAppSettingsAsync();
            NativeProtocol.updateAllAvailableProtocolVersionsAsync();
            UserSettingsManager.logIfAutoAppLinkEnabled();
            BoltsMeasurementEventListener.getInstance(applicationContext);
            cacheDir = new LockOnGetVariable<>((Callable) new Callable<File>() { // from class: com.facebook.FacebookSdk.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public File call() throws Exception {
                    return FacebookSdk.applicationContext.getCacheDir();
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.Instrument, new FeatureManager.Callback() { // from class: com.facebook.FacebookSdk.2
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        InstrumentManager.start();
                    }
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.AppEvents, new FeatureManager.Callback() { // from class: com.facebook.FacebookSdk.3
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        AppEventsManager.start();
                    }
                }
            });
            getExecutor().execute(new FutureTask(new Callable<Void>() { // from class: com.facebook.FacebookSdk.4
                @Override // java.util.concurrent.Callable
                public Void call() throws Exception {
                    AccessTokenManager.getInstance().loadCurrentAccessToken();
                    ProfileManager.getInstance().loadCurrentProfile();
                    if (AccessToken.isCurrentAccessTokenActive() && Profile.getCurrentProfile() == null) {
                        Profile.fetchProfileForCurrentAccessToken();
                    }
                    InitializeCallback initializeCallback2 = initializeCallback;
                    if (initializeCallback2 != null) {
                        initializeCallback2.onInitialized();
                    }
                    AppEventsLogger.initializeLib(FacebookSdk.applicationContext, FacebookSdk.applicationId);
                    AppEventsLogger.newLogger(context.getApplicationContext()).flush();
                    return null;
                }
            }));
            return;
        }
        throw new FacebookException("A valid Facebook app id must be set in the AndroidManifest.xml or set by calling FacebookSdk.setApplicationId before initializing the sdk.");
    }
}
