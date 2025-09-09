package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes5.dex */
public class MqttReceivedMessage extends MqttMessage {
    public int getMessageId() {
        return super.getId();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttMessage
    public void setDuplicate(boolean z2) {
        super.setDuplicate(z2);
    }

    public void setMessageId(int i2) {
        super.setId(i2);
    }
}
