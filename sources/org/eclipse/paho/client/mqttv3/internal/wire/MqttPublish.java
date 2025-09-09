package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes5.dex */
public class MqttPublish extends MqttPersistableWireMessage {
    private byte[] encodedPayload;
    private MqttMessage message;
    private String topicName;

    public MqttPublish(String str, MqttMessage mqttMessage) {
        super((byte) 3);
        this.encodedPayload = null;
        this.topicName = str;
        this.message = mqttMessage;
    }

    protected static byte[] h(MqttMessage mqttMessage) {
        return mqttMessage.getPayload();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        byte qos = (byte) (this.message.getQos() << 1);
        if (this.message.isRetained()) {
            qos = (byte) (qos | 1);
        }
        return (this.message.isDuplicate() || this.f26685b) ? (byte) (qos | 8) : qos;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() throws MqttException, IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            d(dataOutputStream, this.topicName);
            if (this.message.getQos() > 0) {
                dataOutputStream.writeShort(this.f26684a);
            }
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public byte[] getPayload() throws MqttException {
        if (this.encodedPayload == null) {
            this.encodedPayload = h(this.message);
        }
        return this.encodedPayload;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttPersistableWireMessage, org.eclipse.paho.client.mqttv3.MqttPersistable
    public int getPayloadLength() {
        try {
            return getPayload().length;
        } catch (MqttException unused) {
            return 0;
        }
    }

    public String getTopicName() {
        return this.topicName;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return true;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public void setMessageId(int i2) {
        super.setMessageId(i2);
        MqttMessage mqttMessage = this.message;
        if (mqttMessage instanceof MqttReceivedMessage) {
            ((MqttReceivedMessage) mqttMessage).setMessageId(i2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        byte[] payload = this.message.getPayload();
        int iMin = Math.min(payload.length, 20);
        for (int i2 = 0; i2 < iMin; i2++) {
            String hexString = Integer.toHexString(payload[i2]);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            stringBuffer.append(hexString);
        }
        try {
            str = new String(payload, 0, iMin, "UTF-8");
        } catch (Exception unused) {
            str = "?";
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(super.toString());
        stringBuffer2.append(" qos:");
        stringBuffer2.append(this.message.getQos());
        if (this.message.getQos() > 0) {
            stringBuffer2.append(" msgId:");
            stringBuffer2.append(this.f26684a);
        }
        stringBuffer2.append(" retained:");
        stringBuffer2.append(this.message.isRetained());
        stringBuffer2.append(" dup:");
        stringBuffer2.append(this.f26685b);
        stringBuffer2.append(" topic:\"");
        stringBuffer2.append(this.topicName);
        stringBuffer2.append("\"");
        stringBuffer2.append(" payload:[hex:");
        stringBuffer2.append(stringBuffer);
        stringBuffer2.append(" utf8:\"");
        stringBuffer2.append(str);
        stringBuffer2.append("\"");
        stringBuffer2.append(" length:");
        stringBuffer2.append(payload.length);
        stringBuffer2.append("]");
        return stringBuffer2.toString();
    }

    public MqttPublish(byte b2, byte[] bArr) throws MqttException, IOException {
        super((byte) 3);
        this.encodedPayload = null;
        MqttReceivedMessage mqttReceivedMessage = new MqttReceivedMessage();
        this.message = mqttReceivedMessage;
        mqttReceivedMessage.setQos(3 & (b2 >> 1));
        if ((b2 & 1) == 1) {
            this.message.setRetained(true);
        }
        if ((b2 & 8) == 8) {
            ((MqttReceivedMessage) this.message).setDuplicate(true);
        }
        CountingInputStream countingInputStream = new CountingInputStream(new ByteArrayInputStream(bArr));
        DataInputStream dataInputStream = new DataInputStream(countingInputStream);
        this.topicName = a(dataInputStream);
        if (this.message.getQos() > 0) {
            this.f26684a = dataInputStream.readUnsignedShort();
        }
        byte[] bArr2 = new byte[bArr.length - countingInputStream.getCounter()];
        dataInputStream.readFully(bArr2);
        dataInputStream.close();
        this.message.setPayload(bArr2);
    }
}
