package com.google.android.gms.common.api.internal;

import androidx.annotation.BinderThread;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
final class zaar extends com.google.android.gms.signin.internal.zac {
    private final WeakReference zaa;

    zaar(zaaw zaawVar) {
        this.zaa = new WeakReference(zaawVar);
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zae
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zak zakVar) {
        zaaw zaawVar = (zaaw) this.zaa.get();
        if (zaawVar == null) {
            return;
        }
        zaawVar.zaa.f(new zaaq(this, zaawVar, zaawVar, zakVar));
    }
}
