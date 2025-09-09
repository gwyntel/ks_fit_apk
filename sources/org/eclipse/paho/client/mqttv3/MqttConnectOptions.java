package org.eclipse.paho.client.mqttv3;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.paho.client.mqttv3.util.Debug;

/* loaded from: classes5.dex */
public class MqttConnectOptions {
    public static final boolean CLEAN_SESSION_DEFAULT = true;
    public static final int CONNECTION_TIMEOUT_DEFAULT = 30;
    public static final int KEEP_ALIVE_INTERVAL_DEFAULT = 60;
    public static final int MAX_INFLIGHT_DEFAULT = 10;
    public static final int MQTT_VERSION_3_1 = 3;
    public static final int MQTT_VERSION_3_1_1 = 4;
    public static final int MQTT_VERSION_DEFAULT = 0;
    private char[] password;
    private SocketFactory socketFactory;
    private String userName;
    private int keepAliveInterval = 60;
    private int maxInflight = 10;
    private String willDestination = null;
    private MqttMessage willMessage = null;
    private Properties sslClientProps = null;
    private HostnameVerifier sslHostnameVerifier = null;
    private boolean cleanSession = true;
    private int connectionTimeout = 30;
    private String[] serverURIs = null;
    private int MqttVersion = 0;
    private boolean automaticReconnect = false;

    public static int validateURI(String str) {
        try {
            URI uri = new URI(str);
            if ("ws".equals(uri.getScheme())) {
                return 3;
            }
            if ("wss".equals(uri.getScheme())) {
                return 4;
            }
            if (uri.getPath() != null && !uri.getPath().isEmpty()) {
                throw new IllegalArgumentException(str);
            }
            if ("tcp".equals(uri.getScheme())) {
                return 0;
            }
            if ("ssl".equals(uri.getScheme())) {
                return 1;
            }
            if ("local".equals(uri.getScheme())) {
                return 2;
            }
            throw new IllegalArgumentException(str);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException(str);
        }
    }

    private void validateWill(String str, Object obj) throws IllegalArgumentException {
        if (str == null || obj == null) {
            throw new IllegalArgumentException();
        }
        MqttTopic.validate(str, false);
    }

    protected void a(String str, MqttMessage mqttMessage, int i2, boolean z2) {
        this.willDestination = str;
        this.willMessage = mqttMessage;
        mqttMessage.setQos(i2);
        this.willMessage.setRetained(z2);
        this.willMessage.b(false);
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("MqttVersion", new Integer(getMqttVersion()));
        properties.put("CleanSession", Boolean.valueOf(isCleanSession()));
        properties.put("ConTimeout", new Integer(getConnectionTimeout()));
        properties.put("KeepAliveInterval", new Integer(getKeepAliveInterval()));
        properties.put("UserName", getUserName() == null ? TmpConstant.GROUP_ROLE_UNKNOWN : getUserName());
        properties.put("WillDestination", getWillDestination() == null ? TmpConstant.GROUP_ROLE_UNKNOWN : getWillDestination());
        if (getSocketFactory() == null) {
            properties.put("SocketFactory", TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            properties.put("SocketFactory", getSocketFactory());
        }
        if (getSSLProperties() == null) {
            properties.put("SSLProperties", TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            properties.put("SSLProperties", getSSLProperties());
        }
        return properties;
    }

    public int getKeepAliveInterval() {
        return this.keepAliveInterval;
    }

    public int getMaxInflight() {
        return this.maxInflight;
    }

    public int getMqttVersion() {
        return this.MqttVersion;
    }

    public char[] getPassword() {
        return this.password;
    }

    public HostnameVerifier getSSLHostnameVerifier() {
        return this.sslHostnameVerifier;
    }

    public Properties getSSLProperties() {
        return this.sslClientProps;
    }

    public String[] getServerURIs() {
        return this.serverURIs;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getWillDestination() {
        return this.willDestination;
    }

    public MqttMessage getWillMessage() {
        return this.willMessage;
    }

    public boolean isAutomaticReconnect() {
        return this.automaticReconnect;
    }

    public boolean isCleanSession() {
        return this.cleanSession;
    }

    public void setAutomaticReconnect(boolean z2) {
        this.automaticReconnect = z2;
    }

    public void setCleanSession(boolean z2) {
        this.cleanSession = z2;
    }

    public void setConnectionTimeout(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.connectionTimeout = i2;
    }

    public void setKeepAliveInterval(int i2) throws IllegalArgumentException {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.keepAliveInterval = i2;
    }

    public void setMaxInflight(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.maxInflight = i2;
    }

    public void setMqttVersion(int i2) throws IllegalArgumentException {
        if (i2 != 0 && i2 != 3 && i2 != 4) {
            throw new IllegalArgumentException();
        }
        this.MqttVersion = i2;
    }

    public void setPassword(char[] cArr) {
        this.password = cArr;
    }

    public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.sslHostnameVerifier = hostnameVerifier;
    }

    public void setSSLProperties(Properties properties) {
        this.sslClientProps = properties;
    }

    public void setServerURIs(String[] strArr) {
        for (String str : strArr) {
            validateURI(str);
        }
        this.serverURIs = strArr;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public void setUserName(String str) {
        if (str != null && str.trim().equals("")) {
            throw new IllegalArgumentException();
        }
        this.userName = str;
    }

    public void setWill(MqttTopic mqttTopic, byte[] bArr, int i2, boolean z2) throws IllegalArgumentException {
        String name = mqttTopic.getName();
        validateWill(name, bArr);
        a(name, new MqttMessage(bArr), i2, z2);
    }

    public String toString() {
        return Debug.dumpProperties(getDebug(), "Connection options");
    }

    public void setWill(String str, byte[] bArr, int i2, boolean z2) throws IllegalArgumentException {
        validateWill(str, bArr);
        a(str, new MqttMessage(bArr), i2, z2);
    }
}
