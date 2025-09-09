package com.google.android.gms.internal.auth;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzgr implements Iterator {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzgv f13020a;
    private int zzb = -1;
    private boolean zzc;
    private Iterator zzd;

    /* synthetic */ zzgr(zzgv zzgvVar, zzgq zzgqVar) {
        this.f13020a = zzgvVar;
    }

    private final Iterator zza() {
        if (this.zzd == null) {
            this.zzd = this.f13020a.zzc.entrySet().iterator();
        }
        return this.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.zzb + 1 >= this.f13020a.zzb.size()) {
            return !this.f13020a.zzc.isEmpty() && zza().hasNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        this.zzc = true;
        int i2 = this.zzb + 1;
        this.zzb = i2;
        return i2 < this.f13020a.zzb.size() ? (Map.Entry) this.f13020a.zzb.get(this.zzb) : (Map.Entry) zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.zzc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzc = false;
        this.f13020a.zzn();
        if (this.zzb >= this.f13020a.zzb.size()) {
            zza().remove();
            return;
        }
        zzgv zzgvVar = this.f13020a;
        int i2 = this.zzb;
        this.zzb = i2 - 1;
        zzgvVar.zzl(i2);
    }
}
