package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import androidx.media3.exoplayer.ExoPlayer;

/* loaded from: classes3.dex */
final class zzji extends zzaw {
    private final /* synthetic */ zzin zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzji(zzin zzinVar, zzic zzicVar) {
        super(zzicVar);
        this.zza = zzinVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    @WorkerThread
    public final void zzb() {
        if (this.zza.f13286a.zzah()) {
            this.zza.zzn.zza(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }
}
