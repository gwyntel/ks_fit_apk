package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class CountingInputStream extends InputStream {
    private int counter = 0;
    private InputStream in;

    public CountingInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public int getCounter() {
        return this.counter;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i2 = this.in.read();
        if (i2 != -1) {
            this.counter++;
        }
        return i2;
    }

    public void resetCounter() {
        this.counter = 0;
    }
}
