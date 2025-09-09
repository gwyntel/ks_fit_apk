package com.google.common.collect;

import com.google.common.collect.CollectCollectors;
import java.util.function.BinaryOperator;

/* loaded from: classes3.dex */
public final /* synthetic */ class h1 implements BinaryOperator {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        return ((CollectCollectors.EnumMapAccumulator) obj).a((CollectCollectors.EnumMapAccumulator) obj2);
    }
}
