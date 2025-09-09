package com.aliyun.alink.linksdk.tmp.device.deviceshadow;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.connect.a;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.CmpNotifyManager;
import com.aliyun.alink.linksdk.tmp.data.deviceshadow.UpdateParam;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.component.AliasNotifyData;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.component.PropertyAlias;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.request.DeviceExtended.GetDeviceExtendRequest;
import com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.other.GetDeviceNetTypesSupportedRequest;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CheckMeshMessage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.breeze.mix.ConnectionCallback;
import com.aliyun.iot.breeze.mix.MixBleDelegate;
import com.aliyun.iot.breeze.mix.MixBleDevice;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes2.dex */
public class DeviceShadowMgr implements IConnectNotifyListener, INotifyHandler {
    private static final String DEVICESHADOW_CACHE_FILE = "deviceShadow";
    private static final String DEVICESHADOW_DETAILINFO_PRE_KEY = "device_detailInfo_";
    private static final String DEVICESHADOW_PROPERTY_PRE_KEY = "device_property_";
    private static final String DEVICESHADOW_STATUS_PRE_KEY = "device_status_";
    private static final String DEVICESHADOW_SUPPORTED_NETTYPE_PRE_KEY = "device_supported_nettype_";
    private static final String DEVICESHADOW_SUPPORT_DOWN_ALL_PROPS_PRE_KEY = "device_supportDownAllProps_";
    private static final String DEVICESHADOW_TSL_PRE_KEY = "device_tsl_";
    private static final int DEVICESHADOW_VERSION = 1;
    private static final String DEVICESHADOW_WIFISTATUS_PRE_KEY = "device_wifistatus_";
    public static final String TAG = "[Tmp]DeviceShadowMgr";
    private DiskLruHelper mDiskLruCacheHelper;
    private MemoryLruHelper mMemoryLruHelper;
    private PropertyAlias mPropertyAlias;

    private static class InstanceHolder {
        private static DeviceShadowMgr mInstance = new DeviceShadowMgr();

        private InstanceHolder() {
        }
    }

    public static String getCacheKey(String str, String str2) {
        return (str + str2).toLowerCase();
    }

    public static DeviceShadowMgr getInstance() {
        return InstanceHolder.mInstance;
    }

    public boolean comparePropertyValue(JSONObject jSONObject, JSONObject jSONObject2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (jSONObject == null) {
            ALog.w(TAG, "comparePropertyValue newPropertyValue empty false");
            return false;
        }
        if (jSONObject2 == null) {
            ALog.w(TAG, "comparePropertyValue oldPropertyValue empty true");
            return true;
        }
        Object obj = jSONObject.get("value");
        Long lValueOf = jSONObject.getLong("time");
        if (lValueOf == null) {
            lValueOf = Long.valueOf(System.currentTimeMillis());
        }
        Object obj2 = jSONObject2.get("value");
        Long l2 = jSONObject2.getLong("time");
        if (l2 == null) {
            l2 = 0L;
        }
        if (obj2 == null) {
            ALog.w(TAG, "comparePropertyValue oldValue empty true");
            return true;
        }
        if (obj == null) {
            ALog.w(TAG, "comparePropertyValue newValue empty false");
            return false;
        }
        boolean z2 = lValueOf.longValue() >= l2.longValue();
        ALog.i(TAG, "comparePropertyValue newValue:" + obj + " oldValue:" + obj2 + " newTime:" + lValueOf + " oldTime: " + l2 + " isNeedUpdate:" + z2);
        return z2;
    }

