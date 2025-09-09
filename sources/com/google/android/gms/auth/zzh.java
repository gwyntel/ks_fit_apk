package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.io.IOException;

/* loaded from: classes3.dex */
final class zzh implements zzk {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f12662a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Bundle f12663b;

    zzh(String str, Bundle bundle) {
        this.f12662a = str;
        this.f12663b = bundle;
    }

    @Override // com.google.android.gms.auth.zzk
    public final /* bridge */ /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundleZzd = com.google.android.gms.internal.auth.zze.zzb(iBinder).zzd(this.f12662a, this.f12663b);
        zzl.b(bundleZzd);
        String string = bundleZzd.getString("Error");
        if (bundleZzd.getBoolean("booleanResult")) {
            return null;
        }
        throw new GoogleAuthException(string);
    }
}
