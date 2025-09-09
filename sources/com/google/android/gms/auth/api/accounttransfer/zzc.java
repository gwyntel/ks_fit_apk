package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzau;
import com.google.android.gms.internal.auth.zzaz;

/* loaded from: classes3.dex */
final class zzc extends zzn {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzaz f12622c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzc(AccountTransferClient accountTransferClient, int i2, zzaz zzazVar) {
        super(1606);
        this.f12622c = zzazVar;
    }

    @Override // com.google.android.gms.auth.api.accounttransfer.zzl
    protected final void b(zzau zzauVar) throws RemoteException {
        zzauVar.zzh(this.f12632b, this.f12622c);
    }
}
