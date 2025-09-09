package org.eclipse.paho.client.mqttv3.internal;

import java.io.EOFException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.AlarmMqttPingSender;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingResp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class ClientState {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientState";
    private static final int MAX_MSG_ID = 65535;
    private static final int MIN_MSG_ID = 1;
    private static final String PERSISTENCE_CONFIRMED_PREFIX = "sc-";
    private static final String PERSISTENCE_RECEIVED_PREFIX = "r-";
    private static final String PERSISTENCE_SENT_BUFFERED_PREFIX = "sb-";
    private static final String PERSISTENCE_SENT_PREFIX = "s-";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, ClientState.class.getName());
    private int actualInFlight;
    private CommsCallback callback;
    private boolean cleanSession;
    private ClientComms clientComms;
    private int inFlightPubRels;
    private Hashtable inUseMsgIds;
    private Hashtable inboundQoS2;
    private long keepAlive;
    private Hashtable outboundQoS0;
    private Hashtable outboundQoS1;
    private Hashtable outboundQoS2;
    private volatile Vector pendingFlows;
    private volatile Vector pendingMessages;
    private MqttClientPersistence persistence;
    private MqttWireMessage pingCommand;
    private MqttPingSender pingSender;
    private CommsTokenStore tokenStore;
    private int nextMsgId = 0;
    private int maxInflight = 0;
    private Object queueLock = new Object();
    private Object quiesceLock = new Object();
    private boolean quiescing = false;
    private long lastOutboundActivity = 0;
    private long lastInboundActivity = 0;
    private long lastPing = 0;
    private Object pingOutstandingLock = new Object();
    private int pingOutstanding = 0;
    private boolean connected = false;

    protected ClientState(MqttClientPersistence mqttClientPersistence, CommsTokenStore commsTokenStore, CommsCallback commsCallback, ClientComms clientComms, MqttPingSender mqttPingSender) throws MqttException {
        this.clientComms = null;
        this.callback = null;
        this.actualInFlight = 0;
        this.inFlightPubRels = 0;
        this.outboundQoS2 = null;
        this.outboundQoS1 = null;
        this.outboundQoS0 = null;
        this.inboundQoS2 = null;
        this.pingSender = null;
        Logger logger = log;
        logger.setResourceName(clientComms.getClient().getClientId());
        logger.finer(CLASS_NAME, "<Init>", "");
        this.inUseMsgIds = new Hashtable();
        this.pendingFlows = new Vector();
        this.outboundQoS2 = new Hashtable();
        this.outboundQoS1 = new Hashtable();
        this.outboundQoS0 = new Hashtable();
        this.inboundQoS2 = new Hashtable();
        this.pingCommand = new MqttPingReq();
        this.inFlightPubRels = 0;
        this.actualInFlight = 0;
        this.persistence = mqttClientPersistence;
        this.callback = commsCallback;
        this.tokenStore = commsTokenStore;
        this.clientComms = clientComms;
        this.pingSender = mqttPingSender;
        n();
    }

    private void decrementInFlight() {
        synchronized (this.queueLock) {
            try {
                int i2 = this.actualInFlight - 1;
                this.actualInFlight = i2;
                log.fine(CLASS_NAME, "decrementInFlight", "646", new Object[]{new Integer(i2)});
                if (!a()) {
                    this.queueLock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private synchronized int getNextMessageId() throws MqttException {
        int i2;
        try {
            int i3 = this.nextMsgId;
            int i4 = 0;
            do {
                int i5 = this.nextMsgId + 1;
                this.nextMsgId = i5;
                if (i5 > 65535) {
                    this.nextMsgId = 1;
                }
                i2 = this.nextMsgId;
                if (i2 == i3 && (i4 = i4 + 1) == 2) {
                    throw ExceptionHelper.createMqttException(32001);
                }
            } while (this.inUseMsgIds.containsKey(new Integer(i2)));
            Integer num = new Integer(this.nextMsgId);
            this.inUseMsgIds.put(num, num);
        } catch (Throwable th) {
            throw th;
        }
        return this.nextMsgId;
    }

    private String getReceivedPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_RECEIVED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendBufferedPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_SENT_BUFFERED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendConfirmPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_CONFIRMED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_SENT_PREFIX + mqttWireMessage.getMessageId();
    }

    private void insertInOrder(Vector vector, MqttWireMessage mqttWireMessage) {
        int messageId = mqttWireMessage.getMessageId();
        for (int i2 = 0; i2 < vector.size(); i2++) {
            if (((MqttWireMessage) vector.elementAt(i2)).getMessageId() > messageId) {
                vector.insertElementAt(mqttWireMessage, i2);
                return;
            }
        }
        vector.addElement(mqttWireMessage);
    }

    private Vector reOrder(Vector vector) {
        Vector vector2 = new Vector();
        if (vector.size() == 0) {
            return vector2;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i2 < vector.size()) {
            int messageId = ((MqttWireMessage) vector.elementAt(i2)).getMessageId();
            int i6 = messageId - i3;
            if (i6 > i4) {
                i5 = i2;
                i4 = i6;
            }
            i2++;
            i3 = messageId;
        }
        if ((65535 - i3) + ((MqttWireMessage) vector.elementAt(0)).getMessageId() > i4) {
            i5 = 0;
        }
        for (int i7 = i5; i7 < vector.size(); i7++) {
            vector2.addElement(vector.elementAt(i7));
        }
        for (int i8 = 0; i8 < i5; i8++) {
            vector2.addElement(vector.elementAt(i8));
        }
        return vector2;
    }

    private synchronized void releaseMessageId(int i2) {
        this.inUseMsgIds.remove(new Integer(i2));
    }

    private void restoreInflightMessages() {
        this.pendingMessages = new Vector(this.maxInflight);
        this.pendingFlows = new Vector();
        Enumeration enumerationKeys = this.outboundQoS2.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            MqttWireMessage mqttWireMessage = (MqttWireMessage) this.outboundQoS2.get(objNextElement);
            if (mqttWireMessage instanceof MqttPublish) {
                log.fine(CLASS_NAME, "restoreInflightMessages", "610", new Object[]{objNextElement});
                mqttWireMessage.setDuplicate(true);
                insertInOrder(this.pendingMessages, (MqttPublish) mqttWireMessage);
            } else if (mqttWireMessage instanceof MqttPubRel) {
                log.fine(CLASS_NAME, "restoreInflightMessages", "611", new Object[]{objNextElement});
                insertInOrder(this.pendingFlows, (MqttPubRel) mqttWireMessage);
            }
        }
        Enumeration enumerationKeys2 = this.outboundQoS1.keys();
        while (enumerationKeys2.hasMoreElements()) {
            Object objNextElement2 = enumerationKeys2.nextElement();
            MqttPublish mqttPublish = (MqttPublish) this.outboundQoS1.get(objNextElement2);
            mqttPublish.setDuplicate(true);
            log.fine(CLASS_NAME, "restoreInflightMessages", "612", new Object[]{objNextElement2});
            insertInOrder(this.pendingMessages, mqttPublish);
        }
        Enumeration enumerationKeys3 = this.outboundQoS0.keys();
        while (enumerationKeys3.hasMoreElements()) {
            Object objNextElement3 = enumerationKeys3.nextElement();
            MqttPublish mqttPublish2 = (MqttPublish) this.outboundQoS0.get(objNextElement3);
            log.fine(CLASS_NAME, "restoreInflightMessages", "512", new Object[]{objNextElement3});
            insertInOrder(this.pendingMessages, mqttPublish2);
        }
        this.pendingFlows = reOrder(this.pendingFlows);
        this.pendingMessages = reOrder(this.pendingMessages);
    }

    private MqttWireMessage restoreMessage(String str, MqttPersistable mqttPersistable) throws MqttException {
        MqttWireMessage mqttWireMessageCreateWireMessage;
        try {
            mqttWireMessageCreateWireMessage = MqttWireMessage.createWireMessage(mqttPersistable);
        } catch (MqttException e2) {
            log.fine(CLASS_NAME, "restoreMessage", "602", new Object[]{str}, e2);
            if (!(e2.getCause() instanceof EOFException)) {
                throw e2;
            }
            if (str != null) {
                this.persistence.remove(str);
            }
            mqttWireMessageCreateWireMessage = null;
        }
        log.fine(CLASS_NAME, "restoreMessage", "601", new Object[]{str, mqttWireMessageCreateWireMessage});
        return mqttWireMessageCreateWireMessage;
    }

    protected boolean a() {
        int iCount = this.tokenStore.count();
        if (!this.quiescing || iCount != 0 || this.pendingFlows.size() != 0 || !this.callback.isQuiesced()) {
            return false;
        }
        log.fine(CLASS_NAME, "checkQuiesceLock", "626", new Object[]{new Boolean(this.quiescing), new Integer(this.actualInFlight), new Integer(this.pendingFlows.size()), new Integer(this.inFlightPubRels), Boolean.valueOf(this.callback.isQuiesced()), new Integer(iCount)});
        synchronized (this.quiesceLock) {
            this.quiesceLock.notifyAll();
        }
        return true;
    }

    protected void b() throws MqttPersistenceException {
        log.fine(CLASS_NAME, "clearState", ">");
        this.persistence.clear();
        this.inUseMsgIds.clear();
        this.pendingMessages.clear();
        this.pendingFlows.clear();
        this.outboundQoS2.clear();
        this.outboundQoS1.clear();
        this.outboundQoS0.clear();
        this.inboundQoS2.clear();
        this.tokenStore.clear();
    }

    protected void c() {
        this.inUseMsgIds.clear();
        if (this.pendingMessages != null) {
            this.pendingMessages.clear();
        }
        this.pendingFlows.clear();
        this.outboundQoS2.clear();
        this.outboundQoS1.clear();
        this.outboundQoS0.clear();
        this.inboundQoS2.clear();
        this.tokenStore.clear();
        this.inUseMsgIds = null;
        this.pendingMessages = null;
        this.pendingFlows = null;
        this.outboundQoS2 = null;
        this.outboundQoS1 = null;
        this.outboundQoS0 = null;
        this.inboundQoS2 = null;
        this.tokenStore = null;
        this.callback = null;
        this.clientComms = null;
        this.persistence = null;
        this.pingCommand = null;
    }

    public MqttToken checkForActivity(IMqttActionListener iMqttActionListener) throws Throwable {
        Logger logger;
        Object obj;
        long jMax;
        MqttToken mqttToken;
        Logger logger2 = log;
        String str = CLASS_NAME;
        logger2.fine(str, "checkForActivity", "616", new Object[0]);
        synchronized (this.quiesceLock) {
            try {
                if (this.quiescing) {
                    return null;
                }
                h();
                if (!this.connected || this.keepAlive <= 0) {
                    return null;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                Object obj2 = this.pingOutstandingLock;
                synchronized (obj2) {
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        int i2 = this.pingOutstanding;
                        if (i2 > 0) {
                            obj = obj2;
                            long j2 = jCurrentTimeMillis - this.lastInboundActivity;
                            long j3 = this.keepAlive;
                            if (j2 >= 100 + j3) {
                                logger2.severe(str, "checkForActivity", "619", new Object[]{new Long(j3), new Long(this.lastOutboundActivity), new Long(this.lastInboundActivity), new Long(jCurrentTimeMillis), new Long(this.lastPing)});
                                throw ExceptionHelper.createMqttException(32000);
                            }
                            logger = logger2;
                        } else {
                            logger = logger2;
                            obj = obj2;
                        }
                        if (i2 == 0) {
                            long j4 = jCurrentTimeMillis - this.lastOutboundActivity;
                            long j5 = this.keepAlive;
                            if (j4 >= 2 * j5) {
                                logger.severe(str, "checkForActivity", "642", new Object[]{new Long(j5), new Long(this.lastOutboundActivity), new Long(this.lastInboundActivity), new Long(jCurrentTimeMillis), new Long(this.lastPing)});
                                throw ExceptionHelper.createMqttException(32002);
                            }
                        }
                        if ((i2 != 0 || jCurrentTimeMillis - this.lastInboundActivity < this.keepAlive - 100) && jCurrentTimeMillis - this.lastOutboundActivity < this.keepAlive - 100) {
                            logger.fine(str, "checkForActivity", "634", null);
                            jMax = Math.max(1L, h() - (jCurrentTimeMillis - this.lastOutboundActivity));
                            if (this.pingSender instanceof AlarmMqttPingSender) {
                                mqttToken = new MqttToken(this.clientComms.getClient().getClientId());
                                if (iMqttActionListener != null) {
                                    mqttToken.setActionCallback(iMqttActionListener);
                                }
                                this.tokenStore.d(mqttToken, this.pingCommand);
                                this.pendingFlows.insertElementAt(this.pingCommand, 0);
                                jMax = h();
                                notifyQueueLock();
                            } else {
                                mqttToken = null;
                            }
                        } else {
                            logger.fine(str, "checkForActivity", "620", new Object[]{new Long(this.keepAlive), new Long(this.lastOutboundActivity), new Long(this.lastInboundActivity)});
                            mqttToken = new MqttToken(this.clientComms.getClient().getClientId());
                            if (iMqttActionListener != null) {
                                mqttToken.setActionCallback(iMqttActionListener);
                            }
                            this.tokenStore.d(mqttToken, this.pingCommand);
                            this.pendingFlows.insertElementAt(this.pingCommand, 0);
                            jMax = h();
                            notifyQueueLock();
                        }
                        logger.fine(str, "checkForActivity", "624", new Object[]{new Long(jMax)});
                        MqttPingSender mqttPingSender = this.pingSender;
                        if (mqttPingSender instanceof AlarmMqttPingSender) {
                            return mqttToken;
                        }
                        mqttPingSender.schedule(jMax);
                        return mqttToken;
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                throw th;
            } finally {
            }
        }
    }

    public void connected() {
        log.fine(CLASS_NAME, "connected", "631");
        this.connected = true;
        this.pingSender.start();
    }

    protected void d(int i2) {
        log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[]{new Integer(i2)});
        this.persistence.remove(getReceivedPersistenceKey(i2));
        this.inboundQoS2.remove(new Integer(i2));
    }

    public void disconnected(MqttException mqttException) {
        log.fine(CLASS_NAME, "disconnected", "633", new Object[]{mqttException});
        this.connected = false;
        try {
            if (this.cleanSession) {
                b();
            }
            this.pendingMessages.clear();
            this.pendingFlows.clear();
            synchronized (this.pingOutstandingLock) {
                this.pingOutstanding = 0;
            }
        } catch (MqttException unused) {
        }
    }

    protected void e(MqttPublish mqttPublish) {
        log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[]{new Integer(mqttPublish.getMessageId())});
        this.persistence.remove(getReceivedPersistenceKey(mqttPublish));
        this.inboundQoS2.remove(new Integer(mqttPublish.getMessageId()));
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x002b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage f() {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientState.f():org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage");
    }

    protected boolean g() {
        return this.cleanSession;
    }

    public int getActualInFlight() {
        return this.actualInFlight;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("In use msgids", this.inUseMsgIds);
        properties.put("pendingMessages", this.pendingMessages);
        properties.put("pendingFlows", this.pendingFlows);
        properties.put("maxInflight", new Integer(this.maxInflight));
        properties.put("nextMsgID", new Integer(this.nextMsgId));
        properties.put("actualInFlight", new Integer(this.actualInFlight));
        properties.put("inFlightPubRels", new Integer(this.inFlightPubRels));
        properties.put("quiescing", Boolean.valueOf(this.quiescing));
        properties.put("pingoutstanding", new Integer(this.pingOutstanding));
        properties.put("lastOutboundActivity", new Long(this.lastOutboundActivity));
        properties.put("lastInboundActivity", new Long(this.lastInboundActivity));
        properties.put("outboundQoS2", this.outboundQoS2);
        properties.put("outboundQoS1", this.outboundQoS1);
        properties.put("outboundQoS0", this.outboundQoS0);
        properties.put("inboundQoS2", this.inboundQoS2);
        properties.put("tokens", this.tokenStore);
        return properties;
    }

    public int getMaxInFlight() {
        return this.maxInflight;
    }

    protected long h() {
        return this.keepAlive;
    }

    protected void i(MqttToken mqttToken) throws MqttPersistenceException {
        MqttWireMessage wireMessage = mqttToken.internalTok.getWireMessage();
        if (wireMessage == null || !(wireMessage instanceof MqttAck)) {
            return;
        }
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "notifyComplete", "629", new Object[]{new Integer(wireMessage.getMessageId()), mqttToken, wireMessage});
        MqttAck mqttAck = (MqttAck) wireMessage;
        if (mqttAck instanceof MqttPubAck) {
            this.persistence.remove(getSendPersistenceKey(wireMessage));
            this.persistence.remove(getSendBufferedPersistenceKey(wireMessage));
            this.outboundQoS1.remove(new Integer(mqttAck.getMessageId()));
            decrementInFlight();
            releaseMessageId(wireMessage.getMessageId());
            this.tokenStore.removeToken(wireMessage);
            logger.fine(str, "notifyComplete", "650", new Object[]{new Integer(mqttAck.getMessageId())});
        } else if (mqttAck instanceof MqttPubComp) {
            this.persistence.remove(getSendPersistenceKey(wireMessage));
            this.persistence.remove(getSendConfirmPersistenceKey(wireMessage));
            this.persistence.remove(getSendBufferedPersistenceKey(wireMessage));
            this.outboundQoS2.remove(new Integer(mqttAck.getMessageId()));
            this.inFlightPubRels--;
            decrementInFlight();
            releaseMessageId(wireMessage.getMessageId());
            this.tokenStore.removeToken(wireMessage);
            logger.fine(str, "notifyComplete", "645", new Object[]{new Integer(mqttAck.getMessageId()), new Integer(this.inFlightPubRels)});
        }
        a();
    }

    protected void j(MqttAck mqttAck) throws MqttException {
        this.lastInboundActivity = System.currentTimeMillis();
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "notifyReceivedAck", "627", new Object[]{new Integer(mqttAck.getMessageId()), mqttAck});
        MqttToken token = this.tokenStore.getToken(mqttAck);
        if (token == null) {
            logger.fine(str, "notifyReceivedAck", "662", new Object[]{new Integer(mqttAck.getMessageId())});
        } else if (mqttAck instanceof MqttPubRec) {
            send(new MqttPubRel((MqttPubRec) mqttAck), token);
        } else if ((mqttAck instanceof MqttPubAck) || (mqttAck instanceof MqttPubComp)) {
            l(mqttAck, token, null);
        } else if (mqttAck instanceof MqttPingResp) {
            synchronized (this.pingOutstandingLock) {
                try {
                    this.pingOutstanding = Math.max(0, this.pingOutstanding - 1);
                    l(mqttAck, token, null);
                    if (this.pingOutstanding == 0) {
                        this.tokenStore.removeToken(mqttAck);
                    }
                } finally {
                }
            }
            logger.fine(str, "notifyReceivedAck", "636", new Object[]{new Integer(this.pingOutstanding)});
        } else if (mqttAck instanceof MqttConnack) {
            MqttConnack mqttConnack = (MqttConnack) mqttAck;
            int returnCode = mqttConnack.getReturnCode();
            if (returnCode != 0) {
                throw ExceptionHelper.createMqttException(returnCode);
            }
            synchronized (this.queueLock) {
                try {
                    if (this.cleanSession) {
                        b();
                        this.tokenStore.d(token, mqttAck);
                    }
                    this.inFlightPubRels = 0;
                    this.actualInFlight = 0;
                    restoreInflightMessages();
                    connected();
                } finally {
                }
            }
            this.clientComms.connectComplete(mqttConnack, null);
            l(mqttAck, token, null);
            this.tokenStore.removeToken(mqttAck);
            synchronized (this.queueLock) {
                this.queueLock.notifyAll();
            }
        } else {
            l(mqttAck, token, null);
            releaseMessageId(mqttAck.getMessageId());
            this.tokenStore.removeToken(mqttAck);
        }
        a();
    }

    protected void k(MqttWireMessage mqttWireMessage) throws MqttException {
        this.lastInboundActivity = System.currentTimeMillis();
        log.fine(CLASS_NAME, "notifyReceivedMsg", "651", new Object[]{new Integer(mqttWireMessage.getMessageId()), mqttWireMessage});
        if (this.quiescing) {
            return;
        }
        if (!(mqttWireMessage instanceof MqttPublish)) {
            if (mqttWireMessage instanceof MqttPubRel) {
                MqttPublish mqttPublish = (MqttPublish) this.inboundQoS2.get(new Integer(mqttWireMessage.getMessageId()));
                if (mqttPublish == null) {
                    send(new MqttPubComp(mqttWireMessage.getMessageId()), null);
                    return;
                }
                CommsCallback commsCallback = this.callback;
                if (commsCallback != null) {
                    commsCallback.messageArrived(mqttPublish);
                    return;
                }
                return;
            }
            return;
        }
        MqttPublish mqttPublish2 = (MqttPublish) mqttWireMessage;
        int qos = mqttPublish2.getMessage().getQos();
        if (qos == 0 || qos == 1) {
            CommsCallback commsCallback2 = this.callback;
            if (commsCallback2 != null) {
                commsCallback2.messageArrived(mqttPublish2);
                return;
            }
            return;
        }
        if (qos != 2) {
            return;
        }
        this.persistence.put(getReceivedPersistenceKey(mqttWireMessage), mqttPublish2);
        this.inboundQoS2.put(new Integer(mqttPublish2.getMessageId()), mqttPublish2);
        send(new MqttPubRec(mqttPublish2), null);
    }

    protected void l(MqttWireMessage mqttWireMessage, MqttToken mqttToken, MqttException mqttException) {
        mqttToken.internalTok.c(mqttWireMessage, mqttException);
        mqttToken.internalTok.d();
        if (mqttWireMessage != null && (mqttWireMessage instanceof MqttAck) && !(mqttWireMessage instanceof MqttPubRec)) {
            log.fine(CLASS_NAME, "notifyResult", "648", new Object[]{mqttToken.internalTok.getKey(), mqttWireMessage, mqttException});
            this.callback.asyncOperationComplete(mqttToken);
        }
        if (mqttWireMessage == null) {
            log.fine(CLASS_NAME, "notifyResult", "649", new Object[]{mqttToken.internalTok.getKey(), mqttException});
            this.callback.asyncOperationComplete(mqttToken);
        }
    }

    protected void m(MqttWireMessage mqttWireMessage) {
        int i2;
        this.lastOutboundActivity = System.currentTimeMillis();
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "notifySent", "625", new Object[]{mqttWireMessage.getKey()});
        MqttToken token = this.tokenStore.getToken(mqttWireMessage);
        token.internalTok.e();
        if (mqttWireMessage instanceof MqttPingReq) {
            synchronized (this.pingOutstandingLock) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                synchronized (this.pingOutstandingLock) {
                    this.lastPing = jCurrentTimeMillis;
                    i2 = this.pingOutstanding + 1;
                    this.pingOutstanding = i2;
                }
                logger.fine(str, "notifySent", "635", new Object[]{new Integer(i2)});
            }
            return;
        }
        if ((mqttWireMessage instanceof MqttPublish) && ((MqttPublish) mqttWireMessage).getMessage().getQos() == 0) {
            token.internalTok.c(null, null);
            this.callback.asyncOperationComplete(token);
            decrementInFlight();
            releaseMessageId(mqttWireMessage.getMessageId());
            this.tokenStore.removeToken(mqttWireMessage);
            a();
        }
    }

    protected void n() throws MqttException {
        Enumeration enumerationKeys = this.persistence.keys();
        int iMax = this.nextMsgId;
        Vector vector = new Vector();
        log.fine(CLASS_NAME, "restoreState", "600");
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            MqttWireMessage mqttWireMessageRestoreMessage = restoreMessage(str, this.persistence.get(str));
            if (mqttWireMessageRestoreMessage != null) {
                if (str.startsWith(PERSISTENCE_RECEIVED_PREFIX)) {
                    log.fine(CLASS_NAME, "restoreState", "604", new Object[]{str, mqttWireMessageRestoreMessage});
                    this.inboundQoS2.put(new Integer(mqttWireMessageRestoreMessage.getMessageId()), mqttWireMessageRestoreMessage);
                } else if (str.startsWith(PERSISTENCE_SENT_PREFIX)) {
                    MqttPublish mqttPublish = (MqttPublish) mqttWireMessageRestoreMessage;
                    iMax = Math.max(mqttPublish.getMessageId(), iMax);
                    if (this.persistence.containsKey(getSendConfirmPersistenceKey(mqttPublish))) {
                        MqttPubRel mqttPubRel = (MqttPubRel) restoreMessage(str, this.persistence.get(getSendConfirmPersistenceKey(mqttPublish)));
                        if (mqttPubRel != null) {
                            log.fine(CLASS_NAME, "restoreState", "605", new Object[]{str, mqttWireMessageRestoreMessage});
                            this.outboundQoS2.put(new Integer(mqttPubRel.getMessageId()), mqttPubRel);
                        } else {
                            log.fine(CLASS_NAME, "restoreState", "606", new Object[]{str, mqttWireMessageRestoreMessage});
                        }
                    } else {
                        mqttPublish.setDuplicate(true);
                        if (mqttPublish.getMessage().getQos() == 2) {
                            log.fine(CLASS_NAME, "restoreState", "607", new Object[]{str, mqttWireMessageRestoreMessage});
                            this.outboundQoS2.put(new Integer(mqttPublish.getMessageId()), mqttPublish);
                        } else {
                            log.fine(CLASS_NAME, "restoreState", "608", new Object[]{str, mqttWireMessageRestoreMessage});
                            this.outboundQoS1.put(new Integer(mqttPublish.getMessageId()), mqttPublish);
                        }
                    }
                    this.tokenStore.b(mqttPublish).internalTok.f(this.clientComms.getClient());
                    this.inUseMsgIds.put(new Integer(mqttPublish.getMessageId()), new Integer(mqttPublish.getMessageId()));
                } else if (str.startsWith(PERSISTENCE_SENT_BUFFERED_PREFIX)) {
                    MqttPublish mqttPublish2 = (MqttPublish) mqttWireMessageRestoreMessage;
                    iMax = Math.max(mqttPublish2.getMessageId(), iMax);
                    if (mqttPublish2.getMessage().getQos() == 2) {
                        log.fine(CLASS_NAME, "restoreState", "607", new Object[]{str, mqttWireMessageRestoreMessage});
                        this.outboundQoS2.put(new Integer(mqttPublish2.getMessageId()), mqttPublish2);
                    } else if (mqttPublish2.getMessage().getQos() == 1) {
                        log.fine(CLASS_NAME, "restoreState", "608", new Object[]{str, mqttWireMessageRestoreMessage});
                        this.outboundQoS1.put(new Integer(mqttPublish2.getMessageId()), mqttPublish2);
                    } else {
                        log.fine(CLASS_NAME, "restoreState", "511", new Object[]{str, mqttWireMessageRestoreMessage});
                        this.outboundQoS0.put(new Integer(mqttPublish2.getMessageId()), mqttPublish2);
                        this.persistence.remove(str);
                    }
                    this.tokenStore.b(mqttPublish2).internalTok.f(this.clientComms.getClient());
                    this.inUseMsgIds.put(new Integer(mqttPublish2.getMessageId()), new Integer(mqttPublish2.getMessageId()));
                } else if (str.startsWith(PERSISTENCE_CONFIRMED_PREFIX) && !this.persistence.containsKey(getSendPersistenceKey((MqttPubRel) mqttWireMessageRestoreMessage))) {
                    vector.addElement(str);
                }
            }
        }
        Enumeration enumerationElements = vector.elements();
        while (enumerationElements.hasMoreElements()) {
            String str2 = (String) enumerationElements.nextElement();
            log.fine(CLASS_NAME, "restoreState", "609", new Object[]{str2});
            this.persistence.remove(str2);
        }
        this.nextMsgId = iMax;
    }

    public void notifyQueueLock() {
        synchronized (this.queueLock) {
            log.fine(CLASS_NAME, "notifyQueueLock", "638");
            this.queueLock.notifyAll();
        }
    }

    public void notifyReceivedBytes(int i2) {
        if (i2 > 0) {
            this.lastInboundActivity = System.currentTimeMillis();
        }
        log.fine(CLASS_NAME, "notifyReceivedBytes", "630", new Object[]{new Integer(i2)});
    }

    public void notifySentBytes(int i2) {
        if (i2 > 0) {
            this.lastOutboundActivity = System.currentTimeMillis();
        }
        log.fine(CLASS_NAME, "notifySentBytes", "643", new Object[]{new Integer(i2)});
    }

    protected void o(boolean z2) {
        this.cleanSession = z2;
    }

    protected void p(long j2) {
        this.keepAlive = j2 * 1000;
    }

    public void persistBufferedMessage(MqttWireMessage mqttWireMessage) {
        String sendBufferedPersistenceKey = getSendBufferedPersistenceKey(mqttWireMessage);
        try {
            mqttWireMessage.setMessageId(getNextMessageId());
            String sendBufferedPersistenceKey2 = getSendBufferedPersistenceKey(mqttWireMessage);
            try {
                this.persistence.put(sendBufferedPersistenceKey2, (MqttPublish) mqttWireMessage);
            } catch (MqttPersistenceException unused) {
                log.fine(CLASS_NAME, "persistBufferedMessage", "515");
                this.persistence.open(this.clientComms.getClient().getClientId(), this.clientComms.getClient().getServerURI());
                this.persistence.put(sendBufferedPersistenceKey2, (MqttPublish) mqttWireMessage);
            }
            log.fine(CLASS_NAME, "persistBufferedMessage", "513", new Object[]{sendBufferedPersistenceKey2});
        } catch (MqttException unused2) {
            log.warning(CLASS_NAME, "persistBufferedMessage", "513", new Object[]{sendBufferedPersistenceKey});
        }
    }

    protected void q(int i2) {
        this.maxInflight = i2;
        this.pendingMessages = new Vector(this.maxInflight);
    }

    public void quiesce(long j2) {
        if (j2 > 0) {
            Logger logger = log;
            String str = CLASS_NAME;
            logger.fine(str, "quiesce", "637", new Object[]{new Long(j2)});
            synchronized (this.queueLock) {
                this.quiescing = true;
            }
            this.callback.quiesce();
            notifyQueueLock();
            synchronized (this.quiesceLock) {
                try {
                    try {
                        int iCount = this.tokenStore.count();
                        if (iCount > 0 || this.pendingFlows.size() > 0 || !this.callback.isQuiesced()) {
                            logger.fine(str, "quiesce", "639", new Object[]{new Integer(this.actualInFlight), new Integer(this.pendingFlows.size()), new Integer(this.inFlightPubRels), new Integer(iCount)});
                            this.quiesceLock.wait(j2);
                        }
                    } catch (InterruptedException unused) {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            synchronized (this.queueLock) {
                this.pendingMessages.clear();
                this.pendingFlows.clear();
                this.quiescing = false;
                this.actualInFlight = 0;
            }
            log.fine(CLASS_NAME, "quiesce", "640");
        }
    }

    protected void r(MqttPublish mqttPublish) {
        synchronized (this.queueLock) {
            try {
                log.fine(CLASS_NAME, "undo", "618", new Object[]{new Integer(mqttPublish.getMessageId()), new Integer(mqttPublish.getMessage().getQos())});
                if (mqttPublish.getMessage().getQos() == 1) {
                    this.outboundQoS1.remove(new Integer(mqttPublish.getMessageId()));
                } else {
                    this.outboundQoS2.remove(new Integer(mqttPublish.getMessageId()));
                }
                this.pendingMessages.removeElement(mqttPublish);
                this.persistence.remove(getSendPersistenceKey(mqttPublish));
                this.tokenStore.removeToken(mqttPublish);
                if (mqttPublish.getMessage().getQos() > 0) {
                    releaseMessageId(mqttPublish.getMessageId());
                    mqttPublish.setMessageId(0);
                }
                a();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Vector resolveOldTokens(MqttException mqttException) {
        log.fine(CLASS_NAME, "resolveOldTokens", "632", new Object[]{mqttException});
        if (mqttException == null) {
            mqttException = new MqttException(32102);
        }
        Vector outstandingTokens = this.tokenStore.getOutstandingTokens();
        Enumeration enumerationElements = outstandingTokens.elements();
        while (enumerationElements.hasMoreElements()) {
            MqttToken mqttToken = (MqttToken) enumerationElements.nextElement();
            synchronized (mqttToken) {
                try {
                    if (!mqttToken.isComplete() && !mqttToken.internalTok.a() && mqttToken.getException() == null) {
                        mqttToken.internalTok.setException(mqttException);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!(mqttToken instanceof MqttDeliveryToken)) {
                this.tokenStore.removeToken(mqttToken.internalTok.getKey());
            }
        }
        return outstandingTokens;
    }

    public void send(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        if (mqttWireMessage.isMessageIdRequired() && mqttWireMessage.getMessageId() == 0 && ((mqttWireMessage instanceof MqttPublish) || (mqttWireMessage instanceof MqttPubAck) || (mqttWireMessage instanceof MqttPubRec) || (mqttWireMessage instanceof MqttPubRel) || (mqttWireMessage instanceof MqttPubComp) || (mqttWireMessage instanceof MqttSubscribe) || (mqttWireMessage instanceof MqttSuback) || (mqttWireMessage instanceof MqttUnsubscribe) || (mqttWireMessage instanceof MqttUnsubAck))) {
            mqttWireMessage.setMessageId(getNextMessageId());
        }
        if (mqttToken != null) {
            try {
                mqttToken.internalTok.setMessageID(mqttWireMessage.getMessageId());
            } catch (Exception unused) {
            }
        }
        if (mqttWireMessage instanceof MqttPublish) {
            synchronized (this.queueLock) {
                try {
                    int i2 = this.actualInFlight;
                    if (i2 >= this.maxInflight) {
                        log.fine(CLASS_NAME, "send", "613", new Object[]{new Integer(i2)});
                        throw new MqttException(32202);
                    }
                    MqttMessage message = ((MqttPublish) mqttWireMessage).getMessage();
                    log.fine(CLASS_NAME, "send", "628", new Object[]{new Integer(mqttWireMessage.getMessageId()), new Integer(message.getQos()), mqttWireMessage});
                    int qos = message.getQos();
                    if (qos == 1) {
                        this.outboundQoS1.put(new Integer(mqttWireMessage.getMessageId()), mqttWireMessage);
                        this.persistence.put(getSendPersistenceKey(mqttWireMessage), (MqttPublish) mqttWireMessage);
                    } else if (qos == 2) {
                        this.outboundQoS2.put(new Integer(mqttWireMessage.getMessageId()), mqttWireMessage);
                        this.persistence.put(getSendPersistenceKey(mqttWireMessage), (MqttPublish) mqttWireMessage);
                    }
                    this.tokenStore.d(mqttToken, mqttWireMessage);
                    this.pendingMessages.addElement(mqttWireMessage);
                    this.queueLock.notifyAll();
                } finally {
                }
            }
            return;
        }
        log.fine(CLASS_NAME, "send", "615", new Object[]{new Integer(mqttWireMessage.getMessageId()), mqttWireMessage});
        if (mqttWireMessage instanceof MqttConnect) {
            synchronized (this.queueLock) {
                this.tokenStore.d(mqttToken, mqttWireMessage);
                this.pendingFlows.insertElementAt(mqttWireMessage, 0);
                this.queueLock.notifyAll();
            }
            return;
        }
        if (mqttWireMessage instanceof MqttPingReq) {
            this.pingCommand = mqttWireMessage;
        } else if (mqttWireMessage instanceof MqttPubRel) {
            this.outboundQoS2.put(new Integer(mqttWireMessage.getMessageId()), mqttWireMessage);
            this.persistence.put(getSendConfirmPersistenceKey(mqttWireMessage), (MqttPubRel) mqttWireMessage);
        } else if (mqttWireMessage instanceof MqttPubComp) {
            this.persistence.remove(getReceivedPersistenceKey(mqttWireMessage));
        }
        synchronized (this.queueLock) {
            try {
                if (!(mqttWireMessage instanceof MqttAck)) {
                    this.tokenStore.d(mqttToken, mqttWireMessage);
                }
                this.pendingFlows.addElement(mqttWireMessage);
                this.queueLock.notifyAll();
            } finally {
            }
        }
    }

    public void setKeepAliveInterval(long j2) {
        this.keepAlive = j2;
    }

    public void unPersistBufferedMessage(MqttWireMessage mqttWireMessage) {
        try {
            log.fine(CLASS_NAME, "unPersistBufferedMessage", "517", new Object[]{mqttWireMessage.getKey()});
            this.persistence.remove(getSendBufferedPersistenceKey(mqttWireMessage));
        } catch (MqttPersistenceException unused) {
            log.fine(CLASS_NAME, "unPersistBufferedMessage", "518", new Object[]{mqttWireMessage.getKey()});
        }
    }

    private String getReceivedPersistenceKey(int i2) {
        return PERSISTENCE_RECEIVED_PREFIX + i2;
    }
}
