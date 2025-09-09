package org.eclipse.paho.client.mqttv3.internal;

import androidx.health.connect.client.records.CervicalMucusRecord;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.linksdk.alcs.api.utils.ErrorCode;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class CommsTokenStore {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsTokenStore";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CommsTokenStore.class.getName());
    private MqttException closedResponse = null;
    private String logContext;
    private Hashtable tokens;

    public CommsTokenStore(String str) {
        Logger logger = log;
        logger.setResourceName(str);
        this.tokens = new Hashtable();
        this.logContext = str;
        logger.fine(CLASS_NAME, "<Init>", "308");
    }

    protected void a(MqttException mqttException) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "quiesce", "309", new Object[]{mqttException});
            this.closedResponse = mqttException;
        }
    }

    protected MqttDeliveryToken b(MqttPublish mqttPublish) {
        MqttDeliveryToken mqttDeliveryToken;
        synchronized (this.tokens) {
            try {
                String string = new Integer(mqttPublish.getMessageId()).toString();
                if (this.tokens.containsKey(string)) {
                    mqttDeliveryToken = (MqttDeliveryToken) this.tokens.get(string);
                    log.fine(CLASS_NAME, "restoreToken", "302", new Object[]{string, mqttPublish, mqttDeliveryToken});
                } else {
                    mqttDeliveryToken = new MqttDeliveryToken(this.logContext);
                    mqttDeliveryToken.internalTok.setKey(string);
                    this.tokens.put(string, mqttDeliveryToken);
                    log.fine(CLASS_NAME, "restoreToken", "303", new Object[]{string, mqttPublish, mqttDeliveryToken});
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return mqttDeliveryToken;
    }

    protected void c(MqttToken mqttToken, String str) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "saveToken", "307", new Object[]{str, mqttToken.toString()});
            mqttToken.internalTok.setKey(str);
            this.tokens.put(str, mqttToken);
        }
    }

    public void clear() {
        log.fine(CLASS_NAME, CervicalMucusRecord.Appearance.CLEAR, "305", new Object[]{new Integer(this.tokens.size())});
        synchronized (this.tokens) {
            this.tokens.clear();
        }
    }

    public int count() {
        int size;
        synchronized (this.tokens) {
            size = this.tokens.size();
        }
        return size;
    }

    protected void d(MqttToken mqttToken, MqttWireMessage mqttWireMessage) {
        synchronized (this.tokens) {
            try {
                MqttException mqttException = this.closedResponse;
                if (mqttException != null) {
                    throw mqttException;
                }
                String key = mqttWireMessage.getKey();
                log.fine(CLASS_NAME, "saveToken", ErrorCode.UNKNOWN_ERROR_CODE, new Object[]{key, mqttWireMessage});
                c(mqttToken, key);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MqttDeliveryToken[] getOutstandingDelTokens() {
        MqttDeliveryToken[] mqttDeliveryTokenArr;
        synchronized (this.tokens) {
            try {
                log.fine(CLASS_NAME, "getOutstandingDelTokens", "311");
                Vector vector = new Vector();
                Enumeration enumerationElements = this.tokens.elements();
                while (enumerationElements.hasMoreElements()) {
                    MqttToken mqttToken = (MqttToken) enumerationElements.nextElement();
                    if (mqttToken != null && (mqttToken instanceof MqttDeliveryToken) && !mqttToken.internalTok.isNotified()) {
                        vector.addElement(mqttToken);
                    }
                }
                mqttDeliveryTokenArr = (MqttDeliveryToken[]) vector.toArray(new MqttDeliveryToken[vector.size()]);
            } catch (Throwable th) {
                throw th;
            }
        }
        return mqttDeliveryTokenArr;
    }

    public Vector getOutstandingTokens() {
        Vector vector;
        synchronized (this.tokens) {
            try {
                log.fine(CLASS_NAME, "getOutstandingTokens", "312");
                vector = new Vector();
                Enumeration enumerationElements = this.tokens.elements();
                while (enumerationElements.hasMoreElements()) {
                    MqttToken mqttToken = (MqttToken) enumerationElements.nextElement();
                    if (mqttToken != null) {
                        vector.addElement(mqttToken);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vector;
    }

    public MqttToken getToken(MqttWireMessage mqttWireMessage) {
        return (MqttToken) this.tokens.get(mqttWireMessage.getKey());
    }

    public void open() {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "open", "310");
            this.closedResponse = null;
        }
    }

    public MqttToken removeToken(MqttWireMessage mqttWireMessage) {
        if (mqttWireMessage != null) {
            return removeToken(mqttWireMessage.getKey());
        }
        return null;
    }

    public String toString() {
        String string;
        String property = System.getProperty("line.separator", "\n");
        StringBuffer stringBuffer = new StringBuffer();
        synchronized (this.tokens) {
            try {
                Enumeration enumerationElements = this.tokens.elements();
                while (enumerationElements.hasMoreElements()) {
                    stringBuffer.append("{" + ((MqttToken) enumerationElements.nextElement()).internalTok + i.f9804d + property);
                }
                string = stringBuffer.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return string;
    }

    public MqttToken removeToken(String str) {
        log.fine(CLASS_NAME, "removeToken", "306", new Object[]{str});
        if (str != null) {
            return (MqttToken) this.tokens.remove(str);
        }
        return null;
    }

    public MqttToken getToken(String str) {
        return (MqttToken) this.tokens.get(str);
    }
}
