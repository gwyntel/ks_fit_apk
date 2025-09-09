package com.facebook.appevents;

import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.appevents.aam.MetadataIndexer;
import com.facebook.appevents.eventdeactivation.EventDeactivationManager;
import com.facebook.appevents.ml.ModelManager;
import com.facebook.appevents.restrictivedatafilter.RestrictiveDataManager;
import com.facebook.internal.FeatureManager;
import org.json.JSONException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class AppEventsManager {
    public static void start() {
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            FeatureManager.checkFeature(FeatureManager.Feature.AAM, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager.1
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        MetadataIndexer.enable();
                    }
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.RestrictiveDataFiltering, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager.2
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) throws JSONException {
                    if (z2) {
                        RestrictiveDataManager.enable();
                    }
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.PrivacyProtection, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager.3
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        ModelManager.enable();
                    }
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.EventDeactivation, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager.4
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        EventDeactivationManager.enable();
                    }
                }
            });
        }
    }
}
