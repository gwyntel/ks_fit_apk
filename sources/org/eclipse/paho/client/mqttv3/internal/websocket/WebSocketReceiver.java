package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class WebSocketReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketReceiver";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, WebSocketReceiver.class.getName());
    private InputStream input;
    private PipedOutputStream pipedOutputStream;
    private volatile boolean receiving;
    private boolean running = false;
    private boolean stopping = false;
    private Object lifecycle = new Object();
    private Thread receiverThread = null;

    public WebSocketReceiver(InputStream inputStream, PipedInputStream pipedInputStream) throws IOException {
        this.input = inputStream;
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        this.pipedOutputStream = pipedOutputStream;
        pipedInputStream.connect(pipedOutputStream);
    }

    private void closeOutputStream() throws IOException {
        try {
            this.pipedOutputStream.close();
        } catch (IOException unused) {
        }
    }

    public boolean isReceiving() {
        return this.receiving;
    }

    public boolean isRunning() {
        return this.running;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException, IOException {
        while (this.running && this.input != null) {
            try {
                log.fine(CLASS_NAME, "run", "852");
                this.receiving = this.input.available() > 0;
                WebSocketFrame webSocketFrame = new WebSocketFrame(this.input);
                if (!webSocketFrame.isCloseFlag()) {
                    for (int i2 = 0; i2 < webSocketFrame.getPayload().length; i2++) {
                        this.pipedOutputStream.write(webSocketFrame.getPayload()[i2]);
                    }
                    this.pipedOutputStream.flush();
                } else if (!this.stopping) {
                    throw new IOException("Server sent a WebSocket Frame with the Stop OpCode");
                }
                this.receiving = false;
            } catch (IOException unused) {
                stop();
            }
        }
    }

    public void start(String str) {
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            try {
                if (!this.running) {
                    this.running = true;
                    Thread thread = new Thread(this, str);
                    this.receiverThread = thread;
                    thread.start();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void stop() throws InterruptedException {
        boolean z2 = true;
        this.stopping = true;
        synchronized (this.lifecycle) {
            try {
                log.fine(CLASS_NAME, "stop", "850");
                if (this.running) {
                    this.running = false;
                    this.receiving = false;
                    closeOutputStream();
                } else {
                    z2 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2 && !Thread.currentThread().equals(this.receiverThread)) {
            try {
                this.receiverThread.join();
            } catch (InterruptedException unused) {
            }
        }
        this.receiverThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }
}
