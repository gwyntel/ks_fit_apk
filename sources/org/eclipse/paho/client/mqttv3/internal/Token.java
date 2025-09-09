package org.eclipse.paho.client.mqttv3.internal;

import anetwork.channel.util.RequestConstant;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class Token {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.Token";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, Token.class.getName());
    private String key;
    private volatile boolean completed = false;
    private boolean pendingComplete = false;
    private boolean sent = false;
    private Object responseLock = new Object();
    private Object sentLock = new Object();

    /* renamed from: a, reason: collision with root package name */
    protected MqttMessage f26675a = null;
    private MqttWireMessage response = null;
    private MqttException exception = null;
    private String[] topics = null;
    private IMqttAsyncClient client = null;
    private IMqttActionListener callback = null;
    private Object userContext = null;
    private int messageID = 0;
    private boolean notified = false;

    public Token(String str) {
        log.setResourceName(str);
    }

    protected boolean a() {
        return this.pendingComplete;
    }

    protected boolean b() {
        return (getClient() == null || isComplete()) ? false : true;
    }

    protected void c(MqttWireMessage mqttWireMessage, MqttException mqttException) {
        log.fine(CLASS_NAME, "markComplete", "404", new Object[]{getKey(), mqttWireMessage, mqttException});
        synchronized (this.responseLock) {
            try {
                if (mqttWireMessage instanceof MqttAck) {
                    this.f26675a = null;
                }
                this.pendingComplete = true;
                this.response = mqttWireMessage;
                this.exception = mqttException;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean checkResult() throws MqttException {
        if (getException() == null) {
            return true;
        }
        throw getException();
    }

    protected void d() {
        log.fine(CLASS_NAME, "notifyComplete", "404", new Object[]{getKey(), this.response, this.exception});
        synchronized (this.responseLock) {
            try {
                if (this.exception == null && this.pendingComplete) {
                    this.completed = true;
                    this.pendingComplete = false;
                } else {
                    this.pendingComplete = false;
                }
                this.responseLock.notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    protected void e() {
        log.fine(CLASS_NAME, "notifySent", "403", new Object[]{getKey()});
        synchronized (this.responseLock) {
            this.response = null;
            this.completed = false;
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    protected void f(IMqttAsyncClient iMqttAsyncClient) {
        this.client = iMqttAsyncClient;
    }

    protected MqttWireMessage g(long j2) {
        synchronized (this.responseLock) {
            try {
                Logger logger = log;
                String str = CLASS_NAME;
                String key = getKey();
                Long l2 = new Long(j2);
                Boolean bool = new Boolean(this.sent);
                Boolean bool2 = new Boolean(this.completed);
                MqttException mqttException = this.exception;
                logger.fine(str, "waitForResponse", "400", new Object[]{key, l2, bool, bool2, mqttException == null ? RequestConstant.FALSE : "true", this.response, this}, mqttException);
                while (!this.completed) {
                    if (this.exception == null) {
                        try {
                            log.fine(CLASS_NAME, "waitForResponse", "408", new Object[]{getKey(), new Long(j2)});
                            if (j2 <= 0) {
                                this.responseLock.wait();
                            } else {
                                this.responseLock.wait(j2);
                            }
                        } catch (InterruptedException e2) {
                            this.exception = new MqttException(e2);
                        }
                    }
                    if (!this.completed) {
                        MqttException mqttException2 = this.exception;
                        if (mqttException2 != null) {
                            log.fine(CLASS_NAME, "waitForResponse", "401", null, mqttException2);
                            throw this.exception;
                        }
                        if (j2 > 0) {
                            break;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        log.fine(CLASS_NAME, "waitForResponse", "402", new Object[]{getKey(), this.response});
        return this.response;
    }

    public IMqttActionListener getActionCallback() {
        return this.callback;
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public MqttException getException() {
        return this.exception;
    }

    public int[] getGrantedQos() {
        int[] iArr = new int[0];
        MqttWireMessage mqttWireMessage = this.response;
        return mqttWireMessage instanceof MqttSuback ? ((MqttSuback) mqttWireMessage).getGrantedQos() : iArr;
    }

    public String getKey() {
        return this.key;
    }

    public MqttMessage getMessage() {
        return this.f26675a;
    }

    public int getMessageID() {
        return this.messageID;
    }

    public MqttWireMessage getResponse() {
        return this.response;
    }

    public boolean getSessionPresent() {
        MqttWireMessage mqttWireMessage = this.response;
        if (mqttWireMessage instanceof MqttConnack) {
            return ((MqttConnack) mqttWireMessage).getSessionPresent();
        }
        return false;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public MqttWireMessage getWireMessage() {
        return this.response;
    }

    public boolean isComplete() {
        return this.completed;
    }

    public boolean isNotified() {
        return this.notified;
    }

    public void reset() throws MqttException {
        if (b()) {
            throw new MqttException(32201);
        }
        log.fine(CLASS_NAME, "reset", "410", new Object[]{getKey()});
        this.client = null;
        this.completed = false;
        this.response = null;
        this.sent = false;
        this.exception = null;
        this.userContext = null;
    }

    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.callback = iMqttActionListener;
    }

    public void setException(MqttException mqttException) {
        synchronized (this.responseLock) {
            this.exception = mqttException;
        }
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setMessage(MqttMessage mqttMessage) {
        this.f26675a = mqttMessage;
    }

    public void setMessageID(int i2) {
        this.messageID = i2;
    }

    public void setNotified(boolean z2) {
        this.notified = z2;
    }

    public void setTopics(String[] strArr) {
        this.topics = strArr;
    }

    public void setUserContext(Object obj) {
        this.userContext = obj;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=");
        stringBuffer.append(getKey());
        stringBuffer.append(" ,topics=");
        if (getTopics() != null) {
            for (int i2 = 0; i2 < getTopics().length; i2++) {
                stringBuffer.append(getTopics()[i2]);
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append(" ,usercontext=");
        stringBuffer.append(getUserContext());
        stringBuffer.append(" ,isComplete=");
        stringBuffer.append(isComplete());
        stringBuffer.append(" ,isNotified=");
        stringBuffer.append(isNotified());
        stringBuffer.append(" ,exception=");
        stringBuffer.append(getException());
        stringBuffer.append(" ,actioncallback=");
        stringBuffer.append(getActionCallback());
        return stringBuffer.toString();
    }

    public void waitForCompletion() throws MqttException {
        waitForCompletion(-1L);
    }

    public void waitUntilSent() throws MqttException {
        boolean z2;
        synchronized (this.sentLock) {
            synchronized (this.responseLock) {
                MqttException mqttException = this.exception;
                if (mqttException != null) {
                    throw mqttException;
                }
            }
            while (true) {
                z2 = this.sent;
                if (z2) {
                    break;
                }
                try {
                    log.fine(CLASS_NAME, "waitUntilSent", "409", new Object[]{getKey()});
                    this.sentLock.wait();
                } catch (InterruptedException unused) {
                }
            }
            if (!z2) {
                MqttException mqttException2 = this.exception;
                if (mqttException2 != null) {
                    throw mqttException2;
                }
                throw ExceptionHelper.createMqttException(6);
            }
        }
    }

    public void waitForCompletion(long j2) throws MqttException {
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "waitForCompletion", "407", new Object[]{getKey(), new Long(j2), this});
        if (g(j2) != null || this.completed) {
            checkResult();
            return;
        }
        logger.fine(str, "waitForCompletion", "406", new Object[]{getKey(), this});
        MqttException mqttException = new MqttException(32000);
        this.exception = mqttException;
        throw mqttException;
    }
}