    public void deleteDeviceShadow(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "deleteDeviceShadow iotId:" + str + " processListener:" + iProcessListener);
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "deleteDeviceShadow empty error");
            return;
        }
        this.mDiskLruCacheHelper.deleteValue(getCacheKey(DEVICESHADOW_TSL_PRE_KEY, str));
        this.mDiskLruCacheHelper.deleteValue(getCacheKey(DEVICESHADOW_DETAILINFO_PRE_KEY, str));
        this.mMemoryLruHelper.deleteValue(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str));
        this.mMemoryLruHelper.deleteValue(getCacheKey(DEVICESHADOW_STATUS_PRE_KEY, str));
        this.mDiskLruCacheHelper.deleteValue(getCacheKey(DEVICESHADOW_SUPPORT_DOWN_ALL_PROPS_PRE_KEY, str));
        TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(str);
        if (deviceInfo != null) {
            TmpStorage.getInstance().saveAccessInfo(null, null, deviceInfo.getId());
        }
        if (iProcessListener != null) {
            iProcessListener.onSuccess(null);
        }
    }

    public String getCachedProps(String str) {
        return this.mMemoryLruHelper.getString(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str));
    }

    public void getDetailInfo(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iProcessListener == null) {
            ALog.e(TAG, "getDetailInfo processListener empty");
            return;
        }
        String cacheKey = getCacheKey(DEVICESHADOW_DETAILINFO_PRE_KEY, str);
        String string = this.mDiskLruCacheHelper.getString(cacheKey);
        StringBuilder sb = new StringBuilder();
        sb.append("getDeviceDetailInfo iotId: ");
        sb.append(str);
        sb.append(" processListener:");
        sb.append(iProcessListener);
        sb.append(" cacheKey:");
        sb.append(cacheKey);
        sb.append(" isCallbacked:");
        boolean z2 = false;
        sb.append(false);
        sb.append(" deviceDetailInfo:");
        sb.append(string);
        ALog.d(TAG, sb.toString());
        if (!TextUtils.isEmpty(string)) {
            CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
            commonResponsePayload.setCode(200);
            try {
                commonResponsePayload.setData(JSON.parseObject(string));
                iProcessListener.onSuccess(JSON.toJSONString(commonResponsePayload));
            } catch (Exception e2) {
                ALog.e(TAG, "getDetailInfo onSuccess error:" + e2.toString());
            }
            z2 = true;
        }
        updateDetailInfoByCloud(str, z2, iProcessListener);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.CharSequence, java.lang.String] */
    public void getDeviceExtend(String str, String str2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String cacheKey = getCacheKey(str2, str);
        ?? string = this.mDiskLruCacheHelper.getString(cacheKey);
        if (TextUtils.isEmpty(string)) {
            GateWayRequest getDeviceExtendRequest = new GetDeviceExtendRequest(str, str2);
            getDeviceExtendRequest.sendRequest(getDeviceExtendRequest, new IGateWayRequestListener<GetDeviceExtendRequest, GetDeviceExtendRequest.DeviceExtendGetResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.11
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onFail(GetDeviceExtendRequest getDeviceExtendRequest2, AError aError) {
                    IProcessListener iProcessListener2 = iProcessListener;
                    if (iProcessListener2 != null) {
                        iProcessListener2.onFail(new ErrorInfo(aError));
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onSuccess(GetDeviceExtendRequest getDeviceExtendRequest2, GetDeviceExtendRequest.DeviceExtendGetResponse deviceExtendGetResponse) {
                    DeviceShadowMgr.this.mDiskLruCacheHelper.saveValue(cacheKey, (String) deviceExtendGetResponse.data);
                    IProcessListener iProcessListener2 = iProcessListener;
                    if (iProcessListener2 != null) {
                        iProcessListener2.onSuccess(JSON.toJSONString(deviceExtendGetResponse));
                    }
                }
            });
            return;
        }
        GetDeviceExtendRequest.DeviceExtendGetResponse deviceExtendGetResponse = new GetDeviceExtendRequest.DeviceExtendGetResponse();
        deviceExtendGetResponse.data = string;
        deviceExtendGetResponse.code = 200;
        if (iProcessListener != null) {
            iProcessListener.onSuccess(JSON.toJSONString(deviceExtendGetResponse));
        }
    }

    public void getDeviceSupportedNetTypesByIotId(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        String deviceSupportedNetTypesByIotId = getDeviceSupportedNetTypesByIotId(str);
        if (TextUtils.isEmpty(deviceSupportedNetTypesByIotId)) {
            z2 = false;
        } else {
            if (iProcessListener != null) {
                iProcessListener.onSuccess(deviceSupportedNetTypesByIotId);
            }
            z2 = true;
        }
        updateDeviceNetTypesSupportedByIotId(str, !z2, iProcessListener);
    }

    public void getDeviceSupportedNetTypesByPk(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        String deviceSupportedNetTypesByPk = getDeviceSupportedNetTypesByPk(str);
        if (TextUtils.isEmpty(deviceSupportedNetTypesByPk)) {
            z2 = false;
        } else {
            if (iProcessListener != null) {
                iProcessListener.onSuccess(deviceSupportedNetTypesByPk);
            }
            z2 = true;
        }
        updateDeviceNetTypesSupportedByPk(str, !z2, iProcessListener);
    }

    public void getDeviceWifiStatus(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        getDeviceWifiStatus(str);
        updateDeviceWifiStatus(str, true, iProcessListener);
    }

    public void getProps(String str, DeviceShadowFetcher deviceShadowFetcher, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        getProps(str, false, deviceShadowFetcher, iProcessListener);
    }

    public void getStatus(final String str, final DeviceShadowFetcher deviceShadowFetcher, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final boolean z2;
        if (iProcessListener == null) {
            ALog.e(TAG, "getStatus processListener empty");
            return;
        }
        String cacheKey = getCacheKey(DEVICESHADOW_STATUS_PRE_KEY, str);
        final String string = this.mMemoryLruHelper.getString(cacheKey);
        ALog.d(TAG, "getStatus iotId: " + str + " processListener:" + iProcessListener + " cacheKey:" + cacheKey + " isCallbacked:false status:" + string);
        if (TextUtils.isEmpty(string)) {
            z2 = false;
        } else {
            CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
            commonResponsePayload.setCode(200);
            try {
                commonResponsePayload.setData(JSON.parseObject(string));
                iProcessListener.onSuccess(JSON.toJSONString(commonResponsePayload));
            } catch (Exception e2) {
                ALog.e(TAG, "getStatus onSuccess error:" + e2.toString());
            }
            z2 = true;
        }
        if (deviceShadowFetcher != null) {
            deviceShadowFetcher.getStatus(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.2
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z3, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(DeviceShadowMgr.TAG, "getStatus onComplete isSuccess: " + z3 + " data:" + obj);
                    if (obj == null || !z3) {
                        if (z2) {
                            return;
                        }
                        iProcessListener.onFail(new ErrorInfo(300, "getStatus error error"));
                        return;
                    }
                    try {
                        DeviceShadowMgr.this.updateStatusCacheAndNotify(str, JSON.parseObject(String.valueOf(obj)).getJSONObject("data"), string, z2, new DeviceShadowNotifier(deviceShadowFetcher.getMultipleChannelDevice()));
                    } catch (Exception e3) {
                        ALog.e(DeviceShadowMgr.TAG, "getStatus notify error:" + e3.toString());
                    }
                    if (z2) {
                        return;
                    }
                    iProcessListener.onSuccess(obj);
                }
            });
        } else {
            if (z2) {
                return;
            }
            iProcessListener.onFail(new ErrorInfo(300, "getStatus device empty error"));
        }
    }

    public boolean getSupportDownAllProps(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_SUPPORT_DOWN_ALL_PROPS_PRE_KEY, str));
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        ALog.d(TAG, "getAllPropsDevice isAllPropsDevice: " + string);
        return string.equals("true");
    }

    public void getTsl(String str, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iProcessListener == null) {
            ALog.e(TAG, "gettsl processListener empty");
            return;
        }
        String cacheKey = getCacheKey(DEVICESHADOW_TSL_PRE_KEY, str);
        String string = this.mDiskLruCacheHelper.getString(cacheKey);
        StringBuilder sb = new StringBuilder();
        sb.append("gettsl iotId: ");
        sb.append(str);
        sb.append(" processListener:");
        sb.append(iProcessListener);
        sb.append(" cacheKey:");
        sb.append(cacheKey);
        sb.append(" isCallbacked:");
        boolean z2 = false;
        int length = 0;
        sb.append(false);
        sb.append(" tsl:");
        sb.append(string);
        ALog.d(TAG, sb.toString());
        if (!TextUtils.isEmpty(string)) {
            ALog.d(TAG, "getTsl() *** called with: iotId = [" + str + "], processListener = [" + iProcessListener + "]");
            CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
            commonResponsePayload.setCode(200);
            try {
                commonResponsePayload.setData(JSON.parseObject(string));
                String jSONString = JSON.toJSONString(commonResponsePayload);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("getTsl response payload:");
                if (!TextUtils.isEmpty(jSONString)) {
                    length = jSONString.length();
                }
                sb2.append(length);
                ALog.d(TAG, sb2.toString());
                iProcessListener.onSuccess(jSONString);
            } catch (Exception e2) {
                ALog.e(TAG, "getTsl onSuccess error:" + e2.toString());
            }
            z2 = true;
        }
        ALog.d(TAG, "getTsl() called with: iotId = [" + str + "], processListener = [" + iProcessListener + "]");
        updateTslByCloud(str, z2, iProcessListener);
    }

    public boolean isPropertyCached(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2 = !TextUtils.isEmpty(this.mMemoryLruHelper.getString(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str)));
        ALog.d(TAG, "isPropertyCached iotId:" + str + " ret:" + z2);
        return z2;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onConnectStateChange(String str, ConnectState connectState) {
    }

    @Override // com.aliyun.alink.linksdk.tmp.event.INotifyHandler
    public void onMessage(d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject;
        ALog.d(TAG, "onMessage request:" + dVar + " response:" + eVar);
        try {
            JSONObject object = JSON.parseObject(eVar.e());
            String string = object.getString("method");
            if (TextUtils.isEmpty(string) || !string.contains(TmpConstant.METHOD_PROPERTY_POST) || (jSONObject = object.getJSONObject("params")) == null) {
                return;
            }
            updatePropertyCacheAndNotify(eVar.g(), jSONObject, this.mMemoryLruHelper.getString(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, eVar.g())), false, null);
        } catch (Exception e2) {
            ALog.e(TAG, "onMessage error:" + e2.toString());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onNotify(String str, String str2, AMessage aMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject object;
        JSONObject jSONObject;
        Object objRemove;
        ALog.d(TAG, "onNotify connectedId:" + str + " topic:" + str2 + " aMessage:" + aMessage);
        if (TextUtils.isEmpty(str2)) {
            ALog.e(TAG, "onNotify error topic:" + str2 + " connectedId:" + str);
            return;
        }
        try {
            Object obj = aMessage.data;
            object = JSON.parseObject(obj instanceof byte[] ? new String((byte[]) obj, "UTF-8") : obj.toString());
            jSONObject = object.getJSONObject("params");
        } catch (Exception e2) {
            ALog.e(TAG, "onNotify e:" + e2.toString());
        }
        if (str2.contains(TmpConstant.MQTT_TOPIC_PROPERTIES)) {
            String string = jSONObject.getString("iotId");
            updatePropertyCacheAndNotify(string, jSONObject.getJSONObject("items"), this.mMemoryLruHelper.getString(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, string)), false, null);
            return;
        }
        if (str2.contains(TmpConstant.MQTT_TOPIC_STATUS)) {
            String string2 = jSONObject.getString("iotId");
            JSONObject jSONObject2 = jSONObject.getJSONObject("status");
            if (jSONObject2 != null && (objRemove = jSONObject2.remove("value")) != null) {
                jSONObject2.put("status", objRemove);
            }
            updateStatusCacheAndNotify(string2, jSONObject2, this.mMemoryLruHelper.getString(getCacheKey(DEVICESHADOW_STATUS_PRE_KEY, string2)), false, null);
            return;
        }
        if (str2.contains(TmpConstant.MQTT_TOPIC_NOTIFY)) {
            Log.d(TAG, "onNotify() called with: connectedId = [" + str + "], topic = [" + str2 + "], aMessage = [" + aMessage + "]");
            String string3 = jSONObject.getString("identifier");
            JSONObject jSONObject3 = jSONObject.getJSONObject("value");
            String string4 = jSONObject3.getString("iotId");
            String string5 = jSONObject3.getString("operation");
            if (TextUtils.isEmpty(string5) || !string5.equalsIgnoreCase("Unbind")) {
                if (!TextUtils.isEmpty(string3) && string3.equalsIgnoreCase("_LivingLink.propertyNameUpdate")) {
                    try {
                        this.mPropertyAlias.changeTslWithAlias(string4, PropertyAlias.create(string4, JSON.parseArray(jSONObject3.getJSONArray("data").toString(), AliasNotifyData.class)));
                        return;
                    } catch (Exception e3) {
                        ALog.e(TAG, "update error:" + e3.toString());
                        return;
                    }
                }
                if (TextUtils.equals(string3, "awss.BindNotify") && jSONObject3.getIntValue("owned") == 1) {
                    Log.d(TAG, "onNotify: 绑定成功通知");
                    Intent intent = new Intent("iLop.bind.cloud.mesh");
                    intent.putExtra("bindData", jSONObject3.toJSONString());
                    TmpSdk.getContext().sendBroadcast(intent);
                    String string6 = jSONObject3.getString("iotId");
                    if (TextUtils.isEmpty(string6)) {
                        return;
                    }
                    JSONObject jSONObject4 = new JSONObject();
                    object.put("iotId", (Object) string6);
                    MeshManager.getInstance().addProvisionDevice(string6);
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("value", (Object) "1");
                    jSONObject4.put("powerstate", (Object) jSONObject5);
                    optimisticUpdateMeshDevice(string6, jSONObject4);
                    return;
                }
                return;
            }
            deleteDeviceShadow(string4, null);
            try {
                String string7 = jSONObject3.getString("deviceName");
                String string8 = jSONObject3.getString("productKey");
                GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse = (GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse) JSON.parseObject(getDeviceSupportedNetTypesByIotId(string4), GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse.class);
                if (getDeviceNetTypesSupportedResponse == null || !TmpEnum.DeviceNetType.isWifiBtCombo(TmpEnum.DeviceNetType.formatDeviceNetType((List) getDeviceNetTypesSupportedResponse.data))) {
                    return;
                }
                String macByDn = TmpStorage.getInstance().getMacByDn(string7);
                ALog.d(TAG, "combo dev unbinded productKey:" + string8 + " deviceName:" + string7 + " mac:" + macByDn + " breeze close by mac BREEZE:");
                StringBuilder sb = new StringBuilder();
                sb.append(a.f11115b);
                sb.append(string8);
                sb.append(string7);
                ConnectSDK.getInstance().destoryConnect(sb.toString());
                if (TextUtils.isEmpty(macByDn)) {
                    return;
                }
                MixBleDelegate.getInstance().close(macByDn, new ConnectionCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.6
                    public void onConnectionStateChange(MixBleDevice mixBleDevice, int i2, int i3) {
                    }
                });
                return;
            } catch (Exception e4) {
                ALog.w(TAG, "GetDeviceNetTypesSupportedResponse e:" + e4.toString());
                return;
            }
            ALog.e(TAG, "onNotify e:" + e2.toString());
        }
    }

    public void optimisticUpdateMeshDevice(String str, JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Log.d(TAG, "optimisticUpdateMeshDevice() called with: iotId = [" + str + "], newData = [" + jSONObject + "]");
        this.mMemoryLruHelper.saveValue(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str), jSONObject.toString());
    }

    public void refreshDeviceShadow(String str, UpdateParam updateParam, IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType;
        boolean z2;
        ALog.d(TAG, "refreshDeviceShadow iotId:" + str + " updateParam:" + updateParam + " processListener:" + iProcessListener);
        if (TextUtils.isEmpty(str) || updateParam == null || (deviceShadowUpdateType = updateParam.updateType) == null) {
            ALog.e(TAG, "updateCahce empty error");
            return;
        }
        DeviceShadowRefreshListener deviceShadowRefreshListener = new DeviceShadowRefreshListener(iProcessListener, deviceShadowUpdateType.getValue());
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType2 = updateParam.updateType;
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType3 = TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_ALL;
        boolean z3 = true;
        if (deviceShadowUpdateType2 == deviceShadowUpdateType3 || (deviceShadowUpdateType2.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_PROPERTIES.getValue()) > 0) {
            updatePropertiesByCloud(str, deviceShadowRefreshListener);
            z2 = true;
        } else {
            z2 = false;
        }
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType4 = updateParam.updateType;
        if (deviceShadowUpdateType4 == deviceShadowUpdateType3 || (deviceShadowUpdateType4.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_STATUS.getValue()) > 0) {
            updateStatusByCloud(str, deviceShadowRefreshListener);
            z2 = true;
        }
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType5 = updateParam.updateType;
        if (deviceShadowUpdateType5 == deviceShadowUpdateType3 || (deviceShadowUpdateType5.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_DEVICE_DETAIL_INFO.getValue()) > 0) {
            updateDetailInfoByCloud(str, false, deviceShadowRefreshListener);
            z2 = true;
        }
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType6 = updateParam.updateType;
        if (deviceShadowUpdateType6 == deviceShadowUpdateType3 || (deviceShadowUpdateType6.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_TSL.getValue()) > 0) {
            updateTslByCloud(str, false, deviceShadowRefreshListener);
            z2 = true;
        }
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType7 = updateParam.updateType;
        if (deviceShadowUpdateType7 == deviceShadowUpdateType3 || (deviceShadowUpdateType7.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_DEVICE_WIFI_STATUS.getValue()) > 0) {
            updateDeviceWifiStatus(str, true, deviceShadowRefreshListener);
            z2 = true;
        }
        TmpEnum.DeviceShadowUpdateType deviceShadowUpdateType8 = updateParam.updateType;
        if (deviceShadowUpdateType8 == deviceShadowUpdateType3 || (deviceShadowUpdateType8.getValue() & TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_DEVICE_NET_TYPE.getValue()) > 0) {
            updateDeviceNetTypesSupportedByIotId(str, true, deviceShadowRefreshListener);
        } else {
            z3 = z2;
        }
        if (z3) {
            return;
        }
        ALog.e(TAG, "updateCahce updateType error:" + updateParam.updateType);
        if (iProcessListener != null) {
            iProcessListener.onFail(new ErrorInfo(300, "type error"));
        }
    }

    public void setDeviceWifiStatus(String str, TmpEnum.DeviceWifiStatus deviceWifiStatus) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (deviceWifiStatus != null) {
            this.mDiskLruCacheHelper.saveValue(getCacheKey(DEVICESHADOW_WIFISTATUS_PRE_KEY, str), String.valueOf(deviceWifiStatus.getValue()));
        } else {
            ALog.e(TAG, "setDeviceWifiStatus deviceWifiStatus empty iotId:" + str);
        }
    }

    public void setPropertyAlias(String str, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string;
        String string2;
        String string3;
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject object = JSON.parseObject(str);
            if (object != null) {
                string = object.getString("iotId");
                string3 = object.getString("name");
                string2 = object.getString(PushConstants.SUB_ALIAS_STATUS_NAME);
            } else {
                string = null;
                string2 = null;
                string3 = null;
            }
            this.mPropertyAlias.setPropertyAlias(string, string3, string2);
            if (iPanelCallback != null) {
                jSONObject.put("code", (Object) 200);
                iPanelCallback.onComplete(true, jSONObject.toString());
            }
        } catch (Exception e2) {
            ALog.e(TAG, "setPropertyAlias error:" + e2.toString());
            jSONObject.put("code", (Object) 300);
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(true, jSONObject.toString());
            }
        }
    }

    public void setSupportDownAllProps(String str, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setIsAllPropsDevice() called with: iotId = [" + str + "], supportDownAllProps = [" + z2 + "]");
        this.mDiskLruCacheHelper.saveValue(getCacheKey(DEVICESHADOW_SUPPORT_DOWN_ALL_PROPS_PRE_KEY, str), String.valueOf(z2));
    }

    public void setTsl(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setTsl() called with: iotId = [" + str + "], tsl = [" + str2 + "]");
        this.mDiskLruCacheHelper.saveValue(getCacheKey(DEVICESHADOW_TSL_PRE_KEY, str), str2);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public boolean shouldHandle(String str, String str2) {
        return true;
    }

    protected void updateDetailInfoByCloud(final String str, final boolean z2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateDetailInfoByCloud iotId:" + str + " isCallbacked:" + z2 + " processListener:" + iProcessListener);
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "updateDetailInfoByCloud empty error");
        } else {
            CloudUtils.queryProductInfo(str, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.9
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    IProcessListener iProcessListener2;
                    ALog.d(DeviceShadowMgr.TAG, "updateDetailInfoByCloud onFailure iotId:" + str + " aError:" + aError);
                    if (z2 || (iProcessListener2 = iProcessListener) == null) {
                        return;
                    }
                    iProcessListener2.onFail(new ErrorInfo(aError));
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    IProcessListener iProcessListener2;
                    Object obj;
                    IProcessListener iProcessListener3;
                    IProcessListener iProcessListener4;
                    ALog.d(DeviceShadowMgr.TAG, "updateDetailInfoByCloud onResponse iotId:" + str + " onResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        if (z2 || (iProcessListener2 = iProcessListener) == null) {
                            return;
                        }
                        iProcessListener2.onFail(new ErrorInfo(300, "getDeviceDetailInfo aResponse error"));
                        return;
                    }
                    try {
                        CommonResponsePayload commonResponsePayload = (CommonResponsePayload) JSON.parseObject(String.valueOf(obj), CommonResponsePayload.class);
                        if (commonResponsePayload != null && commonResponsePayload.getData() != null) {
                            DeviceShadowMgr.this.mDiskLruCacheHelper.saveValue(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_DETAILINFO_PRE_KEY, str), String.valueOf(commonResponsePayload.getData()));
                            if (z2 || (iProcessListener4 = iProcessListener) == null) {
                                return;
                            }
                            iProcessListener4.onSuccess(String.valueOf(aResponse.data));
                            return;
                        }
                        ALog.e(DeviceShadowMgr.TAG, "queryProductInfo payload error");
                        if (z2 || (iProcessListener3 = iProcessListener) == null) {
                            return;
                        }
                        iProcessListener3.onFail(new ErrorInfo(300, "getDeviceDetailInfo parseObject error"));
                    } catch (Exception e2) {
                        ALog.e(DeviceShadowMgr.TAG, "parseObject error:" + e2.toString());
                    }
                }
            });
        }
    }

    public void updateDeviceNetTypesSupportedByIotId(final String str, final boolean z2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GateWayRequest getDeviceNetTypesSupportedRequest = new GetDeviceNetTypesSupportedRequest(null, str);
        getDeviceNetTypesSupportedRequest.sendRequest(getDeviceNetTypesSupportedRequest, new IGateWayRequestListener<GetDeviceNetTypesSupportedRequest, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.4
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GetDeviceNetTypesSupportedRequest getDeviceNetTypesSupportedRequest2, AError aError) {
                IProcessListener iProcessListener2;
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onFail(new ErrorInfo(aError));
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GetDeviceNetTypesSupportedRequest getDeviceNetTypesSupportedRequest2, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse) {
                IProcessListener iProcessListener2;
                DeviceShadowMgr.this.mDiskLruCacheHelper.saveValue(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_SUPPORTED_NETTYPE_PRE_KEY, str), JSON.toJSONString(getDeviceNetTypesSupportedResponse.data));
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onSuccess(JSON.toJSONString(getDeviceNetTypesSupportedResponse));
            }
        });
    }

    public void updateDeviceNetTypesSupportedByPk(final String str, final boolean z2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GateWayRequest getDeviceNetTypesSupportedRequest = new GetDeviceNetTypesSupportedRequest(str, null);
        getDeviceNetTypesSupportedRequest.sendRequest(getDeviceNetTypesSupportedRequest, new IGateWayRequestListener<GetDeviceNetTypesSupportedRequest, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.5
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GetDeviceNetTypesSupportedRequest getDeviceNetTypesSupportedRequest2, AError aError) {
                IProcessListener iProcessListener2;
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onFail(new ErrorInfo(aError));
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GetDeviceNetTypesSupportedRequest getDeviceNetTypesSupportedRequest2, GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse) {
                IProcessListener iProcessListener2;
                DeviceShadowMgr.this.mDiskLruCacheHelper.saveValue(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_SUPPORTED_NETTYPE_PRE_KEY, str), JSON.toJSONString(getDeviceNetTypesSupportedResponse.data));
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onSuccess(JSON.toJSONString(getDeviceNetTypesSupportedResponse));
            }
        });
    }

    public void updateDeviceWifiStatus(final String str, final boolean z2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GateWayRequest getDeviceExtendRequest = new GetDeviceExtendRequest(str, TmpConstant.DATA_KEY_DEVICE_WIFI_STATUS);
        getDeviceExtendRequest.sendRequest(getDeviceExtendRequest, new IGateWayRequestListener<GetDeviceExtendRequest, GetDeviceExtendRequest.DeviceExtendGetResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.3
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(GetDeviceExtendRequest getDeviceExtendRequest2, AError aError) {
                IProcessListener iProcessListener2;
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onFail(new ErrorInfo(aError));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(GetDeviceExtendRequest getDeviceExtendRequest2, GetDeviceExtendRequest.DeviceExtendGetResponse deviceExtendGetResponse) {
                IProcessListener iProcessListener2;
                DeviceShadowMgr.this.mDiskLruCacheHelper.saveValue(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_WIFISTATUS_PRE_KEY, str), (String) deviceExtendGetResponse.data);
                if (!z2 || (iProcessListener2 = iProcessListener) == null) {
                    return;
                }
                iProcessListener2.onSuccess(JSON.toJSONString(deviceExtendGetResponse));
            }
        });
    }

    protected void updatePropertiesByCloud(final String str, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updatePropertiesByCloud iotId:" + str + " processListener:" + iProcessListener);
        if (CheckMeshMessage.containsMessage(str)) {
            ALog.d(TAG, "getProps: mesh device is in optimistic Update time ,no need get from cloud");
        } else {
            CloudUtils.getProperties(str, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.7
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(DeviceShadowMgr.TAG, "updatePropertiesByCloud onFailure:" + aError);
                    IProcessListener iProcessListener2 = iProcessListener;
                    if (iProcessListener2 != null) {
                        iProcessListener2.onFail(new ErrorInfo(aError));
                    }
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    ALog.d(DeviceShadowMgr.TAG, "updatePropertiesByCloud onResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        IProcessListener iProcessListener2 = iProcessListener;
                        if (iProcessListener2 != null) {
                            iProcessListener2.onFail(new ErrorInfo(300, "getProperties error error"));
                            return;
                        }
                        return;
                    }
                    try {
                        DeviceShadowMgr.this.updatePropertyCacheAndNotify(str, JSON.parseObject(String.valueOf(obj)).getJSONObject("data"), DeviceShadowMgr.this.mMemoryLruHelper.getString(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_PROPERTY_PRE_KEY, str)), false, null);
                        IProcessListener iProcessListener3 = iProcessListener;
                        if (iProcessListener3 != null) {
                            iProcessListener3.onSuccess(aResponse.data);
                        }
                    } catch (Exception e2) {
                        ALog.e(DeviceShadowMgr.TAG, "getProperties onComplete updatePropertyCacheAndNotify error:" + e2.toString());
                    }
                }
            });
        }
    }

    protected void updatePropertyCacheAndNotify(String str, JSONObject jSONObject, String str2, boolean z2, DeviceShadowNotifier deviceShadowNotifier) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (jSONObject == null || jSONObject.isEmpty()) {
            ALog.e(TAG, "updatePropertyCacheAndNotify newData empty");
            return;
        }
        try {
            JSONObject jSONObject2 = str2 == null ? new JSONObject() : JSON.parseObject(str2);
            HashSet<String> hashSet = new HashSet(jSONObject.keySet());
            hashSet.addAll(jSONObject2.keySet());
            JSONObject jSONObject3 = new JSONObject();
            ALog.d(TAG, "updatePropertyCacheAndNotify propertyKeySet:" + hashSet + " iotId:" + str);
            boolean z3 = false;
            for (String str3 : hashSet) {
                JSONObject jSONObject4 = jSONObject.getJSONObject(str3);
                JSONObject jSONObject5 = jSONObject2.getJSONObject(str3);
                if (comparePropertyValue(jSONObject4, jSONObject5)) {
                    jSONObject3.put(str3, (Object) jSONObject4);
                    z3 = true;
                } else {
                    jSONObject3.put(str3, (Object) jSONObject5);
                }
            }
            ALog.d(TAG, "updatePropertyCacheAndNotify iotId:" + str + " newData:" + jSONObject + " oldValue:" + str2 + " saveData:" + jSONObject3 + " needNotify:" + z2 + " notifier:" + deviceShadowNotifier + " isDifference:" + z3);
            if (z3) {
                this.mMemoryLruHelper.saveValue(getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str), jSONObject3.toString());
                if (!z2 || deviceShadowNotifier == null) {
                    return;
                }
                deviceShadowNotifier.notifyPropertyChange(jSONObject);
            }
        } catch (Exception e2) {
            ALog.e(TAG, "updatePropertyCacheAndNotify error:" + e2.toString());
        }
    }

    protected void updateStatusByCloud(final String str, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateStatusByCloud iotId:" + str + " processListener:" + iProcessListener);
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "updateStatusByCloud empty error");
        } else {
            CloudUtils.getStatus(str, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.8
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(DeviceShadowMgr.TAG, "updateStatusByCloud onFailure aError: " + aError);
                    IProcessListener iProcessListener2 = iProcessListener;
                    if (iProcessListener2 != null) {
                        iProcessListener2.onFail(new ErrorInfo(300, "getStatus error error"));
                    }
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    ALog.d(DeviceShadowMgr.TAG, "updateStatusByCloud onComplete aRequest: " + aRequest + " aResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        IProcessListener iProcessListener2 = iProcessListener;
                        if (iProcessListener2 != null) {
                            iProcessListener2.onFail(new ErrorInfo(300, "getStatus response error"));
                            return;
                        }
                        return;
                    }
                    try {
                        DeviceShadowMgr.this.updateStatusCacheAndNotify(str, JSON.parseObject(String.valueOf(obj)).getJSONObject("data"), DeviceShadowMgr.this.mMemoryLruHelper.getString(DeviceShadowMgr.getCacheKey(DeviceShadowMgr.DEVICESHADOW_STATUS_PRE_KEY, str)), false, null);
                    } catch (Exception e2) {
                        ALog.e(DeviceShadowMgr.TAG, "getStatus onResponse error:" + e2.toString());
                    }
                    IProcessListener iProcessListener3 = iProcessListener;
                    if (iProcessListener3 != null) {
                        iProcessListener3.onSuccess(aResponse.data);
                    }
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void updateStatusCacheAndNotify(java.lang.String r7, com.alibaba.fastjson.JSONObject r8, java.lang.String r9, boolean r10, com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowNotifier r11) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r6 = this;
            java.lang.String r0 = "[Tmp]DeviceShadowMgr"
            if (r8 == 0) goto L96
            boolean r1 = r8.isEmpty()
            if (r1 == 0) goto Lc
            goto L96
        Lc:
            r1 = 0
            java.lang.String r2 = java.lang.String.valueOf(r8)     // Catch: java.lang.Exception -> L22
            boolean r3 = android.text.TextUtils.isEmpty(r9)     // Catch: java.lang.Exception -> L1e
            if (r3 != 0) goto L20
            boolean r3 = r9.equalsIgnoreCase(r2)     // Catch: java.lang.Exception -> L1e
            if (r3 != 0) goto L3c
            goto L20
        L1e:
            r3 = move-exception
            goto L24
        L20:
            r1 = 1
            goto L3c
        L22:
            r3 = move-exception
            r2 = 0
        L24:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "updateStatusCacheAndNotify error:"
            r4.append(r5)
            java.lang.String r3 = r3.toString()
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            com.aliyun.alink.linksdk.tools.ALog.e(r0, r3)
        L3c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "updateStatusCacheAndNotify iotId:"
            r3.append(r4)
            r3.append(r7)
            java.lang.String r4 = " newValue:"
            r3.append(r4)
            r3.append(r8)
            java.lang.String r4 = " oldValue:"
            r3.append(r4)
            r3.append(r9)
            java.lang.String r9 = " isCallbacked:"
            r3.append(r9)
            r3.append(r10)
            java.lang.String r9 = " notifier:"
            r3.append(r9)
            r3.append(r11)
            java.lang.String r9 = " isDifference:"
            r3.append(r9)
            r3.append(r1)
            java.lang.String r9 = r3.toString()
            com.aliyun.alink.linksdk.tools.ALog.d(r0, r9)
            com.aliyun.alink.linksdk.tmp.device.deviceshadow.MemoryLruHelper r9 = r6.mMemoryLruHelper
            r9.setRefreshProperty(r7, r1)
            if (r1 == 0) goto L95
            com.aliyun.alink.linksdk.tmp.device.deviceshadow.MemoryLruHelper r9 = r6.mMemoryLruHelper
            java.lang.String r0 = "device_status_"
            java.lang.String r7 = getCacheKey(r0, r7)
            java.lang.String r0 = java.lang.String.valueOf(r2)
            r9.saveValue(r7, r0)
            if (r10 == 0) goto L95
            if (r11 == 0) goto L95
            r11.notifyStatusChange(r8)
        L95:
            return
        L96:
            java.lang.String r7 = "updateStatusCacheAndNotify newData empty"
            com.aliyun.alink.linksdk.tools.ALog.e(r0, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.updateStatusCacheAndNotify(java.lang.String, com.alibaba.fastjson.JSONObject, java.lang.String, boolean, com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowNotifier):void");
    }

    protected void updateTslByCloud(final String str, final boolean z2, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateTslByCloud iotId:" + str + " isCallbacked:" + z2 + " processListener:" + iProcessListener);
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, "updateTslByCloud empty error");
        } else {
            CloudUtils.getTsl(str, new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.10
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    IProcessListener iProcessListener2;
                    ALog.d(DeviceShadowMgr.TAG, "updateTslByCloud onFailure iotId:" + str + " aError:" + aError);
                    if (z2 || (iProcessListener2 = iProcessListener) == null) {
                        return;
                    }
                    iProcessListener2.onFail(new ErrorInfo(aError));
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    IProcessListener iProcessListener2;
                    Object obj;
                    final GetTslResponsePayload getTslResponsePayload;
                    IProcessListener iProcessListener3;
                    ALog.d(DeviceShadowMgr.TAG, "updateTslByCloud ***** iotId:" + str + " onResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        if (z2 || (iProcessListener2 = iProcessListener) == null) {
                            return;
                        }
                        iProcessListener2.onFail(new ErrorInfo(300, "getTsl aResponse error"));
                        return;
                    }
                    GetTslResponsePayload getTslResponsePayload2 = null;
                    try {
                        getTslResponsePayload = (GetTslResponsePayload) JSON.parseObject(String.valueOf(obj), GetTslResponsePayload.class);
                        try {
                            Log.d(DeviceShadowMgr.TAG, "onResponse() called with: aResponse = [" + aResponse.getData() + "]");
                            StringBuilder sb = new StringBuilder();
                            sb.append("onResponse() called with: getCode = ");
                            sb.append(getTslResponsePayload.getCode());
                            Log.d(DeviceShadowMgr.TAG, sb.toString());
                        } catch (Exception e2) {
                            e = e2;
                            getTslResponsePayload2 = getTslResponsePayload;
                            ALog.e(DeviceShadowMgr.TAG, "parseObject error:" + e.toString());
                            getTslResponsePayload = getTslResponsePayload2;
                            if (getTslResponsePayload == null) {
                            }
                            if (z2) {
                                return;
                            } else {
                                return;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                    if (getTslResponsePayload == null && getTslResponsePayload.data != null) {
                        DeviceShadowMgr.this.mPropertyAlias.refreshPropertyAlias(str, getTslResponsePayload, new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.10.1
                            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                            public void onFail(ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                IProcessListener iProcessListener4;
                                AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                                DeviceShadowMgr.this.setTsl(str, String.valueOf(getTslResponsePayload.data));
                                AnonymousClass10 anonymousClass102 = AnonymousClass10.this;
                                if (z2 || (iProcessListener4 = iProcessListener) == null) {
                                    return;
                                }
                                iProcessListener4.onSuccess(JSON.toJSONString(getTslResponsePayload));
                            }

                            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                            public void onSuccess(Object obj2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                IProcessListener iProcessListener4;
                                AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                                DeviceShadowMgr.this.setTsl(str, String.valueOf(getTslResponsePayload.data));
                                AnonymousClass10 anonymousClass102 = AnonymousClass10.this;
                                if (z2 || (iProcessListener4 = iProcessListener) == null) {
                                    return;
                                }
                                iProcessListener4.onSuccess(JSON.toJSONString(getTslResponsePayload));
                            }
                        });
                    } else {
                        if (z2 || (iProcessListener3 = iProcessListener) == null) {
                            return;
                        }
                        iProcessListener3.onFail(new ErrorInfo(300, "payload data parse error"));
                    }
                }
            });
        }
    }

    private DeviceShadowMgr() {
        this.mDiskLruCacheHelper = new DiskLruHelper(DEVICESHADOW_CACHE_FILE, 1);
        this.mMemoryLruHelper = new MemoryLruHelper();
        this.mPropertyAlias = new PropertyAlias(this.mDiskLruCacheHelper);
        CmpNotifyManager.getInstance().addHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_PROPERTIES, this);
        CmpNotifyManager.getInstance().addHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_STATUS, this);
        CmpNotifyManager.getInstance().addHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_NOTIFY, this);
    }

    public void getProps(final String str, final boolean z2, final DeviceShadowFetcher deviceShadowFetcher, final IProcessListener iProcessListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iProcessListener == null) {
            ALog.e(TAG, "getProps processListener empty");
            return;
        }
        String cacheKey = getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str);
        final String string = this.mMemoryLruHelper.getString(cacheKey);
        StringBuilder sb = new StringBuilder();
        sb.append("getProps iotId: ");
        sb.append(str);
        sb.append(" processListener:");
        sb.append(iProcessListener);
        sb.append(" cacheKey:");
        sb.append(cacheKey);
        sb.append(" isCallbacked:");
        boolean z3 = false;
        sb.append(false);
        sb.append(" propertiesStr:");
        sb.append(string);
        ALog.d(TAG, sb.toString());
        if (!TextUtils.isEmpty(string)) {
            CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
            commonResponsePayload.setCode(200);
            try {
                commonResponsePayload.setData(JSON.parseObject(string));
                String jSONString = JSON.toJSONString(commonResponsePayload);
                iProcessListener.onSuccess(jSONString);
                Log.d(TAG, "getLocalProperty,local value=[ " + jSONString + " ]");
            } catch (Exception e2) {
                ALog.e(TAG, "getProps onSuccess error:" + e2.toString());
            }
            z3 = true;
        }
        final boolean z4 = z3;
        if (CheckMeshMessage.containsMessage(str)) {
            ALog.d(TAG, "getProps: mesh device is in optimistic Update time ,no need get from cloud");
            return;
        }
        ALog.d(TAG, "getProps: isCallbacked = " + z4);
        if (deviceShadowFetcher != null) {
            deviceShadowFetcher.getProperties(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr.1
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z5, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(DeviceShadowMgr.TAG, "getProperties onComplete isSuccess: " + z5 + " data:" + obj);
                    if (obj == null || !z5) {
                        if (z4) {
                            return;
                        }
                        iProcessListener.onFail(new ErrorInfo(300, "getProperties error error"));
                        return;
                    }
                    try {
                        DeviceShadowMgr.this.updatePropertyCacheAndNotify(str, JSON.parseObject(String.valueOf(obj)).getJSONObject("data"), string, z2 || z4, new DeviceShadowNotifier(deviceShadowFetcher.getMultipleChannelDevice()));
                    } catch (Exception e3) {
                        ALog.e(DeviceShadowMgr.TAG, "getProperties onComplete updatePropertyCacheAndNotify error:" + e3.toString());
                    }
                    if (z4) {
                        return;
                    }
                    iProcessListener.onSuccess(obj);
                }
            });
        } else {
            if (z4) {
                return;
            }
            iProcessListener.onFail(new ErrorInfo(300, "getProperties device empty error"));
        }
    }

    public String getDeviceWifiStatus(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_WIFISTATUS_PRE_KEY, str));
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            commonResponsePayload.setData(string);
            String jSONString = JSON.toJSONString(commonResponsePayload);
            ALog.d(TAG, "getDeviceWifiStatus response payload:" + jSONString);
            return jSONString;
        } catch (Exception e2) {
            ALog.e(TAG, "getDeviceWifiStatus onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getDeviceSupportedNetTypesByIotId(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_SUPPORTED_NETTYPE_PRE_KEY, str));
        GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse = new GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse();
        getDeviceNetTypesSupportedResponse.code = 200;
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            getDeviceNetTypesSupportedResponse.setData((List) JSON.parseObject(string, List.class));
            String jSONString = JSON.toJSONString(getDeviceNetTypesSupportedResponse);
            StringBuilder sb = new StringBuilder();
            sb.append("getDeviceSupportedNetTypesByIotId response payload:");
            sb.append(TextUtils.isEmpty(jSONString) ? "" : jSONString);
            ALog.d(TAG, sb.toString());
            return jSONString;
        } catch (Exception e2) {
            ALog.e(TAG, "getDeviceSupportedNetTypesByIotId onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getDeviceSupportedNetTypesByPk(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_SUPPORTED_NETTYPE_PRE_KEY, str));
        GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse getDeviceNetTypesSupportedResponse = new GetDeviceNetTypesSupportedRequest.GetDeviceNetTypesSupportedResponse();
        getDeviceNetTypesSupportedResponse.code = 200;
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            getDeviceNetTypesSupportedResponse.setData((List) JSON.parseObject(string, List.class));
            String jSONString = JSON.toJSONString(getDeviceNetTypesSupportedResponse);
            StringBuilder sb = new StringBuilder();
            sb.append("getDeviceSupportedNetTypesByPk response payload:");
            sb.append(TextUtils.isEmpty(jSONString) ? "" : jSONString);
            ALog.d(TAG, sb.toString());
            return jSONString;
        } catch (Exception e2) {
            ALog.e(TAG, "getDeviceWifiStatus onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getDetailInfo(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_DETAILINFO_PRE_KEY, str));
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        try {
            commonResponsePayload.setData(JSON.parseObject(string));
            return JSON.toJSONString(commonResponsePayload);
        } catch (Exception e2) {
            ALog.e(TAG, "getDetailInfo onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getStatus(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String cacheKey = getCacheKey(DEVICESHADOW_STATUS_PRE_KEY, str);
        String string = this.mMemoryLruHelper.getString(cacheKey);
        ALog.d(TAG, "getStatus iotId: " + str + " cacheKey:" + cacheKey + " isCallbacked:false status:" + string);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        try {
            commonResponsePayload.setData(JSON.parseObject(string));
            return JSON.toJSONString(commonResponsePayload);
        } catch (Exception e2) {
            ALog.e(TAG, "getStatus onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getTsl(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.mDiskLruCacheHelper.getString(getCacheKey(DEVICESHADOW_TSL_PRE_KEY, str));
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        try {
            commonResponsePayload.setData(JSON.parseObject(string));
            String jSONString = JSON.toJSONString(commonResponsePayload);
            StringBuilder sb = new StringBuilder();
            sb.append("getTsl response payload:");
            sb.append(TextUtils.isEmpty(jSONString) ? 0 : jSONString.length());
            ALog.d(TAG, sb.toString());
            return jSONString;
        } catch (Exception e2) {
            ALog.e(TAG, "getTsl onSuccess error:" + e2.toString());
            return null;
        }
    }

    public String getProps(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String cacheKey = getCacheKey(DEVICESHADOW_PROPERTY_PRE_KEY, str);
        String string = this.mMemoryLruHelper.getString(cacheKey);
        ALog.d(TAG, "getProps iotId: " + str + " cacheKey:" + cacheKey + " propertiesStr:" + string);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        try {
            commonResponsePayload.setData(JSON.parseObject(string));
            return JSON.toJSONString(commonResponsePayload);
        } catch (Exception e2) {
            ALog.e(TAG, "getProps onSuccess error:" + e2.toString());
            return null;
        }
    }
}
