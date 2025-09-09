package anet.channel.detect;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;

/* loaded from: classes2.dex */
class a implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ExceptionDetector f6721a;

    a(ExceptionDetector exceptionDetector) {
        this.f6721a = exceptionDetector;
    }

    @Override // anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener
    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        ThreadPoolExecutorFactory.submitDetectTask(new b(this));
    }
}
