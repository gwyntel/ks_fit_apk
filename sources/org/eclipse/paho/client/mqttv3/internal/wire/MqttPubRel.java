package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttPubRel extends MqttPersistableWireMessage {
    public MqttPubRel(MqttPubRec mqttPubRec) {
        super((byte) 6);
        setMessageId(mqttPubRec.getMessageId());
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        return (byte) ((this.f26685b ? 8 : 0) | 2);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() {
        return c();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        return super.toString() + " msgId " + this.f26684a;
    }

    public MqttPubRel(byte b2, byte[] bArr) throws IOException {
        super((byte) 6);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        dataInputStream.close();
    }
}
