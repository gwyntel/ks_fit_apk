package com.umeng.message.proguard;

import android.app.Application;
import android.content.Context;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.message.common.UPLog;

/* loaded from: classes4.dex */
public final class x {

    /* renamed from: a, reason: collision with root package name */
    private static Application f22947a;

    public static void a(Context context) {
        if (f22947a != null || context == null) {
            return;
        }
        f22947a = (Application) context.getApplicationContext();
    }

    public static Application a() throws ClassNotFoundException {
        Application application = f22947a;
        if (application != null) {
            return application;
        }
        try {
            Context appContext = UMGlobalContext.getAppContext();
            if (appContext != null) {
                Application application2 = (Application) appContext.getApplicationContext();
                f22947a = application2;
                if (application2 != null) {
                    return application2;
                }
            }
        } catch (Throwable unused) {
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Application application3 = (Application) cls.getMethod("getApplication", null).invoke(cls.getMethod("currentActivityThread", null).invoke(cls, null), null);
            f22947a = application3;
            if (application3 != null) {
                return application3;
            }
        } catch (Exception unused2) {
        }
        UPLog.e("Core", "context null! make sure PushAgent.setup(...) be called in Application.onCreate().");
        throw new IllegalStateException("context null! make sure PushAgent.setup(...) be called in Application.onCreate().");
    }
}
