package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;

/* loaded from: classes3.dex */
final class zaap extends zaav {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zaaw f12707b;
    private final ArrayList zac;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaap(zaaw zaawVar, ArrayList arrayList) {
        super(zaawVar, null);
        this.f12707b = zaawVar;
        this.zac = arrayList;
    }

    @Override // com.google.android.gms.common.api.internal.zaav
    @WorkerThread
    public final void zaa() {
        zaaw zaawVar = this.f12707b;
        zaawVar.zaa.f12738g.f12725d = zaaw.g(zaawVar);
        ArrayList arrayList = this.zac;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Api.Client client = (Api.Client) arrayList.get(i2);
            zaaw zaawVar2 = this.f12707b;
            client.getRemoteService(zaawVar2.zao, zaawVar2.zaa.f12738g.f12725d);
        }
    }
}
