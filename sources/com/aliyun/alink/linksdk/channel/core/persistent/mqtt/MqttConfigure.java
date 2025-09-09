package com.aliyun.alink.linksdk.channel.core.persistent.mqtt;

import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.MqttPingSender;

/* loaded from: classes2.dex */
public class MqttConfigure {
    public static final String DEFAULT_HOST = "${productKey}.iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";
    public static final String DEFAULT_ITLS_HOST = "${productKey}.itls.cn-shanghai.aliyuncs.com:1883";
    public static final String DEFAULT_REGISTER_TLS_HOST = "${productKey}.iot-as-mqtt.cn-shanghai.aliyuncs.com:443";
    public static final String DEFAULT_ROOTCRT = "root.crt";
    public static final int MQTT_SECURE_MODE_DYNAMIC = -2;
    public static final int MQTT_SECURE_MODE_ITLS = 8;
    public static final int MQTT_SECURE_MODE_TCP = 3;
    public static final int MQTT_SECURE_MODE_TLS = 2;
    public static int SECURE_MODE = 2;
    public static final String SIGN_METHOD = "hmacsha1";
    public static boolean automaticReconnect = true;
    public static boolean cleanSession = true;
    public static String clientId = null;
    public static String deviceName = null;
    public static String deviceSecret = null;
    public static String deviceToken = null;
    public static boolean disableNetworkCheckBeforeSend = false;
    public static String extraMqttClientIdItems = null;
    public static boolean isCheckRootCrt = true;
    public static int itlsLogLevel = 1;
    public static int itlsReadTimeout = 1000;
    public static int itlsWriteTimeout = 2000;
    private static int keepAliveInterval = 65;
    public static int maxInflight = 10;
    public static String mqttClientId = null;
    public static String mqttHost = null;
    public static String mqttPassWord = null;
    public static InputStream mqttRootCrtFile = null;
    public static String mqttUserName = null;
    public static MqttPingSender pingSender = null;
    public static String pingSenderType = "java";
    public static String productKey;
    public static String productSecret;
    public static String registerInstanceId;

    public static int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public static boolean setKeepAliveInterval(int i2) {
        if (i2 == 0) {
            keepAliveInterval = 0;
            return true;
        }
        if (i2 < 30 || i2 > 1200) {
            return false;
        }
        keepAliveInterval = i2;
        return true;
    }
}
