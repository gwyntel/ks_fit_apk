package com.huawei.hms.opendevice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private ServiceConnection f16593a;

    /* renamed from: b, reason: collision with root package name */
    private Messenger f16594b = null;

    class a implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Bundle f16595a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ Context f16596b;

        a(Bundle bundle, Context context) {
            this.f16595a = bundle;
            this.f16596b = context;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
            HMSLog.i("RemoteService", "remote service onConnected");
            l.this.f16594b = new Messenger(iBinder);
            Message messageObtain = Message.obtain();
            messageObtain.setData(this.f16595a);
            try {
                l.this.f16594b.send(messageObtain);
            } catch (RemoteException unused) {
                HMSLog.i("RemoteService", "remote service message send failed");
            }
            HMSLog.i("RemoteService", "remote service unbindservice");
            this.f16596b.unbindService(l.this.f16593a);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            HMSLog.i("RemoteService", "remote service onDisconnected");
            l.this.f16594b = null;
        }
    }

    public boolean a(Context context, Bundle bundle, Intent intent) {
        Context applicationContext = context.getApplicationContext();
        this.f16593a = new a(bundle, applicationContext);
        HMSLog.i("RemoteService", "remote service bind service start");
        return applicationContext.bindService(intent, this.f16593a, 1);
    }
}
