package com.google.android.gms.internal.auth;

import android.content.Context;
import com.alipay.sdk.m.u.i;

/* loaded from: classes3.dex */
final class zzcd extends zzda {
    private final Context zza;
    private final zzdj zzb;

    zzcd(Context context, zzdj zzdjVar) {
        this.zza = context;
        this.zzb = zzdjVar;
    }

    @Override // com.google.android.gms.internal.auth.zzda
    final Context a() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.auth.zzda
    final zzdj b() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzda) {
            zzda zzdaVar = (zzda) obj;
            if (this.zza.equals(zzdaVar.a()) && this.zzb.equals(zzdaVar.b())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        return "FlagsContext{context=" + this.zza.toString() + ", hermeticFileOverrides=" + this.zzb.toString() + i.f9804d;
    }
}
