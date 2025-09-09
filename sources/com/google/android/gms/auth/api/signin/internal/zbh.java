package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zbh extends zba {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zbi f12654a;

    zbh(zbi zbiVar) {
        this.f12654a = zbiVar;
    }

    @Override // com.google.android.gms.auth.api.signin.internal.zba, com.google.android.gms.auth.api.signin.internal.zbr
    public final void zbc(Status status) throws RemoteException {
        this.f12654a.setResult((zbi) status);
    }
}
