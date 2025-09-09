package androidx.media3.common;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

@UnstableApi
/* loaded from: classes.dex */
public final class PriorityTaskManager {
    private final Object lock = new Object();
    private final PriorityQueue<Integer> queue = new PriorityQueue<>(10, Collections.reverseOrder());
    private int highestPriority = Integer.MIN_VALUE;

    public static class PriorityTooLowException extends IOException {
        public PriorityTooLowException(int i2, int i3) {
            super("Priority too low [priority=" + i2 + ", highest=" + i3 + "]");
        }
    }

    public void add(int i2) {
        synchronized (this.lock) {
            this.queue.add(Integer.valueOf(i2));
            this.highestPriority = Math.max(this.highestPriority, i2);
        }
    }

    public void proceed(int i2) throws InterruptedException {
        synchronized (this.lock) {
            while (this.highestPriority != i2) {
                try {
                    this.lock.wait();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public boolean proceedNonBlocking(int i2) {
        boolean z2;
        synchronized (this.lock) {
            z2 = this.highestPriority == i2;
        }
        return z2;
    }

    public void proceedOrThrow(int i2) throws PriorityTooLowException {
        synchronized (this.lock) {
            try {
                if (this.highestPriority != i2) {
                    throw new PriorityTooLowException(i2, this.highestPriority);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void remove(int i2) {
        synchronized (this.lock) {
            this.queue.remove(Integer.valueOf(i2));
            this.highestPriority = this.queue.isEmpty() ? Integer.MIN_VALUE : ((Integer) Util.castNonNull(this.queue.peek())).intValue();
            this.lock.notifyAll();
        }
    }
}
