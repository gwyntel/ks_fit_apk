package androidx.media3.common.util;

import android.os.Looper;
import androidx.annotation.Nullable;

@UnstableApi
/* loaded from: classes.dex */
public interface HandlerWrapper {

    public interface Message {
        HandlerWrapper getTarget();

        void sendToTarget();
    }

    Looper getLooper();

    boolean hasMessages(int i2);

    Message obtainMessage(int i2);

    Message obtainMessage(int i2, int i3, int i4);

    Message obtainMessage(int i2, int i3, int i4, @Nullable Object obj);

    Message obtainMessage(int i2, @Nullable Object obj);

    boolean post(Runnable runnable);

    boolean postAtFrontOfQueue(Runnable runnable);

    boolean postDelayed(Runnable runnable, long j2);

    void removeCallbacksAndMessages(@Nullable Object obj);

    void removeMessages(int i2);

    boolean sendEmptyMessage(int i2);

    boolean sendEmptyMessageAtTime(int i2, long j2);

    boolean sendEmptyMessageDelayed(int i2, int i3);

    boolean sendMessageAtFrontOfQueue(Message message);
}
