package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public interface AsyncCallable<V> {
    ListenableFuture<V> call() throws Exception;
}
