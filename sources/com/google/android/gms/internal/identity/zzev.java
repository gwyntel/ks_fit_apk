package com.google.android.gms.internal.identity;

/* loaded from: classes3.dex */
final class zzev extends zzet {
    private final zzex zza;

    zzev(zzex zzexVar, int i2) {
        super(zzexVar.size(), i2);
        this.zza = zzexVar;
    }

    @Override // com.google.android.gms.internal.identity.zzet
    protected final Object a(int i2) {
        return this.zza.get(i2);
    }
}
