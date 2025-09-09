package com.aliyun.alink.linksdk.tmp.device.e;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IDevStateChangeListener;
import com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener;
import com.aliyun.alink.linksdk.tmp.listener.IEventListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends b implements IDiscoveryDeviceStateChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11404a = "[Tmp]ClientWrapper";

    public a(DeviceConfig deviceConfig) {
        super(deviceConfig);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean addDeviceStateChangeListener(IDevStateChangeListener iDevStateChangeListener) {
        return super.addDeviceStateChangeListener(iDevStateChangeListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public TmpEnum.ConnectType getConnectType() {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.mDeviceImpl;
        return aVar != null ? aVar.b() : TmpEnum.ConnectType.CONNECT_TYPE_UNKNOWN;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public TmpEnum.DeviceState getDeviceState() {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.mDeviceImpl;
        return aVar != null ? aVar.l() : TmpEnum.DeviceState.DISCONNECTED;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean getPropertyValue(List<String> list, Object obj, IDevListener iDevListener) {
        return this.mDeviceImpl.a(list, obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public void init(Object obj, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.init(obj, iDevListener);
        com.aliyun.alink.linksdk.tmp.device.d.a.a().a(this);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean invokeService(String str, List<KeyValuePair> list, ExtraData extraData, Object obj, IDevListener iDevListener) {
        return this.mDeviceImpl.a(str, list, extraData, obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener
    public void onDiscoveryDeviceStateChange(DeviceBasicData deviceBasicData, TmpEnum.DiscoveryDeviceState discoveryDeviceState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.device.a aVar;
        ALog.d(f11404a, "onDiscoveryDeviceStateChange mDeviceImpl:" + this.mDeviceImpl + " state:" + discoveryDeviceState);
        if (deviceBasicData == null || (aVar = this.mDeviceImpl) == null) {
            return;
        }
        if (TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_OFFLINE == discoveryDeviceState) {
            if (TextUtils.isEmpty(aVar.j()) || !this.mDeviceImpl.j().equalsIgnoreCase(deviceBasicData.getDevId())) {
                return;
            }
            ALog.d(f11404a, "stop current connect.");
            this.mDeviceImpl.g();
            return;
        }
        if (TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE == discoveryDeviceState) {
            boolean zIsDeviceWifiAndBleCombo = DevService.isDeviceWifiAndBleCombo(deviceBasicData.getSupportedNetType());
            StringBuilder sb = new StringBuilder();
            sb.append("iscombo=");
            sb.append(zIsDeviceWifiAndBleCombo);
            sb.append(", getDeviceState=");
            sb.append(this.mDeviceImpl.l());
            sb.append(", mDeviceImpl.getBasicData=");
            sb.append(this.mDeviceImpl.h());
            sb.append(", mDeviceImpl.getConnectType=");
            sb.append(this.mDeviceImpl.b());
            sb.append(", changePk=");
            sb.append(deviceBasicData.getProductKey());
            sb.append(", changeDn=");
            sb.append(deviceBasicData.getDeviceName());
            sb.append(", changeMac=");
            sb.append(deviceBasicData.mac);
            sb.append(", localdiscoverytype vs wifi =");
            int i2 = deviceBasicData.localDiscoveryType;
            TmpEnum.DeviceNetType deviceNetType = TmpEnum.DeviceNetType.NET_WIFI;
            sb.append(i2 & deviceNetType.getValue());
            sb.append(", iotId=");
            sb.append(deviceBasicData.iotId);
            sb.append("basicData:");
            sb.append(deviceBasicData);
            ALog.d(f11404a, sb.toString());
            if (zIsDeviceWifiAndBleCombo && !TextUtils.isEmpty(this.mDeviceImpl.h()) && this.mDeviceImpl.b() == TmpEnum.ConnectType.CONNECT_TYPE_BLE && this.mDeviceImpl.l() == TmpEnum.DeviceState.CONNECTED && (deviceBasicData.localDiscoveryType & deviceNetType.getValue()) > 0 && this.mDeviceImpl.h().equalsIgnoreCase(TextHelper.combineStr(deviceBasicData.getProductKey(), deviceBasicData.deviceName))) {
                ALog.d(f11404a, "stop current ble connect.");
                this.mDeviceImpl.g();
                this.mDeviceImpl.a((Object) null, new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.e.a.1
                    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                    public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.e(a.f11404a, "stop ble connection and reconnect onFail() called with: tag = [" + obj + "], errorInfo = [" + errorInfo + "]");
                    }

                    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
                    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(a.f11404a, "stop ble connection and reconnect onSuccess() called with: tag = [" + obj + "], returnValue = [" + outputParams + "]");
                    }
                });
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean removeDeviceStateChangeListener(IDevStateChangeListener iDevStateChangeListener) {
        return super.removeDeviceStateChangeListener(iDevStateChangeListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean setPropertyValue(List<KeyValuePair> list, Object obj, IDevListener iDevListener) {
        return this.mDeviceImpl.b(list, obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean subAllEvents(Object obj, IEventListener iEventListener) {
        return this.mDeviceImpl.a(obj, iEventListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean subscribeEvent(String str, Object obj, IEventListener iEventListener) {
        return this.mDeviceImpl.a(str, obj, iEventListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public void unInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.device.d.a.a().b(this);
        super.unInit();
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean unsubscribeEvent(String str, Object obj, IDevListener iDevListener) {
        return this.mDeviceImpl.a(str, obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean setPropertyValue(ExtraData extraData, List<KeyValuePair> list, Object obj, IDevListener iDevListener) {
        return this.mDeviceImpl.a(extraData, list, obj, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.e.b, com.aliyun.alink.linksdk.tmp.api.IDevice
    public boolean setPropertyValue(String str, ValueWrapper valueWrapper, Object obj, IDevListener iDevListener) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KeyValuePair(str, valueWrapper));
        return setPropertyValue(arrayList, obj, iDevListener);
    }
}
