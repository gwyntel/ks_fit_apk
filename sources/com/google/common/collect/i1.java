package com.google.common.collect;

import com.google.common.collect.CollectCollectors;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class i1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((CollectCollectors.EnumMapAccumulator) obj).c();
    }
}
