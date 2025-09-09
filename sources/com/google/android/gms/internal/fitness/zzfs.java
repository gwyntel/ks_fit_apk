package com.google.android.gms.internal.fitness;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzfs extends zzfn {
    final transient Object zza;

    zzfs(Object obj) {
        obj.getClass();
        this.zza = obj;
    }

    @Override // com.google.android.gms.internal.fitness.zzfj, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.equals(obj);
    }

    @Override // com.google.android.gms.internal.fitness.zzfn, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // com.google.android.gms.internal.fitness.zzfn, com.google.android.gms.internal.fitness.zzfj, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return new zzfo(this.zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return '[' + this.zza.toString() + ']';
    }

    @Override // com.google.android.gms.internal.fitness.zzfj
    final int zza(Object[] objArr, int i2) {
        objArr[0] = this.zza;
        return 1;
    }

    @Override // com.google.android.gms.internal.fitness.zzfn, com.google.android.gms.internal.fitness.zzfj
    /* renamed from: zzd */
    public final zzft iterator() {
        return new zzfo(this.zza);
    }
}
