package com.google.android.gms.auth.api.accounttransfer;

/* loaded from: classes3.dex */
final class zzf extends zzj {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzg f12625b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzf(zzg zzgVar, zzl zzlVar) {
        super(zzlVar);
        this.f12625b = zzgVar;
    }

    @Override // com.google.android.gms.internal.auth.zzan, com.google.android.gms.internal.auth.zzat
    public final void zzc(DeviceMetaData deviceMetaData) {
        this.f12625b.f12630a.setResult(deviceMetaData);
    }
}
