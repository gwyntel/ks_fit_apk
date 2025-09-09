package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzau;
import com.google.android.gms.internal.auth.zzax;

/* loaded from: classes3.dex */
final class zze extends zzl {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzax f12624b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zze(AccountTransferClient accountTransferClient, int i2, zzax zzaxVar) {
        super(1607, null);
        this.f12624b = zzaxVar;
    }

    @Override // com.google.android.gms.auth.api.accounttransfer.zzl
    protected final void b(zzau zzauVar) throws RemoteException {
        zzauVar.zzg(new zzd(this, this), this.f12624b);
    }
}
