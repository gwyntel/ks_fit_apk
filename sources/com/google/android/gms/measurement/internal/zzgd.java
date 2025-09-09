package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zzgd {
    private final String zza;
    private final boolean zzb;
    private boolean zzc;
    private boolean zzd;
    private final /* synthetic */ zzge zze;

    public zzgd(zzge zzgeVar, String str, boolean z2) {
        this.zze = zzgeVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = z2;
    }

    @WorkerThread
    public final void zza(boolean z2) {
        SharedPreferences.Editor editorEdit = this.zze.l().edit();
        editorEdit.putBoolean(this.zza, z2);
        editorEdit.apply();
        this.zzd = z2;
    }

    @WorkerThread
    public final boolean zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.l().getBoolean(this.zza, this.zzb);
        }
        return this.zzd;
    }
}
