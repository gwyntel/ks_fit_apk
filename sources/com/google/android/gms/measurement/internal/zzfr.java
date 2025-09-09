package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzfr implements Runnable {
    private final /* synthetic */ int zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Object zzc;
    private final /* synthetic */ Object zzd;
    private final /* synthetic */ Object zze;
    private final /* synthetic */ zzfs zzf;

    zzfr(zzfs zzfsVar, int i2, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzfsVar;
        this.zza = i2;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzge zzgeVarZzn = this.zzf.f13286a.zzn();
        if (!zzgeVarZzn.zzae()) {
            this.zzf.f(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        if (this.zzf.zza == 0) {
            if (this.zzf.zze().zzx()) {
                this.zzf.zza = 'C';
            } else {
                this.zzf.zza = 'c';
            }
        }
        if (this.zzf.zzb < 0) {
            this.zzf.zzb = 81010L;
        }
        String strSubstring = "2" + "01VDIWEA?".charAt(this.zza) + this.zzf.zza + this.zzf.zzb + ":" + zzfs.e(true, this.zzb, this.zzc, this.zzd, this.zze);
        if (strSubstring.length() > 1024) {
            strSubstring = this.zzb.substring(0, 1024);
        }
        zzgi zzgiVar = zzgeVarZzn.zzb;
        if (zzgiVar != null) {
            zzgiVar.zza(strSubstring, 1L);
        }
    }
}
