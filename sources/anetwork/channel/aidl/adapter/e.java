package anetwork.channel.aidl.adapter;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import anet.channel.util.ALog;
import anetwork.channel.aidl.IRemoteNetworkGetter;

/* loaded from: classes2.dex */
final class e implements ServiceConnection {
    e() {
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.RemoteGetter", "[onServiceConnected]ANet_Service start success. ANet run with service mode", null, new Object[0]);
        }
        synchronized (d.class) {
            try {
                d.f7129a = IRemoteNetworkGetter.Stub.asInterface(iBinder);
                if (d.f7132d != null) {
                    d.f7132d.countDown();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        d.f7130b = false;
        d.f7131c = false;
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.RemoteGetter", "ANet_Service Disconnected", null, new Object[0]);
        }
        d.f7129a = null;
        d.f7131c = false;
        if (d.f7132d != null) {
            d.f7132d.countDown();
        }
    }
}
