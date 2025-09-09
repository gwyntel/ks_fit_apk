package com.aliyun.alink.linksdk.tmp.device.panel.linkselection;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IDevice;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener;
import com.aliyun.alink.linksdk.tmp.config.DefaultClientConfig;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.CmpNotifyManager;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.data.devdetail.DevDetailPayload;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.panel.data.AccessInfoPayload;
import com.aliyun.alink.linksdk.tmp.device.panel.data.GetPropPayload;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.LocalConnectParams;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.ProductInfoPayload;
import com.aliyun.alink.linksdk.tmp.device.panel.data.SetPropData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.StatusPayload;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.AlcsEventListenerWrapper;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.AlcsMulChannelEventListenerWrapper;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelDeviceLocalInitListener;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.SubsListenerWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.request.other.GetDeviceNetTypesSupportedRequest;
import com.aliyun.alink.linksdk.tmp.devicemodel.Property;
import com.aliyun.alink.linksdk.tmp.error.CommonError;
import com.aliyun.alink.linksdk.tmp.error.ParamsError;
import com.aliyun.alink.linksdk.tmp.error.UnknownError;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.service.TmpUTPointEx;
import com.aliyun.alink.linksdk.tmp.service.UTPoint;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.DeviceClassificationUtil;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.ResponseUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TgMeshHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tmp.utils.UsageTrackUtils;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import com.google.gson.reflect.TypeToken;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class LocalChannelDevice {
    private static final String TAG = "[Tmp]LocalChannelDevice";
    private static Map<String, ScheduledFuture> scheduledFutureMap = new HashMap();
    private IPanelDeviceLocalInitListener localInitListener;
    private DeviceBasicData mBasicData;
    private IDevice mDevice;
    private String mIotId;
    private AtomicBoolean mIsInited;
    private AtomicBoolean mIsLocalIniting;
    public AlcsEventListenerWrapper mListenerWrapper;
    private CopyOnWriteArrayList<Runnable> mLocalTaskList;
    protected WeakReference<MultipleChannelDevice> mMultipleChannelDeviceWeakReference;

    private class AccessInfoListener implements IConnectSendListener, ICloudProxyListener {
        private IPanelCallback mCallback;
        private IPanelDeviceLocalInitListener mLocalInitListener;

        public AccessInfoListener(IPanelCallback iPanelCallback, IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) {
            this.mCallback = iPanelCallback;
            this.mLocalInitListener = iPanelDeviceLocalInitListener;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0079  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void onComplete(com.aliyun.alink.linksdk.tmp.device.panel.data.AccessInfoPayload r12) throws java.lang.IllegalAccessException, org.json.JSONException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                Method dump skipped, instructions count: 288
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.AccessInfoListener.onComplete(com.aliyun.alink.linksdk.tmp.device.panel.data.AccessInfoPayload):void");
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
        public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            LocalChannelDevice.this.initEndCallback(this.mCallback, false, new UnknownError());
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
        public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
            if (aResponse == null || aResponse.data == null) {
                ALog.e(LocalChannelDevice.TAG, "queryAccessInfo aResponse error null callback:" + this.mCallback);
                LocalChannelDevice.this.initEndCallback(this.mCallback, false, new UnknownError());
                return;
            }
            ALog.d(LocalChannelDevice.TAG, "queryAccessInfo onResponse payload data:" + aResponse.data.toString().trim());
            onComplete((AccessInfoPayload) JSON.parseObject(aResponse.data.toString().trim(), new TypeReference<AccessInfoPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.AccessInfoListener.2
            }, new Feature[0]));
        }

        @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
        public void onFailure(String str, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            LocalChannelDevice.this.initEndCallback(this.mCallback, false, new UnknownError());
        }

        @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
        public void onResponse(String str, Object obj) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
            if (obj == null) {
                ALog.e(LocalChannelDevice.TAG, "queryAccessInfo aResponse error null callback:" + this.mCallback);
                LocalChannelDevice.this.initEndCallback(this.mCallback, false, new UnknownError());
                return;
            }
            ALog.d(LocalChannelDevice.TAG, "queryAccessInfo onResponse payload data:" + obj.toString().trim());
            onComplete((AccessInfoPayload) JSON.parseObject(obj.toString().trim(), new TypeReference<AccessInfoPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.AccessInfoListener.3
            }, new Feature[0]));
        }
    }

    public static class NextTaskCallback implements IPanelCallback {
        private WeakReference<LocalChannelDevice> mLocalChannelDeviceRef;
        private IPanelCallback mPanelCallback;

        public NextTaskCallback(LocalChannelDevice localChannelDevice, IPanelCallback iPanelCallback) {
            this.mLocalChannelDeviceRef = new WeakReference<>(localChannelDevice);
            this.mPanelCallback = iPanelCallback;
        }

        @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
        public void onComplete(boolean z2, Object obj) {
            LocalChannelDevice localChannelDevice = this.mLocalChannelDeviceRef.get();
            IPanelCallback iPanelCallback = this.mPanelCallback;
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(z2, obj);
            }
            if (localChannelDevice != null) {
                localChannelDevice.runNextTask();
            }
        }
    }

    public LocalChannelDevice(String str, TmpEnum.ChannelStrategy channelStrategy) {
        this(str, channelStrategy, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cacheDevDetail(String str, final String str2, final String str3) {
        TmpSdk.getCloudProxy().queryDeviceDetail(str, new ICloudProxyListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.19
            @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
            public void onFailure(String str4, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(LocalChannelDevice.TAG, "listBindingByDev onFailure aError:" + aError);
            }

            @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
            public void onResponse(String str4, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                DevDetailPayload devDetailPayload = (DevDetailPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<DevDetailPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.19.1
                }.getType());
                if (devDetailPayload == null || devDetailPayload.data == null) {
                    ALog.e(LocalChannelDevice.TAG, "listBindingByDev onResponse payload data null");
                } else {
                    DeviceManager.getInstance().saveDeviceDetailInfo(TextHelper.combineStr(str2, str3), GsonUtils.toJson(devDetailPayload.data));
                }
            }
        });
    }

    private void executeScene(JSONObject jSONObject, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (jSONObject != null) {
            jSONObject.remove("expand");
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put(AlinkConstants.KEY_CATEGORY_KEY, "Scene");
            ALog.d(TAG, "executeScene mIotId :" + this.mIotId + " data:" + jSONObject.toString());
            TgMeshManager.getInstance().sendMessge(this.mIotId, 0, "things.attribute.set", jSONObject.toString(), arrayMap, new IActionListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.9
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalChannelDevice.TAG, "TgMeshManager.sendMessge onFailure");
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new ErrorInfo(i2, str));
                    }
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalChannelDevice.TAG, "TgMeshManager.sendMessge success");
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(true, obj);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDevice(LocalConnectParams localConnectParams, final String str, final String str2, String str3, String str4, final IPanelCallback iPanelCallback, final IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initDevice productKey=" + str + "deviceName=" + str2 + "accessKey=" + str3 + "accessToken=" + str4 + "callback=" + iPanelCallback);
        DeviceBasicData deviceBasicData = new DeviceBasicData(false);
        this.mBasicData = deviceBasicData;
        deviceBasicData.setIotId(this.mIotId);
        this.mBasicData.setProductKey(str);
        this.mBasicData.setDeviceName(str2);
        ProductInfoPayload.ProductInfo productInfo = TmpStorage.getInstance().getProductInfo(this.mIotId);
        StringBuilder sb = new StringBuilder();
        sb.append(" productInfo:");
        sb.append(JSON.toJSONString(productInfo));
        ALog.d(TAG, sb.toString());
        if (productInfo != null && DeviceClassificationUtil.isBleMeshDeviceViaProductInfo(productInfo)) {
            this.mBasicData.setSupportedNetType(TmpEnum.DeviceNetType.NET_BLE_MESH.getValue());
        }
        TmpSdk.mHandler.post(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.17
            @Override // java.lang.Runnable
            public void run() {
                LocalChannelDevice localChannelDevice = LocalChannelDevice.this;
                localChannelDevice.cacheDevDetail(localChannelDevice.mIotId, str, str2);
            }
        });
        if (!isDeviceLocalFoud()) {
            ALog.e(TAG, "initDevice isDeviceLocalFoud not found return error");
            initEndCallback(iPanelCallback, false, new UnknownError());
            return;
        }
        UsageTrackUtils.reportUsage();
        this.mBasicData.setLocal(true);
        DeviceBasicData deviceBasicData2 = DeviceManager.getInstance().getDeviceBasicData(this.mBasicData.getDevId());
        if (deviceBasicData2 != null) {
            this.mBasicData.setPort(deviceBasicData2.getPort());
            this.mBasicData.setAddr(deviceBasicData2.getAddr());
        }
        ALog.d(TAG, "initLocalDevice mIsLocalIniting:" + isLocalIniting() + " callback:" + iPanelCallback);
        if (isLocalConnected()) {
            initEndCallback(iPanelCallback, true, null);
            return;
        }
        DefaultClientConfig defaultClientConfig = new DefaultClientConfig(this.mBasicData);
        defaultClientConfig.setAccessKey(str3);
        defaultClientConfig.setAccessToken(str4);
        defaultClientConfig.mConnectKeepType = localConnectParams.mConnectKeepType;
        if (this.mDevice == null) {
            this.mDevice = DeviceManager.getInstance().createDevice(defaultClientConfig);
        }
        if (this.mDevice == null) {
            ALog.e(TAG, "mDevice null");
            initEndCallback(iPanelCallback, true, null);
            return;
        }
        final JSONObject jSONObject = new JSONObject();
        this.mDevice.init(null, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.18
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
                ALog.e(LocalChannelDevice.TAG, "onFail errorInfo:" + errorInfo + " callback:" + iPanelCallback + " mDevice:" + LocalChannelDevice.this.mDevice);
                TmpEnum.DeviceState deviceState = TmpEnum.DeviceState.DISCONNECTED;
                if (LocalChannelDevice.this.mDevice != null) {
                    deviceState = LocalChannelDevice.this.mDevice.getDeviceState();
                }
                try {
                    jSONObject.put("type", LocalChannelDevice.this.getLocalConnectTypeFromConnectType());
                    jSONObject.put("localConnectionState", deviceState.getValue());
                } catch (JSONException e2) {
                    ALog.d(LocalChannelDevice.TAG, "onSuccess e2=" + e2);
                }
                LocalChannelDevice.this.localInitStateNotify(iPanelDeviceLocalInitListener, jSONObject);
                LocalChannelDevice.this.initEndCallback(iPanelCallback, false, new CommonError(errorInfo));
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalChannelDevice.TAG, "onSuccess returnValue:" + outputParams + " callback:" + iPanelCallback + " mDevice:" + LocalChannelDevice.this.mDevice);
                TmpEnum.DeviceState deviceState = TmpEnum.DeviceState.UNKNOW;
                if (LocalChannelDevice.this.mDevice == null) {
                    try {
                        jSONObject.put("type", LocalChannelDevice.this.getLocalConnectTypeFromConnectType());
                        jSONObject.put("localConnectionState", TmpEnum.DeviceState.DISCONNECTED.getValue());
                    } catch (JSONException e2) {
                        ALog.d(LocalChannelDevice.TAG, "onSuccess e=" + e2);
                    }
                    LocalChannelDevice.this.localInitStateNotify(iPanelDeviceLocalInitListener, jSONObject);
                    LocalChannelDevice.this.initEndCallback(iPanelCallback, false, new UnknownError());
                    return;
                }
                TmpEnum.DeviceState deviceState2 = LocalChannelDevice.this.mDevice.getDeviceState();
                try {
                    jSONObject.put("type", LocalChannelDevice.this.getLocalConnectTypeFromConnectType());
                    jSONObject.put("localConnectionState", deviceState2.getValue());
                } catch (JSONException e3) {
                    ALog.d(LocalChannelDevice.TAG, "onSuccess e2=" + e3);
                }
                LocalChannelDevice.this.localInitStateNotify(iPanelDeviceLocalInitListener, jSONObject);
                LocalChannelDevice.this.initEndCallback(iPanelCallback, true, new UnknownError());
            }
        });
        try {
            jSONObject.put("type", getLocalConnectTypeFromConnectType());
            jSONObject.put("localConnectionState", TmpEnum.DeviceState.CONNECTING.getValue());
        } catch (JSONException e2) {
            ALog.d(TAG, "onSuccess e2=" + e2);
        }
        localInitStateNotify(iPanelDeviceLocalInitListener, jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEndCallback(IPanelCallback iPanelCallback, boolean z2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setIsLocalIniting(false);
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(z2, obj);
        }
        runNextTask();
    }

    private void initWithCloud(final IPanelCallback iPanelCallback, final IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init  callback:" + iPanelCallback + " localInitListener:" + iPanelDeviceLocalInitListener);
        if (TmpSdk.getCloudProxy() == null) {
            initEndCallback(iPanelCallback, false, new UnknownError());
        } else {
            TmpSdk.getCloudProxy().queryProductInfo(this.mIotId, new ICloudProxyListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.3
                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onFailure(String str, AError aError) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
                    AccessInfo accessInfo;
                    StringBuilder sb = new StringBuilder();
                    sb.append("queryProductInfo onFailure id:");
                    sb.append(str);
                    sb.append(" error:");
                    sb.append(aError == null ? TmpConstant.GROUP_ROLE_UNKNOWN : aError.toString());
                    ALog.e(LocalChannelDevice.TAG, sb.toString());
                    TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(LocalChannelDevice.this.mIotId);
                    if (deviceInfo != null) {
                        accessInfo = TmpStorage.getInstance().getAccessInfo(deviceInfo.getId());
                        String macByDn = TmpStorage.getInstance().getMacByDn(deviceInfo.mDeviceName);
                        if (!TextUtils.isEmpty(macByDn)) {
                            DeviceManager deviceManager = DeviceManager.getInstance();
                            String str2 = deviceInfo.mProductKey;
                            deviceManager.updateDeviceInfo(str2, macByDn, str2, deviceInfo.mDeviceName);
                        }
                    } else {
                        accessInfo = null;
                    }
                    if (accessInfo != null) {
                        LocalChannelDevice.this.initDevice(new LocalConnectParams(), deviceInfo.mProductKey, deviceInfo.mDeviceName, accessInfo.mAccessKey, accessInfo.mAccessToken, iPanelCallback, iPanelDeviceLocalInitListener);
                    } else {
                        ALog.d(LocalChannelDevice.TAG, "queryProductInfo onFailure callback false return");
                        LocalChannelDevice.this.initEndCallback(iPanelCallback, false, new UnknownError());
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onResponse(String str, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ProductInfoPayload.ProductInfo productInfo;
                    if (obj == null) {
                        ALog.e(LocalChannelDevice.TAG, "queryProductInfo aResponse error null callback:" + iPanelCallback);
                        LocalChannelDevice.this.initEndCallback(iPanelCallback, false, new UnknownError());
                        return;
                    }
                    ProductInfoPayload productInfoPayload = (ProductInfoPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<ProductInfoPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.3.1
                    }.getType());
                    if (productInfoPayload == null || (productInfo = productInfoPayload.data) == null || TextUtils.isEmpty(productInfo.dataFormat)) {
                        ALog.e(LocalChannelDevice.TAG, "queryProductInfo payload error :" + iPanelCallback);
                        LocalChannelDevice.this.initEndCallback(iPanelCallback, false, new UnknownError());
                        return;
                    }
                    TmpStorage.getInstance().saveProductInfo(LocalChannelDevice.this.mIotId, productInfoPayload.data);
                    ALog.d(LocalChannelDevice.TAG, "queryProductInfo onResponse dataFormat:" + productInfoPayload.data.dataFormat + " payload:" + productInfoPayload);
                    TmpSdk.getCloudProxy().queryAccessInfo(LocalChannelDevice.this.mIotId, LocalChannelDevice.this.new AccessInfoListener(iPanelCallback, iPanelDeviceLocalInitListener));
                }
            });
        }
    }

    private boolean isDeviceLocalFoud() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mBasicData == null) {
            ALog.e(TAG, "isDeviceLocalFoud mBasicData null");
            return false;
        }
        ALog.d(TAG, "isDeviceLocalFoud pk:" + this.mBasicData.getProductKey() + " dn:" + this.mBasicData.getDeviceName() + " Device:" + this.mDevice);
        if (this.mBasicData.isBleMeshDevice() || DeviceManager.getInstance().getDeviceBasicData(this.mBasicData.getDevId()) != null) {
            return true;
        }
        ALog.d(TAG, "isDeviceLocalFoud false");
        return false;
    }

    private boolean isLocalDisconnected() {
        IDevice iDevice = this.mDevice;
        return iDevice == null || TmpEnum.DeviceState.DISCONNECTED == iDevice.getDeviceState() || TmpEnum.DeviceState.UNKNOW == this.mDevice.getDeviceState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void localInitStateNotify(IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener, JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "localInitStateNotify state:" + jSONObject + " IPanelDeviceLocalInitListener:" + iPanelDeviceLocalInitListener);
        if (iPanelDeviceLocalInitListener != null) {
            iPanelDeviceLocalInitListener.onDevStateChange(this.mIotId, jSONObject);
            return;
        }
        if (this.localInitListener != null) {
            ALog.d(TAG, "localInitStateNotify state:" + jSONObject + " listener为空: 检测 localInitListener=" + this.localInitListener);
            this.localInitListener.onDevStateChange(this.mIotId, jSONObject);
        }
    }

    private void updateDeviceProperties(final SetPropData setPropData) {
        if (scheduledFutureMap == null) {
            scheduledFutureMap = new HashMap();
        }
        if (scheduledFutureMap.containsKey(this.mIotId)) {
            scheduledFutureMap.get(this.mIotId).cancel(false);
            scheduledFutureMap.remove(this.mIotId);
        }
        scheduledFutureMap.put(this.mIotId, new ScheduledThreadPoolExecutor(1, new ThreadFactory() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.10
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull Runnable runnable) {
                return new Thread(runnable, LocalChannelDevice.TAG);
            }
        }).schedule(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.11
            @Override // java.lang.Runnable
            public void run() {
                LocalChannelDevice.scheduledFutureMap.remove(LocalChannelDevice.this.mIotId);
                CloudUtils.getProperties(LocalChannelDevice.this.mIotId, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.11.1
                    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                    public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(LocalChannelDevice.TAG, "CloudUtils getProperties: onFailure" + JSON.toJSONString(aError));
                    }

                    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                    public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(LocalChannelDevice.TAG, "CloudUtils getProperties: onResponse" + JSON.toJSONString(aResponse));
                        try {
                            com.alibaba.fastjson.JSONObject jSONObject = JSON.parseObject(String.valueOf(aResponse.data)).getJSONObject("data");
                            Map<String, ValueWrapper> map = setPropData.items;
                            if (map == null || map.size() <= 0) {
                                return;
                            }
                            for (String str : map.keySet()) {
                                if (!TextUtils.isEmpty(str) && map.get(str) != null && map.get(str).getValue() != null) {
                                    String strValueOf = String.valueOf(map.get(str).getValue());
                                    com.alibaba.fastjson.JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                                    if (jSONObject2 != null) {
                                        String string = jSONObject2.getString("value");
                                        ALog.d(LocalChannelDevice.TAG, "cloudValue=" + string + ",setValue=" + strValueOf);
                                        if (string != null) {
                                            AMessage aMessage = new AMessage();
                                            com.alibaba.fastjson.JSONObject jSONObject3 = new com.alibaba.fastjson.JSONObject();
                                            com.alibaba.fastjson.JSONObject jSONObject4 = new com.alibaba.fastjson.JSONObject();
                                            jSONObject4.put("iotId", (Object) LocalChannelDevice.this.mIotId);
                                            if (LocalChannelDevice.this.mDevice != null) {
                                                jSONObject4.put("deviceName", (Object) LocalChannelDevice.this.mDevice.getDevName());
                                            }
                                            jSONObject4.put("isSuccess", (Object) Boolean.valueOf(string.equals(strValueOf)));
                                            jSONObject4.put("controlFailed", (Object) Boolean.valueOf(!string.equals(strValueOf)));
                                            jSONObject3.put(str, (Object) jSONObject2);
                                            jSONObject3.put("CloudSequence", (Object) jSONObject.getJSONObject("CloudSequence"));
                                            jSONObject4.put("items", (Object) jSONObject3);
                                            aMessage.setData(jSONObject4);
                                            ALog.d(LocalChannelDevice.TAG, "上报设备属性 dataJO=" + jSONObject4.toJSONString());
                                            WeakReference<MultipleChannelDevice> weakReference = LocalChannelDevice.this.mMultipleChannelDeviceWeakReference;
                                            if (weakReference != null && weakReference.get() != null && LocalChannelDevice.this.mMultipleChannelDeviceWeakReference.get().getPanelEventCallback() != null) {
                                                LocalChannelDevice.this.mMultipleChannelDeviceWeakReference.get().getPanelEventCallback().onNotify(LocalChannelDevice.this.mIotId, TmpConstant.MQTT_TOPIC_PROPERTIES, jSONObject4.toJSONString());
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            ALog.e(LocalChannelDevice.TAG, "getProperties onComplete updatePropertyCacheAndNotify error:" + e2.toString());
                        }
                    }
                });
            }
        }, 3000L, TimeUnit.MILLISECONDS));
    }

    public void addTask(Runnable runnable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "addTask runnable:" + runnable);
        if (runnable == null) {
            return;
        }
        this.mLocalTaskList.add(runnable);
    }

    public TmpEnum.ConnectType getConnectType() {
        IDevice iDevice = this.mDevice;
        return iDevice == null ? TmpEnum.ConnectType.CONNECT_TYPE_UNKNOWN : iDevice.getConnectType();
    }

    public String getDeviceName() {
        DeviceBasicData deviceBasicData = this.mBasicData;
        if (deviceBasicData != null) {
            return deviceBasicData.getDeviceName();
        }
        return null;
    }

    public int getLocalConnectTypeFromConnectType() {
        int i2 = getConnectType().value;
        if (i2 == TmpEnum.ConnectType.CONNECT_TYPE_BLE.value) {
            return 2;
        }
        if (i2 == TmpEnum.ConnectType.CONNECT_TYPE_COAP.value) {
            return 1;
        }
        return i2 == TmpEnum.ConnectType.CONNECT_TYPE_TGMESH.value ? 5 : 0;
    }

    public void getLocalConnectionState(IPanelCallback iPanelCallback) {
        TmpEnum.DeviceState deviceState;
        IDevice iDevice;
        TmpEnum.DeviceState deviceState2 = TmpEnum.DeviceState.DISCONNECTED;
        ALog.d(TAG, "getLocalConnectionState islocaliniting:" + isLocalIniting() + " isIniting:" + isInit());
        if (isDeviceLocalFoud() && (iDevice = this.mDevice) != null) {
            deviceState = iDevice.getDeviceState();
        } else if (isLocalIniting() && isDeviceLocalFoud()) {
            deviceState = TmpEnum.DeviceState.CONNECTING;
        } else if (isLocalConnected() && isDeviceLocalFoud()) {
            deviceState = TmpEnum.DeviceState.CONNECTED;
        } else {
            isDeviceLocalFoud();
            deviceState = deviceState2;
        }
        if (!isBleMeshDevice() || deviceState != TmpEnum.DeviceState.CONNECTED || TgMeshHelper.isMeshNodeReachable(this.mIotId)) {
            deviceState2 = deviceState;
        }
        JSONObject jSONObject = new JSONObject();
        int localConnectTypeFromConnectType = getLocalConnectTypeFromConnectType();
        try {
            jSONObject.put("type", localConnectTypeFromConnectType);
            jSONObject.put("localConnectionState", deviceState2.getValue());
        } catch (JSONException e2) {
            ALog.w(TAG, "getLocalConnectionState exception=" + e2);
            e2.printStackTrace();
        }
        ALog.d(TAG, "getLocalConnectionState() type:" + localConnectTypeFromConnectType + ", getConnectType=" + getConnectType() + "deviceState:" + deviceState2);
        iPanelCallback.onComplete(true, jSONObject);
    }

    public void getMeshStatus(IPanelCallback iPanelCallback) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        TmpEnum.DeviceState deviceState = TmpEnum.DeviceState.DISCONNECTED;
        if (TgMeshHelper.isMeshNodeReachable(this.mIotId)) {
            deviceState = TmpEnum.DeviceState.CONNECTED;
        }
        try {
            jSONObject.put("status", deviceState.getValue());
            jSONObject.put("time", System.currentTimeMillis());
            iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
        } catch (JSONException e2) {
            e2.printStackTrace();
            iPanelCallback.onComplete(false, new ParamsError());
        }
    }

    public String getProductKey() {
        DeviceBasicData deviceBasicData = this.mBasicData;
        if (deviceBasicData != null) {
            return deviceBasicData.getProductKey();
        }
        return null;
    }

    public void getProperties(final IPanelCallback iPanelCallback, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Runnable runnable;
        ALog.d(TAG, "getProperties  callback:" + iPanelCallback + " bCache:" + z2);
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback null");
            return;
        }
        if (this.mBasicData != null && !isDeviceLocalFoud()) {
            iPanelCallback.onComplete(false, new UnknownError());
            return;
        }
        if (isLocalConnected()) {
            getProperties(new NextTaskCallback(this, iPanelCallback));
            return;
        }
        if (z2) {
            runnable = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.4
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    LocalChannelDevice localChannelDevice = LocalChannelDevice.this;
                    localChannelDevice.getProperties(new NextTaskCallback(localChannelDevice, iPanelCallback), false);
                }
            };
        } else {
            iPanelCallback.onComplete(false, new UnknownError());
            runnable = null;
        }
        startLocalConnectAndCacheTask(runnable, z2);
    }

    public void getStatus(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "simulateGetStatus callback:" + iPanelCallback);
        StatusPayload statusPayload = new StatusPayload();
        statusPayload.code = 200;
        statusPayload.id = UUID.randomUUID().toString();
        statusPayload.data.time = System.currentTimeMillis();
        statusPayload.data.status = 1;
        iPanelCallback.onComplete(true, GsonUtils.toJson(statusPayload));
    }

    public void init(LocalConnectParams localConnectParams, IPanelCallback iPanelCallback, IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) {
        ALog.d(TAG, "init callback:" + iPanelCallback + " localInitListener:" + iPanelDeviceLocalInitListener + " connectParams:" + localConnectParams);
        if (iPanelDeviceLocalInitListener != null) {
            this.localInitListener = iPanelDeviceLocalInitListener;
        }
        this.mIsInited.set(true);
        if (isLocalIniting()) {
            initEndCallback(iPanelCallback, false, new UnknownError());
            return;
        }
        if (isLocalConnected()) {
            initEndCallback(iPanelCallback, true, new UnknownError());
            return;
        }
        setIsLocalIniting(true);
        ProductInfoPayload.ProductInfo productInfo = TmpStorage.getInstance().getProductInfo(this.mIotId);
        if (productInfo == null) {
            initWithCloud(iPanelCallback, iPanelDeviceLocalInitListener);
            return;
        }
        final TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(this.mIotId);
        if (deviceInfo == null) {
            initWithCloud(iPanelCallback, iPanelDeviceLocalInitListener);
            return;
        }
        AccessInfo accessInfo = TmpStorage.getInstance().getAccessInfo(deviceInfo.getId());
        if (accessInfo == null) {
            initWithCloud(iPanelCallback, iPanelDeviceLocalInitListener);
            return;
        }
        String deviceSupportedNetTypesByPk = DeviceShadowMgr.getInstance().getDeviceSupportedNetTypesByPk(deviceInfo.mProductKey);
        GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse = !TextUtils.isEmpty(deviceSupportedNetTypesByPk) ? (GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject(deviceSupportedNetTypesByPk, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class) : null;
        initDevice(new LocalConnectParams(), deviceInfo.mProductKey, deviceInfo.mDeviceName, accessInfo.mAccessKey, accessInfo.mAccessToken, iPanelCallback, iPanelDeviceLocalInitListener);
        String macByDn = TmpStorage.getInstance().getMacByDn(deviceInfo.mDeviceName);
        if (!TextUtils.isEmpty(macByDn)) {
            DeviceManager deviceManager = DeviceManager.getInstance();
            String str = deviceInfo.mProductKey;
            deviceManager.updateDeviceInfo(str, macByDn, str, deviceInfo.mDeviceName);
        } else if (isBleMeshDevice()) {
            DeviceManager deviceManager2 = DeviceManager.getInstance();
            String str2 = deviceInfo.mProductKey;
            deviceManager2.updateDeviceInfo(str2, macByDn, str2, deviceInfo.mDeviceName);
        } else if ("NET_BT".equalsIgnoreCase(productInfo.netType) || "NET_OTHER".equalsIgnoreCase(productInfo.netType) || (getDeviceNetTypesSupportedResponse != null && DevService.isDeviceWifiAndBleCombo(TmpEnum.DeviceNetType.formatDeviceNetType((List) getDeviceNetTypesSupportedResponse.data)))) {
            CloudUtils.getDeviceExtendProperty(this.mIotId, TmpConstant.DATA_KEY_DEVICENAME, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.2
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    StringBuilder sb = new StringBuilder();
                    sb.append("getDeviceExtendProperty onFailure error:");
                    sb.append(aError == null ? "" : aError.toString());
                    ALog.w(LocalChannelDevice.TAG, sb.toString());
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        ALog.e(LocalChannelDevice.TAG, "getDeviceExtendProperty onResponse response or response.data empty");
                        return;
                    }
                    String string = obj.toString();
                    if (TextUtils.isEmpty(string)) {
                        ALog.e(LocalChannelDevice.TAG, "getDeviceExtendProperty data empty");
                        return;
                    }
                    try {
                        CommonResponsePayload commonResponsePayload = (CommonResponsePayload) JSON.parseObject(string, CommonResponsePayload.class);
                        if (commonResponsePayload == null || TextUtils.isEmpty((CharSequence) commonResponsePayload.getData())) {
                            return;
                        }
                        String str3 = (String) commonResponsePayload.getData();
                        DeviceManager deviceManager3 = DeviceManager.getInstance();
                        TmpStorage.DeviceInfo deviceInfo2 = deviceInfo;
                        String str4 = deviceInfo2.mProductKey;
                        deviceManager3.updateDeviceInfo(str4, str3, str4, deviceInfo2.mDeviceName);
                    } catch (Exception e2) {
                        ALog.e(LocalChannelDevice.TAG, "getDeviceExtendProperty onResponse error:" + e2.toString());
                    }
                }
            });
        }
    }

    public void invokeService(final String str, final PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback, boolean z2) {
        Runnable runnable;
        ALog.d(TAG, "invokeServiceLocal params:" + str + " callback:" + iPanelCallback + " bCache:" + z2);
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback null");
            return;
        }
        if (isBleMeshDevice()) {
            iPanelCallback.onComplete(false, new UnknownError());
            ALog.e(TAG, "mesh does not support local invoke service");
        } else {
            if (isLocalConnected()) {
                invokeService(str, panelMethodExtraData, new NextTaskCallback(this, iPanelCallback));
                return;
            }
            if (z2) {
                runnable = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.12
                    @Override // java.lang.Runnable
                    public void run() {
                        LocalChannelDevice localChannelDevice = LocalChannelDevice.this;
                        localChannelDevice.invokeService(str, panelMethodExtraData, new NextTaskCallback(localChannelDevice, iPanelCallback), false);
                    }
                };
            } else {
                iPanelCallback.onComplete(false, new ParamsError());
                runnable = null;
            }
            startLocalConnectAndCacheTask(runnable, z2);
        }
    }

    public boolean isBleMeshDevice() {
        DeviceBasicData deviceBasicData = this.mBasicData;
        if (deviceBasicData != null) {
            return deviceBasicData.isBleMeshDevice();
        }
        return false;
    }

    public boolean isInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isInit iotId: mIotId :" + this.mIsInited.get());
        return this.mIsInited.get();
    }

    public boolean isLocalConnected() {
        if (isBleMeshDevice() && !TgMeshHelper.isMeshNodeReachable(this.mIotId)) {
            TgMeshHelper.connect(this.mIotId);
        }
        IDevice iDevice = this.mDevice;
        boolean z2 = false;
        if (iDevice != null && TmpEnum.DeviceState.CONNECTED == iDevice.getDeviceState()) {
            z2 = true;
        }
        ALog.d(TAG, "isLocalConnected isLocalConnected:" + z2 + " mDevice:" + this.mDevice);
        return z2;
    }

    public boolean isLocalIniting() {
        IDevice iDevice;
        boolean z2 = this.mIsLocalIniting.get() || ((iDevice = this.mDevice) != null && iDevice.getDeviceState() == TmpEnum.DeviceState.CONNECTING);
        ALog.d(TAG, "isLocalIniting :" + z2);
        return z2;
    }

    public synchronized void runNextTask() {
        ALog.d(TAG, "runNextTask mLocalTaskList:" + this.mLocalTaskList.size());
        CopyOnWriteArrayList<Runnable> copyOnWriteArrayList = this.mLocalTaskList;
        if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
            Runnable runnableRemove = this.mLocalTaskList.remove(0);
            if (runnableRemove != null) {
                runnableRemove.run();
            }
        }
    }

    public void setIsLocalIniting(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setIsLocalIniting :" + z2 + " mIsLocalIniting:" + this.mIsLocalIniting.get());
        this.mIsLocalIniting.compareAndSet(z2 ^ true, z2);
    }

    public void setProperties(final String str, final PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback, boolean z2) {
        Runnable runnable;
        ALog.d(TAG, "setProperties params:" + str + " callback:" + iPanelCallback + " bCache:" + z2);
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback null");
            return;
        }
        if (isLocalConnected()) {
            setProperties(str, panelMethodExtraData, new NextTaskCallback(this, iPanelCallback));
            return;
        }
        if (z2) {
            runnable = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.6
                @Override // java.lang.Runnable
                public void run() {
                    LocalChannelDevice localChannelDevice = LocalChannelDevice.this;
                    localChannelDevice.setProperties(str, panelMethodExtraData, new NextTaskCallback(localChannelDevice, iPanelCallback), false);
                }
            };
        } else {
            iPanelCallback.onComplete(false, new ParamsError());
            runnable = null;
        }
        startLocalConnectAndCacheTask(runnable, z2);
    }

    public void startLocalConnect(IPanelCallback iPanelCallback) {
        ALog.d(TAG, "startLocalConnect callback:" + iPanelCallback);
        if (iPanelCallback == null) {
            ALog.e(TAG, "startLocalConnect callback null");
            return;
        }
        if (isLocalConnected() || isLocalIniting()) {
            iPanelCallback.onComplete(true, null);
            return;
        }
        LocalConnectParams localConnectParams = new LocalConnectParams();
        localConnectParams.mConnectKeepType = AlcsPalConst.CONNECT_KEEP_TYPE_PERSISTENCE;
        init(localConnectParams, iPanelCallback, this.localInitListener);
    }

    public void startLocalConnectAndCacheTask(Runnable runnable, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (isBleMeshDevice()) {
            TgMeshHelper.connect(this.mIotId);
        }
        addTask(runnable);
        if (isLocalConnected()) {
            runNextTask();
            return;
        }
        if (isLocalIniting()) {
            if (PersistentNet.getInstance().getConnectState() == PersistentConnectState.CONNECTED) {
                runNextTask();
            }
        } else if (z2) {
            init(new LocalConnectParams(), new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.20
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z3, Object obj) {
                    LocalChannelDevice.this.runNextTask();
                }
            }, null);
        } else {
            runNextTask();
        }
    }

    public void stopLocalConnect(final IPanelCallback iPanelCallback) {
        ALog.d(TAG, "stopLocalConnect() called with: callback = [" + iPanelCallback + "]");
        if (iPanelCallback == null) {
            ALog.e(TAG, "stopLocalConnect callback null");
            return;
        }
        if (!isDeviceLocalFoud()) {
            iPanelCallback.onComplete(true, null);
            return;
        }
        if (!isLocalDisconnected() && !isLocalIniting()) {
            iPanelCallback.onComplete(true, null);
            return;
        }
        addTask(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.16
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (LocalChannelDevice.this.mDevice != null) {
                    LocalChannelDevice.this.mDevice.unInit();
                    ALog.d(LocalChannelDevice.TAG, "stopLocalConnect setmDevice to null." + Log.getStackTraceString(new Throwable()));
                    LocalChannelDevice.this.mDevice = null;
                }
                iPanelCallback.onComplete(true, null);
            }
        });
        if (isLocalConnected()) {
            runNextTask();
        }
    }

    public void subAllEvents(final IPanelEventCallback iPanelEventCallback, final IPanelCallback iPanelCallback, boolean z2) {
        Runnable runnable;
        ALog.d(TAG, "subAllEvents listener:" + iPanelEventCallback + " callback:" + iPanelCallback + " bCache:" + z2 + " device:" + this.mDevice);
        if (iPanelEventCallback == null) {
            ALog.e(TAG, "getProperties listener null");
            return;
        }
        if (this.mBasicData != null && !isDeviceLocalFoud()) {
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(false, new ParamsError());
                return;
            }
            return;
        }
        CmpNotifyManager.getInstance().addHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_PROPERTIES, new SubsListenerWrapper(this.mIotId, iPanelEventCallback));
        if (isLocalConnected()) {
            subAllEvents(iPanelEventCallback, new NextTaskCallback(this, iPanelCallback));
            return;
        }
        if (z2) {
            runnable = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.15
                @Override // java.lang.Runnable
                public void run() {
                    LocalChannelDevice localChannelDevice = LocalChannelDevice.this;
                    localChannelDevice.subAllEvents(iPanelEventCallback, new NextTaskCallback(localChannelDevice, iPanelCallback), false);
                }
            };
        } else {
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(false, new ParamsError());
            }
            runnable = null;
        }
        startLocalConnectAndCacheTask(runnable, z2);
    }

    public void uninit() {
        ALog.d(TAG, "uninit mDevice:" + this.mDevice + ", stackTrace=" + Log.getStackTraceString(new Throwable()));
        IDevice iDevice = this.mDevice;
        if (iDevice != null) {
            AlcsEventListenerWrapper alcsEventListenerWrapper = this.mListenerWrapper;
            if (alcsEventListenerWrapper != null) {
                iDevice.removeDeviceStateChangeListener(alcsEventListenerWrapper);
                this.mListenerWrapper = null;
            }
            this.mDevice.unInit();
            this.mDevice = null;
        }
        this.mIsLocalIniting.set(false);
        this.mIsInited.set(false);
        CmpNotifyManager.getInstance().removeHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_PROPERTIES);
    }

    public LocalChannelDevice(String str, TmpEnum.ChannelStrategy channelStrategy, MultipleChannelDevice multipleChannelDevice) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mLocalTaskList = new CopyOnWriteArrayList<>();
        this.mIotId = str;
        this.mIsLocalIniting = new AtomicBoolean(false);
        this.mIsInited = new AtomicBoolean(false);
        this.mMultipleChannelDeviceWeakReference = new WeakReference<>(multipleChannelDevice);
        TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(this.mIotId);
        if (deviceInfo != null) {
            DeviceBasicData deviceBasicData = new DeviceBasicData(false);
            this.mBasicData = deviceBasicData;
            deviceBasicData.setIotId(this.mIotId);
            this.mBasicData.setProductKey(deviceInfo.mProductKey);
            this.mBasicData.setDeviceName(deviceInfo.mDeviceName);
            String macByDn = TmpStorage.getInstance().getMacByDn(deviceInfo.mDeviceName);
            if (!TextUtils.isEmpty(macByDn)) {
                DeviceManager deviceManager = DeviceManager.getInstance();
                String str2 = deviceInfo.mProductKey;
                deviceManager.updateDeviceInfo(str2, macByDn, str2, deviceInfo.mDeviceName);
            }
            ThreadTools.submitTask(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.1
                @Override // java.lang.Runnable
                public void run() {
                    LocalChannelDevice.this.init(new LocalConnectParams(), null, null);
                }
            }, true, 600);
        }
        ALog.d(TAG, "LocalChannelDevice iotId:" + str);
    }

    private void setProperties(String str, PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, ValueWrapper> map;
        ALog.d(TAG, "setPropertiesinner params:" + str + " callback:" + iPanelCallback + " mDevice:" + this.mDevice);
        if (iPanelCallback == null) {
            ALog.e(TAG, "setProperties callback null");
            return;
        }
        if (this.mDevice == null) {
            iPanelCallback.onComplete(false, new ParamsError());
            ALog.e(TAG, "setProperties mDevice null");
            return;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (TextUtils.equals(jSONObject.optString("expand"), "Scene")) {
                    executeScene(jSONObject, iPanelCallback);
                    return;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        final UTPoint uTPointCreateUTPoint = UTPoint.createUTPoint(panelMethodExtraData, "/thing/properties/set");
        if (uTPointCreateUTPoint != null) {
            uTPointCreateUTPoint.trackStart();
        }
        final TmpUTPointEx tmpUTPointEx = new TmpUTPointEx(this.mIotId, uTPointCreateUTPoint == null ? "" : uTPointCreateUTPoint.getPerformanceId(), "alcs");
        tmpUTPointEx.trackStart();
        SetPropData setPropData = (SetPropData) GsonUtils.fromJson(str, new TypeToken<SetPropData>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.7
        }.getType());
        ArrayList arrayList = new ArrayList();
        if (setPropData != null && (map = setPropData.items) != null) {
            for (Map.Entry<String, ValueWrapper> entry : map.entrySet()) {
                arrayList.add(new KeyValuePair(entry.getKey(), entry.getValue()));
            }
        }
        this.mDevice.setPropertyValue(new ExtraData(panelMethodExtraData), arrayList, (Object) null, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.8
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalChannelDevice.TAG, "setProperties onFail:" + errorInfo);
                UTPoint uTPoint = uTPointCreateUTPoint;
                if (uTPoint != null) {
                    uTPoint.trackEnd();
                }
                tmpUTPointEx.trackEnd(errorInfo == null ? -1 : errorInfo.getErrorCode());
                iPanelCallback.onComplete(false, new CommonError(errorInfo));
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalChannelDevice.TAG, "setProperties onSuccess:" + outputParams);
                UTPoint uTPoint = uTPointCreateUTPoint;
                if (uTPoint != null) {
                    uTPoint.trackEnd();
                }
                tmpUTPointEx.trackEnd(0);
                iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(new JSONObject()));
            }
        });
    }

    private void getProperties(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getProperties  callback:" + iPanelCallback + " mDevice:" + this.mDevice);
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback null");
            return;
        }
        if (this.mDevice == null) {
            iPanelCallback.onComplete(false, new UnknownError());
            ALog.e(TAG, "getProperties mDevice null");
            return;
        }
        ArrayList arrayList = new ArrayList();
        List<Property> properties = this.mDevice.getProperties();
        if (properties != null && !properties.isEmpty()) {
            for (Property property : properties) {
                if (property != null) {
                    arrayList.add(property.getIdentifier());
                }
            }
        }
        this.mDevice.getPropertyValue(arrayList, null, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.5
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) {
                iPanelCallback.onComplete(false, new CommonError(errorInfo));
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                JSONObject jSONObject = null;
                if (LocalChannelDevice.this.mDevice == null) {
                    onFail(obj, null);
                    return;
                }
                Map<String, ValueWrapper> allPropertyValue = LocalChannelDevice.this.mDevice.getAllPropertyValue();
                if (allPropertyValue != null && !allPropertyValue.isEmpty()) {
                    GetPropPayload getPropPayload = new GetPropPayload();
                    for (Map.Entry<String, ValueWrapper> entry : allPropertyValue.entrySet()) {
                        ALog.d(LocalChannelDevice.TAG, "key:" + entry.getKey() + " value:" + entry.getValue());
                        getPropPayload.data.put(entry.getKey(), new GetPropPayload.PropItem(0L, entry.getValue()));
                    }
                    ALog.d(LocalChannelDevice.TAG, "getProperties curTime:0 data::" + getPropPayload);
                    try {
                        jSONObject = new JSONObject(GsonUtils.toJson(getPropPayload.data));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (jSONObject != null) {
                    iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
                } else {
                    ALog.d(LocalChannelDevice.TAG, "getPropertyValue onSuccess object null");
                    iPanelCallback.onComplete(false, new ParamsError());
                }
            }
        });
    }

    private void invokeService(String str, PanelMethodExtraData panelMethodExtraData, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "invokeServiceLocal params:" + str + " callback:" + iPanelCallback + " mDevice:" + this.mDevice);
        if (iPanelCallback == null) {
            ALog.e(TAG, "setProperties callback null");
            return;
        }
        if (this.mDevice == null) {
            iPanelCallback.onComplete(false, new ParamsError());
            ALog.e(TAG, "setProperties mDevice null");
            return;
        }
        if (isBleMeshDevice()) {
            iPanelCallback.onComplete(false, new UnknownError());
            ALog.e(TAG, "mesh does not support local invoke service");
            return;
        }
        InvokeServiceData invokeServiceData = (InvokeServiceData) GsonUtils.fromJson(str, new TypeToken<InvokeServiceData>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.13
        }.getType());
        ArrayList arrayList = new ArrayList();
        Map<String, ValueWrapper> map = invokeServiceData.args;
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, ValueWrapper> entry : invokeServiceData.args.entrySet()) {
                arrayList.add(new KeyValuePair(entry.getKey(), entry.getValue()));
            }
        }
        this.mDevice.invokeService(invokeServiceData.identifier, arrayList, new ExtraData(panelMethodExtraData), (Object) null, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalChannelDevice.14
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalChannelDevice.TAG, "invokeService onFail:" + errorInfo);
                iPanelCallback.onComplete(false, new CommonError(errorInfo));
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalChannelDevice.TAG, "invokeService onSuccess:" + outputParams);
                String json = GsonUtils.toJson(outputParams);
                JSONObject jSONObject = new JSONObject();
                try {
                    if (!TextUtils.isEmpty(json)) {
                        jSONObject = new JSONObject(json);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
            }
        });
    }

    private void subAllEvents(IPanelEventCallback iPanelEventCallback, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "private subAllEvents listener:" + iPanelEventCallback + " callback:" + iPanelCallback + " mDevice:" + this.mDevice);
        if (iPanelEventCallback == null) {
            ALog.e(TAG, "subAllEvents callback null");
            return;
        }
        IDevice iDevice = this.mDevice;
        if (iDevice == null) {
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(false, new ParamsError());
                return;
            }
            return;
        }
        AlcsEventListenerWrapper alcsEventListenerWrapper = this.mListenerWrapper;
        if (alcsEventListenerWrapper != null) {
            iDevice.removeDeviceStateChangeListener(alcsEventListenerWrapper);
        }
        AlcsMulChannelEventListenerWrapper alcsMulChannelEventListenerWrapper = new AlcsMulChannelEventListenerWrapper(this, this.mBasicData, iPanelCallback, iPanelEventCallback);
        this.mListenerWrapper = alcsMulChannelEventListenerWrapper;
        this.mDevice.addDeviceStateChangeListener(alcsMulChannelEventListenerWrapper);
        this.mDevice.subAllEvents(null, this.mListenerWrapper);
    }
}
