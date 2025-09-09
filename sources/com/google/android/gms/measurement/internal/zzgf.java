package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zzgf {
    private final String zza;
    private final long zzb;
    private boolean zzc;
    private long zzd;
    private final /* synthetic */ zzge zze;

    public zzgf(zzge zzgeVar, String str, long j2) {
        this.zze = zzgeVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = j2;
    }

    @WorkerThread
    public final long zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.l().getLong(this.zza, this.zzb);
        }
        return this.zzd;
    }

    @WorkerThread
    public final void zza(long j2) {
        SharedPreferences.Editor editorEdit = this.zze.l().edit();
        editorEdit.putLong(this.zza, j2);
        editorEdit.apply();
        this.zzd = j2;
    }
}
