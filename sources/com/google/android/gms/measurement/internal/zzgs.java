package com.google.android.gms.measurement.internal;

import androidx.collection.LruCache;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzgs extends LruCache<String, com.google.android.gms.internal.measurement.zzb> {
    private final /* synthetic */ zzgp zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzgs(zzgp zzgpVar, int i2) {
        super(20);
        this.zza = zzgpVar;
    }

    @Override // androidx.collection.LruCache
    protected final /* synthetic */ Object a(Object obj) {
        String str = (String) obj;
        Preconditions.checkNotEmpty(str);
        return zzgp.a(this.zza, str);
    }
}
