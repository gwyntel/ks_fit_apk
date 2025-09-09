package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttPubComp extends MqttAck {
    public MqttPubComp(byte b2, byte[] bArr) throws IOException {
        super((byte) 7);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        dataInputStream.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() {
        return c();
    }

    public MqttPubComp(MqttPublish mqttPublish) {
        super((byte) 7);
        this.f26684a = mqttPublish.getMessageId();
    }

    public MqttPubComp(int i2) {
        super((byte) 7);
        this.f26684a = i2;
    }
}
