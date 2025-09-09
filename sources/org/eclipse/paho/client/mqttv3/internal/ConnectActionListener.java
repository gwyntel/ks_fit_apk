package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;

/* loaded from: classes5.dex */
public class ConnectActionListener implements IMqttActionListener {
    private MqttAsyncClient client;
    private ClientComms comms;
    private MqttCallbackExtended mqttCallbackExtended;
    private MqttConnectOptions options;
    private int originalMqttVersion;
    private MqttClientPersistence persistence;
    private boolean reconnect;
    private IMqttActionListener userCallback;
    private Object userContext;
    private MqttToken userToken;

    public ConnectActionListener(MqttAsyncClient mqttAsyncClient, MqttClientPersistence mqttClientPersistence, ClientComms clientComms, MqttConnectOptions mqttConnectOptions, MqttToken mqttToken, Object obj, IMqttActionListener iMqttActionListener, boolean z2) {
        this.persistence = mqttClientPersistence;
        this.client = mqttAsyncClient;
        this.comms = clientComms;
        this.options = mqttConnectOptions;
        this.userToken = mqttToken;
        this.userContext = obj;
        this.userCallback = iMqttActionListener;
        this.originalMqttVersion = mqttConnectOptions.getMqttVersion();
        this.reconnect = z2;
    }

    public void connect() throws MqttPersistenceException {
        MqttToken mqttToken = new MqttToken(this.client.getClientId());
        mqttToken.setActionCallback(this);
        mqttToken.setUserContext(this);
        this.persistence.open(this.client.getClientId(), this.client.getServerURI());
        if (this.options.isCleanSession()) {
            this.persistence.clear();
        }
        if (this.options.getMqttVersion() == 0) {
            this.options.setMqttVersion(4);
        }
        try {
            this.comms.connect(this.options, mqttToken);
        } catch (MqttException e2) {
            onFailure(mqttToken, e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken iMqttToken, Throwable th) throws IllegalArgumentException {
        int length = this.comms.getNetworkModules().length;
        int networkModuleIndex = this.comms.getNetworkModuleIndex() + 1;
        if (networkModuleIndex >= length && (this.originalMqttVersion != 0 || this.options.getMqttVersion() != 4)) {
            if (this.originalMqttVersion == 0) {
                this.options.setMqttVersion(0);
            }
            this.userToken.internalTok.c(null, th instanceof MqttException ? (MqttException) th : new MqttException(th));
            this.userToken.internalTok.d();
            this.userToken.internalTok.f(this.client);
            if (this.userCallback != null) {
                this.userToken.setUserContext(this.userContext);
                this.userCallback.onFailure(this.userToken, th);
                return;
            }
            return;
        }
        if (this.originalMqttVersion != 0) {
            this.comms.setNetworkModuleIndex(networkModuleIndex);
        } else if (this.options.getMqttVersion() == 4) {
            this.options.setMqttVersion(3);
        } else {
            this.options.setMqttVersion(4);
            this.comms.setNetworkModuleIndex(networkModuleIndex);
        }
        try {
            connect();
        } catch (MqttPersistenceException e2) {
            onFailure(iMqttToken, e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken iMqttToken) throws IllegalArgumentException {
        if (this.originalMqttVersion == 0) {
            this.options.setMqttVersion(0);
        }
        this.userToken.internalTok.c(iMqttToken.getResponse(), null);
        this.userToken.internalTok.d();
        this.userToken.internalTok.f(this.client);
        this.comms.notifyConnect();
        if (this.userCallback != null) {
            this.userToken.setUserContext(this.userContext);
            this.userCallback.onSuccess(this.userToken);
        }
        if (this.mqttCallbackExtended != null) {
            this.mqttCallbackExtended.connectComplete(this.reconnect, this.comms.getNetworkModules()[this.comms.getNetworkModuleIndex()].getServerURI());
        }
    }

    public void setMqttCallbackExtended(MqttCallbackExtended mqttCallbackExtended) {
        this.mqttCallbackExtended = mqttCallbackExtended;
    }
}
