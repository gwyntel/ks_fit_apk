package com.aliyun.private_service;

import android.content.Context;
import com.aliyun.utils.DeviceInfoUtils;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class PrivateService {
    static {
        f.f();
    }

    @Deprecated
    public static void initService(Context context, String str) {
        if (f.b() && context != null) {
            nInitService(context.getApplicationContext(), str);
        }
    }

    public static void loadClass() {
    }

    private static native void nInitService(Object obj, String str);

    private static native void nInitService(Object obj, byte[] bArr);

    private static native void nPreInitService(Object obj);

    public static void preInitService(Context context) {
        if (f.b() && context != null) {
            DeviceInfoUtils.setSDKContext(context);
            nPreInitService(context.getApplicationContext());
        }
    }

    public static void initService(Context context, byte[] bArr) {
        if (f.b() && context != null) {
            nInitService(context.getApplicationContext(), bArr);
        }
    }
}
