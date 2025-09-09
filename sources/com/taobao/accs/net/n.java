package com.taobao.accs.net;

import com.taobao.accs.base.AccsConnectStateListener;
import com.taobao.accs.base.TaoBaseService;

/* loaded from: classes4.dex */
class n implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaoBaseService.ConnectInfo f20243a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ AccsConnectStateListener f20244b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ m f20245c;

    n(m mVar, TaoBaseService.ConnectInfo connectInfo, AccsConnectStateListener accsConnectStateListener) {
        this.f20245c = mVar;
        this.f20243a = connectInfo;
        this.f20244b = accsConnectStateListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        TaoBaseService.ConnectInfo connectInfo = this.f20243a;
        if (connectInfo.connected) {
            this.f20244b.onConnected(connectInfo);
        } else {
            this.f20244b.onDisconnected(connectInfo);
        }
    }
}
