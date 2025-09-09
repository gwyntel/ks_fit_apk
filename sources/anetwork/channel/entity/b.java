package anetwork.channel.entity;

import java.util.concurrent.ThreadFactory;

/* loaded from: classes2.dex */
final class b implements ThreadFactory {
    b() {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, String.format("RepeaterThread:%d", Integer.valueOf(a.f7211b.getAndIncrement())));
    }
}
