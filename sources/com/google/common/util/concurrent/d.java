package com.google.common.util.concurrent;

import sun.misc.Unsafe;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class d {
    public static /* synthetic */ boolean a(Unsafe unsafe, Object obj, long j2, Object obj2, Object obj3) {
        while (!unsafe.compareAndSwapObject(obj, j2, obj2, obj3)) {
            if (unsafe.getObject(obj, j2) != obj2) {
                return false;
            }
        }
        return true;
    }
}
