package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttDisconnect extends MqttWireMessage {
    public static final String KEY = "Disc";

    public MqttDisconnect() {
        super((byte) 14);
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
        return KEY;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return false;
    }

    public MqttDisconnect(byte b2, byte[] bArr) throws IOException {
        super((byte) 14);
    }
}
