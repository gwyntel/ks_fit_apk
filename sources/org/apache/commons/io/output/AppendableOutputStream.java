package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Appendable;

/* loaded from: classes5.dex */
public class AppendableOutputStream<T extends Appendable> extends OutputStream {
    private final T appendable;

    public AppendableOutputStream(T t2) {
        this.appendable = t2;
    }

    public T getAppendable() {
        return this.appendable;
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        this.appendable.append((char) i2);
    }
}
