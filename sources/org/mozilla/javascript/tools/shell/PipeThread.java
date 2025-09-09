package org.mozilla.javascript.tools.shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.mozilla.javascript.Context;

/* loaded from: classes5.dex */
class PipeThread extends Thread {
    private InputStream from;
    private boolean fromProcess;
    private OutputStream to;

    PipeThread(boolean z2, InputStream inputStream, OutputStream outputStream) {
        setDaemon(true);
        this.fromProcess = z2;
        this.from = inputStream;
        this.to = outputStream;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            Global.pipe(this.fromProcess, this.from, this.to);
        } catch (IOException e2) {
            throw Context.throwAsScriptRuntimeEx(e2);
        }
    }
}
