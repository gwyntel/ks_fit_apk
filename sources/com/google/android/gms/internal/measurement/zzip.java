package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* loaded from: classes3.dex */
class zzip<E> extends zzis<E> {

    /* renamed from: a, reason: collision with root package name */
    Object[] f13215a;

    /* renamed from: b, reason: collision with root package name */
    int f13216b;

    /* renamed from: c, reason: collision with root package name */
    boolean f13217c;

    zzip(int i2) {
        zzic.a(4, "initialCapacity");
        this.f13215a = new Object[4];
        this.f13216b = 0;
    }

    public zzip<E> zza(E e2) {
        zzhn.zza(e2);
        zza(this.f13216b + 1);
        Object[] objArr = this.f13215a;
        int i2 = this.f13216b;
        this.f13216b = i2 + 1;
        objArr[i2] = e2;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzis
    public /* synthetic */ zzis zzb(Object obj) {
        return zza((zzip<E>) obj);
    }

    @Override // com.google.android.gms.internal.measurement.zzis
    public zzis<E> zza(E... eArr) {
        int length = eArr.length;
        zzjf.b(eArr, length);
        zza(this.f13216b + length);
        System.arraycopy(eArr, 0, this.f13215a, this.f13216b, length);
        this.f13216b += length;
        return this;
    }

    private final void zza(int i2) {
        Object[] objArr = this.f13215a;
        if (objArr.length < i2) {
            this.f13215a = Arrays.copyOf(objArr, zzis.a(objArr.length, i2));
            this.f13217c = false;
        } else if (this.f13217c) {
            this.f13215a = (Object[]) objArr.clone();
            this.f13217c = false;
        }
    }
}
