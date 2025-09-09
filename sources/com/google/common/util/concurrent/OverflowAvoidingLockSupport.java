package com.google.common.util.concurrent;

import com.google.common.annotations.J2ktIncompatible;
import java.util.concurrent.locks.LockSupport;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class OverflowAvoidingLockSupport {
    private OverflowAvoidingLockSupport() {
    }

    static void a(Object obj, long j2) {
        LockSupport.parkNanos(obj, Math.min(j2, 2147483647999999999L));
    }
}
