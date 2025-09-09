package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class CommsCallback implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsCallback";
    private static final int INBOUND_QUEUE_SIZE = 10;
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CommsCallback.class.getName());
    private Future callbackFuture;
    private Thread callbackThread;
    private ClientComms clientComms;
    private ClientState clientState;
    private MqttCallback mqttCallback;
    private MqttCallbackExtended reconnectInternalCallback;
    private String threadName;
    public boolean running = false;
    private boolean quiescing = false;
    private Object lifecycle = new Object();
    private Object workAvailable = new Object();
    private Object spaceAvailable = new Object();
    private boolean manualAcks = false;
    private final Semaphore runningSemaphore = new Semaphore(1);
    private Vector messageQueue = new Vector(10);
    private Vector completeQueue = new Vector(10);
    private Hashtable callbacks = new Hashtable();

    CommsCallback(ClientComms clientComms) {
        this.clientComms = clientComms;
        log.setResourceName(clientComms.getClient().getClientId());
    }

    private void handleActionComplete(MqttToken mqttToken) throws MqttException {
        synchronized (mqttToken) {
            try {
                log.fine(CLASS_NAME, "handleActionComplete", "705", new Object[]{mqttToken.internalTok.getKey()});
                if (mqttToken.isComplete()) {
                    this.clientState.i(mqttToken);
                }
                mqttToken.internalTok.d();
                if (!mqttToken.internalTok.isNotified()) {
                    if (this.mqttCallback != null && (mqttToken instanceof MqttDeliveryToken) && mqttToken.isComplete()) {
                        this.mqttCallback.deliveryComplete((MqttDeliveryToken) mqttToken);
                    }
                    fireActionEvent(mqttToken);
                }
                if (mqttToken.isComplete() && ((mqttToken instanceof MqttDeliveryToken) || (mqttToken.getActionCallback() instanceof IMqttActionListener))) {
                    mqttToken.internalTok.setNotified(true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void handleMessage(MqttPublish mqttPublish) throws Exception {
        String topicName = mqttPublish.getTopicName();
        log.fine(CLASS_NAME, "handleMessage", "713", new Object[]{new Integer(mqttPublish.getMessageId()), topicName});
        a(topicName, mqttPublish.getMessageId(), mqttPublish.getMessage());
        if (this.manualAcks) {
            return;
        }
        if (mqttPublish.getMessage().getQos() == 1) {
            this.clientComms.o(new MqttPubAck(mqttPublish), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (mqttPublish.getMessage().getQos() == 2) {
            this.clientComms.n(mqttPublish);
            MqttPubComp mqttPubComp = new MqttPubComp(mqttPublish);
            ClientComms clientComms = this.clientComms;
            clientComms.o(mqttPubComp, new MqttToken(clientComms.getClient().getClientId()));
        }
    }

    protected boolean a(String str, int i2, MqttMessage mqttMessage) throws Exception {
        Enumeration enumerationKeys = this.callbacks.keys();
        boolean z2 = false;
        while (enumerationKeys.hasMoreElements()) {
            String str2 = (String) enumerationKeys.nextElement();
            if (MqttTopic.isMatched(str2, str)) {
                mqttMessage.setId(i2);
                ((IMqttMessageListener) this.callbacks.get(str2)).messageArrived(str, mqttMessage);
                z2 = true;
            }
        }
        if (this.mqttCallback == null || z2) {
            return z2;
        }
        mqttMessage.setId(i2);
        this.mqttCallback.messageArrived(str, mqttMessage);
        return true;
    }

    public void asyncOperationComplete(MqttToken mqttToken) {
        if (this.running) {
            this.completeQueue.addElement(mqttToken);
            synchronized (this.workAvailable) {
                log.fine(CLASS_NAME, "asyncOperationComplete", "715", new Object[]{mqttToken.internalTok.getKey()});
                this.workAvailable.notifyAll();
            }
            return;
        }
        try {
            handleActionComplete(mqttToken);
        } catch (Throwable th) {
            log.fine(CLASS_NAME, "asyncOperationComplete", "719", null, th);
            this.clientComms.shutdownConnection(null, new MqttException(th));
        }
    }

    protected Thread b() {
        return this.callbackThread;
    }

    public void connectionLost(MqttException mqttException) {
        try {
            if (this.mqttCallback != null && mqttException != null) {
                log.fine(CLASS_NAME, "connectionLost", "708", new Object[]{mqttException});
                this.mqttCallback.connectionLost(mqttException);
            }
            MqttCallbackExtended mqttCallbackExtended = this.reconnectInternalCallback;
            if (mqttCallbackExtended == null || mqttException == null) {
                return;
            }
            mqttCallbackExtended.connectionLost(mqttException);
        } catch (Throwable th) {
            log.fine(CLASS_NAME, "connectionLost", "720", new Object[]{th});
        }
    }

    public void fireActionEvent(MqttToken mqttToken) {
        IMqttActionListener actionCallback;
        if (mqttToken == null || (actionCallback = mqttToken.getActionCallback()) == null) {
            return;
        }
        if (mqttToken.getException() == null) {
            log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
            actionCallback.onSuccess(mqttToken);
        } else {
            log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
            actionCallback.onFailure(mqttToken, mqttToken.getException());
        }
    }

    public boolean isQuiesced() {
        return this.quiescing && this.completeQueue.size() == 0 && this.messageQueue.size() == 0;
    }

    public void messageArrived(MqttPublish mqttPublish) {
        if (this.mqttCallback != null || this.callbacks.size() > 0) {
            synchronized (this.spaceAvailable) {
                while (this.running && !this.quiescing && this.messageQueue.size() >= 10) {
                    try {
                        log.fine(CLASS_NAME, "messageArrived", "709");
                        this.spaceAvailable.wait(200L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (this.quiescing) {
                return;
            }
            this.messageQueue.addElement(mqttPublish);
            synchronized (this.workAvailable) {
                log.fine(CLASS_NAME, "messageArrived", "710");
                this.workAvailable.notifyAll();
            }
        }
    }

    public void messageArrivedComplete(int i2, int i3) throws MqttException {
        if (i3 == 1) {
            this.clientComms.o(new MqttPubAck(i2), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (i3 == 2) {
            this.clientComms.m(i2);
            MqttPubComp mqttPubComp = new MqttPubComp(i2);
            ClientComms clientComms = this.clientComms;
            clientComms.o(mqttPubComp, new MqttToken(clientComms.getClient().getClientId()));
        }
    }

    public void quiesce() {
        this.quiescing = true;
        synchronized (this.spaceAvailable) {
            log.fine(CLASS_NAME, "quiesce", "711");
            this.spaceAvailable.notifyAll();
        }
    }

    public void removeMessageListener(String str) {
        this.callbacks.remove(str);
    }

    public void removeMessageListeners() {
        this.callbacks.clear();
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        MqttToken mqttToken;
        MqttPublish mqttPublish;
        Thread threadCurrentThread = Thread.currentThread();
        this.callbackThread = threadCurrentThread;
        threadCurrentThread.setName(this.threadName);
        try {
            this.runningSemaphore.acquire();
            while (this.running) {
                try {
                    try {
                        synchronized (this.workAvailable) {
                            try {
                                if (this.running && this.messageQueue.isEmpty() && this.completeQueue.isEmpty()) {
                                    log.fine(CLASS_NAME, "run", "704");
                                    this.workAvailable.wait();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (InterruptedException unused) {
                    }
                    if (this.running) {
                        synchronized (this.completeQueue) {
                            try {
                                if (this.completeQueue.isEmpty()) {
                                    mqttToken = null;
                                } else {
                                    mqttToken = (MqttToken) this.completeQueue.elementAt(0);
                                    this.completeQueue.removeElementAt(0);
                                }
                            } finally {
                            }
                        }
                        if (mqttToken != null) {
                            handleActionComplete(mqttToken);
                        }
                        synchronized (this.messageQueue) {
                            try {
                                if (this.messageQueue.isEmpty()) {
                                    mqttPublish = null;
                                } else {
                                    mqttPublish = (MqttPublish) this.messageQueue.elementAt(0);
                                    this.messageQueue.removeElementAt(0);
                                }
                            } finally {
                            }
                        }
                        if (mqttPublish != null) {
                            handleMessage(mqttPublish);
                        }
                    }
                    if (this.quiescing) {
                        this.clientState.a();
                    }
                    this.runningSemaphore.release();
                    synchronized (this.spaceAvailable) {
                        log.fine(CLASS_NAME, "run", "706");
                        this.spaceAvailable.notifyAll();
                    }
                } catch (Throwable th2) {
                    try {
                        Logger logger = log;
                        String str = CLASS_NAME;
                        logger.fine(str, "run", "714", null, th2);
                        this.running = false;
                        this.clientComms.shutdownConnection(null, new MqttException(th2));
                        this.runningSemaphore.release();
                        synchronized (this.spaceAvailable) {
                            logger.fine(str, "run", "706");
                            this.spaceAvailable.notifyAll();
                        }
                    } catch (Throwable th3) {
                        this.runningSemaphore.release();
                        synchronized (this.spaceAvailable) {
                            log.fine(CLASS_NAME, "run", "706");
                            this.spaceAvailable.notifyAll();
                            throw th3;
                        }
                    }
                }
            }
        } catch (InterruptedException unused2) {
            this.running = false;
        }
    }

    public void setCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public void setManualAcks(boolean z2) {
        this.manualAcks = z2;
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callbacks.put(str, iMqttMessageListener);
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.reconnectInternalCallback = mqttCallbackExtended;
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        synchronized (this.lifecycle) {
            try {
                if (!this.running) {
                    this.messageQueue.clear();
                    this.completeQueue.clear();
                    this.running = true;
                    this.quiescing = false;
                    this.callbackFuture = executorService.submit(this);
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
                Future future = this.callbackFuture;
                if (future != null) {
                    future.cancel(true);
                }
                if (this.running) {
                    Logger logger = log;
                    String str = CLASS_NAME;
                    logger.fine(str, "stop", "700");
                    this.running = false;
                    if (!Thread.currentThread().equals(this.callbackThread)) {
                        try {
                            synchronized (this.workAvailable) {
                                logger.fine(str, "stop", "701");
                                this.workAvailable.notifyAll();
                            }
                            this.runningSemaphore.acquire();
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
                this.callbackThread = null;
                log.fine(CLASS_NAME, "stop", "703");
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
