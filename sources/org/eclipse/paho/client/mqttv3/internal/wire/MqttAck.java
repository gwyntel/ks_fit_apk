package org.eclipse.paho.client.mqttv3.internal.wire;

/* loaded from: classes5.dex */
public abstract class MqttAck extends MqttWireMessage {
    public MqttAck(byte b2) {
        super(b2);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        return (byte) 0;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        return super.toString() + " msgId " + this.f26684a;
    }
}
