package com.alibaba.sdk.android.openaccount.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;

/* loaded from: classes2.dex */
public class ActivityHelper {
    private static WeakReference<Activity> activity;
    private static volatile Application.ActivityLifecycleCallbacks callback;

    public static Activity getCurrentActivity() {
        WeakReference<Activity> weakReference = activity;
        Activity activity2 = weakReference == null ? null : weakReference.get();
        return activity2 == null ? getCurrentActivityFromActivityThread() : activity2;
    }

    private static Activity getCurrentActivityFromActivityThread() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(null, null);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            for (Object obj : ((Map) declaredField.get(objInvoke)).values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField(PushConstants.INTENT_ACTIVITY_NAME);
                    declaredField3.setAccessible(true);
                    return (Activity) declaredField3.get(obj);
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    @TargetApi(14)
    public static void register(Application application) {
        if (application == null) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "null application for activity lifecycle registeration");
        } else {
            if (callback != null) {
                return;
            }
            synchronized (ActivityHelper.class) {
                callback = new Application.ActivityLifecycleCallbacks() { // from class: com.alibaba.sdk.android.openaccount.util.ActivityHelper.1
                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityCreated(Activity activity2, Bundle bundle) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityDestroyed(Activity activity2) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityPaused(Activity activity2) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityResumed(Activity activity2) {
                        WeakReference unused = ActivityHelper.activity = new WeakReference(activity2);
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivitySaveInstanceState(Activity activity2, Bundle bundle) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStarted(Activity activity2) {
                    }

                    @Override // android.app.Application.ActivityLifecycleCallbacks
                    public void onActivityStopped(Activity activity2) {
                    }
                };
                application.registerActivityLifecycleCallbacks(callback);
            }
        }
    }

    @TargetApi(14)
    public static void unregister(Application application) {
        if (application == null) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "null application for activity lifecycle registeration");
        } else {
            if (callback == null) {
                return;
            }
            synchronized (ActivityHelper.class) {
                application.unregisterActivityLifecycleCallbacks(callback);
                callback = null;
            }
        }
    }
}
