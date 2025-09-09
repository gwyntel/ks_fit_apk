package anet.channel;

import anetwork.channel.cache.CacheManager;

/* loaded from: classes2.dex */
final class j implements Runnable {
    j() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            anet.channel.b.a aVar = new anet.channel.b.a();
            aVar.a();
            CacheManager.addCache(aVar, new k(this), 1);
        } catch (Exception unused) {
        }
    }
}
