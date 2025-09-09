package org.mozilla.javascript;

import java.util.Arrays;

/* loaded from: classes5.dex */
class ResolvedOverload {
    final int index;
    final Class<?>[] types;

    ResolvedOverload(Object[] objArr, int i2) {
        this.index = i2;
        this.types = new Class[objArr.length];
        int length = objArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            Object objUnwrap = objArr[i3];
            objUnwrap = objUnwrap instanceof Wrapper ? ((Wrapper) objUnwrap).unwrap() : objUnwrap;
            this.types[i3] = objUnwrap == null ? null : objUnwrap.getClass();
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ResolvedOverload)) {
            return false;
        }
        ResolvedOverload resolvedOverload = (ResolvedOverload) obj;
        return Arrays.equals(this.types, resolvedOverload.types) && this.index == resolvedOverload.index;
    }

    public int hashCode() {
        return Arrays.hashCode(this.types);
    }

    boolean matches(Object[] objArr) {
        if (objArr.length != this.types.length) {
            return false;
        }
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Object objUnwrap = objArr[i2];
            if (objUnwrap instanceof Wrapper) {
                objUnwrap = ((Wrapper) objUnwrap).unwrap();
            }
            if (objUnwrap == null) {
                if (this.types[i2] != null) {
                    return false;
                }
            } else if (objUnwrap.getClass() != this.types[i2]) {
                return false;
            }
        }
        return true;
    }
}
