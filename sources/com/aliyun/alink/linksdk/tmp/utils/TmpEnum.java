package com.aliyun.alink.linksdk.tmp.utils;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes2.dex */
public class TmpEnum {
    private static final String QOS_CON_STRING = "Qos_CON";
    private static final String QOS_NON_STRING = "Qos_NON";
    private static final String TAG = "[Tmp]TmpEnum";

    public enum ChannelStrategy {
        LOCAL_CHANNEL_FIRST,
        LOCAL_CHANNEL_ONLY,
        CLOUD_CHANNEL_ONLY
    }

    public enum ConnectType {
        CONNECT_TYPE_UNKNOWN(0),
        CONNECT_TYPE_COAP(1),
        CONNECT_TYPE_MQTT(2),
        CONNECT_TYPE_APIGATE(3),
        CONNECT_TYPE_BLE(4),
        CONNECT_TYPE_TGMESH(5);

        public int value;

        ConnectType(int i2) {
            this.value = i2;
        }
    }

    public enum DeviceNetType {
        NET_UNKNOWN(0),
        NET_WIFI(1),
        NET_BT(2),
        NET_COMBOMESH(256),
        NET_LORA(4),
        NET_CELLULAR(8),
        NET_ZIGBEE(16),
        NET_ETHERNET(32),
        NET_OTHER(4096),
        NET_BLE_MESH(128);

        private int value;

        DeviceNetType(int i2) {
            this.value = i2;
        }

        public static int formatDeviceNetType(List<String> list) {
            if (list == null) {
                return 0;
            }
            int i2 = list.contains("NET_LORA") ? NET_LORA.value : 0;
            if (list.contains("NET_CELLULAR")) {
                i2 |= NET_CELLULAR.value;
            }
            if (list.contains("NET_WIFI")) {
                i2 |= NET_WIFI.value;
            }
            if (list.contains("NET_ZIGBEE")) {
                i2 |= NET_ZIGBEE.value;
            }
            if (list.contains("NET_ETHERNET")) {
                i2 |= NET_ETHERNET.value;
            }
            if (list.contains("NET_BT")) {
                i2 |= NET_BT.value;
            }
            if (list.contains("NET_OTHER")) {
                i2 |= NET_OTHER.value;
            }
            return list.contains("NET_COMBOMESH") ? i2 | NET_COMBOMESH.value : i2;
        }

        public static boolean isWifiBtCombo(int i2) {
            return (NET_WIFI.getValue() & i2) > 0 && (i2 & NET_BT.getValue()) > 0;
        }

