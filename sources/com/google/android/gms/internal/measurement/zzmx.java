package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzmx implements zzoa {
    private static final zzng zza = new zzmw();
    private final zzng zzb;

    public zzmx() {
        this(new zzmy(zzlu.zza(), zza()));
    }

    private static zzng zza() {
        try {
            return (zzng) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception unused) {
            return zza;
        }
    }

    private zzmx(zzng zzngVar) {
        this.zzb = (zzng) zzlz.c(zzngVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.measurement.zzoa
    public final <T> zzob<T> zza(Class<T> cls) {
        zzod.zza(cls);
        zznh zznhVarZza = this.zzb.zza(cls);
        if (zznhVarZza.zzc()) {
            if (zzlw.class.isAssignableFrom(cls)) {
                return zznp.a(zzod.zzb(), zzln.b(), zznhVarZza.zza());
            }
            return zznp.a(zzod.zza(), zzln.a(), zznhVarZza.zza());
        }
        if (zzlw.class.isAssignableFrom(cls)) {
            if (zza(zznhVarZza)) {
                return zznn.b(cls, zznhVarZza, zznt.b(), zzmo.d(), zzod.zzb(), zzln.b(), zzne.b());
            }
            return zznn.b(cls, zznhVarZza, zznt.b(), zzmo.d(), zzod.zzb(), null, zzne.b());
        }
        if (zza(zznhVarZza)) {
            return zznn.b(cls, zznhVarZza, zznt.a(), zzmo.a(), zzod.zza(), zzln.a(), zzne.a());
        }
        return zznn.b(cls, zznhVarZza, zznt.a(), zzmo.a(), zzod.zza(), null, zzne.a());
    }

    private static boolean zza(zznh zznhVar) {
        return zzmz.f13232a[zznhVar.zzb().ordinal()] != 1;
    }
}
