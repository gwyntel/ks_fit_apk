package com.google.common.reflect;

import java.lang.reflect.TypeVariable;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class TypeParameter<T> extends TypeCapture<T> {

    /* renamed from: a, reason: collision with root package name */
    final TypeVariable f14727a;

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof TypeParameter) {
            return this.f14727a.equals(((TypeParameter) obj).f14727a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f14727a.hashCode();
    }

    public String toString() {
        return this.f14727a.toString();
    }
}
