package org.eclipse.paho.client.mqttv3;

/* loaded from: classes5.dex */
public class MqttDeliveryToken extends MqttToken implements IMqttDeliveryToken {
    public MqttDeliveryToken() {
    }

    protected void a(MqttMessage mqttMessage) {
        this.internalTok.setMessage(mqttMessage);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
    public MqttMessage getMessage() throws MqttException {
        return this.internalTok.getMessage();
    }

    public MqttDeliveryToken(String str) {
        super(str);
    }
}
