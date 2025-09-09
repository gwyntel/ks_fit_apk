package com.aliyun.common;

import android.content.Context;
import com.aliyun.aio.aio_env.AlivcEnv;
import com.aliyun.aio.keep.API;

@API
/* loaded from: classes2.dex */
public class AlivcLicense {

    @API
    public static abstract class LicenseCallback {
        public void onCheckResult(boolean z2, String str) {
        }

        public void onConfigResult(boolean z2, String str) {
        }
    }

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ LicenseCallback f11514a;

        a(LicenseCallback licenseCallback) {
            this.f11514a = licenseCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            AlivcBase.nativeCheckLicense(this.f11514a);
        }
    }

    public static void checkLicense(LicenseCallback licenseCallback) {
        if (licenseCallback == null) {
            return;
        }
        AlivcBase.loadSo();
        new Thread(new a(licenseCallback)).start();
    }

    public static void configLicense(Context context, String str, String str2, AlivcEnv.GlobalEnv globalEnv, LicenseCallback licenseCallback) {
        if (AlivcBase.getEnvironmentManager().setGlobalEnvironment(globalEnv, null)) {
            configLicense(context, str, str2, licenseCallback);
            return;
        }
        if (licenseCallback != null) {
            licenseCallback.onConfigResult(false, "Env Can not change from " + AlivcBase.getEnvironmentManager().getGlobalEnvironment() + " to " + globalEnv);
        }
    }

    public static String getCurrentLicense() {
        AlivcBase.loadSo();
        return AlivcBase.nativeGetCurrentLicense();
    }

    public static void configLicense(Context context, String str, String str2, LicenseCallback licenseCallback) {
        AlivcBase.loadSo();
        AlivcBase.nativeConfigLicense(context, str, str2, licenseCallback);
    }
}
