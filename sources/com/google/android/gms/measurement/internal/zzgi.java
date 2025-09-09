package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zzgi {
    private final String zza;
    private final String zzb;
    private final String zzc;
    private final long zzd;
    private final /* synthetic */ zzge zze;

    @WorkerThread
    private final long zzb() {
        return this.zze.l().getLong(this.zza, 0L);
    }

    @WorkerThread
    private final void zzc() {
        this.zze.zzt();
        long jCurrentTimeMillis = this.zze.zzb().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zze.l().edit();
        editorEdit.remove(this.zzb);
        editorEdit.remove(this.zzc);
        editorEdit.putLong(this.zza, jCurrentTimeMillis);
        editorEdit.apply();
    }

    @WorkerThread
    public final Pair<String, Long> zza() {
        long jAbs;
        this.zze.zzt();
        this.zze.zzt();
        long jZzb = zzb();
        if (jZzb == 0) {
            zzc();
            jAbs = 0;
        } else {
            jAbs = Math.abs(jZzb - this.zze.zzb().currentTimeMillis());
        }
        long j2 = this.zzd;
        if (jAbs < j2) {
            return null;
        }
        if (jAbs > (j2 << 1)) {
            zzc();
            return null;
        }
        String string = this.zze.l().getString(this.zzc, null);
        long j3 = this.zze.l().getLong(this.zzb, 0L);
        zzc();
        return (string == null || j3 <= 0) ? zzge.f13277b : new Pair<>(string, Long.valueOf(j3));
    }

    private zzgi(zzge zzgeVar, String str, long j2) {
        this.zze = zzgeVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j2 > 0);
        this.zza = str + ":start";
        this.zzb = str + ":count";
        this.zzc = str + ":value";
        this.zzd = j2;
    }

    @WorkerThread
    public final void zza(String str, long j2) {
        this.zze.zzt();
        if (zzb() == 0) {
            zzc();
        }
        if (str == null) {
            str = "";
        }
        long j3 = this.zze.l().getLong(this.zzb, 0L);
        if (j3 <= 0) {
            SharedPreferences.Editor editorEdit = this.zze.l().edit();
            editorEdit.putString(this.zzc, str);
            editorEdit.putLong(this.zzb, 1L);
            editorEdit.apply();
            return;
        }
        long j4 = j3 + 1;
        boolean z2 = (this.zze.zzq().S().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j4;
        SharedPreferences.Editor editorEdit2 = this.zze.l().edit();
        if (z2) {
            editorEdit2.putString(this.zzc, str);
        }
        editorEdit2.putLong(this.zzb, j4);
        editorEdit2.apply();
    }
}
