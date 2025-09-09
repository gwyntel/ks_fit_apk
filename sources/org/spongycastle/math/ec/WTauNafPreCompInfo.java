package org.spongycastle.math.ec;

import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class WTauNafPreCompInfo implements PreCompInfo {
    public ECPoint.AbstractF2m[] preComp = null;

    public ECPoint.AbstractF2m[] getPreComp() {
        return this.preComp;
    }

    public void setPreComp(ECPoint.AbstractF2m[] abstractF2mArr) {
        this.preComp = abstractF2mArr;
    }
}
