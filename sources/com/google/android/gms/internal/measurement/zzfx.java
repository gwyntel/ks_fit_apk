package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.alipay.sdk.m.u.i;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
final class zzfx extends zzgw {
    private final Context zza;

    @Nullable
    private final zzhs<zzho<zzgj>> zzb;

    zzfx(Context context, zzhs zzhsVar) {
        if (context == null) {
            throw new NullPointerException("Null context");
        }
        this.zza = context;
        this.zzb = zzhsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzgw
    final Context a() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzgw
    final zzhs b() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        zzhs<zzho<zzgj>> zzhsVar;
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzgw) {
            zzgw zzgwVar = (zzgw) obj;
            if (this.zza.equals(zzgwVar.a()) && ((zzhsVar = this.zzb) != null ? zzhsVar.equals(zzgwVar.b()) : zzgwVar.b() == null)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (this.zza.hashCode() ^ 1000003) * 1000003;
        zzhs<zzho<zzgj>> zzhsVar = this.zzb;
        return iHashCode ^ (zzhsVar == null ? 0 : zzhsVar.hashCode());
    }

    public final String toString() {
        return "FlagsContext{context=" + String.valueOf(this.zza) + ", hermeticFileOverrides=" + String.valueOf(this.zzb) + i.f9804d;
    }
}
