package com.aliyun.aio.aio_env;

import anetwork.channel.util.RequestConstant;
import com.aliyun.aio.keep.API;

@API
/* loaded from: classes2.dex */
public class AlivcEnv {
    private static final String KEY_PREPARED = "prepare";
    private static AlivcEnv sInstance = new AlivcEnv();
    private boolean hasPrepared = true;

    @API
    public interface ErrorCallback {
        @API
        void onError(int i2, String str);
    }

    @API
    public enum GlobalEnv {
        ENV_DEFAULT,
        ENV_CN,
        ENV_SEA,
        ENV_GLOBAL_OVERSEA,
        ENV_GLOBAL_DEFAULT
    }

    public static AlivcEnv getInstance() {
        return sInstance;
    }

    private static native int nativeGetEnv();

    private static native String nativeGetErrorMessage(int i2);

    private static native int nativeSetEnv(int i2);

    private static native boolean nativeSetOption(String str, String str2);

    public GlobalEnv getGlobalEnvironment() {
        if (!this.hasPrepared) {
            return GlobalEnv.ENV_DEFAULT;
        }
        int iNativeGetEnv = nativeGetEnv();
        if (iNativeGetEnv < 0 || iNativeGetEnv >= GlobalEnv.values().length) {
            throw new IllegalArgumentException("Invalid ordinal for GlobalEnv enum");
        }
        return GlobalEnv.values()[iNativeGetEnv];
    }

    public int setGlobalEnvironment(GlobalEnv globalEnv) {
        if (!this.hasPrepared) {
            return -1;
        }
        if (globalEnv == GlobalEnv.ENV_GLOBAL_DEFAULT) {
            globalEnv = GlobalEnv.ENV_DEFAULT;
        }
        return nativeSetEnv(globalEnv.ordinal());
    }

    public boolean setOption(String str, String str2) {
        if (KEY_PREPARED.equalsIgnoreCase(str)) {
            if (str2 == null) {
                str2 = "";
            }
            if (str2.equalsIgnoreCase(RequestConstant.FALSE)) {
                this.hasPrepared = false;
            }
            return false;
        }
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        return nativeSetOption(str, str2);
    }

    public boolean setGlobalEnvironment(GlobalEnv globalEnv, ErrorCallback errorCallback) {
        if (!this.hasPrepared) {
            return false;
        }
        if (globalEnv == GlobalEnv.ENV_GLOBAL_DEFAULT) {
            globalEnv = GlobalEnv.ENV_DEFAULT;
        }
        int iNativeSetEnv = nativeSetEnv(globalEnv.ordinal());
        if (iNativeSetEnv == 0) {
            return true;
        }
        if (errorCallback != null) {
            errorCallback.onError(iNativeSetEnv, nativeGetErrorMessage(iNativeSetEnv));
        }
        return false;
    }
}
