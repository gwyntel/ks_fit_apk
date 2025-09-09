package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.auth.zzau;
import com.google.android.gms.internal.auth.zzav;

/* loaded from: classes3.dex */
final class zzi extends zzn {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzav f12628c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzi(AccountTransferClient accountTransferClient, int i2, zzav zzavVar) {
        super(1610);
        this.f12628c = zzavVar;
    }

    @Override // com.google.android.gms.auth.api.accounttransfer.zzl
    protected final void b(zzau zzauVar) throws RemoteException {
        zzauVar.zzf(this.f12632b, this.f12628c);
    }
}
