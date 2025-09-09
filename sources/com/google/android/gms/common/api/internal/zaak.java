package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zaak implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaw f12702a;

    zaak(zaaw zaawVar) {
        this.f12702a = zaawVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zaaw zaawVar = this.f12702a;
        zaawVar.zad.cancelAvailabilityErrorNotifications(zaawVar.zac);
    }
}
