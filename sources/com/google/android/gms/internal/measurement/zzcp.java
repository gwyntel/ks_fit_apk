package com.google.android.gms.internal.measurement;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzcp extends Handler {

    @Nullable
    private static zzcr zza;
    private final Looper zzb;

    public zzcp() {
        this.zzb = Looper.getMainLooper();
    }

    public zzcp(Looper looper) {
        super(looper);
        this.zzb = Looper.getMainLooper();
    }
}
