package org.spongycastle.jce.interfaces;

import java.security.PublicKey;
import org.spongycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public interface ECPublicKey extends ECKey, PublicKey {
    ECPoint getQ();
}
