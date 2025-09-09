package org.mozilla.javascript.tools.shell;

import java.io.OutputStream;
import javax.swing.SwingUtilities;

/* loaded from: classes5.dex */
class ConsoleWriter extends OutputStream {
    private StringBuffer buffer = new StringBuffer();
    private ConsoleTextArea textArea;

    public ConsoleWriter(ConsoleTextArea consoleTextArea) {
        this.textArea = consoleTextArea;
    }

    private void flushBuffer() {
        String string = this.buffer.toString();
        this.buffer.setLength(0);
        SwingUtilities.invokeLater(new ConsoleWrite(this.textArea, string));
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flush();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public synchronized void flush() {
        if (this.buffer.length() > 0) {
            flushBuffer();
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int i2) {
        this.buffer.append((char) i2);
        if (i2 == 10) {
            flushBuffer();
        }
    }

    public synchronized void write(char[] cArr, int i2, int i3) {
        while (i2 < i3) {
            try {
                this.buffer.append(cArr[i2]);
                if (cArr[i2] == '\n') {
                    flushBuffer();
                }
                i2++;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
