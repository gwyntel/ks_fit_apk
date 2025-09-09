package com.aliyun.common.crash;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ApplicationExitInfo;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.aliyun.aio.keep.CalledByNative;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

@CalledByNative
/* loaded from: classes2.dex */
public class AlivcJavaCrash {
    private static final String TAG = "AlivcJavaCrash";
    private WeakReference<Context> mContextRef;
    private a mExceptHandler;
    private volatile boolean mIsRegister = false;
    private b mLifeTracker;

    public static native void nativeForegroundChange(boolean z2);

    public static native void nativeOnCrashCallback(int i2, long j2, String str, String str2);

    public static native void nativeOnLastCrashTombstone(String str);

    public void active() {
        a aVar;
        if (!this.mIsRegister || (aVar = this.mExceptHandler) == null) {
            return;
        }
        aVar.a();
    }

    public void checkLastCrash(Context context, String str, int i2) throws IOException {
        if (str == null || context == null || Build.VERSION.SDK_INT < 31) {
            return;
        }
        List historicalProcessExitReasons = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getHistoricalProcessExitReasons(context.getPackageName(), i2, 0);
        if (historicalProcessExitReasons.isEmpty()) {
            return;
        }
        Iterator it = historicalProcessExitReasons.iterator();
        while (it.hasNext()) {
            ApplicationExitInfo applicationExitInfoA = u.b.a(it.next());
            Log.d("ALI_CRASH_DEBUG", String.format("Pid: %d, Reason: %d, status: %d, descr: %s timestamp: %d", Integer.valueOf(applicationExitInfoA.getPid()), Integer.valueOf(applicationExitInfoA.getReason()), Integer.valueOf(applicationExitInfoA.getStatus()), applicationExitInfoA.getDescription(), Long.valueOf(applicationExitInfoA.getTimestamp())));
            if (applicationExitInfoA.getReason() == 5) {
                try {
                    InputStream traceInputStream = applicationExitInfoA.getTraceInputStream();
                    if (traceInputStream != null) {
                        String str2 = String.format("%s/crash_tombstone_%d", str, Integer.valueOf(applicationExitInfoA.getPid()));
                        FileOutputStream fileOutputStream = new FileOutputStream(str2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i3 = traceInputStream.read(bArr);
                                if (i3 == -1) {
                                    break;
                                } else {
                                    fileOutputStream.write(bArr, 0, i3);
                                }
                            }
                            fileOutputStream.close();
                            nativeOnLastCrashTombstone(str2);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                }
                                throw th2;
                            }
                        }
                    } else {
                        continue;
                    }
                } catch (IOException unused) {
                    Log.e(TAG, "Read crash input stream error");
                }
            }
        }
    }

    public void inactive() {
        a aVar;
        if (!this.mIsRegister || (aVar = this.mExceptHandler) == null) {
            return;
        }
        aVar.b();
    }

    public void register(Context context) {
        if (this.mIsRegister) {
            return;
        }
        this.mIsRegister = true;
        this.mExceptHandler = new a();
        if (context == null) {
            Log.e(TAG, "Context对象为空");
            return;
        }
        this.mLifeTracker = new b();
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        Application application = (Application) context;
        this.mContextRef = new WeakReference<>(application);
        application.registerActivityLifecycleCallbacks(this.mLifeTracker);
        application.registerComponentCallbacks(this.mLifeTracker);
    }

    public void unRegister() {
    }
}
