package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.RestrictTo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
final class ActivityRecreator {
    private static final String LOG_TAG = "ActivityRecreator";

    /* renamed from: a, reason: collision with root package name */
    protected static final Class f3374a;

    /* renamed from: b, reason: collision with root package name */
    protected static final Field f3375b;

    /* renamed from: c, reason: collision with root package name */
    protected static final Field f3376c;

    /* renamed from: d, reason: collision with root package name */
    protected static final Method f3377d;

    /* renamed from: e, reason: collision with root package name */
    protected static final Method f3378e;

    /* renamed from: f, reason: collision with root package name */
    protected static final Method f3379f;
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final class LifecycleCheckCallbacks implements Application.ActivityLifecycleCallbacks {

        /* renamed from: a, reason: collision with root package name */
        Object f3386a;
        private Activity mActivity;
        private final int mRecreatingHashCode;
        private boolean mStarted = false;
        private boolean mDestroyed = false;
        private boolean mStopQueued = false;

        LifecycleCheckCallbacks(Activity activity) {
            this.mActivity = activity;
            this.mRecreatingHashCode = activity.hashCode();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (this.mActivity == activity) {
                this.mActivity = null;
                this.mDestroyed = true;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (!this.mDestroyed || this.mStopQueued || this.mStarted || !ActivityRecreator.a(this.f3386a, this.mRecreatingHashCode, activity)) {
                return;
            }
            this.mStopQueued = true;
            this.f3386a = null;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            if (this.mActivity == activity) {
                this.mStarted = true;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    }

    static {
        Class<?> activityThreadClass = getActivityThreadClass();
        f3374a = activityThreadClass;
        f3375b = getMainThreadField();
        f3376c = getTokenField();
        f3377d = getPerformStopActivity3Params(activityThreadClass);
        f3378e = getPerformStopActivity2Params(activityThreadClass);
        f3379f = getRequestRelaunchActivityMethod(activityThreadClass);
    }

    private ActivityRecreator() {
    }

    protected static boolean a(Object obj, int i2, Activity activity) {
        try {
            final Object obj2 = f3376c.get(activity);
            if (obj2 == obj && activity.hashCode() == i2) {
                final Object obj3 = f3375b.get(activity);
                mainHandler.postAtFrontOfQueue(new Runnable() { // from class: androidx.core.app.ActivityRecreator.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Method method = ActivityRecreator.f3377d;
                            if (method != null) {
                                method.invoke(obj3, obj2, Boolean.FALSE, "AppCompat recreation");
                            } else {
                                ActivityRecreator.f3378e.invoke(obj3, obj2, Boolean.FALSE);
                            }
                        } catch (RuntimeException e2) {
                            if (e2.getClass() == RuntimeException.class && e2.getMessage() != null && e2.getMessage().startsWith("Unable to stop")) {
                                throw e2;
                            }
                        } catch (Throwable th) {
                            Log.e(ActivityRecreator.LOG_TAG, "Exception while invoking performStopActivity", th);
                        }
                    }
                });
                return true;
            }
            return false;
        } catch (Throwable th) {
            Log.e(LOG_TAG, "Exception while fetching field values", th);
            return false;
        }
    }

    static boolean b(Activity activity) {
        Object obj;
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return true;
        }
        if (needsRelaunchCall() && f3379f == null) {
            return false;
        }
        if (f3378e == null && f3377d == null) {
            return false;
        }
        try {
            final Object obj2 = f3376c.get(activity);
            if (obj2 == null || (obj = f3375b.get(activity)) == null) {
                return false;
            }
            final Application application = activity.getApplication();
            final LifecycleCheckCallbacks lifecycleCheckCallbacks = new LifecycleCheckCallbacks(activity);
            application.registerActivityLifecycleCallbacks(lifecycleCheckCallbacks);
            Handler handler = mainHandler;
            handler.post(new Runnable() { // from class: androidx.core.app.ActivityRecreator.1
                @Override // java.lang.Runnable
                public void run() {
                    lifecycleCheckCallbacks.f3386a = obj2;
                }
            });
            try {
                if (needsRelaunchCall()) {
                    Method method = f3379f;
                    Boolean bool = Boolean.FALSE;
                    method.invoke(obj, obj2, null, null, 0, bool, null, null, bool, bool);
                } else {
                    activity.recreate();
                }
                handler.post(new Runnable() { // from class: androidx.core.app.ActivityRecreator.2
                    @Override // java.lang.Runnable
                    public void run() {
                        application.unregisterActivityLifecycleCallbacks(lifecycleCheckCallbacks);
                    }
                });
                return true;
            } catch (Throwable th) {
                mainHandler.post(new Runnable() { // from class: androidx.core.app.ActivityRecreator.2
                    @Override // java.lang.Runnable
                    public void run() {
                        application.unregisterActivityLifecycleCallbacks(lifecycleCheckCallbacks);
                    }
                });
                throw th;
            }
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Class<?> getActivityThreadClass() {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Field getMainThreadField() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mMainThread");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method getPerformStopActivity2Params(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method getPerformStopActivity3Params(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE, String.class);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method getRequestRelaunchActivityMethod(Class<?> cls) {
        if (needsRelaunchCall() && cls != null) {
            try {
                Class<?> cls2 = Boolean.TYPE;
                Method declaredMethod = cls.getDeclaredMethod("requestRelaunchActivity", IBinder.class, List.class, List.class, Integer.TYPE, cls2, Configuration.class, Configuration.class, cls2, cls2);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private static Field getTokenField() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mToken");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean needsRelaunchCall() {
        int i2 = Build.VERSION.SDK_INT;
        return i2 == 26 || i2 == 27;
    }
}
