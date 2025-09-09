package anet.channel.status;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Iterator;

/* loaded from: classes2.dex */
final class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f6923a;

    a(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f6923a = networkStatus;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Iterator<NetworkStatusHelper.INetworkStatusChangeListener> it = NetworkStatusHelper.listeners.iterator();
            while (it.hasNext()) {
                NetworkStatusHelper.INetworkStatusChangeListener next = it.next();
                long jCurrentTimeMillis = System.currentTimeMillis();
                next.onNetworkStatusChanged(this.f6923a);
                if (System.currentTimeMillis() - jCurrentTimeMillis > 500) {
                    ALog.e("awcn.NetworkStatusHelper", "call back cost too much time", null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, next);
                }
            }
        } catch (Exception unused) {
        }
    }
}
