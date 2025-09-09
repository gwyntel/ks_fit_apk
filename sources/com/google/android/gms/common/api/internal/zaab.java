package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zaab implements PendingResult.StatusListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ BasePendingResult f12696a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zaad f12697b;

    zaab(zaad zaadVar, BasePendingResult basePendingResult) {
        this.f12697b = zaadVar;
        this.f12696a = basePendingResult;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        this.f12697b.zaa.remove(this.f12696a);
    }
}
