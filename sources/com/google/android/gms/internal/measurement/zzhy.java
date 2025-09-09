package com.google.android.gms.internal.measurement;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
abstract class zzhy<E> extends zzjs<E> {
    private final int zza;
    private int zzb;

    protected zzhy(int i2, int i3) {
        zzhn.zzb(i3, i2);
        this.zza = i2;
        this.zzb = i3;
    }

    protected abstract Object a(int i2);

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.zzb < this.zza;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.zzb > 0;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i2 = this.zzb;
        this.zzb = i2 + 1;
        return (E) a(i2);
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.zzb;
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i2 = this.zzb - 1;
        this.zzb = i2;
        return (E) a(i2);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.zzb - 1;
    }
}
