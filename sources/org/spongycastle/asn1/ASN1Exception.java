package org.spongycastle.asn1;

import java.io.IOException;

/* loaded from: classes5.dex */
public class ASN1Exception extends IOException {
    public Throwable cause;

    public ASN1Exception(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public ASN1Exception(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
