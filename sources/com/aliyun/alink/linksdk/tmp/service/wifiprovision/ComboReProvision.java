package com.aliyun.alink.linksdk.tmp.service.wifiprovision;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.device.d.a;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ComboReProvision implements IDiscoveryDeviceStateChangeListener {
    private static final String TAG = "[Tmp]ComboReProvision";

    public boolean isDeviceWifiNotSet(DeviceBasicData deviceBasicData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            return ((Integer) deviceBasicData.extraData.get(PalDiscoveryDeviceInfo.EXTRA_KEY_BREEZE_SUB_TYPE)).intValue() == 3;
        } catch (Exception e2) {
            ALog.e(TAG, "isDeviceWifiNotSet e:" + e2.toString());
            return false;
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener
    public void onDiscoveryDeviceStateChange(final DeviceBasicData deviceBasicData, TmpEnum.DiscoveryDeviceState discoveryDeviceState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDiscoveryDeviceStateChange basicData:" + deviceBasicData + " state:" + discoveryDeviceState);
        if (deviceBasicData != null && TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE == discoveryDeviceState && "2".equals(deviceBasicData.modelType) && TmpEnum.DeviceNetType.isWifiBtCombo(deviceBasicData.getSupportedNetType()) && isDeviceWifiNotSet(deviceBasicData)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.service.wifiprovision.ComboReProvision.1
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(ComboReProvision.TAG, "ComboReProvision action");
                    String dnByMac = TmpStorage.getInstance().getDnByMac(deviceBasicData.mac);
                    if (TextUtils.isEmpty(dnByMac)) {
                        ALog.d(ComboReProvision.TAG, "onDiscoveryDeviceStateChange dn empty basicData:" + deviceBasicData);
                        return;
                    }
                    String iotId = TmpStorage.getInstance().getIotId(deviceBasicData.productKey, dnByMac);
                    if (TextUtils.isEmpty(iotId)) {
                        ALog.d(ComboReProvision.TAG, "onDiscoveryDeviceStateChange iotid empty basicData:" + deviceBasicData);
                        return;
                    }
                    try {
                        if (((CommonResponsePayload) JSON.parseObject(DeviceShadowMgr.getInstance().getDeviceWifiStatus(iotId), CommonResponsePayload.class)) != null) {
                            DevService.setWifiStatus(iotId, TmpEnum.DeviceWifiStatus.DeviceWifiStatus_NotSet, null);
                            return;
                        }
                        ALog.d(ComboReProvision.TAG, "onDiscoveryDeviceStateChange payload empty basicData:" + deviceBasicData);
                    } catch (Exception e2) {
                        ALog.e(ComboReProvision.TAG, "parseObject e:" + e2.toString());
                    }
                }
            });
        }
    }

    public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a.a().a(this);
    }
}
