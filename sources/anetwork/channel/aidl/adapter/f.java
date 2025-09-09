package anetwork.channel.aidl.adapter;

import anet.channel.util.ALog;

/* loaded from: classes2.dex */
final class f implements Runnable {
    f() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (d.f7131c) {
            d.f7131c = false;
            ALog.e("anet.RemoteGetter", "binding service timeout. reset status!", null, new Object[0]);
        }
    }
}
