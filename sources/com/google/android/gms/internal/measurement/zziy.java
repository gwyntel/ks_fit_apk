package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* loaded from: classes3.dex */
public final class zziy<K, V> {

    /* renamed from: a, reason: collision with root package name */
    zzix f13218a;
    private Object[] zzb;
    private int zzc;
    private boolean zzd;

    public zziy() {
        this(4);
    }

    public final zziy<K, V> zza(K k2, V v2) {
        int i2 = (this.zzc + 1) << 1;
        Object[] objArr = this.zzb;
        if (i2 > objArr.length) {
            this.zzb = Arrays.copyOf(objArr, zzis.a(objArr.length, i2));
            this.zzd = false;
        }
        zzic.b(k2, v2);
        Object[] objArr2 = this.zzb;
        int i3 = this.zzc;
        objArr2[i3 * 2] = k2;
        objArr2[(i3 * 2) + 1] = v2;
        this.zzc = i3 + 1;
        return this;
    }

    zziy(int i2) {
        this.zzb = new Object[i2 * 2];
        this.zzc = 0;
        this.zzd = false;
    }

    public final zziv<K, V> zza() {
        zzix zzixVar = this.f13218a;
        if (zzixVar == null) {
            int i2 = this.zzc;
            Object[] objArr = this.zzb;
            this.zzd = true;
            zzjh zzjhVarZza = zzjh.zza(i2, objArr, this);
            zzix zzixVar2 = this.f13218a;
            if (zzixVar2 == null) {
                return zzjhVarZza;
            }
            throw zzixVar2.a();
        }
        throw zzixVar.a();
    }
}
