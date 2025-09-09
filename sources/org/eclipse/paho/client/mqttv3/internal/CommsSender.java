package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class CommsSender implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsSender";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CommsSender.class.getName());
    private ClientComms clientComms;
    private ClientState clientState;
    private MqttOutputStream out;
    private Future senderFuture;
    private String threadName;
    private CommsTokenStore tokenStore;
    private boolean running = false;
    private Object lifecycle = new Object();
    private Thread sendThread = null;
    private final Semaphore runningSemaphore = new Semaphore(1);

    public CommsSender(ClientComms clientComms, ClientState clientState, CommsTokenStore commsTokenStore, OutputStream outputStream) {
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.out = new MqttOutputStream(clientState, outputStream);
        this.clientComms = clientComms;
        this.clientState = clientState;
        this.tokenStore = commsTokenStore;
        log.setResourceName(clientComms.getClient().getClientId());
    }

    private void handleRunException(MqttWireMessage mqttWireMessage, Exception exc) {
        log.fine(CLASS_NAME, "handleRunException", "804", null, exc);
        MqttException mqttException = !(exc instanceof MqttException) ? new MqttException(32109, exc) : (MqttException) exc;
        this.running = false;
        this.clientComms.shutdownConnection(null, mqttException);
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        Thread threadCurrentThread = Thread.currentThread();
        this.sendThread = threadCurrentThread;
        threadCurrentThread.setName(this.threadName);
        try {
            this.runningSemaphore.acquire();
            MqttWireMessage mqttWireMessageF = null;
            while (this.running && this.out != null) {
                try {
                    try {
                        mqttWireMessageF = this.clientState.f();
                        if (mqttWireMessageF != null) {
                            log.fine(CLASS_NAME, "run", "802", new Object[]{mqttWireMessageF.getKey(), mqttWireMessageF});
                            if (mqttWireMessageF instanceof MqttAck) {
                                this.out.write(mqttWireMessageF);
                                this.out.flush();
                            } else {
                                MqttToken token = this.tokenStore.getToken(mqttWireMessageF);
                                if (token != null) {
                                    synchronized (token) {
                                        this.out.write(mqttWireMessageF);
                                        try {
                                            this.out.flush();
                                        } catch (IOException e2) {
                                            if (!(mqttWireMessageF instanceof MqttDisconnect)) {
                                                throw e2;
                                            }
                                        }
                                        this.clientState.m(mqttWireMessageF);
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } else {
                            log.fine(CLASS_NAME, "run", "803");
                            this.running = false;
                        }
                    } catch (MqttException e3) {
                        handleRunException(mqttWireMessageF, e3);
                    } catch (Exception e4) {
                        handleRunException(mqttWireMessageF, e4);
                    }
                } catch (Throwable th) {
                    this.running = false;
                    this.runningSemaphore.release();
                    throw th;
                }
            }
            this.running = false;
            this.runningSemaphore.release();
            log.fine(CLASS_NAME, "run", "805");
        } catch (InterruptedException unused) {
            this.running = false;
        }
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        synchronized (this.lifecycle) {
            try {
                if (!this.running) {
                    this.running = true;
                    this.senderFuture = executorService.submit(this);
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
                Future future = this.senderFuture;
                if (future != null) {
                    future.cancel(true);
                }
                log.fine(CLASS_NAME, "stop", "800");
                if (this.running) {
                    this.running = false;
                    if (!Thread.currentThread().equals(this.sendThread)) {
                        while (this.running) {
                            try {
                                this.clientState.notifyQueueLock();
                                this.runningSemaphore.tryAcquire(100L, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException unused) {
                                semaphore = this.runningSemaphore;
                            } catch (Throwable th) {
                                this.runningSemaphore.release();
                                throw th;
                            }
                        }
                        semaphore = this.runningSemaphore;
                        semaphore.release();
                    }
                }
                this.sendThread = null;
                log.fine(CLASS_NAME, "stop", "801");
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
