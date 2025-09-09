package com.aliyun.iot.aep.sdk.bridge.core.context;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes3.dex */
public interface OnNewIntentManager {

    public interface OnNewIntentListener {
        void onNewIntent(Activity activity, Intent intent);
    }

    void addOnNewIntentListener(OnNewIntentListener onNewIntentListener);

    void removeOnNewIntentListener(OnNewIntentListener onNewIntentListener);
}
