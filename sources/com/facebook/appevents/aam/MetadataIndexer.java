package com.facebook.appevents.aam;

import android.app.Activity;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class MetadataIndexer {
    private static final String TAG = "com.facebook.appevents.aam.MetadataIndexer";
    private static final AtomicBoolean enabled = new AtomicBoolean(false);

    public static void enable() {
        try {
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.aam.MetadataIndexer.1
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (AttributionIdentifiers.isTrackingLimited(FacebookSdk.getApplicationContext())) {
                        return;
                    }
                    MetadataIndexer.enabled.set(true);
                    MetadataIndexer.updateRules();
                }
            });
        } catch (Exception e2) {
            Utility.logd(TAG, e2);
        }
    }

    public static void onActivityResumed(Activity activity) {
        try {
            if (enabled.get() && !MetadataRule.getRules().isEmpty()) {
                MetadataViewObserver.startTrackingActivity(activity);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateRules() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String rawAamRules;
        FetchedAppSettings fetchedAppSettingsQueryAppSettings = FetchedAppSettingsManager.queryAppSettings(FacebookSdk.getApplicationId(), false);
        if (fetchedAppSettingsQueryAppSettings == null || (rawAamRules = fetchedAppSettingsQueryAppSettings.getRawAamRules()) == null) {
            return;
        }
        MetadataRule.updateRules(rawAamRules);
    }
}
