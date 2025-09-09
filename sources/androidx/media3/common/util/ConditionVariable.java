package androidx.media3.common.util;

@UnstableApi
/* loaded from: classes.dex */
public class ConditionVariable {
    private final Clock clock;
    private boolean isOpen;

    public ConditionVariable() {
        this(Clock.DEFAULT);
    }

    public synchronized void block() throws InterruptedException {
        while (!this.isOpen) {
            wait();
        }
    }

    public synchronized void blockUninterruptible() {
        boolean z2 = false;
        while (!this.isOpen) {
            try {
                wait();
            } catch (InterruptedException unused) {
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized boolean close() {
        boolean z2;
        z2 = this.isOpen;
        this.isOpen = false;
        return z2;
    }

    public synchronized boolean isOpen() {
        return this.isOpen;
    }

    public synchronized boolean open() {
        if (this.isOpen) {
            return false;
        }
        this.isOpen = true;
        notifyAll();
        return true;
    }

    public ConditionVariable(Clock clock) {
        this.clock = clock;
    }

    public synchronized boolean block(long j2) throws InterruptedException {
        if (j2 <= 0) {
            return this.isOpen;
        }
        long jElapsedRealtime = this.clock.elapsedRealtime();
        long j3 = j2 + jElapsedRealtime;
        if (j3 < jElapsedRealtime) {
            block();
        } else {
            while (!this.isOpen && jElapsedRealtime < j3) {
                wait(j3 - jElapsedRealtime);
                jElapsedRealtime = this.clock.elapsedRealtime();
            }
        }
        return this.isOpen;
    }
}
