package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import androidx.annotation.VisibleForTesting;

@VisibleForTesting
/* loaded from: classes3.dex */
public final class zze implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12853a;
    private final int zzb;

    public zze(BaseGmsClient baseGmsClient, int i2) {
        this.f12853a = baseGmsClient;
        this.zzb = i2;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        BaseGmsClient baseGmsClient = this.f12853a;
        if (iBinder == null) {
            BaseGmsClient.w(baseGmsClient, 16);
            return;
        }
        synchronized (baseGmsClient.zzq) {
            try {
                BaseGmsClient baseGmsClient2 = this.f12853a;
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                baseGmsClient2.zzr = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGmsServiceBroker)) ? new zzad(iBinder) : (IGmsServiceBroker) iInterfaceQueryLocalInterface;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f12853a.x(0, null, this.zzb);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.f12853a.zzq) {
            this.f12853a.zzr = null;
        }
        BaseGmsClient baseGmsClient = this.f12853a;
        int i2 = this.zzb;
        Handler handler = baseGmsClient.f12809b;
        handler.sendMessage(handler.obtainMessage(6, i2, 1));
    }
}
