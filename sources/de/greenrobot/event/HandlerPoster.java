package de.greenrobot.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes4.dex */
final class HandlerPoster extends Handler {
    private final EventBus eventBus;
    private boolean handlerActive;
    private final int maxMillisInsideHandleMessage;
    private final PendingPostQueue queue;

    HandlerPoster(EventBus eventBus, Looper looper, int i2) {
        super(looper);
        this.eventBus = eventBus;
        this.maxMillisInsideHandleMessage = i2;
        this.queue = new PendingPostQueue();
    }

    void a(Subscription subscription, Object obj) {
        PendingPost pendingPostA = PendingPost.a(subscription, obj);
        synchronized (this) {
            try {
                this.queue.a(pendingPostA);
                if (!this.handlerActive) {
                    this.handlerActive = true;
                    if (!sendMessage(obtainMessage())) {
                        throw new EventBusException("Could not send handler message");
                    }
                }
            } finally {
            }
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            do {
                PendingPost pendingPostB = this.queue.b();
                if (pendingPostB == null) {
                    synchronized (this) {
                        pendingPostB = this.queue.b();
                        if (pendingPostB == null) {
                            this.handlerActive = false;
                            return;
                        }
                    }
                }
                this.eventBus.c(pendingPostB);
            } while (SystemClock.uptimeMillis() - jUptimeMillis < this.maxMillisInsideHandleMessage);
            if (!sendMessage(obtainMessage())) {
                throw new EventBusException("Could not send handler message");
            }
            this.handlerActive = true;
        } catch (Throwable th) {
            this.handlerActive = false;
            throw th;
        }
    }
}
