package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
final class zzin {
    static int a(int i2) {
        return (int) (Integer.rotateLeft((int) (i2 * (-862048943)), 15) * 461845907);
    }

    static int b(Object obj) {
        return a(obj == null ? 0 : obj.hashCode());
    }
}
