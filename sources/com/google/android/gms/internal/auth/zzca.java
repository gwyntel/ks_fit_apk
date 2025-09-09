package com.google.android.gms.internal.auth;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzca extends ContentObserver {
    zzca(Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        zzcb.zzl.set(true);
    }
}
