package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public enum BoundType {
    OPEN(false),
    CLOSED(true);

    final boolean inclusive;

    BoundType(boolean z2) {
        this.inclusive = z2;
    }

    static BoundType forBoolean(boolean z2) {
        return z2 ? CLOSED : OPEN;
    }
}
