package com.facebook.internal;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.facebook.FacebookSdk;

/* loaded from: classes3.dex */
public class InstallReferrerUtil {
    private static final String IS_REFERRER_UPDATED = "is_referrer_updated";

    public interface Callback {
        void onReceiveReferrerUrl(String str);
    }

    private InstallReferrerUtil() {
    }

    private static boolean isUpdated() {
        return FacebookSdk.getApplicationContext().getSharedPreferences(FacebookSdk.APP_EVENT_PREFERENCES, 0).getBoolean(IS_REFERRER_UPDATED, false);
    }

    private static void tryConnectReferrerInfo(final Callback callback) {
        final InstallReferrerClient installReferrerClientBuild = InstallReferrerClient.newBuilder(FacebookSdk.getApplicationContext()).build();
        installReferrerClientBuild.startConnection(new InstallReferrerStateListener() { // from class: com.facebook.internal.InstallReferrerUtil.1
            @Override // com.android.installreferrer.api.InstallReferrerStateListener
            public void onInstallReferrerServiceDisconnected() {
            }

            @Override // com.android.installreferrer.api.InstallReferrerStateListener
            public void onInstallReferrerSetupFinished(int i2) {
                if (i2 != 0) {
                    if (i2 != 2) {
                        return;
                    }
                    InstallReferrerUtil.updateReferrer();
                    return;
                }
                try {
                    String installReferrer = installReferrerClientBuild.getInstallReferrer().getInstallReferrer();
                    if (installReferrer != null && (installReferrer.contains("fb") || installReferrer.contains("facebook"))) {
                        callback.onReceiveReferrerUrl(installReferrer);
                    }
                    InstallReferrerUtil.updateReferrer();
                } catch (Exception unused) {
                }
            }
        });
    }

    public static void tryUpdateReferrerInfo(Callback callback) {
        if (isUpdated()) {
            return;
        }
        tryConnectReferrerInfo(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateReferrer() {
        FacebookSdk.getApplicationContext().getSharedPreferences(FacebookSdk.APP_EVENT_PREFERENCES, 0).edit().putBoolean(IS_REFERRER_UPDATED, true).apply();
    }
}
