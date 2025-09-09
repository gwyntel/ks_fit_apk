package com.facebook;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.huawei.hms.push.AttributionReporter;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class AccessTokenManager {
    public static final String ACTION_CURRENT_ACCESS_TOKEN_CHANGED = "com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED";
    public static final String EXTRA_NEW_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN";
    public static final String EXTRA_OLD_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN";
    private static final String ME_PERMISSIONS_GRAPH_PATH = "me/permissions";
    public static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    public static final String TAG = "AccessTokenManager";
    private static final String TOKEN_EXTEND_GRAPH_PATH = "oauth/access_token";
    private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
    private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    private final LocalBroadcastManager localBroadcastManager;
    private AtomicBoolean tokenRefreshInProgress = new AtomicBoolean(false);
    private Date lastAttemptedTokenExtendDate = new Date(0);

    private static class RefreshResult {
        public String accessToken;
        public Long dataAccessExpirationTime;
        public int expiresAt;
        public String graphDomain;

        private RefreshResult() {
        }
    }

    AccessTokenManager(LocalBroadcastManager localBroadcastManager, AccessTokenCache accessTokenCache) {
        Validate.notNull(localBroadcastManager, "localBroadcastManager");
        Validate.notNull(accessTokenCache, "accessTokenCache");
        this.localBroadcastManager = localBroadcastManager;
        this.accessTokenCache = accessTokenCache;
    }

    private static GraphRequest createExtendAccessTokenRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle();
        bundle.putString("grant_type", "fb_extend_sso_token");
        bundle.putString("client_id", accessToken.getApplicationId());
        return new GraphRequest(accessToken, TOKEN_EXTEND_GRAPH_PATH, bundle, HttpMethod.GET, callback);
    }

    private static GraphRequest createGrantedPermissionsRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        return new GraphRequest(accessToken, ME_PERMISSIONS_GRAPH_PATH, new Bundle(), HttpMethod.GET, callback);
    }

    static AccessTokenManager getInstance() {
        if (instance == null) {
            synchronized (AccessTokenManager.class) {
                try {
                    if (instance == null) {
                        instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCurrentAccessTokenImpl(final AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback) {
        final AccessToken accessToken = this.currentAccessToken;
        if (accessToken == null) {
            if (accessTokenRefreshCallback != null) {
                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
            }
        } else {
            if (!this.tokenRefreshInProgress.compareAndSet(false, true)) {
                if (accessTokenRefreshCallback != null) {
                    accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("Refresh already in progress"));
                    return;
                }
                return;
            }
            this.lastAttemptedTokenExtendDate = new Date();
            final HashSet hashSet = new HashSet();
            final HashSet hashSet2 = new HashSet();
            final HashSet hashSet3 = new HashSet();
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final RefreshResult refreshResult = new RefreshResult();
            GraphRequestBatch graphRequestBatch = new GraphRequestBatch(createGrantedPermissionsRequest(accessToken, new GraphRequest.Callback() { // from class: com.facebook.AccessTokenManager.2
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    JSONArray jSONArrayOptJSONArray;
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    if (jSONObject == null || (jSONArrayOptJSONArray = jSONObject.optJSONArray("data")) == null) {
                        return;
                    }
                    atomicBoolean.set(true);
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                        if (jSONObjectOptJSONObject != null) {
                            String strOptString = jSONObjectOptJSONObject.optString(AttributionReporter.SYSTEM_PERMISSION);
                            String strOptString2 = jSONObjectOptJSONObject.optString("status");
                            if (!Utility.isNullOrEmpty(strOptString) && !Utility.isNullOrEmpty(strOptString2)) {
                                String lowerCase = strOptString2.toLowerCase(Locale.US);
                                if (lowerCase.equals("granted")) {
                                    hashSet.add(strOptString);
                                } else if (lowerCase.equals("declined")) {
                                    hashSet2.add(strOptString);
                                } else if (lowerCase.equals("expired")) {
                                    hashSet3.add(strOptString);
                                } else {
                                    Log.w(AccessTokenManager.TAG, "Unexpected status: " + lowerCase);
                                }
                            }
                        }
                    }
                }
            }), createExtendAccessTokenRequest(accessToken, new GraphRequest.Callback() { // from class: com.facebook.AccessTokenManager.3
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    if (jSONObject == null) {
                        return;
                    }
                    refreshResult.accessToken = jSONObject.optString("access_token");
                    refreshResult.expiresAt = jSONObject.optInt("expires_at");
                    refreshResult.dataAccessExpirationTime = Long.valueOf(jSONObject.optLong(AccessToken.DATA_ACCESS_EXPIRATION_TIME));
                    refreshResult.graphDomain = jSONObject.optString(NativeProtocol.RESULT_ARGS_GRAPH_DOMAIN, null);
                }
            }));
            graphRequestBatch.addCallback(new GraphRequestBatch.Callback() { // from class: com.facebook.AccessTokenManager.4
                @Override // com.facebook.GraphRequestBatch.Callback
                public void onBatchCompleted(GraphRequestBatch graphRequestBatch2) throws Throwable {
                    AccessToken accessToken2;
                    try {
                        if (AccessTokenManager.getInstance().getCurrentAccessToken() != null && AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() == accessToken.getUserId()) {
                            if (!atomicBoolean.get()) {
                                RefreshResult refreshResult2 = refreshResult;
                                if (refreshResult2.accessToken == null && refreshResult2.expiresAt == 0) {
                                    AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback2 = accessTokenRefreshCallback;
                                    if (accessTokenRefreshCallback2 != null) {
                                        accessTokenRefreshCallback2.OnTokenRefreshFailed(new FacebookException("Failed to refresh access token"));
                                    }
                                    AccessTokenManager.this.tokenRefreshInProgress.set(false);
                                    return;
                                }
                            }
                            String token = refreshResult.accessToken;
                            if (token == null) {
                                token = accessToken.getToken();
                            }
                            accessToken2 = accessToken;
                            AccessToken accessToken3 = new AccessToken(token, accessToken.getApplicationId(), accessToken.getUserId(), atomicBoolean.get() ? hashSet : accessToken.getPermissions(), atomicBoolean.get() ? hashSet2 : accessToken.getDeclinedPermissions(), atomicBoolean.get() ? hashSet3 : accessToken.getExpiredPermissions(), accessToken.getSource(), refreshResult.expiresAt != 0 ? new Date(refreshResult.expiresAt * 1000) : accessToken.getExpires(), new Date(), refreshResult.dataAccessExpirationTime != null ? new Date(1000 * refreshResult.dataAccessExpirationTime.longValue()) : accessToken.getDataAccessExpirationTime(), refreshResult.graphDomain);
                            try {
                                AccessTokenManager.getInstance().setCurrentAccessToken(accessToken2);
                                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                                AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback3 = accessTokenRefreshCallback;
                                if (accessTokenRefreshCallback3 != null) {
                                    accessTokenRefreshCallback3.OnTokenRefreshed(accessToken2);
                                    return;
                                }
                                return;
                            } catch (Throwable th) {
                                th = th;
                                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                                AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback4 = accessTokenRefreshCallback;
                                if (accessTokenRefreshCallback4 != null && accessToken2 != null) {
                                    accessTokenRefreshCallback4.OnTokenRefreshed(accessToken2);
                                }
                                throw th;
                            }
                        }
                        AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback5 = accessTokenRefreshCallback;
                        if (accessTokenRefreshCallback5 != null) {
                            accessTokenRefreshCallback5.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
                        }
                        AccessTokenManager.this.tokenRefreshInProgress.set(false);
                    } catch (Throwable th2) {
                        th = th2;
                        accessToken2 = null;
                    }
                }
            });
            graphRequestBatch.executeAsync();
        }
    }

    private void sendCurrentAccessTokenChangedBroadcastIntent(AccessToken accessToken, AccessToken accessToken2) {
        Intent intent = new Intent(FacebookSdk.getApplicationContext(), (Class<?>) CurrentAccessTokenExpirationBroadcastReceiver.class);
        intent.setAction(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        intent.putExtra(EXTRA_OLD_ACCESS_TOKEN, accessToken);
        intent.putExtra(EXTRA_NEW_ACCESS_TOKEN, accessToken2);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    private void setTokenExpirationBroadcastAlarm() {
        Context applicationContext = FacebookSdk.getApplicationContext();
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (!AccessToken.isCurrentAccessTokenActive() || currentAccessToken.getExpires() == null || alarmManager == null) {
            return;
        }
        Intent intent = new Intent(applicationContext, (Class<?>) CurrentAccessTokenExpirationBroadcastReceiver.class);
        intent.setAction(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        try {
            alarmManager.set(1, currentAccessToken.getExpires().getTime(), PendingIntent.getBroadcast(applicationContext, 0, intent, 0));
        } catch (Exception unused) {
        }
    }

    private boolean shouldExtendAccessToken() {
        if (this.currentAccessToken == null) {
            return false;
        }
        long time = new Date().getTime();
        return this.currentAccessToken.getSource().canExtendToken() && time - this.lastAttemptedTokenExtendDate.getTime() > 3600000 && time - this.currentAccessToken.getLastRefresh().getTime() > 86400000;
    }

    void currentAccessTokenChanged() {
        AccessToken accessToken = this.currentAccessToken;
        sendCurrentAccessTokenChangedBroadcastIntent(accessToken, accessToken);
    }

    void extendAccessTokenIfNeeded() {
        if (shouldExtendAccessToken()) {
            refreshCurrentAccessToken(null);
        }
    }

    AccessToken getCurrentAccessToken() {
        return this.currentAccessToken;
    }

    boolean loadCurrentAccessToken() {
        AccessToken accessTokenLoad = this.accessTokenCache.load();
        if (accessTokenLoad == null) {
            return false;
        }
        setCurrentAccessToken(accessTokenLoad, false);
        return true;
    }

    void refreshCurrentAccessToken(final AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            refreshCurrentAccessTokenImpl(accessTokenRefreshCallback);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.facebook.AccessTokenManager.1
                @Override // java.lang.Runnable
                public void run() {
                    AccessTokenManager.this.refreshCurrentAccessTokenImpl(accessTokenRefreshCallback);
                }
            });
        }
    }

    void setCurrentAccessToken(AccessToken accessToken) {
        setCurrentAccessToken(accessToken, true);
    }

    private void setCurrentAccessToken(AccessToken accessToken, boolean z2) {
        AccessToken accessToken2 = this.currentAccessToken;
        this.currentAccessToken = accessToken;
        this.tokenRefreshInProgress.set(false);
        this.lastAttemptedTokenExtendDate = new Date(0L);
        if (z2) {
            if (accessToken != null) {
                this.accessTokenCache.save(accessToken);
            } else {
                this.accessTokenCache.clear();
                Utility.clearFacebookCookies(FacebookSdk.getApplicationContext());
            }
        }
        if (Utility.areObjectsEqual(accessToken2, accessToken)) {
            return;
        }
        sendCurrentAccessTokenChangedBroadcastIntent(accessToken2, accessToken);
        setTokenExpirationBroadcastAlarm();
    }
}
