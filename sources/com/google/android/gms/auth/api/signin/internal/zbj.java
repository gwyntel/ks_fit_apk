package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zbj extends zba {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zbk f12655a;

    zbj(zbk zbkVar) {
        this.f12655a = zbkVar;
    }

    @Override // com.google.android.gms.auth.api.signin.internal.zba, com.google.android.gms.auth.api.signin.internal.zbr
    public final void zbb(Status status) throws RemoteException {
        this.f12655a.setResult((zbk) status);
    }
}
