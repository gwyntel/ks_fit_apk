package com.google.android.gms.internal.common;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;

/* loaded from: classes3.dex */
class zzaa extends zzab {

    /* renamed from: a, reason: collision with root package name */
    Object[] f13036a = new Object[4];

    /* renamed from: b, reason: collision with root package name */
    int f13037b = 0;

    /* renamed from: c, reason: collision with root package name */
    boolean f13038c;

    zzaa(int i2) {
    }

    @CanIgnoreReturnValue
    public final zzaa zza(Object obj) {
        obj.getClass();
        int i2 = this.f13037b;
        int i3 = i2 + 1;
        Object[] objArr = this.f13036a;
        int length = objArr.length;
        if (length < i3) {
            int i4 = length + (length >> 1) + 1;
            if (i4 < i3) {
                int iHighestOneBit = Integer.highestOneBit(i2);
                i4 = iHighestOneBit + iHighestOneBit;
            }
            if (i4 < 0) {
                i4 = Integer.MAX_VALUE;
            }
            this.f13036a = Arrays.copyOf(objArr, i4);
            this.f13038c = false;
        } else if (this.f13038c) {
            this.f13036a = (Object[]) objArr.clone();
            this.f13038c = false;
        }
        Object[] objArr2 = this.f13036a;
        int i5 = this.f13037b;
        this.f13037b = i5 + 1;
        objArr2[i5] = obj;
        return this;
    }
}
