package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
final class zabt implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ConnectionResult f12747a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zabu f12748b;

    zabt(zabu zabuVar, ConnectionResult connectionResult) {
        this.f12748b = zabuVar;
        this.f12747a = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zabu zabuVar = this.f12748b;
        zabq zabqVar = (zabq) zabuVar.f12749a.zan.get(zabuVar.zac);
        if (zabqVar == null) {
            return;
        }
        if (!this.f12747a.isSuccess()) {
            zabqVar.zar(this.f12747a, null);
            return;
        }
        this.f12748b.zaf = true;
        if (this.f12748b.zab.requiresSignIn()) {
            this.f12748b.zah();
            return;
        }
        try {
            zabu zabuVar2 = this.f12748b;
            zabuVar2.zab.getRemoteService(null, zabuVar2.zab.getScopesForConnectionlessNonSignIn());
        } catch (SecurityException e2) {
            Log.e("GoogleApiManager", "Failed to get service from broker. ", e2);
            this.f12748b.zab.disconnect("Failed to get service from broker.");
            zabqVar.zar(new ConnectionResult(10), null);
        }
    }
}
