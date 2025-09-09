package com.xiaomi.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class el implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a, reason: collision with root package name */
    private Context f23662a;

    /* renamed from: a, reason: collision with other field name */
    private String f333a;

    /* renamed from: b, reason: collision with root package name */
    private String f23663b;

    public el(Context context, String str) {
        this.f23662a = context;
        this.f333a = str;
    }

    private void a(String str) {
        iv ivVar = new iv();
        ivVar.a(str);
        ivVar.a(System.currentTimeMillis());
        ivVar.a(ip.ActivityActiveTimeStamp);
        et.a(this.f23662a, ivVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        String localClassName = activity.getLocalClassName();
        if (TextUtils.isEmpty(this.f333a) || TextUtils.isEmpty(localClassName)) {
            return;
        }
        this.f23663b = "";
        if (!TextUtils.isEmpty("") && !TextUtils.equals(this.f23663b, localClassName)) {
            this.f333a = "";
            return;
        }
        a(this.f23662a.getPackageName() + "|" + localClassName + ":" + this.f333a + "," + String.valueOf(System.currentTimeMillis() / 1000));
        this.f333a = "";
        this.f23663b = "";
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (TextUtils.isEmpty(this.f23663b)) {
            this.f23663b = activity.getLocalClassName();
        }
        this.f333a = String.valueOf(System.currentTimeMillis() / 1000);
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
}
