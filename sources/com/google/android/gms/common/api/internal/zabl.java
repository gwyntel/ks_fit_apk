package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector;

/* loaded from: classes3.dex */
final class zabl implements BackgroundDetector.BackgroundStateChangeListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ GoogleApiManager f12740a;

    zabl(GoogleApiManager googleApiManager) {
        this.f12740a = googleApiManager;
    }

    @Override // com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
    public final void onBackgroundStateChanged(boolean z2) {
        GoogleApiManager googleApiManager = this.f12740a;
        googleApiManager.zar.sendMessage(googleApiManager.zar.obtainMessage(1, Boolean.valueOf(z2)));
    }
}
