package anetwork.channel.unified;

import anet.channel.thread.ThreadPoolExecutorFactory;

/* loaded from: classes2.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f7269a;

    f(e eVar) {
        this.f7269a = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ThreadPoolExecutorFactory.submitPriorityTask(this.f7269a, ThreadPoolExecutorFactory.Priority.HIGH);
    }
}
