package de.greenrobot.event;

/* loaded from: classes4.dex */
final class PendingPostQueue {
    private PendingPost head;
    private PendingPost tail;

    PendingPostQueue() {
    }

    synchronized void a(PendingPost pendingPost) {
        try {
            if (pendingPost == null) {
                throw new NullPointerException("null cannot be enqueued");
            }
            PendingPost pendingPost2 = this.tail;
            if (pendingPost2 != null) {
                pendingPost2.f24977c = pendingPost;
                this.tail = pendingPost;
            } else {
                if (this.head != null) {
                    throw new IllegalStateException("Head present, but no tail");
                }
                this.tail = pendingPost;
                this.head = pendingPost;
            }
            notifyAll();
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized PendingPost b() {
        PendingPost pendingPost;
        pendingPost = this.head;
        if (pendingPost != null) {
            PendingPost pendingPost2 = pendingPost.f24977c;
            this.head = pendingPost2;
            if (pendingPost2 == null) {
                this.tail = null;
            }
        }
        return pendingPost;
    }

    synchronized PendingPost c(int i2) {
        try {
            if (this.head == null) {
                wait(i2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return b();
    }
}
