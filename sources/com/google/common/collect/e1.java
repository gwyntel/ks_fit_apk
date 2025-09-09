package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class e1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Preconditions.checkNotNull(obj);
    }
}
