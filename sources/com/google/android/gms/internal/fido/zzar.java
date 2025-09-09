package com.google.android.gms.internal.fido;

/* loaded from: classes3.dex */
final class zzar extends zzao {
    private final zzat zza;

    zzar(zzat zzatVar, int i2) {
        super(zzatVar.size(), i2);
        this.zza = zzatVar;
    }

    @Override // com.google.android.gms.internal.fido.zzao
    protected final Object a(int i2) {
        return this.zza.get(i2);
    }
}
