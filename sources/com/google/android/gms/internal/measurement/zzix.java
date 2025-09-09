package com.google.android.gms.internal.measurement;

import com.huawei.hms.framework.common.ContainerUtils;

/* loaded from: classes3.dex */
final class zzix {
    private final Object zza;
    private final Object zzb;
    private final Object zzc;

    zzix(Object obj, Object obj2, Object obj3) {
        this.zza = obj;
        this.zzb = obj2;
        this.zzc = obj3;
    }

    final IllegalArgumentException a() {
        return new IllegalArgumentException("Multiple entries with same key: " + String.valueOf(this.zza) + ContainerUtils.KEY_VALUE_DELIMITER + String.valueOf(this.zzb) + " and " + String.valueOf(this.zza) + ContainerUtils.KEY_VALUE_DELIMITER + String.valueOf(this.zzc));
    }
}
