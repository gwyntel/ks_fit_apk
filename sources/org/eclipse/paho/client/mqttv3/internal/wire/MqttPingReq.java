package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttPingReq extends MqttWireMessage {
    public static final String KEY = "Ping";

    public MqttPingReq() {
        super((byte) 12);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        return (byte) 0;
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

    public MqttPingReq(byte b2, byte[] bArr) throws IOException {
        super((byte) 12);
    }
}
