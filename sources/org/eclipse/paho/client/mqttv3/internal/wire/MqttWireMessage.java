package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;

/* loaded from: classes5.dex */
public abstract class MqttWireMessage {
    public static final byte MESSAGE_TYPE_CONNACK = 2;
    public static final byte MESSAGE_TYPE_CONNECT = 1;
    public static final byte MESSAGE_TYPE_DISCONNECT = 14;
    public static final byte MESSAGE_TYPE_PINGREQ = 12;
    public static final byte MESSAGE_TYPE_PINGRESP = 13;
    public static final byte MESSAGE_TYPE_PUBACK = 4;
    public static final byte MESSAGE_TYPE_PUBCOMP = 7;
    public static final byte MESSAGE_TYPE_PUBLISH = 3;
    public static final byte MESSAGE_TYPE_PUBREC = 5;
    public static final byte MESSAGE_TYPE_PUBREL = 6;
    public static final byte MESSAGE_TYPE_SUBACK = 9;
    public static final byte MESSAGE_TYPE_SUBSCRIBE = 8;
    public static final byte MESSAGE_TYPE_UNSUBACK = 11;
    public static final byte MESSAGE_TYPE_UNSUBSCRIBE = 10;
    private static final String[] PACKET_NAMES = {"reserved", "CONNECT", "CONNACK", "PUBLISH", "PUBACK", "PUBREC", "PUBREL", "PUBCOMP", "SUBSCRIBE", "SUBACK", "UNSUBSCRIBE", "UNSUBACK", "PINGREQ", "PINGRESP", "DISCONNECT"};
    private byte type;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f26685b = false;

    /* renamed from: a, reason: collision with root package name */
    protected int f26684a = 0;

    public MqttWireMessage(byte b2) {
        this.type = b2;
    }

    protected static byte[] b(long j2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        do {
            byte b2 = (byte) (j2 % 128);
            j2 /= 128;
            if (j2 > 0) {
                b2 = (byte) (b2 | 128);
            }
            byteArrayOutputStream.write(b2);
            i2++;
            if (j2 <= 0) {
                break;
            }
        } while (i2 < 4);
        return byteArrayOutputStream.toByteArray();
    }

    public static MqttWireMessage createWireMessage(MqttPersistable mqttPersistable) throws MqttException {
        byte[] payloadBytes = mqttPersistable.getPayloadBytes();
        if (payloadBytes == null) {
            payloadBytes = new byte[0];
        }
        return createWireMessage(new MultiByteArrayInputStream(mqttPersistable.getHeaderBytes(), mqttPersistable.getHeaderOffset(), mqttPersistable.getHeaderLength(), payloadBytes, mqttPersistable.getPayloadOffset(), mqttPersistable.getPayloadLength()));
    }

    protected static MultiByteInteger g(DataInputStream dataInputStream) throws IOException {
        long j2 = 0;
        int i2 = 0;
        int i3 = 1;
        do {
            i2++;
            j2 += (r5 & Byte.MAX_VALUE) * i3;
            i3 *= 128;
        } while ((dataInputStream.readByte() & 128) != 0);
        return new MultiByteInteger(j2, i2);
    }

    protected String a(DataInputStream dataInputStream) throws MqttException, IOException {
        try {
            byte[] bArr = new byte[dataInputStream.readUnsignedShort()];
            dataInputStream.readFully(bArr);
            return new String(bArr, "UTF-8");
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    protected byte[] c() throws MqttException, IOException {
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

    protected void d(DataOutputStream dataOutputStream, String str) throws MqttException, IOException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte length = (byte) ((bytes.length >>> 8) & 255);
            byte length2 = (byte) (bytes.length & 255);
            dataOutputStream.write(length);
            dataOutputStream.write(length2);
            dataOutputStream.write(bytes);
        } catch (UnsupportedEncodingException e2) {
            throw new MqttException(e2);
        } catch (IOException e3) {
            throw new MqttException(e3);
        }
    }

    protected abstract byte e();

    protected abstract byte[] f();

    public byte[] getHeader() throws MqttException, IOException {
        try {
            int type = ((getType() & 15) << 4) ^ (e() & 15);
            byte[] bArrF = f();
            int length = bArrF.length + getPayload().length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeByte(type);
            dataOutputStream.write(b(length));
            dataOutputStream.write(bArrF);
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }

    public String getKey() {
        return new Integer(getMessageId()).toString();
    }

    public int getMessageId() {
        return this.f26684a;
    }

    public byte[] getPayload() throws MqttException {
        return new byte[0];
    }

    public byte getType() {
        return this.type;
    }

    public boolean isMessageIdRequired() {
        return true;
    }

    public boolean isRetryable() {
        return false;
    }

    public void setDuplicate(boolean z2) {
        this.f26685b = z2;
    }

    public void setMessageId(int i2) {
        this.f26684a = i2;
    }

    public String toString() {
        return PACKET_NAMES[this.type];
    }

    public static MqttWireMessage createWireMessage(byte[] bArr) throws MqttException {
        return createWireMessage(new ByteArrayInputStream(bArr));
    }

    private static MqttWireMessage createWireMessage(InputStream inputStream) throws MqttException, IOException {
        try {
            DataInputStream dataInputStream = new DataInputStream(new CountingInputStream(inputStream));
            int unsignedByte = dataInputStream.readUnsignedByte();
            byte b2 = (byte) (unsignedByte >> 4);
            byte b3 = (byte) (unsignedByte & 15);
            long counter = (r0.getCounter() + g(dataInputStream).getValue()) - r0.getCounter();
            byte[] bArr = new byte[0];
            if (counter > 0) {
                int i2 = (int) counter;
                byte[] bArr2 = new byte[i2];
                dataInputStream.readFully(bArr2, 0, i2);
                bArr = bArr2;
            }
            if (b2 == 1) {
                return new MqttConnect(b3, bArr);
            }
            if (b2 == 3) {
                return new MqttPublish(b3, bArr);
            }
            if (b2 == 4) {
                return new MqttPubAck(b3, bArr);
            }
            if (b2 == 7) {
                return new MqttPubComp(b3, bArr);
            }
            if (b2 == 2) {
                return new MqttConnack(b3, bArr);
            }
            if (b2 == 12) {
                return new MqttPingReq(b3, bArr);
            }
            if (b2 == 13) {
                return new MqttPingResp(b3, bArr);
            }
            if (b2 == 8) {
                return new MqttSubscribe(b3, bArr);
            }
            if (b2 == 9) {
                return new MqttSuback(b3, bArr);
            }
            if (b2 == 10) {
                return new MqttUnsubscribe(b3, bArr);
            }
            if (b2 == 11) {
                return new MqttUnsubAck(b3, bArr);
            }
            if (b2 == 6) {
                return new MqttPubRel(b3, bArr);
            }
            if (b2 == 5) {
                return new MqttPubRec(b3, bArr);
            }
            if (b2 == 14) {
                return new MqttDisconnect(b3, bArr);
            }
            throw ExceptionHelper.createMqttException(6);
        } catch (IOException e2) {
            throw new MqttException(e2);
        }
    }
}
