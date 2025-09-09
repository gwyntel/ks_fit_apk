package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;

@ElementTypesAreNonnullByDefault
@GwtCompatible
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
