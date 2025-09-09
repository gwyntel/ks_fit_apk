package com.hihonor.push.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes3.dex */
public class g0 implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    public Messenger f15495a;

    /* renamed from: b, reason: collision with root package name */
    public Bundle f15496b;

    /* renamed from: c, reason: collision with root package name */
    public Context f15497c;

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        Log.i("MessengerSrvConnection", "onServiceConnected");
        this.f15495a = new Messenger(iBinder);
        Message messageObtain = Message.obtain();
        messageObtain.setData(this.f15496b);
        try {
            this.f15495a.send(messageObtain);
        } catch (Exception e2) {
            e2.getMessage();
        }
        Log.i("MessengerSrvConnection", "start unbind service.");
        try {
            this.f15497c.unbindService(this);
            Log.i("MessengerSrvConnection", "unbind service end.");
        } catch (Exception unused) {
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.i("MessengerSrvConnection", "onServiceDisconnected");
        this.f15495a = null;
        this.f15496b = null;
        this.f15497c = null;
    }
}
