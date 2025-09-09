package org.spongycastle.util.io.pem;

import java.io.IOException;

/* loaded from: classes5.dex */
public class PemGenerationException extends IOException {
    public Throwable cause;

    public PemGenerationException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public PemGenerationException(String str) {
        super(str);
    }
}
