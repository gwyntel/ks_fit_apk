package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* loaded from: classes5.dex */
public class MqttUnsubscribe extends MqttWireMessage {
    private int count;
    private String[] names;

    public MqttUnsubscribe(String[] strArr) {
        super((byte) 10);
        this.names = strArr;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        return (byte) ((this.f26685b ? 8 : 0) | 2);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() throws MqttException, IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeShort(this.f26684a);
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public byte[] getPayload() throws MqttException, IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            int i2 = 0;
            while (true) {
                String[] strArr = this.names;
                if (i2 >= strArr.length) {
                    dataOutputStream.flush();
                    return byteArrayOutputStream.toByteArray();
                }
                d(dataOutputStream, strArr[i2]);
                i2++;
            }
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isRetryable() {
        return true;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(" names:[");
        for (int i2 = 0; i2 < this.count; i2++) {
            if (i2 > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append("\"" + this.names[i2] + "\"");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public MqttUnsubscribe(byte b2, byte[] bArr) throws IOException {
        super((byte) 10);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        boolean z2 = false;
        this.count = 0;
        this.names = new String[10];
        while (!z2) {
            try {
                this.names[this.count] = a(dataInputStream);
            } catch (Exception unused) {
                z2 = true;
            }
        }
        dataInputStream.close();
    }
}
