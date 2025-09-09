package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class SSLNetworkModule extends TCPNetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, SSLNetworkModule.class.getName());
    private String[] enabledCiphers;
    private int handshakeTimeoutSecs;
    private String host;
    private HostnameVerifier hostnameVerifier;
    private int port;

    public SSLNetworkModule(SSLSocketFactory sSLSocketFactory, String str, int i2, String str2) {
        super(sSLSocketFactory, str, i2, str2);
        this.host = str;
        this.port = i2;
        log.setResourceName(str2);
    }

    public String[] getEnabledCiphers() {
        return this.enabledCiphers;
    }

    public HostnameVerifier getSSLHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return "ssl://" + this.host + ":" + this.port;
    }

    public void setEnabledCiphers(String[] strArr) {
        this.enabledCiphers = strArr;
        if (this.f26674a == null || strArr == null) {
            return;
        }
        if (log.isLoggable(5)) {
            String str = "";
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (i2 > 0) {
                    str = str + ",";
                }
                str = str + strArr[i2];
            }
            log.fine(CLASS_NAME, "setEnabledCiphers", "260", new Object[]{str});
        }
        ((SSLSocket) this.f26674a).setEnabledCipherSuites(strArr);
    }

    public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void setSSLhandshakeTimeout(int i2) {
        super.setConnectTimeout(i2);
        this.handshakeTimeoutSecs = i2;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        super.start();
        setEnabledCiphers(this.enabledCiphers);
        int soTimeout = this.f26674a.getSoTimeout();
        this.f26674a.setSoTimeout(this.handshakeTimeoutSecs * 1000);
        ((SSLSocket) this.f26674a).startHandshake();
        if (this.hostnameVerifier != null) {
            this.hostnameVerifier.verify(this.host, ((SSLSocket) this.f26674a).getSession());
        }
        this.f26674a.setSoTimeout(soTimeout);
    }
}
