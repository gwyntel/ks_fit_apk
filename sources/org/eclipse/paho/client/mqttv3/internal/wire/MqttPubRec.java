package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttPubRec extends MqttAck {
    public MqttPubRec(byte b2, byte[] bArr) throws IOException {
        super((byte) 5);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        dataInputStream.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() {
        return c();
    }

    public MqttPubRec(MqttPublish mqttPublish) {
        super((byte) 5);
        this.f26684a = mqttPublish.getMessageId();
    }
}
