package com.aliyun.iot.aep.sdk.bridge.core.context;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes3.dex */
public interface OnActivityResultManager {

    public interface OnActivityResultListener {
        void onActivityResult(Activity activity, int i2, int i3, Intent intent);
    }

    void addOnActivityResultListener(OnActivityResultListener onActivityResultListener);

    void removeOnActivityResultListener(OnActivityResultListener onActivityResultListener);
}
