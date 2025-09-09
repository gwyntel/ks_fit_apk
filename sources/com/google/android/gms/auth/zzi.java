package com.google.android.gms.auth;

import android.os.IBinder;
import android.os.RemoteException;
import java.io.IOException;

/* loaded from: classes3.dex */
final class zzi implements zzk {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccountChangeEventsRequest f12664a;

    zzi(AccountChangeEventsRequest accountChangeEventsRequest) {
        this.f12664a = accountChangeEventsRequest;
    }

    @Override // com.google.android.gms.auth.zzk
    public final /* bridge */ /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        AccountChangeEventsResponse accountChangeEventsResponseZzh = com.google.android.gms.internal.auth.zze.zzb(iBinder).zzh(this.f12664a);
        zzl.b(accountChangeEventsResponseZzh);
        return accountChangeEventsResponseZzh.getEvents();
    }
}
