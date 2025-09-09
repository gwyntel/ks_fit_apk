package anet.channel.util;

import anet.channel.statist.NetTypeStat;
import anet.channel.thread.ThreadPoolExecutorFactory;

/* loaded from: classes2.dex */
final class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f7084a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ NetTypeStat f7085b;

    d(String str, NetTypeStat netTypeStat) {
        this.f7084a = str;
        this.f7085b = netTypeStat;
    }

    @Override // java.lang.Runnable
    public void run() {
        ThreadPoolExecutorFactory.submitPriorityTask(new e(this), ThreadPoolExecutorFactory.Priority.LOW);
    }
}
