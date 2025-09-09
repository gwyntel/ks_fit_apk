package com.taobao.accs.data;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* loaded from: classes4.dex */
class k implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MsgDistributeService f20165a;

    k(MsgDistributeService msgDistributeService) {
        this.f20165a = msgDistributeService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
