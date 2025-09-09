package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes5.dex */
public class MqttSubscribe extends MqttWireMessage {
    private int count;
    private String[] names;
    private int[] qos;

    public MqttSubscribe(byte b2, byte[] bArr) throws IOException {
        super((byte) 8);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.f26684a = dataInputStream.readUnsignedShort();
        boolean z2 = false;
        this.count = 0;
        this.names = new String[10];
        this.qos = new int[10];
        while (!z2) {
            try {
                this.names[this.count] = a(dataInputStream);
                int[] iArr = this.qos;
                int i2 = this.count;
                this.count = i2 + 1;
                iArr[i2] = dataInputStream.readByte();
            } catch (Exception unused) {
                z2 = true;
            }
        }
        dataInputStream.close();
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
                dataOutputStream.writeByte(this.qos[i2]);
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
            stringBuffer.append("\"");
            stringBuffer.append(this.names[i2]);
            stringBuffer.append("\"");
        }
        stringBuffer.append("] qos:[");
        for (int i3 = 0; i3 < this.count; i3++) {
            if (i3 > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.qos[i3]);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public MqttSubscribe(String[] strArr, int[] iArr) {
        super((byte) 8);
        this.names = strArr;
        this.qos = iArr;
        if (strArr.length == iArr.length) {
            this.count = strArr.length;
            for (int i2 : iArr) {
                MqttMessage.validateQos(i2);
            }
            return;
        }
        throw new IllegalArgumentException();
    }
}
