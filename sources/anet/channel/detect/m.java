package anet.channel.detect;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f6741a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ l f6742b;

    m(l lVar, NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f6742b = lVar;
        this.f6741a = networkStatus;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            NetworkStatusHelper.NetworkStatus networkStatus = this.f6741a;
            if (networkStatus != NetworkStatusHelper.NetworkStatus.NO && networkStatus != NetworkStatusHelper.NetworkStatus.NONE) {
                this.f6742b.f6740a.a(NetworkStatusHelper.getUniqueId(networkStatus));
            }
        } catch (Throwable th) {
            ALog.e("anet.MTUDetector", "MTU detecet fail.", null, th, new Object[0]);
        }
    }
}
