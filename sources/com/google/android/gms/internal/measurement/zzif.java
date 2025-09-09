package com.google.android.gms.internal.measurement;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
final class zzif extends AbstractSet {
    private final /* synthetic */ zzib zza;

    zzif(zzib zzibVar) {
        this.zza = zzibVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@CheckForNull Object obj) {
        Map mapZzf = this.zza.zzf();
        if (mapZzf != null) {
            return mapZzf.entrySet().contains(obj);
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            int iZza = this.zza.zza(entry.getKey());
            if (iZza != -1 && zzhl.zza(zzib.zzb(this.zza, iZza), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.zza.zzc();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@CheckForNull Object obj) {
        Map mapZzf = this.zza.zzf();
        if (mapZzf != null) {
            return mapZzf.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zza.zzh()) {
            return false;
        }
        int iZzi = this.zza.zzi();
        int iD = zzil.d(entry.getKey(), entry.getValue(), iZzi, this.zza.zzj(), this.zza.zzk(), this.zza.zzl(), this.zza.zzm());
        if (iD == -1) {
            return false;
        }
        this.zza.zzb(iD, iZzi);
        this.zza.zzg--;
        this.zza.zzg();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }
}
