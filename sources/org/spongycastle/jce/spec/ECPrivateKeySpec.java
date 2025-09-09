package org.spongycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECPrivateKeySpec extends ECKeySpec {

    /* renamed from: d, reason: collision with root package name */
    public BigInteger f26791d;

    public ECPrivateKeySpec(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f26791d = bigInteger;
    }

    public BigInteger getD() {
        return this.f26791d;
    }
}
