package org.eclipse.paho.client.mqttv3;

/* loaded from: classes5.dex */
public class MqttMessage {
    private int messageId;
    private byte[] payload;
    private boolean mutable = true;
    private int qos = 1;
    private boolean retained = false;
    private boolean dup = false;

    public MqttMessage() {
        setPayload(new byte[0]);
    }

    public static void validateQos(int i2) {
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException();
        }
    }

    protected void a() {
        if (!this.mutable) {
            throw new IllegalStateException();
        }
    }

    protected void b(boolean z2) {
        this.mutable = z2;
    }

    public void clearPayload() {
        a();
        this.payload = new byte[0];
    }

    public int getId() {
        return this.messageId;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public int getQos() {
        return this.qos;
    }

    public boolean isDuplicate() {
        return this.dup;
    }

    public boolean isRetained() {
        return this.retained;
    }

    protected void setDuplicate(boolean z2) {
        this.dup = z2;
    }

    public void setId(int i2) {
        this.messageId = i2;
    }

    public void setPayload(byte[] bArr) {
        a();
        bArr.getClass();
        this.payload = bArr;
    }

    public void setQos(int i2) {
        a();
        validateQos(i2);
        this.qos = i2;
    }

    public void setRetained(boolean z2) {
        a();
        this.retained = z2;
    }

    public String toString() {
        return new String(this.payload);
    }

    public MqttMessage(byte[] bArr) {
        setPayload(bArr);
    }
}
