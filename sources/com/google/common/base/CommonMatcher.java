package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class CommonMatcher {
    CommonMatcher() {
    }

    public abstract int end();

    public abstract boolean find();

    public abstract boolean find(int i2);

    public abstract boolean matches();

    public abstract String replaceAll(String str);

    public abstract int start();
}
