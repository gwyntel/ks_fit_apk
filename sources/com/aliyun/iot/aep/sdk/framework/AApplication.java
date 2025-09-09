package com.aliyun.iot.aep.sdk.framework;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.multidex.MultiDexApplication;
import com.aliyun.iot.aep.sdk.abus.ABus;
import com.aliyun.iot.aep.sdk.abus.AChannelIDProvider;
import com.aliyun.iot.aep.sdk.abus.IBus;
import com.aliyun.iot.aep.sdk.abus.IChannelIDProvider;
import com.aliyun.iot.aep.sdk.framework.language.LanguageManager;

/* loaded from: classes3.dex */
public abstract class AApplication extends MultiDexApplication {

    /* renamed from: a, reason: collision with root package name */
    private static AApplication f11750a;

    /* renamed from: b, reason: collision with root package name */
    private IBus f11751b = new ABus();

    /* renamed from: c, reason: collision with root package name */
    private IChannelIDProvider f11752c = new AChannelIDProvider();

    static final class a implements Application.ActivityLifecycleCallbacks {

        /* renamed from: a, reason: collision with root package name */
        private final AApplication f11753a;

        /* renamed from: b, reason: collision with root package name */
        private volatile int f11754b;

        /* renamed from: c, reason: collision with root package name */
        private volatile int f11755c;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            int i2 = this.f11754b + 1;
            this.f11754b = i2;
            if (1 == i2) {
                this.f11753a.a(activity, bundle);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            int i2 = this.f11754b - 1;
            this.f11754b = i2;
            if (i2 == 0) {
                this.f11753a.a(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            int i2 = this.f11755c + 1;
            this.f11755c = i2;
            if (1 == i2) {
                this.f11753a.c(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            int i2 = this.f11755c + 1;
            this.f11755c = i2;
            if (1 == i2) {
                this.f11753a.b(activity);
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

        private a(AApplication aApplication) {
            this.f11754b = 0;
            this.f11755c = 0;
            this.f11753a = aApplication;
        }
    }

    public static AApplication getInstance() {
        return f11750a;
    }

    protected void a(Activity activity) {
    }

    protected void b(Activity activity) {
    }

    protected void c(Activity activity) {
    }

    public int generateChannelID() {
        return this.f11752c.generateChannelID();
    }

    public IBus getBus() {
        return this.f11751b;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = super.getResources();
        LanguageManager.updateApplicationLanguage(resources);
        return resources;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new a());
        f11750a = this;
    }

    protected void a(Activity activity, Bundle bundle) {
    }
}
