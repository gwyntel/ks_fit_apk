package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class NullnessCasts {
    private NullnessCasts() {
    }

    static Object a(Object obj) {
        return obj;
    }

    static Object b() {
        return null;
    }
}
