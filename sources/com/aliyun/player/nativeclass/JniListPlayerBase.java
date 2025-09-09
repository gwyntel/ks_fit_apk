package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class JniListPlayerBase {
    private long mNativeContext = 0;

    static {
        f.f();
    }

    public JniListPlayerBase(Context context, long j2, long j3) {
        if (f.b()) {
            nConstruct(j2, j3);
        }
    }

    public static void loadClass() {
    }

    public int GetMultiBitratesMode() {
        if (f.b()) {
            return nGetMultiBiratesMode();
        }
        return 0;
    }

    public void SetMultiBitratesMode(int i2) {
        if (f.b()) {
            nSetMultiBitratesMode(i2);
        }
    }

    public void clear() {
        if (f.b()) {
            nClear();
        }
    }

    public void enablePreloadStrategy(int i2, boolean z2) {
        if (f.b()) {
            nEnablePreloadStrategy(i2, z2);
        }
    }

    public String getCurrentUid() {
        return !f.b() ? "" : nGetCurrentUid();
    }

    public int getMaxPreloadMemorySizeMB() {
        if (f.b()) {
            return nGetMaxPreloadMemorySizeMB();
        }
        return 0;
    }

    protected long getNativeListContext() {
        return this.mNativeContext;
    }

    native void nClear();

    native void nConstruct(long j2, long j3);

    native void nEnablePreloadStrategy(int i2, boolean z2);

    native String nGetCurrentUid();

    native int nGetMaxPreloadMemorySizeMB();

    native int nGetMultiBiratesMode();

    native void nRelease();

    native void nRemoveSource(String str);

    native void nSetMaxPreloadMemorySizeMB(int i2);

    native void nSetMultiBitratesMode(int i2);

    native void nSetPreloadCount(int i2);

    native void nSetPreloadCount(int i2, int i3);

    native void nSetPreloadScene(int i2);

    native void nSetPreloadStrategyParam(int i2, String str);

    native void nStop();

    native void nUpdatePreloadConfig(Object obj);

    native void nUpdatePreloadConfig(String str, Object obj);

    public void release() {
        if (f.b()) {
            nRelease();
        }
    }

    public void removeSource(String str) {
        if (f.b()) {
            nRemoveSource(str);
        }
    }

    public void setMaxPreloadMemorySizeMB(int i2) {
        if (f.b()) {
            nSetMaxPreloadMemorySizeMB(i2);
        }
    }

    protected void setNativeListContext(long j2) {
        this.mNativeContext = j2;
    }

    public void setPreloadCount(int i2) {
        if (f.b()) {
            nSetPreloadCount(i2);
        }
    }

    public void setPreloadScene(int i2) {
        if (f.b()) {
            nSetPreloadScene(i2);
        }
    }

    public void setPreloadStrategyParam(int i2, String str) {
        if (f.b()) {
            nSetPreloadStrategyParam(i2, str);
        }
    }

    public void stop() {
        if (f.b()) {
            nStop();
        }
    }

    public void updatePreloadConfig(PreloadConfig preloadConfig) {
        if (f.b()) {
            nUpdatePreloadConfig(preloadConfig);
        }
    }

    public void setPreloadCount(int i2, int i3) {
        if (f.b()) {
            nSetPreloadCount(i2, i3);
        }
    }

    public void updatePreloadConfig(String str, PreloadConfig preloadConfig) {
        if (f.b()) {
            nUpdatePreloadConfig(str, preloadConfig);
        }
    }
}
