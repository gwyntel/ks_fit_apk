package org.spongycastle.crypto.ec;

import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ECPair {

    /* renamed from: x, reason: collision with root package name */
    public final ECPoint f26785x;

    /* renamed from: y, reason: collision with root package name */
    public final ECPoint f26786y;

    public ECPair(ECPoint eCPoint, ECPoint eCPoint2) {
        this.f26785x = eCPoint;
        this.f26786y = eCPoint2;
    }

    public boolean equals(ECPair eCPair) {
        return eCPair.getX().equals(getX()) && eCPair.getY().equals(getY());
    }

    public ECPoint getX() {
        return this.f26785x;
    }

    public ECPoint getY() {
        return this.f26786y;
    }

    public int hashCode() {
        return this.f26785x.hashCode() + (this.f26786y.hashCode() * 37);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ECPair) {
            return equals((ECPair) obj);
        }
        return false;
    }
}
