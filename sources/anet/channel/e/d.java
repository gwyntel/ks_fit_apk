package anet.channel.e;

import anet.channel.status.NetworkStatusHelper;

/* loaded from: classes2.dex */
final class d implements NetworkStatusHelper.INetworkStatusChangeListener {
    d() {
    }

    @Override // anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener
    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        a.a(networkStatus);
    }
}
