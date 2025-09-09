package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzaq;
import com.google.android.gms.internal.auth.zzau;

/* loaded from: classes3.dex */
final class zzg extends zzl {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzaq f12626b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzg(AccountTransferClient accountTransferClient, int i2, zzaq zzaqVar) {
        super(1608, null);
        this.f12626b = zzaqVar;
    }

    @Override // com.google.android.gms.auth.api.accounttransfer.zzl
    protected final void b(zzau zzauVar) throws RemoteException {
        zzauVar.zzd(new zzf(this, this), this.f12626b);
    }
}
