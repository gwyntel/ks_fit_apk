package com.google.android.gms.internal.common;

/* loaded from: classes3.dex */
final class zzae extends zzz {
    private final zzag zza;

    zzae(zzag zzagVar, int i2) {
        super(zzagVar.size(), i2);
        this.zza = zzagVar;
    }

    @Override // com.google.android.gms.internal.common.zzz
    protected final Object a(int i2) {
        return this.zza.get(i2);
    }
}
