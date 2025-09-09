package org.spongycastle.jce.spec;

import java.security.spec.KeySpec;

/* loaded from: classes5.dex */
public class ECKeySpec implements KeySpec {
    public ECParameterSpec spec;

    public ECKeySpec(ECParameterSpec eCParameterSpec) {
        this.spec = eCParameterSpec;
    }

    public ECParameterSpec getParams() {
        return this.spec;
    }
}
