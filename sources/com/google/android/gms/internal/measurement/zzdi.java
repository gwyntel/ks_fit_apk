package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.measurement.dynamite.ModuleDescriptor;
import com.google.android.gms.internal.measurement.zzdf;

/* loaded from: classes3.dex */
final class zzdi extends zzdf.zza {
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ Context zze;
    private final /* synthetic */ Bundle zzf;
    private final /* synthetic */ zzdf zzg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdi(zzdf zzdfVar, String str, String str2, Context context, Bundle bundle) {
        super(zzdfVar);
        this.zzg = zzdfVar;
        this.zzc = str;
        this.zzd = str2;
        this.zze = context;
        this.zzf = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzdf.zza
    public final void zza() {
        String str;
        String str2;
        String str3;
        try {
            if (this.zzg.zzc(this.zzc, this.zzd)) {
                str3 = this.zzd;
                str2 = this.zzc;
                str = this.zzg.zzc;
            } else {
                str = null;
                str2 = null;
                str3 = null;
            }
            Preconditions.checkNotNull(this.zze);
            zzdf zzdfVar = this.zzg;
            zzdfVar.zzj = zzdfVar.a(this.zze, true);
            if (this.zzg.zzj == null) {
                Log.w(this.zzg.zzc, "Failed to connect to measurement client.");
                return;
            }
            int localVersion = DynamiteModule.getLocalVersion(this.zze, ModuleDescriptor.MODULE_ID);
            ((zzcu) Preconditions.checkNotNull(this.zzg.zzj)).initialize(ObjectWrapper.wrap(this.zze), new zzdd(81010L, Math.max(localVersion, r0), DynamiteModule.getRemoteVersion(this.zze, ModuleDescriptor.MODULE_ID) < localVersion, str, str2, str3, this.zzf, com.google.android.gms.measurement.internal.zzgw.zza(this.zze)), this.f13188a);
        } catch (Exception e2) {
            this.zzg.zza(e2, true, false);
        }
    }
}
