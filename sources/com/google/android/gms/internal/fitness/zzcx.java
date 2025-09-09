package com.google.android.gms.internal.fitness;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.fitness.result.BleDevicesResult;

/* loaded from: classes3.dex */
final class zzcx extends zzev {
    private final BaseImplementation.ResultHolder zza;

    @Override // com.google.android.gms.internal.fitness.zzew
    public final void zzb(BleDevicesResult bleDevicesResult) {
        this.zza.setResult(bleDevicesResult);
    }
}
