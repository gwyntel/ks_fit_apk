package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public abstract class zzis<E> {
    zzis() {
    }

    static int a(int i2, int i3) {
        if (i3 < 0) {
            throw new AssertionError("cannot store more than MAX_VALUE elements");
        }
        int iHighestOneBit = i2 + (i2 >> 1) + 1;
        if (iHighestOneBit < i3) {
            iHighestOneBit = Integer.highestOneBit(i3 - 1) << 1;
        }
        if (iHighestOneBit < 0) {
            return Integer.MAX_VALUE;
        }
        return iHighestOneBit;
    }

    public zzis<E> zza(E... eArr) {
        for (E e2 : eArr) {
            zzb(e2);
        }
        return this;
    }

    public abstract zzis<E> zzb(E e2);
}
