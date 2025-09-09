package com.google.android.gms.internal.auth;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zzdw extends zzdy {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzef f13012a;
    private int zzb = 0;
    private final int zzc;

    zzdw(zzef zzefVar) {
        this.f13012a = zzefVar;
        this.zzc = zzefVar.zzd();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.gms.internal.auth.zzea
    public final byte zza() {
        int i2 = this.zzb;
        if (i2 >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i2 + 1;
        return this.f13012a.zzb(i2);
    }
}
