package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* loaded from: classes3.dex */
final class zzgg extends ContentObserver {
    zzgg(zzge zzgeVar, Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        zzgl.zzc();
    }
}
