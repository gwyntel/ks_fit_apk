package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import java.util.Iterator;

/* loaded from: classes4.dex */
class as implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ao f23387a;

    as(ao aoVar) {
        this.f23387a = aoVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.f23387a) {
            this.f23387a.f127a = new Messenger(iBinder);
            this.f23387a.f23382c = false;
            Iterator it = this.f23387a.f130a.iterator();
            while (it.hasNext()) {
                try {
                    this.f23387a.f127a.send((Message) it.next());
                } catch (RemoteException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
            this.f23387a.f130a.clear();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.f23387a.f127a = null;
        this.f23387a.f23382c = false;
    }
}
