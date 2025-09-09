package com.alibaba.sdk.android.openaccount;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.callback.InitResultCallback;
import com.alibaba.sdk.android.openaccount.executor.impl.ExecutorServiceImpl;
import com.alibaba.sdk.android.openaccount.task.InitTask;
import com.alibaba.sdk.android.openaccount.util.SqliteUtil;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;

/* loaded from: classes2.dex */
public class OpenAccountSDK {
    private static Context androidContext;
    private static SqliteUtil sqliteUtil;

    public static void asyncInit(Context context, InitResultCallback initResultCallback) {
        internalAsyncInit(context, initResultCallback);
    }

    public static Context getAndroidContext() {
        return androidContext;
    }

    public static String getProperty(String str) {
        return ConfigManager.getInstance().getStringProperty(str, null);
    }

    public static <T> T getService(Class<T> cls) {
        if (cls == null) {
            return null;
        }
        return (T) Pluto.DEFAULT_INSTANCE.getBean(cls);
    }

    public static SqliteUtil getSqliteUtil() {
        return sqliteUtil;
    }

    public static Version getVersion() {
        return ConfigManager.getInstance().getSDKVersion();
    }

    private static InitTask internalAsyncInit(Context context, InitResultCallback initResultCallback) {
        androidContext = context.getApplicationContext();
        sqliteUtil = new SqliteUtil(androidContext);
        InitTask initTask = new InitTask(context, initResultCallback);
        ExecutorServiceImpl.INSTANCE.postHandlerTask(initTask);
        return initTask;
    }

    public static void registerModule(ModuleInfo moduleInfo) {
        Pluto.DEFAULT_INSTANCE.registerModule(moduleInfo);
    }

    public static void setProperty(String str, String str2) {
        ConfigManager.getInstance().setProperty(str, str2);
    }

    public static void syncInit(Context context, InitResultCallback initResultCallback) {
        internalAsyncInit(context, initResultCallback).await();
    }

    public static void turnOnDebug() {
        Log.w("OpenAccountSDK", "************************************\nDebug is enabled, make sure to turn it off in the production environment\n************************************");
        ConfigManager.getInstance().setDebugEnabled(true);
        ConfigManager.getInstance().setLogLevel(3);
    }
}
