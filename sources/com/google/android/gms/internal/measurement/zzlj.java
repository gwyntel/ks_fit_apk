package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlw;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes3.dex */
public class zzlj {

    /* renamed from: a, reason: collision with root package name */
    static final zzlj f13225a = new zzlj(true);
    private static volatile boolean zzb = false;
    private static boolean zzc = true;
    private static volatile zzlj zzd;
    private final Map<zza, zzlw.zzd<?, ?>> zze = Collections.emptyMap();

    private static final class zza {
        private final Object zza;
        private final int zzb;

        zza(Object obj, int i2) {
            this.zza = obj;
            this.zzb = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) obj;
            return this.zza == zzaVar.zza && this.zzb == zzaVar.zzb;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zza) * 65535) + this.zzb;
        }
    }

    private zzlj(boolean z2) {
    }

    public static zzlj zza() {
        zzlj zzljVar = zzd;
        if (zzljVar != null) {
            return zzljVar;
        }
        synchronized (zzlj.class) {
            try {
                zzlj zzljVar2 = zzd;
                if (zzljVar2 != null) {
                    return zzljVar2;
                }
                zzlj zzljVarB = zzlv.b(zzlj.class);
                zzd = zzljVarB;
                return zzljVarB;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final <ContainingType extends zznj> zzlw.zzd<ContainingType, ?> zza(ContainingType containingtype, int i2) {
        return (zzlw.zzd) this.zze.get(new zza(containingtype, i2));
    }
}
