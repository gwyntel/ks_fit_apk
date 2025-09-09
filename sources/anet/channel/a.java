package anet.channel;

import android.content.Intent;
import anet.channel.util.ALog;
import java.util.Iterator;

/* loaded from: classes2.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f6677a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ AccsSessionManager f6678b;

    a(AccsSessionManager accsSessionManager, Intent intent) {
        this.f6678b = accsSessionManager;
        this.f6677a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        Iterator it = AccsSessionManager.f6566c.iterator();
        while (it.hasNext()) {
            try {
                ((ISessionListener) it.next()).onConnectionChanged(this.f6677a);
            } catch (Exception e2) {
                ALog.e("awcn.AccsSessionManager", "notifyListener exception.", null, e2, new Object[0]);
            }
        }
    }
}
