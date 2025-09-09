package com.huawei.hms.adapter.sysobs;

import android.os.Handler;
import android.os.Looper;
import androidx.media3.exoplayer.ExoPlayer;
import com.huawei.hms.support.log.HMSLog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ApkResolutionFailedManager {

    /* renamed from: c, reason: collision with root package name */
    private static final ApkResolutionFailedManager f15788c = new ApkResolutionFailedManager();

    /* renamed from: a, reason: collision with root package name */
    private final Handler f15789a = new Handler(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    private final Map<String, Runnable> f15790b = new HashMap(2);

    private ApkResolutionFailedManager() {
    }

    public static ApkResolutionFailedManager getInstance() {
        return f15788c;
    }

    public void postTask(String str, Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            HMSLog.e("ApkResolutionFailedManager", "postTask is not in main thread");
        } else {
            this.f15790b.put(str, runnable);
            this.f15789a.postDelayed(runnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    public void removeTask(String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            HMSLog.e("ApkResolutionFailedManager", "removeTask is not in main thread");
            return;
        }
        Runnable runnableRemove = this.f15790b.remove(str);
        if (runnableRemove == null) {
            HMSLog.e("ApkResolutionFailedManager", "cancel runnable is null");
        } else {
            this.f15789a.removeCallbacks(runnableRemove);
        }
    }

    public void removeValueOnly(String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            HMSLog.e("ApkResolutionFailedManager", "removeValueOnly is not in main thread");
        } else {
            this.f15790b.remove(str);
        }
    }
}
