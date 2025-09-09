package com.aliyun.aio_stat;

import android.content.Context;
import com.aliyun.aio.keep.CalledByNative;

@CalledByNative
/* loaded from: classes2.dex */
public class AioStat {
    public static boolean init(Context context) {
        if (context != null) {
            nSetApplicationContext(context);
        }
        return nInit();
    }

    private static native boolean nInit();

    private static native boolean nRelease();

    static native void nSetApplicationContext(Context context);

    public static boolean release() {
        return nRelease();
    }
}
