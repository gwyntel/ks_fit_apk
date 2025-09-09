package com.facebook.internal.instrument;

import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.instrument.crashreport.CrashHandler;
import com.facebook.internal.instrument.errorreport.ErrorReportHandler;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class InstrumentManager {
    public static void start() {
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            FeatureManager.checkFeature(FeatureManager.Feature.CrashReport, new FeatureManager.Callback() { // from class: com.facebook.internal.instrument.InstrumentManager.1
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        CrashHandler.enable();
                    }
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.ErrorReport, new FeatureManager.Callback() { // from class: com.facebook.internal.instrument.InstrumentManager.2
                @Override // com.facebook.internal.FeatureManager.Callback
                public void onCompleted(boolean z2) {
                    if (z2) {
                        ErrorReportHandler.enable();
                    }
                }
            });
        }
    }
}
