package com.aliyun.alink.linksdk.alcs.api;

import android.os.Handler;
import android.os.Looper;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AlcsCoAPSdk {
    private static final String TAG = "AlcsCoAPSdk";
    public static Handler mHandler;

    public static void init() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        mHandler = new Handler(Looper.getMainLooper());
        ALog.d(TAG, "AlcsCoAPSdk init sdkversion:2.0.1-SNAPSHOT");
    }
}
