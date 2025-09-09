package com.aliyun.iot.aep.sdk.bridge.core.context;

import android.app.Activity;

/* loaded from: classes3.dex */
public interface ActivityLifeCircleManager {

    public interface ActivityLifeCircleListener {
        void onPause(Activity activity);

        void onResume(Activity activity);

        void onStart(Activity activity);

        void onStop(Activity activity);
    }

    void addActivityLifeCircleListener(ActivityLifeCircleListener activityLifeCircleListener);

    void removeActivityLifeCircleListener(ActivityLifeCircleListener activityLifeCircleListener);
}
