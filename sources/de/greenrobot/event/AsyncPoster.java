package de.greenrobot.event;

/* loaded from: classes4.dex */
class AsyncPoster implements Runnable {
    private final EventBus eventBus;
    private final PendingPostQueue queue = new PendingPostQueue();

    AsyncPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object obj) {
        this.queue.a(PendingPost.a(subscription, obj));
        EventBus.f24971a.execute(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        PendingPost pendingPostB = this.queue.b();
        if (pendingPostB == null) {
            throw new IllegalStateException("No pending post available");
        }
        this.eventBus.c(pendingPostB);
    }
}
