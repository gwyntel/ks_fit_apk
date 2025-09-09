package com.xiaomi.push.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import java.util.Iterator;

/* loaded from: classes4.dex */
class bv implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ServiceClient f24563a;

    bv(ServiceClient serviceClient) {
        this.f24563a = serviceClient;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.f24563a) {
            this.f24563a.f947b = new Messenger(iBinder);
            this.f24563a.f948b = false;
            Iterator it = this.f24563a.f945a.iterator();
            while (it.hasNext()) {
                try {
                    this.f24563a.f947b.send((Message) it.next());
                } catch (RemoteException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
            this.f24563a.f945a.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f24563a.f947b = null;
        this.f24563a.f948b = false;
    }
}
