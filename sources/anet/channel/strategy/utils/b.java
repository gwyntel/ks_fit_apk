package anet.channel.strategy.utils;

import anet.channel.util.ALog;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes2.dex */
final class b implements ThreadFactory {
    b() {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "AMDC" + a.f7058a.incrementAndGet());
        ALog.i(anet.channel.strategy.dispatch.a.TAG, "thread created!", null, "name", thread.getName());
        thread.setPriority(5);
        return thread;
    }
}
