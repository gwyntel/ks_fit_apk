package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class WebSocketSecureNetworkModule extends SSLNetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, WebSocketSecureNetworkModule.class.getName());
    private String host;
    private ByteArrayOutputStream outputStream;
    private PipedInputStream pipedInputStream;
    private int port;
    private String uri;
    private WebSocketReceiver webSocketReceiver;

    public WebSocketSecureNetworkModule(SSLSocketFactory sSLSocketFactory, String str, String str2, int i2, String str3) {
        super(sSLSocketFactory, str2, i2, str3);
        this.outputStream = new ExtendedByteArrayOutputStream(this);
        this.uri = str;
        this.host = str2;
        this.port = i2;
        this.pipedInputStream = new PipedInputStream();
        log.setResourceName(str3);
    }

    InputStream a() {
        return super.getInputStream();
    }

    OutputStream b() {
        return super.getOutputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        return this.pipedInputStream;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule, org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "wss://" + this.host + ":" + this.port;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule, org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        super.start();
        new WebSocketHandshake(super.getInputStream(), super.getOutputStream(), this.uri, this.host, this.port).execute();
        WebSocketReceiver webSocketReceiver = new WebSocketReceiver(a(), this.pipedInputStream);
        this.webSocketReceiver = webSocketReceiver;
        webSocketReceiver.start("WssSocketReceiver");
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() throws InterruptedException, IOException {
        b().write(new WebSocketFrame((byte) 8, true, "1000".getBytes()).encodeFrame());
        b().flush();
        WebSocketReceiver webSocketReceiver = this.webSocketReceiver;
        if (webSocketReceiver != null) {
            webSocketReceiver.stop();
        }
        super.stop();
    }
}
