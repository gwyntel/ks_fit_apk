package com.huawei.hms.push;

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
public class p {

    /* renamed from: a, reason: collision with root package name */
    private ServiceConnection f16722a;

    /* renamed from: b, reason: collision with root package name */
    private Messenger f16723b = null;

    class a implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Bundle f16724a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ Context f16725b;

        a(Bundle bundle, Context context) {
            this.f16724a = bundle;
            this.f16725b = context;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
            HMSLog.i("RemoteService", "remote service onConnected");
            p.this.f16723b = new Messenger(iBinder);
            Message messageObtain = Message.obtain();
            messageObtain.setData(this.f16724a);
            try {
                p.this.f16723b.send(messageObtain);
            } catch (RemoteException unused) {
                HMSLog.i("RemoteService", "remote service message send failed");
            }
            HMSLog.i("RemoteService", "remote service unbindservice");
            this.f16725b.unbindService(p.this.f16722a);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            HMSLog.i("RemoteService", "remote service onDisconnected");
            p.this.f16723b = null;
        }
    }

    public boolean a(Context context, Bundle bundle, Intent intent) {
        Context applicationContext = context.getApplicationContext();
        this.f16722a = new a(bundle, applicationContext);
        HMSLog.i("RemoteService", "remote service bind service start");
        return applicationContext.bindService(intent, this.f16722a, 1);
    }
}
