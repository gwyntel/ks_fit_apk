package anetwork.channel.aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.degrade.DegradableNetworkDelegate;
import anetwork.channel.http.HttpNetworkDelegate;

/* loaded from: classes2.dex */
public class NetworkService extends Service {

    /* renamed from: b, reason: collision with root package name */
    private Context f7116b;

    /* renamed from: c, reason: collision with root package name */
    private RemoteNetwork.Stub f7117c = null;

    /* renamed from: d, reason: collision with root package name */
    private RemoteNetwork.Stub f7118d = null;

    /* renamed from: a, reason: collision with root package name */
    IRemoteNetworkGetter.Stub f7115a = new IRemoteNetworkGetter.Stub() { // from class: anetwork.channel.aidl.NetworkService.1
        @Override // anetwork.channel.aidl.IRemoteNetworkGetter
        public RemoteNetwork get(int i2) throws RemoteException {
            return i2 == 1 ? NetworkService.this.f7117c : NetworkService.this.f7118d;
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        this.f7116b = getApplicationContext();
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.NetworkService", "onBind:" + intent.getAction(), null, new Object[0]);
        }
        this.f7117c = new DegradableNetworkDelegate(this.f7116b);
        this.f7118d = new HttpNetworkDelegate(this.f7116b);
        if (IRemoteNetworkGetter.class.getName().equals(intent.getAction())) {
            return this.f7115a;
        }
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        return 2;
    }
}
