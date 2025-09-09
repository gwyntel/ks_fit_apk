package org.spongycastle.asn1.x9;

/* loaded from: classes5.dex */
public abstract class X9ECParametersHolder {
    public X9ECParameters params;

    public abstract X9ECParameters createParameters();

    public synchronized X9ECParameters getParameters() {
        try {
            if (this.params == null) {
                this.params = createParameters();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.params;
    }
}
