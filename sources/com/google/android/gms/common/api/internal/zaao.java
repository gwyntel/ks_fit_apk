package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
final class zaao extends zaav {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zaaw f12706b;
    private final Map zac;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaao(zaaw zaawVar, Map map) {
        super(zaawVar, null);
        this.f12706b = zaawVar;
        this.zac = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaav
    @WorkerThread
    @GuardedBy(KsProperty.Lock)
    public final void zaa() {
        com.google.android.gms.common.internal.zal zalVar = new com.google.android.gms.common.internal.zal(this.f12706b.zad);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zac.keySet()) {
            if (!client.requiresGooglePlayServices() || ((zaal) this.zac.get(client)).zac) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int i2 = 0;
        int iZab = -1;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            while (i2 < size) {
                iZab = zalVar.zab(this.f12706b.zac, (Api.Client) arrayList.get(i2));
                i2++;
                if (iZab != 0) {
                    break;
                }
            }
        } else {
            int size2 = arrayList2.size();
            while (i2 < size2) {
                iZab = zalVar.zab(this.f12706b.zac, (Api.Client) arrayList2.get(i2));
                i2++;
                if (iZab == 0) {
                    break;
                }
            }
        }
        if (iZab != 0) {
            ConnectionResult connectionResult = new ConnectionResult(iZab, null);
            zaaw zaawVar = this.f12706b;
            zaawVar.zaa.f(new zaam(this, zaawVar, connectionResult));
            return;
        }
        zaaw zaawVar2 = this.f12706b;
        if (zaawVar2.zam && zaawVar2.zak != null) {
            zaawVar2.zak.zab();
        }
        for (Api.Client client2 : this.zac.keySet()) {
            BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks = (BaseGmsClient.ConnectionProgressReportCallbacks) this.zac.get(client2);
            if (!client2.requiresGooglePlayServices() || zalVar.zab(this.f12706b.zac, client2) == 0) {
                client2.connect(connectionProgressReportCallbacks);
            } else {
                zaaw zaawVar3 = this.f12706b;
                zaawVar3.zaa.f(new zaan(this, zaawVar3, connectionProgressReportCallbacks));
            }
        }
    }
}
