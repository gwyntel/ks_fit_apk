package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzfp implements zzgj {
    private static final zzfv zza = new zzfn();
    private final zzfv zzb;

    public zzfp() {
        zzfv zzfvVar;
        zzes zzesVarZza = zzes.zza();
        try {
            zzfvVar = (zzfv) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception unused) {
            zzfvVar = zza;
        }
        zzfo zzfoVar = new zzfo(zzesVarZza, zzfvVar);
        byte[] bArr = zzfa.zzd;
        this.zzb = zzfoVar;
    }

    private static boolean zzb(zzfu zzfuVar) {
        return zzfuVar.zzc() + (-1) != 1;
    }

    @Override // com.google.android.gms.internal.auth.zzgj
    public final zzgi zza(Class cls) {
        zzgk.zze(cls);
        zzfu zzfuVarZzb = this.zzb.zzb(cls);
        return zzfuVarZzb.zzb() ? zzev.class.isAssignableFrom(cls) ? zzgb.a(zzgk.zzb(), zzeo.b(), zzfuVarZzb.zza()) : zzgb.a(zzgk.zza(), zzeo.a(), zzfuVarZzb.zza()) : zzev.class.isAssignableFrom(cls) ? zzb(zzfuVarZzb) ? zzga.c(cls, zzfuVarZzb, zzgd.b(), zzfl.d(), zzgk.zzb(), zzeo.b(), zzft.b()) : zzga.c(cls, zzfuVarZzb, zzgd.b(), zzfl.d(), zzgk.zzb(), null, zzft.b()) : zzb(zzfuVarZzb) ? zzga.c(cls, zzfuVarZzb, zzgd.a(), zzfl.c(), zzgk.zza(), zzeo.a(), zzft.a()) : zzga.c(cls, zzfuVarZzb, zzgd.a(), zzfl.c(), zzgk.zza(), null, zzft.a());
    }
}
