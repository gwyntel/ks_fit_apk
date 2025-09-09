package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class TCPNetworkModule implements NetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, TCPNetworkModule.class.getName());

    /* renamed from: a, reason: collision with root package name */
    protected Socket f26674a;
    private int conTimeout;
    private SocketFactory factory;
    private String host;
    private int port;

    public TCPNetworkModule(SocketFactory socketFactory, String str, int i2, String str2) {
        log.setResourceName(str2);
        this.factory = socketFactory;
        this.host = str;
        this.port = i2;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        return this.f26674a.getInputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        return this.f26674a.getOutputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "tcp://" + this.host + ":" + this.port;
    }

    public void setConnectTimeout(int i2) {
        this.conTimeout = i2;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        try {
            log.fine(CLASS_NAME, "start", "252", new Object[]{this.host, new Integer(this.port), new Long(this.conTimeout * 1000)});
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.host, this.port);
            SocketFactory socketFactory = this.factory;
            if (socketFactory instanceof SSLSocketFactory) {
                Socket socket = new Socket();
                socket.connect(inetSocketAddress, this.conTimeout * 1000);
                this.f26674a = ((SSLSocketFactory) this.factory).createSocket(socket, this.host, this.port, true);
            } else {
                Socket socketCreateSocket = socketFactory.createSocket();
                this.f26674a = socketCreateSocket;
                socketCreateSocket.connect(inetSocketAddress, this.conTimeout * 1000);
            }
        } catch (ConnectException e2) {
            log.fine(CLASS_NAME, "start", "250", null, e2);
            throw new MqttException(32103, e2);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() throws IOException {
        Socket socket = this.f26674a;
        if (socket != null) {
            socket.close();
        }
    }
}
