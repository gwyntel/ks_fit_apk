package io.flutter.util;

import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    public static void checkState(boolean z2) {
        if (!z2) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean z2, @Nullable Object obj) {
        if (!z2) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }
}
