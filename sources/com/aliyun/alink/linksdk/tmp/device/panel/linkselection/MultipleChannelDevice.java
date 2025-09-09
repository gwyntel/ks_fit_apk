package com.aliyun.alink.linksdk.tmp.device.panel.linkselection;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.MeshManager;
import com.aliyun.alink.linksdk.tmp.device.panel.data.LocalConnectParams;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelDeviceLocalInitListener;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.SerializePropCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.request.DeviceExtended.GetDeviceExtendRequest;
import com.aliyun.alink.linksdk.tmp.device.request.other.GetDeviceNetTypesSupportedRequest;
import com.aliyun.alink.linksdk.tmp.error.CommonError;
import com.aliyun.alink.linksdk.tmp.error.ParamsError;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CheckMeshMessage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.DeviceClassificationUtil;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.ResponseUtils;
import com.aliyun.alink.linksdk.tmp.utils.TgMeshHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class MultipleChannelDevice {
    private static final int CLICKTIME = 800;
    private static final String TAG = "[Tmp]MultipleChannelDevice";
    private static final Map<String, ScheduledFuture> scheduledFutureMap = new HashMap();
    private CloudChannelDevice mCloudChannelDevice;
    private String mIotId;
    private LocalChannelDevice mLocalChannelDevice;
    private WeakReference<IPanelEventCallback> mPanelEventCallback;
    private PanelMethodExtraData mPanelMethodExtraData;
    private long clickTime = 0;
    private ScheduledFuture scheduledFuture = null;

    /* renamed from: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice$16, reason: invalid class name */
    static /* synthetic */ class AnonymousClass16 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$tmp$device$deviceshadow$MeshManager$DeviceStatus;

        static {
            int[] iArr = new int[MeshManager.DeviceStatus.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$tmp$device$deviceshadow$MeshManager$DeviceStatus = iArr;
            try {
                iArr[MeshManager.DeviceStatus.online.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$device$deviceshadow$MeshManager$DeviceStatus[MeshManager.DeviceStatus.offline.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public MultipleChannelDevice(String str, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("MultipleChannelDevice iotId:");
        sb.append(str);
        sb.append(" extraData:");
        sb.append(panelMethodExtraData == null ? TmpConstant.GROUP_ROLE_UNKNOWN : panelMethodExtraData.toString());
        ALog.d(TAG, sb.toString());
        this.mIotId = str;
        if (panelMethodExtraData == null) {
            this.mPanelMethodExtraData = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        } else {
            this.mPanelMethodExtraData = panelMethodExtraData;
        }
        this.mPanelEventCallback = new WeakReference<>(null);
        this.mCloudChannelDevice = new CloudChannelDevice(str, this, null);
        this.mLocalChannelDevice = new LocalChannelDevice(str, null, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backupCloudControl(String str, PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "backupCloudControl");
        this.mLocalChannelDevice.setProperties(str, panelMethodExtraData, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.10
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, Object obj) {
                if (z2) {
                    iPanelCallback.onComplete(true, obj);
                } else {
                    iPanelCallback.onComplete(false, new JSONObject());
                }
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bleMeshCloudControl(String str, final PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "bleMeshCloudControl params:" + str);
        JSONObject object = JSON.parseObject(str);
        object.put((JSONObject) "comboFlag", (String) Boolean.valueOf(isMeshComboDevice()));
        final String jSONString = JSON.toJSONString(object);
        this.mCloudChannelDevice.getStatus(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.11
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(MultipleChannelDevice.TAG, "bleMeshCloudControl getStatus isSuccess:" + z2);
                if (!z2 || obj == null || TextUtils.isEmpty(obj.toString())) {
                    ALog.d(MultipleChannelDevice.TAG, "bleMeshCloudControl getStatus false");
                    MultipleChannelDevice.this.backupCloudControl(jSONString, panelMethodExtraData, iPanelCallback);
                    return;
                }
                try {
                    JSONObject object2 = JSON.parseObject(obj.toString());
                    if (object2 != null && object2.containsKey("data") && object2.getJSONObject("data") != null && object2.getJSONObject("data").containsKey("status") && object2.getJSONObject("data").getInteger("status").intValue() == 1) {
                        ALog.d(MultipleChannelDevice.TAG, "mCloudChannelDevice.setProperties params:" + jSONString);
                        MultipleChannelDevice.this.mCloudChannelDevice.setPropertiesMesh(jSONString, panelMethodExtraData, iPanelCallback);
                    } else {
                        ALog.d(MultipleChannelDevice.TAG, "bleMeshCloudControl getStatus jsData:" + object2);
                        MultipleChannelDevice.this.backupCloudControl(jSONString, panelMethodExtraData, iPanelCallback);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ALog.d(MultipleChannelDevice.TAG, "bleMeshCloudControl getStatus Exception");
                    MultipleChannelDevice.this.backupCloudControl(jSONString, panelMethodExtraData, iPanelCallback);
                }
            }
        });
    }

    private boolean isIniting() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2 = this.mCloudChannelDevice.isIniting() && this.mLocalChannelDevice.isLocalIniting();
        ALog.d(TAG, "isIniting :" + z2);
        return z2;
    }

    private boolean isLocalIniting() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zIsLocalIniting = this.mLocalChannelDevice.isLocalIniting();
        ALog.d(TAG, "isLocalIniting :" + zIsLocalIniting);
        return zIsLocalIniting;
    }

    private boolean isMeshComboDevice() {
        return DeviceClassificationUtil.isComboMeshDeviceViaIotID(this.mIotId);
    }

    private void meshLocalFrisControl(final String str, final IPanelCallback iPanelCallback, final PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "meshLocalFrisControl");
        if (TgMeshHelper.isMeshNodeReachable(this.mIotId)) {
            ALog.d(TAG, "isMeshNodeReachable true");
            this.mLocalChannelDevice.setProperties(str, panelMethodExtraData, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.9
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (z2) {
                        iPanelCallback.onComplete(true, obj);
                    } else if (DeviceClassificationUtil.isBleMeshDeviceViaProductInfo(TmpStorage.getInstance().getProductInfo(MultipleChannelDevice.this.mIotId))) {
                        MultipleChannelDevice.this.bleMeshCloudControl(str, panelMethodExtraData, iPanelCallback);
                    } else {
                        MultipleChannelDevice.this.mCloudChannelDevice.setProperties(str, panelMethodExtraData, iPanelCallback);
                    }
                }
            }, true);
        } else {
            ALog.d(TAG, "isMeshNodeReachable false");
            TgMeshHelper.connect(this.mIotId);
            bleMeshCloudControl(str, panelMethodExtraData, iPanelCallback);
        }
    }

    public void getCachedWifiStatus(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getWifiStatus callback empty");
            return;
        }
        String deviceWifiStatus = DeviceShadowMgr.getInstance().getDeviceWifiStatus(getIotId());
        if (TextUtils.isEmpty(deviceWifiStatus)) {
            iPanelCallback.onComplete(false, new CommonError(new ErrorInfo(-200, "getDeviceWifiStatus error")));
            return;
        }
        String strValueOf = String.valueOf(TmpEnum.DeviceWifiStatus.DeviceWifiStatus_NotSupport.getValue());
        try {
            strValueOf = (String) ((GetDeviceExtendRequest.DeviceExtendGetResponse) JSON.parseObject(deviceWifiStatus, GetDeviceExtendRequest.DeviceExtendGetResponse.class)).data;
        } catch (Exception e2) {
            ALog.e(TAG, "getWifiStatus parseObject e:" + e2.toString());
            iPanelCallback.onComplete(false, new CommonError(new ErrorInfo(-200, "getDeviceWifiStatus error")));
        }
        iPanelCallback.onComplete(true, strValueOf);
    }

    public String getDeviceName() {
        return this.mLocalChannelDevice.getDeviceName();
    }

    public void getDeviceNetTypesSupported(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getWifiStatus callback empty");
        } else {
            DeviceShadowMgr.getInstance().getDeviceSupportedNetTypesByIotId(getIotId(), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.15
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse;
                    if (iPanelCallback != null) {
                        try {
                            getDeviceNetTypesSupportedResponse = (GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject((String) obj, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class);
                        } catch (Exception e2) {
                            ALog.e(MultipleChannelDevice.TAG, "getDeviceNetTypesSupported e:" + e2.toString());
                            getDeviceNetTypesSupportedResponse = null;
                        }
                        String strValueOf = String.valueOf(TmpEnum.DeviceNetType.NET_UNKNOWN.getValue());
                        if (getDeviceNetTypesSupportedResponse != null) {
                            strValueOf = String.valueOf(TmpEnum.DeviceNetType.formatDeviceNetType((List) getDeviceNetTypesSupportedResponse.data));
                        }
                        iPanelCallback.onComplete(true, strValueOf);
                    }
                }
            });
        }
    }

    public void getDeviceTSL(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getDeviceTSL callback null");
            return;
        }
        ALog.d(TAG, "getDeviceTSL callback:" + iPanelCallback);
        CloudUtils.getTsl(this.mIotId, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.2
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(MultipleChannelDevice.TAG, "getDeviceTSL failure");
                iPanelCallback.onComplete(false, aError);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                ALog.e(MultipleChannelDevice.TAG, "getDeviceTSL success");
                ALog.d(MultipleChannelDevice.TAG, "updateTslByCloud ***** iotId:" + MultipleChannelDevice.this.mIotId + " onResponse:" + aResponse);
                if (aResponse == null || (obj = aResponse.data) == null) {
                    iPanelCallback.onComplete(false, new ErrorInfo(300, "getTsl aResponse error"));
                    return;
                }
                try {
                    GetTslResponsePayload getTslResponsePayload = (GetTslResponsePayload) JSON.parseObject(String.valueOf(obj), GetTslResponsePayload.class);
                    ALog.d(MultipleChannelDevice.TAG, "onResponse() called with: aResponse = [" + aResponse.getData() + "]");
                    StringBuilder sb = new StringBuilder();
                    sb.append("onResponse() called with: getCode = ");
                    sb.append(getTslResponsePayload.getCode());
                    ALog.d(MultipleChannelDevice.TAG, sb.toString());
                    iPanelCallback.onComplete(true, getTslResponsePayload);
                } catch (Exception e2) {
                    ALog.e(MultipleChannelDevice.TAG, "parseObject error:" + e2.toString());
                    iPanelCallback.onComplete(false, new ErrorInfo(300, "parseObject error:" + e2.toString()));
                }
            }
        });
    }

    @Deprecated
    public void getEvents(IPanelCallback iPanelCallback) {
        getLastEvent(iPanelCallback);
    }

    public String getIotId() {
        return this.mIotId;
    }

    public void getLastEvent(IPanelCallback iPanelCallback) {
        this.mCloudChannelDevice.getLastEvent(iPanelCallback);
    }

    public void getLocalConnectionState(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getLocalConnectionState islocaliniting:" + isLocalIniting() + " isIniting:" + isIniting());
        if (iPanelCallback != null) {
            this.mLocalChannelDevice.getLocalConnectionState(iPanelCallback);
            return;
        }
        ALog.e(TAG, "stopLocalConnect callback:" + iPanelCallback);
    }

    public IPanelEventCallback getPanelEventCallback() {
        return this.mPanelEventCallback.get();
    }

    public String getProductKey() {
        return this.mLocalChannelDevice.getProductKey();
    }

    public void getProperties(final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) {
        StringBuilder sb = new StringBuilder();
        sb.append("getProperties callback:");
        sb.append(iPanelCallback);
        sb.append(" extraData:");
        sb.append(panelMethodExtraData == null ? "" : panelMethodExtraData.toString());
        ALog.d(TAG, sb.toString());
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback null");
            return;
        }
        if (panelMethodExtraData == null) {
            ALog.w(TAG, "getProperties extraData null");
            panelMethodExtraData = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        }
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = panelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            this.mCloudChannelDevice.getProperties(iPanelCallback);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2) {
            this.mLocalChannelDevice.getProperties(iPanelCallback, true);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST == channelStrategy2) {
            this.mLocalChannelDevice.getProperties(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.4
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    if (z2) {
                        iPanelCallback.onComplete(z2, obj);
                    } else {
                        MultipleChannelDevice.this.mCloudChannelDevice.getProperties(iPanelCallback);
                    }
                }
            }, true);
        }
    }

    public void getStatus(IPanelCallback iPanelCallback) {
        ALog.d(TAG, "getStatus callback:" + iPanelCallback);
        if (iPanelCallback == null) {
            ALog.e(TAG, "getStatus callback  null");
            return;
        }
        if (!isBleMeshDevice()) {
            this.mCloudChannelDevice.getStatus(iPanelCallback);
            return;
        }
        MeshManager.DeviceStatus meshCurrentStatus = MeshManager.getInstance().getMeshCurrentStatus(this.mIotId);
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        int i2 = AnonymousClass16.$SwitchMap$com$aliyun$alink$linksdk$tmp$device$deviceshadow$MeshManager$DeviceStatus[meshCurrentStatus.ordinal()];
        try {
            jSONObject.put("status", (i2 == 1 || i2 != 2) ? 1 : 3);
            jSONObject.put("time", System.currentTimeMillis());
            iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
        } catch (JSONException e2) {
            e2.printStackTrace();
            iPanelCallback.onComplete(false, new ParamsError());
        }
    }

    public void getWifiStatus(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getWifiStatus callback empty");
        } else {
            DeviceShadowMgr.getInstance().getDeviceWifiStatus(getIotId(), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.14
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (iPanelCallback != null) {
                        String strValueOf = String.valueOf(TmpEnum.DeviceWifiStatus.DeviceWifiStatus_NotSupport.getValue());
                        try {
                            strValueOf = (String) ((GetDeviceExtendRequest.DeviceExtendGetResponse) JSON.parseObject((String) obj, GetDeviceExtendRequest.DeviceExtendGetResponse.class)).data;
                        } catch (Exception e2) {
                            ALog.e(MultipleChannelDevice.TAG, "getWifiStatus parseObject e:" + e2.toString());
                            IPanelCallback iPanelCallback2 = iPanelCallback;
                            if (iPanelCallback2 != null) {
                                iPanelCallback2.onComplete(false, new CommonError(new ErrorInfo(-200, "getDeviceWifiStatus error")));
                            }
                        }
                        iPanelCallback.onComplete(true, strValueOf);
                    }
                }
            });
        }
    }

    public void init(IPanelCallback iPanelCallback) {
        init(null);
    }

    public void invokeService(final String str, final IPanelCallback iPanelCallback, final PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("invokeService params:");
        sb.append(str);
        sb.append("callback:");
        sb.append(iPanelCallback);
        sb.append(" extraData:");
        sb.append(panelMethodExtraData == null ? "" : panelMethodExtraData.toString());
        ALog.d(TAG, sb.toString());
        if (iPanelCallback == null) {
            ALog.e(TAG, "invokeService callback null");
            return;
        }
        if (panelMethodExtraData == null) {
            ALog.w(TAG, "invokeService extraData null");
            panelMethodExtraData = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        }
        panelMethodExtraData.build(JSON.parseObject(str));
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = panelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            this.mCloudChannelDevice.invokeService(str, panelMethodExtraData, iPanelCallback);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2) {
            this.mLocalChannelDevice.invokeService(str, panelMethodExtraData, iPanelCallback, true);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST == channelStrategy2) {
            this.mLocalChannelDevice.invokeService(str, panelMethodExtraData, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.12
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    if (z2) {
                        iPanelCallback.onComplete(z2, obj);
                    } else {
                        MultipleChannelDevice.this.mCloudChannelDevice.invokeService(str, panelMethodExtraData, iPanelCallback);
                    }
                }
            }, true);
        }
    }

    public boolean isBleMeshDevice() {
        LocalChannelDevice localChannelDevice = this.mLocalChannelDevice;
        if (localChannelDevice != null) {
            return localChannelDevice.isBleMeshDevice();
        }
        return false;
    }

    public boolean isInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = this.mPanelMethodExtraData.channelStrategy;
        boolean zIsInit = channelStrategy == channelStrategy2 ? this.mCloudChannelDevice.isInit() : TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2 ? this.mLocalChannelDevice.isInit() : this.mCloudChannelDevice.isInit() && this.mLocalChannelDevice.isInit();
        ALog.d(TAG, "isInit :" + zIsInit);
        return zIsInit;
    }

    public boolean isLocalConnected() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zIsLocalConnected = this.mLocalChannelDevice.isLocalConnected();
        ALog.d(TAG, "isLocalInited :" + zIsLocalConnected);
        return zIsLocalConnected;
    }

    public void queryTimerDownlinkTask(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "queryTimerDownlinkTask callback null");
            return;
        }
        ALog.d(TAG, "queryTimerDownlinkTask callback:" + iPanelCallback);
        CloudUtils.queryTimerDownlinkTask(this.mIotId, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.3
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(MultipleChannelDevice.TAG, "queryTimerDownlinkTask failure");
                iPanelCallback.onComplete(false, aError);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                Object obj;
                ALog.e(MultipleChannelDevice.TAG, "queryTimerDownlinkTask success");
                ALog.d(MultipleChannelDevice.TAG, "queryTimerDownlinkTask ***** iotId:" + MultipleChannelDevice.this.mIotId + " onResponse:" + aResponse);
                if (aResponse == null || (obj = aResponse.data) == null) {
                    iPanelCallback.onComplete(false, new ErrorInfo(300, "getTsl aResponse error"));
                    return;
                }
                try {
                    GetTslResponsePayload getTslResponsePayload = (GetTslResponsePayload) JSON.parseObject(String.valueOf(obj), GetTslResponsePayload.class);
                    ALog.d(MultipleChannelDevice.TAG, "queryTimerDownlinkTask() called with: aResponse = [" + aResponse.getData() + "]");
                    StringBuilder sb = new StringBuilder();
                    sb.append("queryTimerDownlinkTask() called with: getCode = ");
                    sb.append(getTslResponsePayload.getCode());
                    ALog.d(MultipleChannelDevice.TAG, sb.toString());
                    iPanelCallback.onComplete(true, getTslResponsePayload);
                } catch (Exception e2) {
                    ALog.e(MultipleChannelDevice.TAG, "parseObject error:" + e2.toString());
                    iPanelCallback.onComplete(false, new ErrorInfo(300, "parseObject error:" + e2.toString()));
                }
            }
        });
    }

    public void setProperties(final String str, final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PanelMethodExtraData panelMethodExtraData2;
        final IPanelCallback iPanelCallback2 = iPanelCallback;
        StringBuilder sb = new StringBuilder();
        sb.append("setProperties params:");
        sb.append(str);
        sb.append(" extraData:");
        sb.append(panelMethodExtraData == null ? "" : panelMethodExtraData.toString());
        ALog.d(TAG, sb.toString());
        if (iPanelCallback2 == null) {
            ALog.e(TAG, "setProperties callback null");
            return;
        }
        JSONObject object = JSON.parseObject(str);
        JSONObject jSONObject = object != null ? object.getJSONObject("items") : null;
        final boolean z2 = jSONObject != null && jSONObject.containsKey("DeviceTimer");
        ALog.d(TAG, "device timer set action: " + z2);
        if (panelMethodExtraData == null) {
            ALog.w(TAG, "setProperties extraData null");
            panelMethodExtraData2 = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        } else {
            panelMethodExtraData2 = panelMethodExtraData;
        }
        if (DeviceShadowMgr.getInstance().getSupportDownAllProps(getIotId())) {
            panelMethodExtraData2.channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        }
        final IPanelCallback iPanelCallback3 = new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.5
            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
            public void onComplete(boolean z3, @Nullable Object obj) {
                iPanelCallback2.onComplete(z3, obj);
                if (!z3 || z2) {
                    return;
                }
                CheckMeshMessage.updateDeviceProperties(MultipleChannelDevice.this.getIotId(), str, MultipleChannelDevice.this.getPanelEventCallback());
                CheckMeshMessage.refreshAppDevice(MultipleChannelDevice.this.getIotId(), str);
            }
        };
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = this.clickTime;
        if (jCurrentTimeMillis - j2 > 800 || j2 == 0 || !isBleMeshDevice() || !panelMethodExtraData2.mRateLimiting) {
            ALog.d(TAG, "直接下发 params:");
            if (isBleMeshDevice()) {
                iPanelCallback2 = iPanelCallback3;
            }
            setProperties(str, iPanelCallback2, panelMethodExtraData2, true);
        } else {
            ALog.d(TAG, "延迟下发 params:" + ((this.clickTime + 800) - System.currentTimeMillis()));
            ScheduledFuture scheduledFuture = this.scheduledFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            final PanelMethodExtraData panelMethodExtraData3 = panelMethodExtraData2;
            this.scheduledFuture = new ScheduledThreadPoolExecutor(1, new ThreadFactory() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.6
                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(@NonNull Runnable runnable) {
                    return new Thread(runnable, MultipleChannelDevice.TAG);
                }
            }).schedule(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.7
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    MultipleChannelDevice multipleChannelDevice = MultipleChannelDevice.this;
                    multipleChannelDevice.setProperties(str, multipleChannelDevice.isBleMeshDevice() ? iPanelCallback3 : iPanelCallback, panelMethodExtraData3, true);
                }
            }, (this.clickTime + 800) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        this.clickTime = System.currentTimeMillis();
    }

    public void setPropertyAlias(String str, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DeviceShadowMgr.getInstance().setPropertyAlias(str, iPanelCallback);
    }

    public void startLocalConnect(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startLocalConnect callback:" + iPanelCallback + " mLocalChannelDevice:" + this.mLocalChannelDevice);
        if (iPanelCallback != null) {
            this.mLocalChannelDevice.startLocalConnect(iPanelCallback);
            return;
        }
        ALog.e(TAG, "startLocalConnect callback:" + iPanelCallback);
    }

    public void stopLocalConnect(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopLocalConnect callback:" + iPanelCallback);
        if (iPanelCallback != null) {
            this.mLocalChannelDevice.stopLocalConnect(iPanelCallback);
            return;
        }
        ALog.e(TAG, "stopLocalConnect callback:" + iPanelCallback);
    }

    public void subAllEvents(IPanelEventCallback iPanelEventCallback, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        subAllEvents(iPanelEventCallback, iPanelCallback, this.mPanelMethodExtraData);
    }

    public void uninit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "uninit iotid:" + this.mIotId);
        this.mCloudChannelDevice.uninit();
        this.mLocalChannelDevice.uninit();
    }

    public void init(final IPanelCallback iPanelCallback, IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init  mIotId:" + this.mIotId + " callback:" + iPanelCallback + " mIsIniting:" + isIniting() + " localInitListener:" + iPanelDeviceLocalInitListener);
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = this.mPanelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            this.mLocalChannelDevice.init(new LocalConnectParams(), iPanelCallback, iPanelDeviceLocalInitListener);
        } else if (TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY == channelStrategy2) {
            this.mCloudChannelDevice.init(iPanelCallback);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST == channelStrategy2) {
            this.mLocalChannelDevice.init(new LocalConnectParams(), new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.1
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) {
                    MultipleChannelDevice.this.mCloudChannelDevice.init(iPanelCallback);
                }
            }, iPanelDeviceLocalInitListener);
        }
    }

    public void subAllEvents(IPanelEventCallback iPanelEventCallback, final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("subAllEvents listener:");
        sb.append(iPanelEventCallback);
        sb.append(" callback:");
        sb.append(iPanelCallback);
        sb.append(" extraData:");
        sb.append(panelMethodExtraData == null ? "" : panelMethodExtraData.toString());
        ALog.d(TAG, sb.toString());
        if (iPanelEventCallback == null) {
            ALog.e(TAG, "subAllEvent callback null");
            return;
        }
        final SerializePropCallback serializePropCallback = new SerializePropCallback(iPanelEventCallback);
        this.mPanelEventCallback = new WeakReference<>(serializePropCallback);
        if (panelMethodExtraData == null) {
            ALog.w(TAG, "subAllEvents extraData null");
            panelMethodExtraData = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        }
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = panelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            this.mCloudChannelDevice.subAllEvents(serializePropCallback, iPanelCallback);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2) {
            this.mLocalChannelDevice.subAllEvents(serializePropCallback, iPanelCallback, true);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST == channelStrategy2) {
            this.mCloudChannelDevice.subAllEvents(serializePropCallback, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.13
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(final boolean z2, @Nullable Object obj) {
                    MultipleChannelDevice.this.mLocalChannelDevice.subAllEvents(serializePropCallback, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.13.1
                        @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                        public void onComplete(boolean z3, @Nullable Object obj2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            boolean z4 = z3 || z2;
                            ALog.d(MultipleChannelDevice.TAG, "subAllEvents onComplete ret:" + z4);
                            IPanelCallback iPanelCallback2 = iPanelCallback;
                            if (iPanelCallback2 != null) {
                                iPanelCallback2.onComplete(z4, null);
                            }
                        }
                    }, true);
                }
            });
        }
    }

    public void setProperties(final String str, final IPanelCallback iPanelCallback, final PanelMethodExtraData panelMethodExtraData, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        panelMethodExtraData.build(JSON.parseObject(str));
        ALog.d(TAG, "extraData :" + panelMethodExtraData);
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = panelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            this.mCloudChannelDevice.setProperties(str, panelMethodExtraData, iPanelCallback);
            return;
        }
        if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2) {
            this.mLocalChannelDevice.setProperties(str, panelMethodExtraData, iPanelCallback, true);
        } else if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST == channelStrategy2) {
            if (DeviceClassificationUtil.isBleMeshDeviceViaProductInfo(TmpStorage.getInstance().getProductInfo(this.mIotId))) {
                meshLocalFrisControl(str, iPanelCallback, panelMethodExtraData);
            } else {
                this.mLocalChannelDevice.setProperties(str, panelMethodExtraData, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice.8
                    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                    public void onComplete(boolean z3, Object obj) {
                        if (z3) {
                            iPanelCallback.onComplete(true, obj);
                        } else {
                            TmpStorage.getInstance().getProductInfo(MultipleChannelDevice.this.mIotId);
                            MultipleChannelDevice.this.mCloudChannelDevice.setProperties(str, panelMethodExtraData, iPanelCallback);
                        }
                    }
                }, true);
            }
        }
    }
}
