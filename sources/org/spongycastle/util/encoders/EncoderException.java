package org.spongycastle.util.encoders;

/* loaded from: classes5.dex */
public class EncoderException extends IllegalStateException {
    public Throwable cause;

    public EncoderException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
