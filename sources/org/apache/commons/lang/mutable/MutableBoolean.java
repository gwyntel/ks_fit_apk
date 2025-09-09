package org.apache.commons.lang.mutable;

import java.io.Serializable;
import org.apache.commons.lang.BooleanUtils;

/* loaded from: classes5.dex */
public class MutableBoolean implements Mutable, Serializable, Comparable {
    private static final long serialVersionUID = -4830728138360036487L;
    private boolean value;

    public MutableBoolean() {
    }

    public boolean booleanValue() {
        return this.value;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        boolean z2 = ((MutableBoolean) obj).value;
        boolean z3 = this.value;
        if (z3 == z2) {
            return 0;
        }
        return z3 ? 1 : -1;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableBoolean) && this.value == ((MutableBoolean) obj).booleanValue();
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return BooleanUtils.toBooleanObject(this.value);
    }

    public int hashCode() {
        return (this.value ? Boolean.TRUE : Boolean.FALSE).hashCode();
    }

    public boolean isFalse() {
        return !this.value;
    }

    public boolean isTrue() {
        return this.value;
    }

    public void setValue(boolean z2) {
        this.value = z2;
    }

    public Boolean toBoolean() {
        return BooleanUtils.toBooleanObject(this.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableBoolean(boolean z2) {
        this.value = z2;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        setValue(((Boolean) obj).booleanValue());
    }

    public MutableBoolean(Boolean bool) {
        this.value = bool.booleanValue();
    }
}
