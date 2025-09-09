package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zbf extends zba {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zbg f12651a;

    zbf(zbg zbgVar) {
        this.f12651a = zbgVar;
    }

    @Override // com.google.android.gms.auth.api.signin.internal.zba, com.google.android.gms.auth.api.signin.internal.zbr
    public final void zbd(GoogleSignInAccount googleSignInAccount, Status status) throws RemoteException {
        if (googleSignInAccount != null) {
            zbg zbgVar = this.f12651a;
            zbn.zbc(zbgVar.f12652d).zbe(zbgVar.f12653e, googleSignInAccount);
        }
        this.f12651a.setResult((zbg) new GoogleSignInResult(googleSignInAccount, status));
    }
}
