package com.alibaba.sdk.android.openaccount.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.widget.Toast;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.FailureCallback;
import com.alibaba.sdk.android.openaccount.executor.impl.ExecutorServiceImpl;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.InitWaitTask;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.EOFException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/* loaded from: classes2.dex */
public class CommonUtils {
    private static String CURRENT_PROCESS_NAME = null;
    private static String PREFIX = "\\u";

    public static int dp2px(Context context, float f2) {
        return (int) (f2 * context.getResources().getDisplayMetrics().density);
    }

    public static String getAndroidManifestMetadata(Context context, String str) {
        Object obj;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null && (obj = bundle.get(str)) != null) {
                return obj.toString();
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getCurrentProcessName() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (OpenAccountSDK.getAndroidContext() == null) {
            return null;
        }
        String str = CURRENT_PROCESS_NAME;
        if (str != null) {
            return str;
        }
        try {
            runningAppProcesses = ((ActivityManager) OpenAccountSDK.getAndroidContext().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        } catch (Exception unused) {
        }
        if (runningAppProcesses == null) {
            return null;
        }
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                String str2 = runningAppProcessInfo.processName;
                CURRENT_PROCESS_NAME = str2;
                return str2;
            }
        }
        return null;
    }

    public static int isApplicationDefaultProcess() {
        String currentProcessName;
        if (OpenAccountSDK.getAndroidContext() == null || (currentProcessName = getCurrentProcessName()) == null) {
            return -1;
        }
        return currentProcessName.equals(OpenAccountSDK.getAndroidContext().getPackageName()) ? 1 : 0;
    }

    public static boolean isConnectionTimeout(Throwable th) {
        if ((th instanceof SocketTimeoutException) || (th instanceof EOFException) || (th instanceof ConnectException)) {
            return true;
        }
        Throwable cause = th.getCause();
        if (cause == null) {
            return false;
        }
        return (cause instanceof SocketTimeoutException) || (cause instanceof EOFException) || (cause instanceof ConnectException);
    }

    public static boolean isDebuggable() {
        if (ConfigManager.getInstance().isDebugEnabled()) {
            return true;
        }
        try {
            return (OpenAccountSDK.getAndroidContext().getPackageManager().getApplicationInfo(OpenAccountSDK.getAndroidContext().getPackageName(), 16384).flags & 2) != 0;
        } catch (PackageManager.NameNotFoundException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static boolean isEqual(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static boolean isNetworkAvailable() {
        if (OpenAccountSDK.getAndroidContext() == null) {
            return true;
        }
        return isNetworkAvailable(OpenAccountSDK.getAndroidContext());
    }

    public static void onFailure(final FailureCallback failureCallback, final Message message) {
        if (failureCallback == null) {
            return;
        }
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.util.CommonUtils.1
            @Override // java.lang.Runnable
            public void run() {
                FailureCallback failureCallback2 = failureCallback;
                Message message2 = message;
                failureCallback2.onFailure(message2.code, message2.message);
            }
        });
    }

    public static int px2dp(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + ((f2 >= 0.0f ? 1 : -1) * 0.5f));
    }

    public static boolean sessionFail(FailureCallback failureCallback) {
        SessionManagerService sessionManagerService = (SessionManagerService) OpenAccountSDK.getService(SessionManagerService.class);
        if (sessionManagerService != null) {
            OpenAccountSession session = sessionManagerService.getSession();
            if (session != null && session.isLogin()) {
                return false;
            }
            Message messageCreateMessage = MessageUtils.createMessage(10011, new Object[0]);
            AliSDKLogger.log(OpenAccountConstants.LOG_TAG, messageCreateMessage);
            if (failureCallback != null) {
                failureCallback.onFailure(messageCreateMessage.code, messageCreateMessage.message);
            }
        }
        return true;
    }

    public static void startInitWaitTask(Context context, FailureCallback failureCallback, Runnable runnable, String str) {
        startInitWaitTask(context, failureCallback, runnable, str, false);
    }

    public static String toString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static void toast(final String str) {
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.util.CommonUtils.4
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(OpenAccountSDK.getAndroidContext(), ResourceUtils.getString(str), 0).show();
            }
        });
    }

    public static void toastNetworkError() {
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.util.CommonUtils.5
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(OpenAccountSDK.getAndroidContext(), MessageUtils.getMessageContent(MessageConstants.NETWORK_NOT_AVAILABLE, new Object[0]), 0).show();
            }
        });
    }

    public static void toastSystemException() {
        toast("ali_sdk_openaccount_dynamic_system_exception");
    }

    public static void onFailure(final FailureCallback failureCallback, final ResultCode resultCode) {
        if (failureCallback == null) {
            return;
        }
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.util.CommonUtils.2
            @Override // java.lang.Runnable
            public void run() {
                FailureCallback failureCallback2 = failureCallback;
                ResultCode resultCode2 = resultCode;
                failureCallback2.onFailure(resultCode2.code, resultCode2.message);
            }
        });
    }

    public static void startInitWaitTask(Context context, FailureCallback failureCallback, Runnable runnable, String str, boolean z2) {
        new InitWaitTask(context, failureCallback, runnable, str, z2).execute(new Void[0]);
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null) {
            return false;
        }
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo != null && (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getState() == NetworkInfo.State.CONNECTING)) {
                return true;
            }
        }
        return false;
    }

    public static void onFailure(final FailureCallback failureCallback, final int i2, final String str) {
        if (failureCallback == null) {
            return;
        }
        ExecutorServiceImpl.INSTANCE.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.util.CommonUtils.3
            @Override // java.lang.Runnable
            public void run() {
                failureCallback.onFailure(i2, str);
            }
        });
    }
}
