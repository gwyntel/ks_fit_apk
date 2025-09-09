package com.google.common.util.concurrent;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class i0 {
    public static /* synthetic */ boolean a(AtomicReferenceArray atomicReferenceArray, int i2, Object obj, Object obj2) {
        while (!atomicReferenceArray.compareAndSet(i2, obj, obj2)) {
            if (atomicReferenceArray.get(i2) != obj) {
                return false;
            }
        }
        return true;
    }
}
