package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zzb extends com.google.android.gms.internal.common.zzi {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12851a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzb(BaseGmsClient baseGmsClient, Looper looper) {
        super(looper);
        this.f12851a = baseGmsClient;
    }

    private static final void zza(Message message) {
        zzc zzcVar = (zzc) message.obj;
        zzcVar.b();
        zzcVar.zzg();
    }

    private static final boolean zzb(Message message) {
        int i2 = message.what;
        return i2 == 2 || i2 == 1 || i2 == 7;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (this.f12851a.f12811d.get() != message.arg1) {
            if (zzb(message)) {
                zza(message);
                return;
            }
            return;
        }
        int i2 = message.what;
        if ((i2 == 1 || i2 == 7 || ((i2 == 4 && !this.f12851a.b()) || message.what == 5)) && !this.f12851a.isConnecting()) {
            zza(message);
            return;
        }
        int i3 = message.what;
        if (i3 == 4) {
            this.f12851a.zzB = new ConnectionResult(message.arg2);
            if (BaseGmsClient.A(this.f12851a)) {
                BaseGmsClient baseGmsClient = this.f12851a;
                if (!baseGmsClient.zzC) {
                    baseGmsClient.zzp(3, null);
                    return;
                }
            }
            BaseGmsClient baseGmsClient2 = this.f12851a;
            ConnectionResult connectionResult = baseGmsClient2.zzB != null ? baseGmsClient2.zzB : new ConnectionResult(8);
            this.f12851a.f12810c.onReportServiceBinding(connectionResult);
            this.f12851a.j(connectionResult);
            return;
        }
        if (i3 == 5) {
            BaseGmsClient baseGmsClient3 = this.f12851a;
            ConnectionResult connectionResult2 = baseGmsClient3.zzB != null ? baseGmsClient3.zzB : new ConnectionResult(8);
            this.f12851a.f12810c.onReportServiceBinding(connectionResult2);
            this.f12851a.j(connectionResult2);
            return;
        }
        if (i3 == 3) {
            Object obj = message.obj;
            ConnectionResult connectionResult3 = new ConnectionResult(message.arg2, obj instanceof PendingIntent ? (PendingIntent) obj : null);
            this.f12851a.f12810c.onReportServiceBinding(connectionResult3);
            this.f12851a.j(connectionResult3);
            return;
        }
        if (i3 == 6) {
            this.f12851a.zzp(5, null);
            BaseGmsClient baseGmsClient4 = this.f12851a;
            if (baseGmsClient4.zzw != null) {
                baseGmsClient4.zzw.onConnectionSuspended(message.arg2);
            }
            this.f12851a.onConnectionSuspended(message.arg2);
            BaseGmsClient.z(this.f12851a, 5, 1, null);
            return;
        }
        if (i3 == 2 && !this.f12851a.isConnected()) {
            zza(message);
            return;
        }
        if (zzb(message)) {
            ((zzc) message.obj).zze();
            return;
        }
        Log.wtf("GmsClient", "Don't know how to handle message: " + message.what, new Exception());
    }
}
