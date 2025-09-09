package com.google.android.gms.auth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzby;
import java.io.IOException;

/* loaded from: classes3.dex */
final class zzj implements zzk {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f12665a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Context f12666b;

    zzj(String str, Context context) {
        this.f12665a = str;
        this.f12666b = context;
    }

    @Override // com.google.android.gms.auth.zzk
    public final /* bridge */ /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundleZzg = com.google.android.gms.internal.auth.zze.zzb(iBinder).zzg(this.f12665a);
        zzl.b(bundleZzg);
        String string = bundleZzg.getString("Error");
        Intent intent = (Intent) bundleZzg.getParcelable("userRecoveryIntent");
        PendingIntent pendingIntent = (PendingIntent) bundleZzg.getParcelable("userRecoveryPendingIntent");
        if (zzby.SUCCESS.equals(zzby.zza(string))) {
            return Boolean.TRUE;
        }
        zzl.zzn(this.f12666b, "requestGoogleAccountsAccess", string, intent, pendingIntent);
        throw new GoogleAuthException("Invalid state. Shouldn't happen");
    }
}
