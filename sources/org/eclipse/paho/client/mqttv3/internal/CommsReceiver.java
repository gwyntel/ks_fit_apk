package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class CommsReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsReceiver";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CommsReceiver.class.getName());
    private ClientComms clientComms;
    private ClientState clientState;
    private MqttInputStream in;
    private Future receiverFuture;
    private volatile boolean receiving;
    private String threadName;
    private CommsTokenStore tokenStore;
    private volatile boolean running = false;
    private Object lifecycle = new Object();
    private Thread recThread = null;
    private final Semaphore runningSemaphore = new Semaphore(1);

    public CommsReceiver(ClientComms clientComms, ClientState clientState, CommsTokenStore commsTokenStore, InputStream inputStream) {
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.in = new MqttInputStream(clientState, inputStream);
        this.clientComms = clientComms;
        this.clientState = clientState;
        this.tokenStore = commsTokenStore;
        log.setResourceName(clientComms.getClient().getClientId());
    }

    public boolean isReceiving() {
        return this.receiving;
    }

    public boolean isRunning() {
        return this.running;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        Thread threadCurrentThread = Thread.currentThread();
        this.recThread = threadCurrentThread;
        threadCurrentThread.setName(this.threadName);
        try {
            this.runningSemaphore.acquire();
            MqttToken token = null;
            while (this.running && this.in != null) {
                try {
                    try {
                        try {
                            try {
                                Logger logger = log;
                                String str = CLASS_NAME;
                                logger.fine(str, "run", "852");
                                this.receiving = this.in.available() > 0;
                                MqttWireMessage mqttWireMessage = this.in.readMqttWireMessage();
                                this.receiving = false;
                                if (mqttWireMessage instanceof MqttAck) {
                                    token = this.tokenStore.getToken(mqttWireMessage);
                                    if (token != null) {
                                        synchronized (token) {
                                            this.clientState.j((MqttAck) mqttWireMessage);
                                        }
                                    } else {
                                        if (!(mqttWireMessage instanceof MqttPubRec) && !(mqttWireMessage instanceof MqttPubComp) && !(mqttWireMessage instanceof MqttPubAck)) {
                                            throw new MqttException(6);
                                        }
                                        logger.fine(str, "run", "857");
                                    }
                                } else if (mqttWireMessage != null) {
                                    this.clientState.k(mqttWireMessage);
                                }
                            } catch (MqttException e2) {
                                log.fine(CLASS_NAME, "run", "856", null, e2);
                                this.running = false;
                                this.clientComms.shutdownConnection(token, e2);
                            }
                        } catch (IOException e3) {
                            log.fine(CLASS_NAME, "run", "853");
                            this.running = false;
                            if (!this.clientComms.isDisconnecting()) {
                                this.clientComms.shutdownConnection(token, new MqttException(32109, e3));
                            }
                        }
                        this.receiving = false;
                    } catch (Throwable th) {
                        this.receiving = false;
                        throw th;
                    }
                } catch (Throwable th2) {
                    this.runningSemaphore.release();
                    throw th2;
                }
            }
            this.runningSemaphore.release();
            log.fine(CLASS_NAME, "run", "854");
        } catch (InterruptedException unused) {
            this.running = false;
        }
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            try {
                if (!this.running) {
                    this.running = true;
                    this.receiverFuture = executorService.submit(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void stop() {
        Semaphore semaphore;
        synchronized (this.lifecycle) {
            try {
                Future future = this.receiverFuture;
                if (future != null) {
                    future.cancel(true);
                }
                log.fine(CLASS_NAME, "stop", "850");
                if (this.running) {
                    this.running = false;
                    this.receiving = false;
                    if (!Thread.currentThread().equals(this.recThread)) {
                        try {
                            this.runningSemaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS);
                            semaphore = this.runningSemaphore;
                        } catch (InterruptedException unused) {
                            semaphore = this.runningSemaphore;
                        } catch (Throwable th) {
                            this.runningSemaphore.release();
                            throw th;
                        }
                        semaphore.release();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.recThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }
}
