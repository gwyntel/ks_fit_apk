package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

@KeepForSdk
/* loaded from: classes3.dex */
public class DataBufferIterator<T> implements Iterator<T> {

    /* renamed from: a, reason: collision with root package name */
    protected final DataBuffer f12792a;

    /* renamed from: b, reason: collision with root package name */
    protected int f12793b = -1;

    public DataBufferIterator(@NonNull DataBuffer dataBuffer) {
        this.f12792a = (DataBuffer) Preconditions.checkNotNull(dataBuffer);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f12793b < this.f12792a.getCount() + (-1);
    }

    @Override // java.util.Iterator
    @NonNull
    public Object next() {
        if (hasNext()) {
            DataBuffer dataBuffer = this.f12792a;
            int i2 = this.f12793b + 1;
            this.f12793b = i2;
            return dataBuffer.get(i2);
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.f12793b);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
