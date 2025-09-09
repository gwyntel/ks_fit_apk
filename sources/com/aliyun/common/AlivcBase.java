package com.aliyun.common;

import anetwork.channel.util.RequestConstant;
import com.aliyun.aio.aio_env.AlivcEnv;
import com.aliyun.aio.keep.CalledByNative;
import com.aliyun.player.BuildConfig;

@CalledByNative
/* loaded from: classes2.dex */
public class AlivcBase {
    static boolean hasLoad = false;

    public static AlivcEnv getEnvironmentManager() {
        loadSo();
        AlivcEnv alivcEnv = AlivcEnv.getInstance();
        if (!hasLoad) {
            alivcEnv.setOption("prepare", RequestConstant.FALSE);
        }
        return alivcEnv;
    }

    public static boolean isSdkReady() {
        loadSo();
        return hasLoad;
    }

    static void loadSo() {
        if (hasLoad) {
            return;
        }
        boolean zLoadLibrary = AlivcNativeLoader.loadLibrary("all_in_one");
        hasLoad = zLoadLibrary;
        if (!zLoadLibrary) {
            hasLoad = AlivcNativeLoader.loadLibrary(BuildConfig.PLAYER_LIB_NAME);
        }
        if (!hasLoad) {
            hasLoad = AlivcNativeLoader.loadLibrary("ugsv");
        }
        if (hasLoad) {
            return;
        }
        hasLoad = AlivcNativeLoader.loadLibrary("rtc_core");
    }

    static native void nativeCheckLicense(Object obj);

    static native void nativeConfigLicense(Object obj, String str, String str2, Object obj2);

    static native String nativeGetCurrentLicense();

    private static native void nativeSetIntegrationWay(String str);

    public static void setIntegrationWay(String str) {
        loadSo();
        if (hasLoad) {
            nativeSetIntegrationWay(str);
        }
    }
}
