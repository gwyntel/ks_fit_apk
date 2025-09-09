package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tmp.a.a;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.group.DeviceLocalStatusChangePayload;
import com.aliyun.alink.linksdk.tmp.device.request.localgroup.QueryLocalGroupDeviceRequest;
import com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class LocalDeviceListChangeListenerWrapper implements a.InterfaceC0074a, IDiscoveryDeviceStateChangeListener {
    private static final String TAG = "[Tmp]LocalDeviceListChangeListenerImpl";
    private IPanelGroupEventCallback mCallback;
    private String mGroupId;
    private QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData mLocalGroupData;

    public LocalDeviceListChangeListenerWrapper(IPanelGroupEventCallback iPanelGroupEventCallback, String str, QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData) {
        this.mCallback = iPanelGroupEventCallback;
        this.mLocalGroupData = queryLocalGroupDeviceResData;
        this.mGroupId = str;
    }

    @Override // com.aliyun.alink.linksdk.tmp.a.a.InterfaceC0074a
    public void onDeviceListChange(int i2, DeviceBasicData deviceBasicData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner> list;
        DeviceLocalStatusChangePayload.Status status;
        QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData = this.mLocalGroupData;
        if (queryLocalGroupDeviceResData == null || deviceBasicData == null || this.mCallback == null || (list = queryLocalGroupDeviceResData.items) == null) {
            ALog.w(TAG, "onDeviceListChange mLocalGroupData empty or changedData emtpy or mCallback empty mLocalGroupData:" + this.mLocalGroupData + " changedData:" + deviceBasicData + " mCallback:" + this.mCallback);
            return;
        }
        for (QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner queryLocalGroupDeviceResDataInner : list) {
            if (queryLocalGroupDeviceResDataInner.productKey.equalsIgnoreCase(deviceBasicData.getProductKey()) && queryLocalGroupDeviceResDataInner.deviceName.equalsIgnoreCase(deviceBasicData.getDeviceName())) {
                if (i2 == 1) {
                    status = new DeviceLocalStatusChangePayload.Status(System.currentTimeMillis(), 1);
                } else if (i2 != 2) {
                    return;
                } else {
                    status = new DeviceLocalStatusChangePayload.Status(System.currentTimeMillis(), 3);
                }
                DeviceLocalStatusChangePayload.DeviceLocalStatus deviceLocalStatus = new DeviceLocalStatusChangePayload.DeviceLocalStatus(queryLocalGroupDeviceResDataInner.iotId, queryLocalGroupDeviceResDataInner.productKey, queryLocalGroupDeviceResDataInner.deviceName, status);
                ArrayList arrayList = new ArrayList();
                arrayList.add(deviceLocalStatus);
                this.mCallback.onNotify(this.mGroupId, TmpConstant.URI_TOPIC_LOCALDEVICE_STATECHANGE, JSON.toJSONString(new DeviceLocalStatusChangePayload(new DeviceLocalStatusChangePayload.DeviceLocalStatusChangeParams(arrayList))));
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener
    public void onDiscoveryDeviceStateChange(DeviceBasicData deviceBasicData, TmpEnum.DiscoveryDeviceState discoveryDeviceState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE == discoveryDeviceState) {
            onDeviceListChange(1, deviceBasicData);
            return;
        }
        if (TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_OFFLINE == discoveryDeviceState) {
            onDeviceListChange(2, deviceBasicData);
            return;
        }
        ALog.e(TAG, "onDiscoveryDeviceStateChange error state:" + discoveryDeviceState);
    }

    public void setLocalGroupData(QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData) {
        this.mLocalGroupData = queryLocalGroupDeviceResData;
    }
}
