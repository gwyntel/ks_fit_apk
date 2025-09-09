package com.google.android.gms.internal.measurement;

import java.util.Comparator;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public class zzjb<K, V> extends zzja<K, V> implements zzjg<K, V> {
    private final transient zzjc<V> emptySet;

    zzjb(zziv<K, zzjc<V>> zzivVar, int i2, @CheckForNull Comparator<? super V> comparator) {
        super(zzivVar, i2);
        this.emptySet = zzjo.zza;
    }
}
