package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
interface LongAddable {
    void add(long j2);

    void increment();

    long sum();
}
