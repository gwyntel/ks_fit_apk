package anet.channel.monitor;

import anet.channel.status.NetworkStatusHelper;

/* loaded from: classes2.dex */
class c implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b f6830a;

    c(b bVar) {
        this.f6830a = bVar;
    }

    @Override // anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener
    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f6830a.f6828n.a();
        b.f6820f = 0L;
        this.f6830a.d();
    }
}
