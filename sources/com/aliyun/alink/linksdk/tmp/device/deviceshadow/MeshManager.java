package com.aliyun.alink.linksdk.tmp.device.deviceshadow;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.ailabs.iot.mesh.MeshStatusCallback;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.callback.DeviceOnlineStatusListener;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.CmpNotifyManager;
import com.aliyun.alink.linksdk.tmp.utils.TgMeshHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class MeshManager {
    private static final String TAG = "[Tmp]MeshManager";
    private boolean inReconnectionTime;
    private boolean initialized;
    private final ConcurrentHashMap<String, DeviceStatus> mDeviceCloudStatusMap;
    private final List<String> mDeviceListMap;
    MeshNetWorkStatusCallback mMeshNetWorkStatusCallback;
    private final List<String> mProvisionDeviceListMap;
    private String meshMsg;
    private final MeshMsgListener meshMsgListener;
    private int meshStatus;
    private final MeshStatusCallback meshStatusCallback;
    private final DeviceOnlineStatusListener onlineStatusListener;

    /* renamed from: x, reason: collision with root package name */
    int f11403x;

    public enum DeviceStatus {
        stateless,
        online,
        offline
    }

    public interface MeshNetWorkStatusCallback {
        void isConnect(boolean z2);
    }

    private static class SingletonClassInstance {
        private static final MeshManager instance = new MeshManager();

        private SingletonClassInstance() {
        }

        public static MeshManager instance() {
            return instance;
        }
    }

    private synchronized void allDeviceSetStatus() {
    }

    private DeviceStatus getCurrentStatus(DeviceStatus deviceStatus, DeviceStatus deviceStatus2) {
        DeviceStatus deviceStatus3 = DeviceStatus.online;
        if (deviceStatus == deviceStatus3 || deviceStatus2 == deviceStatus3) {
            return deviceStatus3;
        }
        DeviceStatus deviceStatus4 = DeviceStatus.offline;
        return (deviceStatus2 == deviceStatus4 && deviceStatus == deviceStatus4) ? deviceStatus4 : DeviceStatus.stateless;
    }

    public static MeshManager getInstance() {
        return SingletonClassInstance.instance();
    }

    private void updateCloudStatus(String str, DeviceStatus deviceStatus) {
        Log.d(TAG, "updateCloudStatus() called with: iotId = [" + str + "], stateless = [" + deviceStatus + "]");
        this.mDeviceCloudStatusMap.put(str, deviceStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceStatus(String str) {
        DeviceStatus meshCurrentStatus;
        Log.d(TAG, "updateDeviceStatus() called with: iotId = [" + str + "]");
        if (TextUtils.isEmpty(str) || (meshCurrentStatus = getMeshCurrentStatus(str)) == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("value", (Object) Integer.valueOf(meshCurrentStatus == DeviceStatus.online ? 1 : meshCurrentStatus == DeviceStatus.offline ? 3 : 2));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("iotId", (Object) str);
        jSONObject2.put("status", (Object) jSONObject);
        jSONObject2.put(TmpConstant.TMP_LOCAL_STATUS, (Object) Boolean.TRUE);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("params", (Object) jSONObject2);
        jSONObject3.put("iotId", (Object) str);
        AMessage aMessage = new AMessage();
        aMessage.data = jSONObject3;
        CmpNotifyManager.getInstance().onNotify(PersistentConnect.CONNECT_ID, TmpConstant.MQTT_TOPIC_STATUS, aMessage);
    }

    public void addMeshDevice(String str, int i2) {
        Log.d(TAG, "addMeshDevice() called with: iotId = [" + str + "], status = [" + i2 + "]");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mDeviceListMap.add(str);
        this.mDeviceCloudStatusMap.put(str, i2 == 1 ? DeviceStatus.online : DeviceStatus.offline);
    }

    public void addProvisionDevice(String str) {
        Log.d(TAG, "addProvisionDevice() called with: mIotId = [" + str + "]");
        this.mProvisionDeviceListMap.add(str);
    }

    public DeviceStatus getCloudStatus(String str) {
        return this.mDeviceCloudStatusMap.get(str);
    }

    public DeviceStatus getLocalStatus(String str) {
        return !Utils.isBleEnabled() ? DeviceStatus.offline : queryDeviceOnlineStatus(str);
    }

    public DeviceStatus getMeshCurrentStatus(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Log.d(TAG, "getMeshCurrentStatus() called with: iotId = [" + str + "]");
        DeviceStatus deviceStatus = DeviceStatus.stateless;
        DeviceStatus deviceStatusQueryDeviceOnlineStatus = queryDeviceOnlineStatus(str);
        DeviceStatus deviceStatus2 = this.mDeviceCloudStatusMap.containsKey(str) ? this.mDeviceCloudStatusMap.get(str) : deviceStatus;
        boolean zIsMeshNetworkOK = isMeshNetworkOK();
        if (deviceStatusQueryDeviceOnlineStatus == deviceStatus && zIsMeshNetworkOK) {
            ALog.d(TAG, "getMeshCurrentStatus: localStatus is stateless but mesh network is ok ");
            deviceStatusQueryDeviceOnlineStatus = DeviceStatus.online;
        }
        if (!zIsMeshNetworkOK) {
            ALog.d(TAG, "getMeshCurrentStatus: mesh network is offline");
            deviceStatusQueryDeviceOnlineStatus = DeviceStatus.offline;
        }
        if (!this.inReconnectionTime || deviceStatusQueryDeviceOnlineStatus != DeviceStatus.offline) {
            deviceStatus = deviceStatusQueryDeviceOnlineStatus;
        }
        if (!Utils.isBleEnabled()) {
            deviceStatus = DeviceStatus.offline;
        }
        if (zIsMeshNetworkOK && deviceStatus == DeviceStatus.offline && this.mProvisionDeviceListMap.contains(str)) {
            ALog.d(TAG, "刚配网的设备,认为在线");
            return DeviceStatus.online;
        }
        ALog.d(TAG, "getMeshCurrentStatus() called with: deviceId = [" + str + "], localStatus = [ " + deviceStatus + " ], cloudStatus = [ " + deviceStatus2 + " ]");
        return getCurrentStatus(deviceStatus, deviceStatus2);
    }

    public synchronized void initMeshManager() {
        Log.d(TAG, "initMeshManager");
        if (!this.initialized) {
            TgMeshManager.getInstance().registerMeshMessageListener(this.meshMsgListener);
            TgMeshManager.getInstance().registerCallback(this.meshStatusCallback);
            TgMeshManager.getInstance().registerDeviceOnlineStatusListener(this.onlineStatusListener);
            this.initialized = true;
        }
    }

    public boolean isMeshDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.mDeviceListMap.contains(str);
    }

    public boolean isMeshNetworkOK() {
        return TgMeshManager.getInstance().isConnectedToMesh();
    }

    public DeviceStatus queryDeviceOnlineStatus(String str) {
        String strCoverIotIdToDevId = MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str);
        LogUtils.d(TAG, "queryDeviceOnlineStatus() called with: iotId = [" + str + "],devId = [" + strCoverIotIdToDevId + "]");
        if (TextUtils.isEmpty(strCoverIotIdToDevId)) {
            return DeviceStatus.stateless;
        }
        TgMeshManager.DevOnlineStatus devOnlineStatusQueryDeviceOnlineStatus = TgMeshManager.getInstance().queryDeviceOnlineStatus(strCoverIotIdToDevId);
        LogUtils.d(TAG, "queryDeviceOnlineStatus: localStatus=" + devOnlineStatusQueryDeviceOnlineStatus);
        return devOnlineStatusQueryDeviceOnlineStatus == null ? DeviceStatus.stateless : TgMeshManager.DevOnlineStatus.DEV_ST_ONLINE == devOnlineStatusQueryDeviceOnlineStatus ? DeviceStatus.online : TgMeshManager.DevOnlineStatus.DEV_ST_OFFLINE == devOnlineStatusQueryDeviceOnlineStatus ? DeviceStatus.offline : DeviceStatus.stateless;
    }

    public void removeDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mDeviceListMap.remove(str);
        this.mDeviceCloudStatusMap.remove(str);
        this.mProvisionDeviceListMap.remove(str);
    }

    public void setStatusCallback(MeshNetWorkStatusCallback meshNetWorkStatusCallback) {
        this.mMeshNetWorkStatusCallback = meshNetWorkStatusCallback;
    }

    public void test() {
        Log.d(TAG, "test() called");
        Iterator<String> it = this.mDeviceListMap.iterator();
        while (true) {
            int i2 = 1;
            if (!it.hasNext()) {
                this.f11403x++;
                return;
            }
            String next = it.next();
            DeviceStatus deviceStatus = DeviceStatus.offline;
            JSONObject jSONObject = new JSONObject();
            DeviceStatus deviceStatus2 = this.f11403x % 2 == 0 ? DeviceStatus.online : deviceStatus;
            if (deviceStatus2 != DeviceStatus.online) {
                i2 = deviceStatus2 == deviceStatus ? 3 : 2;
            }
            jSONObject.put("value", (Object) Integer.valueOf(i2));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("iotId", (Object) next);
            jSONObject2.put("status", (Object) jSONObject);
            jSONObject2.put(TmpConstant.TMP_LOCAL_STATUS, (Object) Boolean.TRUE);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("params", (Object) jSONObject2);
            jSONObject3.put("iotId", (Object) next);
            AMessage aMessage = new AMessage();
            aMessage.data = jSONObject3;
            CmpNotifyManager.getInstance().onNotify(PersistentConnect.CONNECT_ID, TmpConstant.MQTT_TOPIC_STATUS, aMessage);
        }
    }

    private MeshManager() {
        this.meshStatus = -2;
        this.meshMsg = "";
        this.initialized = false;
        this.inReconnectionTime = false;
        this.meshMsgListener = new MeshMsgListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.MeshManager.1
            @Override // com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener
            public void onReceiveMeshMessage(byte[] bArr, MeshAccessPayload meshAccessPayload) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                short s2 = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
                ALog.i(MeshManager.TAG, "onReceiveMeshMessage opcode = " + meshAccessPayload.getOpCode() + ", address = " + ((int) s2));
                if (meshAccessPayload.getTranslatedTSLDesc() != null) {
                    Log.i(MeshManager.TAG, "onReceiveMeshMessage translatedTSLDesc = " + meshAccessPayload.getTranslatedTSLDesc());
                }
            }
        };
        this.meshStatusCallback = new MeshStatusCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.MeshManager.2
            @Override // com.alibaba.ailabs.iot.mesh.StatusCallback
            public void onStatus(int i2, String str) {
                Log.d(MeshManager.TAG, "meshStatusCallback onStatus= " + i2 + ", " + str);
                if ((i2 == 2 || i2 == -2) && i2 != MeshManager.this.meshStatus) {
                    MeshManager.this.meshStatus = i2;
                    if (i2 == 2) {
                        MeshNetWorkStatusCallback meshNetWorkStatusCallback = MeshManager.this.mMeshNetWorkStatusCallback;
                        if (meshNetWorkStatusCallback != null) {
                            meshNetWorkStatusCallback.isConnect(true);
                            return;
                        }
                        return;
                    }
                    MeshManager.this.inReconnectionTime = true;
                    ThreadTools.submitTask(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.MeshManager.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MeshNetWorkStatusCallback meshNetWorkStatusCallback2;
                            MeshManager.this.inReconnectionTime = false;
                            if (MeshManager.this.meshStatus != -2 || (meshNetWorkStatusCallback2 = MeshManager.this.mMeshNetWorkStatusCallback) == null) {
                                return;
                            }
                            meshNetWorkStatusCallback2.isConnect(false);
                        }
                    }, true, 30000);
                    if (MeshManager.this.mDeviceListMap.size() > 0) {
                        TgMeshHelper.connect((String) MeshManager.this.mDeviceListMap.get(0));
                    }
                }
            }
        };
        this.onlineStatusListener = new DeviceOnlineStatusListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.MeshManager.3
            @Override // com.alibaba.ailabs.iot.mesh.callback.DeviceOnlineStatusListener
            public void onOnlineStatusChange(String str, TgMeshManager.DevOnlineStatus devOnlineStatus) {
                String strConvertDevIdToIotId = MeshDeviceInfoManager.getInstance().convertDevIdToIotId(str);
                if (TextUtils.isEmpty(strConvertDevIdToIotId)) {
                    return;
                }
                if (!MeshManager.this.mDeviceListMap.contains(strConvertDevIdToIotId)) {
                    MeshManager.this.mDeviceListMap.add(strConvertDevIdToIotId);
                }
                Log.d(MeshManager.TAG, "onOnlineStatusChange() called with: iotId = [" + strConvertDevIdToIotId + "], devOnlineStatus = [" + devOnlineStatus + "]");
                MeshManager.this.updateDeviceStatus(strConvertDevIdToIotId);
            }
        };
        this.f11403x = 0;
        this.mDeviceListMap = new ArrayList();
        this.mProvisionDeviceListMap = new ArrayList();
        this.mDeviceCloudStatusMap = new ConcurrentHashMap<>();
    }

    public void updateCloudStatus(String str, int i2) {
        DeviceStatus deviceStatus;
        Log.d(TAG, "updateCloudStatus() called with: iotId = [" + str + "], value = [" + i2 + "]");
        if (i2 == 1) {
            deviceStatus = DeviceStatus.online;
        } else {
            deviceStatus = i2 == 3 ? DeviceStatus.offline : DeviceStatus.stateless;
        }
        updateCloudStatus(str, deviceStatus);
    }
}
