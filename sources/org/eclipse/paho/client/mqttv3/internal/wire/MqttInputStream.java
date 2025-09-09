package org.eclipse.paho.client.mqttv3.internal.wire;

import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class MqttInputStream extends InputStream {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, MqttInputStream.class.getName());
    private ClientState clientState;
    private DataInputStream in;
    private byte[] packet;
    private long packetLen;
    private ByteArrayOutputStream bais = new ByteArrayOutputStream();
    private long remLen = -1;

    public MqttInputStream(ClientState clientState, InputStream inputStream) {
        this.clientState = clientState;
        this.in = new DataInputStream(inputStream);
    }

    private void readFully() throws IOException {
        int size = this.bais.size();
        long j2 = this.packetLen;
        int i2 = size + ((int) j2);
        int i3 = (int) (this.remLen - j2);
        if (i3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        while (i4 < i3) {
            try {
                int i5 = this.in.read(this.packet, i2 + i4, i3 - i4);
                this.clientState.notifyReceivedBytes(i5);
                if (i5 < 0) {
                    throw new EOFException();
                }
                i4 += i5;
            } catch (SocketTimeoutException e2) {
                this.packetLen += i4;
                throw e2;
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.in.read();
    }

    public MqttWireMessage readMqttWireMessage() throws MqttException, IOException {
        try {
            if (this.remLen < 0) {
                this.bais.reset();
                byte b2 = this.in.readByte();
                this.clientState.notifyReceivedBytes(1);
                byte b3 = (byte) ((b2 >>> 4) & 15);
                if (b3 < 1 || b3 > 14) {
                    throw ExceptionHelper.createMqttException(32108);
                }
                this.remLen = MqttWireMessage.g(this.in).getValue();
                this.bais.write(b2);
                this.bais.write(MqttWireMessage.b(this.remLen));
                this.packet = new byte[(int) (this.bais.size() + this.remLen)];
                this.packetLen = 0L;
            }
            if (this.remLen < 0) {
                return null;
            }
            readFully();
            this.remLen = -1L;
            byte[] byteArray = this.bais.toByteArray();
            System.arraycopy(byteArray, 0, this.packet, 0, byteArray.length);
            MqttWireMessage mqttWireMessageCreateWireMessage = MqttWireMessage.createWireMessage(this.packet);
            log.fine(CLASS_NAME, "readMqttWireMessage", SyncBoneInvoker.ERROR_SUB_CODE_METHOD_NOT_IMPLEMENTED, new Object[]{mqttWireMessageCreateWireMessage});
            return mqttWireMessageCreateWireMessage;
        } catch (SocketTimeoutException unused) {
            return null;
        }
    }
}
