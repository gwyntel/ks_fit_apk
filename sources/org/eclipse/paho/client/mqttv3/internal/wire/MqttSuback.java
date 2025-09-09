package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MqttSuback extends MqttAck {
    private int[] grantedQos;

    public MqttSuback(byte b2, byte[] bArr) throws IOException {
        super((byte) 9);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        this.grantedQos = new int[bArr.length - 2];
        int i2 = 0;
        for (int i3 = dataInputStream.read(); i3 != -1; i3 = dataInputStream.read()) {
            this.grantedQos[i2] = i3;
            i2++;
        }
        dataInputStream.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() {
        return new byte[0];
    }

    public int[] getGrantedQos() {
        return this.grantedQos;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttAck, org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(" granted Qos");
        for (int i2 = 0; i2 < this.grantedQos.length; i2++) {
            stringBuffer.append(" ");
            stringBuffer.append(this.grantedQos[i2]);
        }
        return stringBuffer.toString();
    }
}
