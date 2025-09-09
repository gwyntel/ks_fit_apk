package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzau;
import com.google.android.gms.internal.auth.zzbb;

/* loaded from: classes3.dex */
final class zzh extends zzn {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzbb f12627c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzh(AccountTransferClient accountTransferClient, int i2, zzbb zzbbVar) {
        super(1609);
        this.f12627c = zzbbVar;
    }

    @Override // com.google.android.gms.auth.api.accounttransfer.zzl
    protected final void b(zzau zzauVar) throws RemoteException {
        zzauVar.zze(this.f12632b, this.f12627c);
    }
}
