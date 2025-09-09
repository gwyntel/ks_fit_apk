package org.eclipse.paho.client.mqttv3.internal.wire;

/* loaded from: classes5.dex */
public class MqttPingResp extends MqttAck {
    public static final String KEY = "Ping";

    public MqttPingResp(byte b2, byte[] bArr) {
        super((byte) 13);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() {
        return new byte[0];
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String getKey() {
        return "Ping";
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return false;
    }
}
