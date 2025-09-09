package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class CommonPattern {
    CommonPattern() {
    }

    public static CommonPattern compile(String str) {
        return Platform.a(str);
    }

    public static boolean isPcreLike() {
        return Platform.f();
    }

    public abstract int flags();

    public abstract CommonMatcher matcher(CharSequence charSequence);

    public abstract String pattern();

    public abstract String toString();
}
