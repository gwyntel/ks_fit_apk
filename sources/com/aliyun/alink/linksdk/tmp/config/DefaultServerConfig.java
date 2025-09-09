package com.aliyun.alink.linksdk.tmp.config;

import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import java.util.Map;

/* loaded from: classes2.dex */
public class DefaultServerConfig extends DeviceConfig {
    public String mBkList;
    private ConnectType mConnectType = ConnectType.COAP;
    public String mIotDeviceName;
    public String mIotProductKey;
    public String mIotSecret;
    private String mPrefix;
    private Map<String, ValueWrapper> mPropertValues;
    private String mSecret;

    public enum ConnectType {
        COAP,
        MQTT,
        COAP_AND_MQTT;

        public static boolean isConnectContainCoap(ConnectType connectType) {
            return COAP == connectType || COAP_AND_MQTT == connectType;
        }

        public static boolean isConnectContainMqtt(ConnectType connectType) {
            return MQTT == connectType || COAP_AND_MQTT == connectType;
        }
    }

    public DefaultServerConfig() {
        this.mDeviceType = DeviceConfig.DeviceType.SERVER;
    }

    public ConnectType getConnectType() {
        return this.mConnectType;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public Map<String, ValueWrapper> getPropertValues() {
        return this.mPropertValues;
    }

    public String getSecret() {
        return this.mSecret;
    }

    public void setConnectType(ConnectType connectType) {
        this.mConnectType = connectType;
    }

    public void setPrefix(String str) {
        this.mPrefix = str;
    }

    public void setPropertValues(Map<String, ValueWrapper> map) {
        this.mPropertValues = map;
    }

    public void setSecret(String str) {
        this.mSecret = str;
    }
}
