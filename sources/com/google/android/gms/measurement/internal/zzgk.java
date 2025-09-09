package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zzgk {
    private final String zza;
    private final String zzb;
    private boolean zzc;
    private String zzd;
    private final /* synthetic */ zzge zze;

    public zzgk(zzge zzgeVar, String str, String str2) {
        this.zze = zzgeVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = null;
    }

    @WorkerThread
    public final String zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.l().getString(this.zza, null);
        }
        return this.zzd;
    }

    @WorkerThread
    public final void zza(String str) {
        SharedPreferences.Editor editorEdit = this.zze.l().edit();
        editorEdit.putString(this.zza, str);
        editorEdit.apply();
        this.zzd = str;
    }
}
