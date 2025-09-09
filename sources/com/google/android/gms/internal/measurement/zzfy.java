package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzfy extends ContentObserver {
    private final /* synthetic */ zzfw zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzfy(zzfw zzfwVar, Handler handler) {
        super(null);
        this.zza = zzfwVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        this.zza.zzd();
    }
}
