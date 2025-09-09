package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes5.dex */
public class MqttConnect extends MqttWireMessage {
    public static final String KEY = "Con";
    private int MqttVersion;
    private boolean cleanSession;
    private String clientId;
    private int keepAliveInterval;
    private char[] password;
    private String userName;
    private String willDestination;
    private MqttMessage willMessage;

    public MqttConnect(byte b2, byte[] bArr) throws MqttException, IOException {
        super((byte) 1);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        a(dataInputStream);
        dataInputStream.readByte();
        dataInputStream.readByte();
        this.keepAliveInterval = dataInputStream.readUnsignedShort();
        this.clientId = a(dataInputStream);
        dataInputStream.close();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte e() {
        return (byte) 0;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] f() throws MqttException, IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            int i2 = this.MqttVersion;
            if (i2 == 3) {
                d(dataOutputStream, "MQIsdp");
            } else if (i2 == 4) {
                d(dataOutputStream, "MQTT");
            }
            dataOutputStream.write(this.MqttVersion);
            byte qos = this.cleanSession ? (byte) 2 : (byte) 0;
            MqttMessage mqttMessage = this.willMessage;
            if (mqttMessage != null) {
                qos = (byte) (((byte) (qos | 4)) | (mqttMessage.getQos() << 3));
                if (this.willMessage.isRetained()) {
                    qos = (byte) (qos | 32);
                }
            }
            if (this.userName != null) {
                qos = (byte) (qos | 128);
                if (this.password != null) {
                    qos = (byte) (qos | 64);
                }
            }
            dataOutputStream.write(qos);
            dataOutputStream.writeShort(this.keepAliveInterval);
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String getKey() {
        return "Con";
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public byte[] getPayload() throws MqttException, IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            d(dataOutputStream, this.clientId);
            if (this.willMessage != null) {
                d(dataOutputStream, this.willDestination);
                dataOutputStream.writeShort(this.willMessage.getPayload().length);
                dataOutputStream.write(this.willMessage.getPayload());
            }
            String str = this.userName;
            if (str != null) {
                d(dataOutputStream, str);
                char[] cArr = this.password;
                if (cArr != null) {
                    d(dataOutputStream, new String(cArr));
                }
            }
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    public boolean isCleanSession() {
        return this.cleanSession;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return false;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String toString() {
        return super.toString() + " clientId " + this.clientId + " keepAliveInterval " + this.keepAliveInterval;
    }

    public MqttConnect(String str, int i2, boolean z2, int i3, String str2, char[] cArr, MqttMessage mqttMessage, String str3) {
        super((byte) 1);
        this.clientId = str;
        this.cleanSession = z2;
        this.keepAliveInterval = i3;
        this.userName = str2;
        this.password = cArr;
        this.willMessage = mqttMessage;
        this.willDestination = str3;
        this.MqttVersion = i2;
    }
}
