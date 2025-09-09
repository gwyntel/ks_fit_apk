package de.greenrobot.event;

import android.util.Log;

/* loaded from: classes4.dex */
final class BackgroundPoster implements Runnable {
    private final EventBus eventBus;
    private volatile boolean executorRunning;
    private final PendingPostQueue queue = new PendingPostQueue();

    BackgroundPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object obj) {
        PendingPost pendingPostA = PendingPost.a(subscription, obj);
        synchronized (this) {
            try {
                this.queue.a(pendingPostA);
                if (!this.executorRunning) {
                    this.executorRunning = true;
                    EventBus.f24971a.execute(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                try {
                    PendingPost pendingPostC = this.queue.c(1000);
                    if (pendingPostC == null) {
                        synchronized (this) {
                            pendingPostC = this.queue.b();
                            if (pendingPostC == null) {
                                this.executorRunning = false;
                                this.executorRunning = false;
                                return;
                            }
                        }
                    }
                    this.eventBus.c(pendingPostC);
                } catch (InterruptedException e2) {
                    Log.w("Event", String.valueOf(Thread.currentThread().getName()) + " was interruppted", e2);
                    this.executorRunning = false;
                    return;
                }
            } catch (Throwable th) {
                this.executorRunning = false;
                throw th;
            }
        }
    }
}
