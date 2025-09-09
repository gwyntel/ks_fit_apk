package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class JniUrlListPlayer extends JniListPlayerBase {
    private static final String TAG = "JniUrlListPlayer";

    static {
        f.f();
    }

    public JniUrlListPlayer(Context context, long j2, long j3) {
        super(context, j2, j3);
    }

    public static void loadClass() {
    }

    public void addUrl(String str, String str2) {
        if (f.b()) {
            nAddUrl(str, str2);
        }
    }

    public long getCurrentPlayerIndex() {
        if (f.b()) {
            return nGetCurrentPlayerIndex();
        }
        return 0L;
    }

    public long getPreRenderPlayerIndex() {
        if (f.b()) {
            return nGetPreRenderPlayerIndex();
        }
        return 0L;
    }

    public boolean moveTo(String str) {
        if (f.b()) {
            return nMoveTo(str);
        }
        return false;
    }

    public boolean moveToNext(boolean z2) {
        if (f.b()) {
            return nMoveToNext(z2);
        }
        return false;
    }

    public boolean moveToPrev() {
        if (f.b()) {
            return nMoveToPrev();
        }
        return false;
    }

    native void nAddUrl(String str, String str2);

    native void nAddUrl(String str, String str2, Object obj);

    native long nGetCurrentPlayerIndex();

    native long nGetPreRenderPlayerIndex();

    native boolean nMoveTo(String str);

    native boolean nMoveToNext(boolean z2);

    native boolean nMoveToPrev();

    public void addUrl(String str, String str2, PreloadConfig preloadConfig) {
        if (f.b()) {
            nAddUrl(str, str2, preloadConfig);
        }
    }
}
