package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public final class zzif {
    public static <T> T zza(@NonNull Bundle bundle, String str, Class<T> cls, T t2) {
        T t3 = (T) bundle.get(str);
        if (t3 == null) {
            return t2;
        }
        if (cls.isAssignableFrom(t3.getClass())) {
            return t3;
        }
        throw new IllegalStateException(String.format("Invalid conditional user property field type. '%s' expected [%s] but was [%s]", str, cls.getCanonicalName(), t3.getClass().getCanonicalName()));
    }

    public static void zza(@NonNull Bundle bundle, @NonNull Object obj) {
        if (obj instanceof Double) {
            bundle.putDouble("value", ((Double) obj).doubleValue());
        } else if (obj instanceof Long) {
            bundle.putLong("value", ((Long) obj).longValue());
        } else {
            bundle.putString("value", obj.toString());
        }
    }
}
