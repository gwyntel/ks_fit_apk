package com.aliyun.common.crash;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes2.dex */
public class b implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11529a = "b";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f11530b = false;

    private void a(boolean z2) {
        AlivcJavaCrash.nativeForegroundChange(z2);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (f11530b) {
            Log.d(f11529a, "app went to foreground");
            f11530b = false;
            a(true);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i2) {
        if (i2 == 20) {
            Log.d(f11529a, "app went to background");
            f11530b = true;
            a(false);
        }
    }
}
