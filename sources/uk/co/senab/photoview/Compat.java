package uk.co.senab.photoview;

import android.annotation.TargetApi;
import android.view.View;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes5.dex */
public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static int getPointerIndex(int i2) {
        return getPointerIndexHoneyComb(i2);
    }

    @TargetApi(5)
    private static int getPointerIndexEclair(int i2) {
        return (i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    @TargetApi(11)
    private static int getPointerIndexHoneyComb(int i2) {
        return (i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        postOnAnimationJellyBean(view, runnable);
    }

    @TargetApi(16)
    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}