        public int getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'UPDATE_TYPE_DEVICE_DYNAMIC_TYPE' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class DeviceShadowUpdateType {
        private static final /* synthetic */ DeviceShadowUpdateType[] $VALUES;
        public static final DeviceShadowUpdateType UPDATE_OPTION_ALL;
        public static final DeviceShadowUpdateType UPDATE_OPTION_ALL_TYPE_EXCEPT_WIFISTATUS;
        public static final DeviceShadowUpdateType UPDATE_OPTION_DEVICE_DETAIL_INFO;
        public static final DeviceShadowUpdateType UPDATE_OPTION_DEVICE_NET_TYPE;
        public static final DeviceShadowUpdateType UPDATE_OPTION_DEVICE_WIFI_STATUS;
        public static final DeviceShadowUpdateType UPDATE_OPTION_PROPERTIES;
        public static final DeviceShadowUpdateType UPDATE_OPTION_STATUS;
        public static final DeviceShadowUpdateType UPDATE_OPTION_TSL;
        public static final DeviceShadowUpdateType UPDATE_TYPE_DEVICE_DYNAMIC_TYPE;
        private int value;

        static {
            DeviceShadowUpdateType deviceShadowUpdateType = new DeviceShadowUpdateType("UPDATE_OPTION_TSL", 0, 1);
            UPDATE_OPTION_TSL = deviceShadowUpdateType;
            DeviceShadowUpdateType deviceShadowUpdateType2 = new DeviceShadowUpdateType("UPDATE_OPTION_DEVICE_DETAIL_INFO", 1, 2);
            UPDATE_OPTION_DEVICE_DETAIL_INFO = deviceShadowUpdateType2;
            DeviceShadowUpdateType deviceShadowUpdateType3 = new DeviceShadowUpdateType("UPDATE_OPTION_PROPERTIES", 2, 4);
            UPDATE_OPTION_PROPERTIES = deviceShadowUpdateType3;
            DeviceShadowUpdateType deviceShadowUpdateType4 = new DeviceShadowUpdateType("UPDATE_OPTION_STATUS", 3, 8);
            UPDATE_OPTION_STATUS = deviceShadowUpdateType4;
            DeviceShadowUpdateType deviceShadowUpdateType5 = new DeviceShadowUpdateType("UPDATE_OPTION_DEVICE_WIFI_STATUS", 4, 16);
            UPDATE_OPTION_DEVICE_WIFI_STATUS = deviceShadowUpdateType5;
            DeviceShadowUpdateType deviceShadowUpdateType6 = new DeviceShadowUpdateType("UPDATE_OPTION_DEVICE_NET_TYPE", 5, 32);
            UPDATE_OPTION_DEVICE_NET_TYPE = deviceShadowUpdateType6;
            DeviceShadowUpdateType deviceShadowUpdateType7 = new DeviceShadowUpdateType("UPDATE_TYPE_DEVICE_DYNAMIC_TYPE", 6, deviceShadowUpdateType3.value | deviceShadowUpdateType4.value);
            UPDATE_TYPE_DEVICE_DYNAMIC_TYPE = deviceShadowUpdateType7;
            DeviceShadowUpdateType deviceShadowUpdateType8 = new DeviceShadowUpdateType("UPDATE_OPTION_ALL_TYPE_EXCEPT_WIFISTATUS", 7, deviceShadowUpdateType2.value | deviceShadowUpdateType.value | deviceShadowUpdateType3.value | deviceShadowUpdateType4.value | deviceShadowUpdateType6.value);
            UPDATE_OPTION_ALL_TYPE_EXCEPT_WIFISTATUS = deviceShadowUpdateType8;
            DeviceShadowUpdateType deviceShadowUpdateType9 = new DeviceShadowUpdateType("UPDATE_OPTION_ALL", 8, -1);
            UPDATE_OPTION_ALL = deviceShadowUpdateType9;
            $VALUES = new DeviceShadowUpdateType[]{deviceShadowUpdateType, deviceShadowUpdateType2, deviceShadowUpdateType3, deviceShadowUpdateType4, deviceShadowUpdateType5, deviceShadowUpdateType6, deviceShadowUpdateType7, deviceShadowUpdateType8, deviceShadowUpdateType9};
        }

        private DeviceShadowUpdateType(String str, int i2, int i3) {
            this.value = i3;
        }

        public static DeviceShadowUpdateType valueOf(String str) {
            return (DeviceShadowUpdateType) Enum.valueOf(DeviceShadowUpdateType.class, str);
        }

        public static DeviceShadowUpdateType[] values() {
            return (DeviceShadowUpdateType[]) $VALUES.clone();
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum DeviceState {
        UNKNOW(0),
        CONNECTED(1),
        DISCONNECTED(2),
        CONNECTING(3);

        private int value;

        DeviceState(int i2) {
            this.value = i2;
        }

        public static DeviceState createConnectState(ConnectState connectState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (ConnectState.CONNECTED == connectState) {
                return CONNECTED;
            }
            if (ConnectState.DISCONNECTED == connectState || ConnectState.CONNECTFAIL == connectState) {
                return DISCONNECTED;
            }
            if (ConnectState.CONNECTING == connectState) {
                return CONNECTING;
            }
            ALog.e(TmpEnum.TAG, "createConnectState state unknown state:" + connectState);
            return UNKNOW;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum DeviceWifiStatus {
        DeviceWifiStatus_NotSupport(-1),
        DeviceWifiStatus_NotSet(0),
        DeviceWifiStatus_Set(1),
        DeviceWifiStatus_Setting(2);

        private int value;

        DeviceWifiStatus(int i2) {
            this.value = i2;
        }

        public static DeviceWifiStatus formatDeviceWifiStatus(int i2) {
            return i2 != -1 ? i2 != 0 ? i2 != 1 ? i2 != 2 ? DeviceWifiStatus_NotSupport : DeviceWifiStatus_Setting : DeviceWifiStatus_Set : DeviceWifiStatus_NotSet : DeviceWifiStatus_NotSupport;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum DiscoveryDeviceState {
        DISCOVERY_STATE_ONLINE,
        DISCOVERY_STATE_OFFLINE
    }

    public enum GroupRoleType {
        UNKNOWN(TmpConstant.GROUP_ROLE_UNKNOWN),
        CONTROLLER(TmpConstant.GROUP_ROLE_CONTROLLER),
        DEVICE(TmpConstant.GROUP_ROLE_DEVICE);

        private String value;

        GroupRoleType(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum QoSLevel {
        QoS_CON(0),
        QoS_NON(1);

        int value;

        QoSLevel(int i2) {
            this.value = i2;
        }

        public static QoSLevel getQoSLevel(int i2) {
            return i2 != 0 ? i2 != 1 ? QoS_CON : QoS_NON : QoS_CON;
        }

        public int getValue() {
            return this.value;
        }

        public static QoSLevel getQoSLevel(String str) {
            QoSLevel qoSLevel = QoS_CON;
            return (TextUtils.isEmpty(str) || TmpEnum.QOS_CON_STRING.equalsIgnoreCase(str) || !TmpEnum.QOS_NON_STRING.equalsIgnoreCase(str)) ? qoSLevel : QoS_NON;
        }
    }
}
