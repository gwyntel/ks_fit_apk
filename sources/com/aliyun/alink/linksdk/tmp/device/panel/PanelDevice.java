package com.aliyun.alink.linksdk.tmp.device.panel;

import android.content.Context;
import com.aliyun.alink.linksdk.tmp.data.deviceshadow.UpdateParam;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowFetcher;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.linkselection.MultipleChannelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelDeviceLocalInitListener;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback;
import com.aliyun.alink.linksdk.tmp.error.CommonError;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class PanelDevice {
    private static final String TAG = "[Tmp]PanelDevice";
    private MultipleChannelDevice mMultipleChannelDevice;

    public PanelDevice(String str) {
        this(str, null);
    }

    public void cacheProperties(final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "cacheProperties callback:" + iPanelCallback);
        UpdateParam updateParam = new UpdateParam();
        updateParam.updateType = TmpEnum.DeviceShadowUpdateType.UPDATE_OPTION_PROPERTIES;
        DeviceShadowMgr.getInstance().refreshDeviceShadow(this.mMultipleChannelDevice.getIotId(), updateParam, new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice.2
            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
            public void onFail(ErrorInfo errorInfo) {
                IPanelCallback iPanelCallback2 = iPanelCallback;
                if (iPanelCallback2 != null) {
                    iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                }
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
            public void onSuccess(Object obj) {
                IPanelCallback iPanelCallback2 = iPanelCallback;
                if (iPanelCallback2 != null) {
                    iPanelCallback2.onComplete(true, "");
                }
            }
        });
    }

    public void getCachedWifiStatus(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.getWifiStatus(iPanelCallback);
    }

    public void getDetailInfoByCache(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getDetailInfo callback empty");
        } else {
            DeviceShadowMgr.getInstance().getDetailInfo(this.mMultipleChannelDevice.getIotId(), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice.5
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(true, String.valueOf(obj));
                    }
                }
            });
        }
    }

    public void getDeviceNetTypesSupported(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.getDeviceNetTypesSupported(iPanelCallback);
    }

    public void getDeviceTSL(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.getDeviceTSL(iPanelCallback);
    }

    @Deprecated
    public void getEvents(IPanelCallback iPanelCallback) {
        getLastEvent(iPanelCallback);
    }

    public String getIotId() {
        return this.mMultipleChannelDevice.getIotId();
    }

    public void getLastEvent(IPanelCallback iPanelCallback) {
        this.mMultipleChannelDevice.getLastEvent(iPanelCallback);
    }

    public void getLocalConnectionState(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.getLocalConnectionState(iPanelCallback);
    }

    public void getProperties(IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) {
        this.mMultipleChannelDevice.getProperties(iPanelCallback, panelMethodExtraData);
    }

    public void getPropertiesByCache(final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getPropertiesByCache() called with: callback = [" + iPanelCallback + "], extraData = [" + panelMethodExtraData + "]");
        if (iPanelCallback == null) {
            ALog.e(TAG, "getProperties callback empty");
        } else {
            DeviceShadowMgr.getInstance().getProps(this.mMultipleChannelDevice.getIotId(), new DeviceShadowFetcher(this.mMultipleChannelDevice, panelMethodExtraData), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice.1
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(PanelDevice.TAG, "getPropertiesByCache onFail() called with: errorInfo = [" + errorInfo + "]");
                    iPanelCallback.onComplete(false, new CommonError(errorInfo));
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(PanelDevice.TAG, "getPropertiesByCache onSuccess() called with: returnValue = [" + obj + "]");
                    iPanelCallback.onComplete(true, String.valueOf(obj));
                }
            });
        }
    }

    public void getStatus(IPanelCallback iPanelCallback) {
        this.mMultipleChannelDevice.getStatus(iPanelCallback);
    }

    public void getStatusByCache(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getStatus callback empty");
            return;
        }
        MultipleChannelDevice multipleChannelDevice = this.mMultipleChannelDevice;
        if (multipleChannelDevice == null || !multipleChannelDevice.isBleMeshDevice()) {
            DeviceShadowMgr.getInstance().getStatus(this.mMultipleChannelDevice.getIotId(), new DeviceShadowFetcher(this.mMultipleChannelDevice, null), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice.3
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(true, String.valueOf(obj));
                    }
                }
            });
        } else {
            this.mMultipleChannelDevice.getStatus(iPanelCallback);
        }
    }

    public void getTslByCache(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getTslByCache() called with: callback = [" + iPanelCallback + "]");
        if (iPanelCallback == null) {
            ALog.e(TAG, "getTsl callback empty");
        } else {
            DeviceShadowMgr.getInstance().getTsl(this.mMultipleChannelDevice.getIotId(), new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice.4
                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onFail(ErrorInfo errorInfo) {
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(false, new CommonError(errorInfo));
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
                public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(PanelDevice.TAG, "onSuccess() called with: returnValue = [" + obj + "]");
                    IPanelCallback iPanelCallback2 = iPanelCallback;
                    if (iPanelCallback2 != null) {
                        iPanelCallback2.onComplete(true, String.valueOf(obj));
                    }
                }
            });
        }
    }

    public void getWifiStatus(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.getWifiStatus(iPanelCallback);
    }

    public void init(Context context, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        init(context, iPanelCallback, null);
    }

    public void invokeService(String str, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        invokeService(str, iPanelCallback, null);
    }

    public boolean isInit() {
        return this.mMultipleChannelDevice.isInit();
    }

    public void queryTimerDownlinkTask(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.queryTimerDownlinkTask(iPanelCallback);
    }

    public void setProperties(String str, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setProperties(str, iPanelCallback, null);
    }

    public void setPropertyAlias(String str, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.setPropertyAlias(str, iPanelCallback);
    }

    public void startLocalConnect(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.startLocalConnect(iPanelCallback);
    }

    public void stopLocalConnect(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.stopLocalConnect(iPanelCallback);
    }

    @Deprecated
    public void subAllEvent(IPanelEventCallback iPanelEventCallback, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        subAllEvents(iPanelEventCallback, iPanelCallback);
    }

    public void subAllEvents(IPanelEventCallback iPanelEventCallback, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.subAllEvents(iPanelEventCallback, iPanelCallback);
    }

    public void uninit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.uninit();
    }

    public PanelDevice(String str, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "PanelDevice iotId:" + str);
        this.mMultipleChannelDevice = new MultipleChannelDevice(str, panelMethodExtraData);
    }

    public void getProperties(IPanelCallback iPanelCallback) {
        getProperties(iPanelCallback, null);
    }

    public void init(Context context, IPanelCallback iPanelCallback, IPanelDeviceLocalInitListener iPanelDeviceLocalInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.init(iPanelCallback, iPanelDeviceLocalInitListener);
    }

    public void invokeService(String str, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.invokeService(str, iPanelCallback, panelMethodExtraData);
    }

    public void setProperties(String str, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.setProperties(str, iPanelCallback, panelMethodExtraData);
    }

    public void subAllEvents(IPanelEventCallback iPanelEventCallback, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMultipleChannelDevice.subAllEvents(iPanelEventCallback, iPanelCallback, panelMethodExtraData);
    }
}
