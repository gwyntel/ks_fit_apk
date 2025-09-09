package org.spongycastle.math.field;

import c.a.d.a;

/* loaded from: classes5.dex */
public class GF2Polynomial implements Polynomial {
    public final int[] exponents;

    public GF2Polynomial(int[] iArr) {
        this.exponents = a.a(iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GF2Polynomial) {
            return a.a(this.exponents, ((GF2Polynomial) obj).exponents);
        }
        return false;
    }

    @Override // org.spongycastle.math.field.Polynomial
    public int getDegree() {
        return this.exponents[r0.length - 1];
    }

    @Override // org.spongycastle.math.field.Polynomial
    public int[] getExponentsPresent() {
        return a.a(this.exponents);
    }

    public int hashCode() {
        return a.b(this.exponents);
    }
}
