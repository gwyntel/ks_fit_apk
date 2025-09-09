package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.NoSuchElementException;

@KeepForSdk
/* loaded from: classes3.dex */
public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
    private Object zac;

    public SingleRefDataBufferIterator(@NonNull DataBuffer dataBuffer) {
        super(dataBuffer);
    }

    @Override // com.google.android.gms.common.data.DataBufferIterator, java.util.Iterator
    @NonNull
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Cannot advance the iterator beyond " + this.f12793b);
        }
        int i2 = this.f12793b + 1;
        this.f12793b = i2;
        if (i2 == 0) {
            Object objCheckNotNull = Preconditions.checkNotNull(this.f12792a.get(0));
            this.zac = objCheckNotNull;
            if (!(objCheckNotNull instanceof DataBufferRef)) {
                throw new IllegalStateException("DataBuffer reference of type " + String.valueOf(objCheckNotNull.getClass()) + " is not movable");
            }
        } else {
            ((DataBufferRef) Preconditions.checkNotNull(this.zac)).a(this.f12793b);
        }
        return this.zac;
    }
}
