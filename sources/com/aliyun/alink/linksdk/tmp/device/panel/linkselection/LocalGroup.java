package com.aliyun.alink.linksdk.tmp.device.panel.linkselection;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.GroupConfig;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.b;
import com.aliyun.alink.linksdk.tmp.device.d.a;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.group.GroupLocalStatePayload;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelGroupEventCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.LocalDeviceListChangeListenerWrapper;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.SetGroupPropCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.SkipGroupPropCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.device.payload.service.DeviceItem;
import com.aliyun.alink.linksdk.tmp.device.request.localgroup.QueryLocalGroupDeviceRequest;
import com.aliyun.alink.linksdk.tmp.error.CommonError;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.ResponseUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TgMeshHelper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class LocalGroup {
    private static final String TAG = "[Tmp]LocalGroup";
    protected String mGroupId;
    protected b mGroupImpl;
    protected LocalDeviceListChangeListenerWrapper mLocalDeviceChangeListener;
    protected Map<String, LocalChannelDevice> mLocalDeviceList;
    private IPanelGroupEventCallback mPanelEventCallback;

    public LocalGroup(String str) {
        this.mGroupId = str;
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.groupId = str;
        this.mGroupImpl = new b(groupConfig);
        this.mLocalDeviceList = new ConcurrentHashMap();
    }

    protected boolean createAllLocalDevice(QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData) {
        List<QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner> list;
        if (queryLocalGroupDeviceResData == null || (list = queryLocalGroupDeviceResData.items) == null || list.size() <= 0) {
            return false;
        }
        for (QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner queryLocalGroupDeviceResDataInner : queryLocalGroupDeviceResData.items) {
            if (!this.mLocalDeviceList.containsKey(queryLocalGroupDeviceResDataInner.iotId)) {
                this.mLocalDeviceList.put(queryLocalGroupDeviceResDataInner.iotId, new LocalChannelDevice(queryLocalGroupDeviceResDataInner.iotId, null));
            }
        }
        return true;
    }

    public void getLocalState(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getLocalState call empty");
            return;
        }
        ALog.d(TAG, "getLocalState boneCallback:" + iPanelCallback);
        new GroupLocalStatePayload().setCode(300);
        this.mGroupImpl.a(new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.6
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                LocalGroup.this.getLocalState(iPanelCallback, new CommonError(errorInfo), null);
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                LocalGroup localGroup = LocalGroup.this;
                localGroup.getLocalState(iPanelCallback, null, localGroup.mGroupImpl.a().localGroupDeviceData);
            }
        });
    }

    public void invokeGroupService(List<DeviceItem> list, String str, final IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "invokeGroupService bleItemList=" + list + " params:" + str + " callback:" + iPanelCallback + " extraData:" + panelMethodExtraData);
        InvokeServiceData invokeServiceData = (InvokeServiceData) GsonUtils.fromJson(str, new TypeToken<InvokeServiceData>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.2
        }.getType());
        ArrayList arrayList = new ArrayList();
        Map<String, ValueWrapper> map = invokeServiceData.args;
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, ValueWrapper> entry : invokeServiceData.args.entrySet()) {
                arrayList.add(new KeyValuePair(entry.getKey(), entry.getValue()));
            }
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.mGroupImpl.a(invokeServiceData.identifier, arrayList, new ExtraData(panelMethodExtraData), new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.3
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalGroup.TAG, "invokeService onFail:" + errorInfo);
                iPanelCallback.onComplete(false, new CommonError(errorInfo));
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(LocalGroup.TAG, "invokeService onSuccess:" + outputParams);
                String json = GsonUtils.toJson(outputParams);
                JSONObject jSONObject = new JSONObject();
                try {
                    if (!TextUtils.isEmpty(json)) {
                        jSONObject = (JSONObject) JSON.toJSON(json);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (atomicBoolean.compareAndSet(false, true)) {
                    iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
                }
            }
        });
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = list.size();
        ALog.d(TAG, "need ble invoke service.");
        for (int i2 = 0; i2 < size; i2++) {
            DeviceItem deviceItem = list.get(i2);
            if (deviceItem != null && !TextUtils.isEmpty(deviceItem.iotId)) {
                for (Map.Entry<String, LocalChannelDevice> entry2 : this.mLocalDeviceList.entrySet()) {
                    if (deviceItem.iotId.equalsIgnoreCase(entry2.getKey())) {
                        entry2.getValue().invokeService(str, panelMethodExtraData, new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.4
                            @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                            public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                ALog.d(LocalGroup.TAG, "invokeService onComplete onSuccess:" + obj);
                                String json = GsonUtils.toJson(obj);
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    if (!TextUtils.isEmpty(json)) {
                                        jSONObject = (JSONObject) JSON.toJSON(json);
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                if (atomicBoolean.compareAndSet(false, true)) {
                                    iPanelCallback.onComplete(true, ResponseUtils.getSuccessRspJson(jSONObject));
                                }
                            }
                        }, true);
                    }
                }
            }
        }
    }

    public void isAllDeviceLocalOnlineAndConnected(final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isAllDeviceLocalOnlineAndConnected callback:" + iPanelCallback);
        if (iPanelCallback == null) {
            ALog.e(TAG, "isAllDeviceLocalOnlineAndConnected callback empty");
        } else {
            getLocalState(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.7
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalGroup.TAG, "isAllDeviceLocalOnlineAndConnected getLocalState isSuccess:" + z2 + " data:" + obj);
                    LocalGroup.this.createAllLocalDevice(LocalGroup.this.mGroupImpl.a().localGroupDeviceData);
                    iPanelCallback.onComplete(z2, String.valueOf(obj));
                }
            });
        }
    }

    public void setGroupProperties(List<DeviceItem> list, String str, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setGroupProperties params:" + str + ";bleItemList=" + list + " extraData:" + panelMethodExtraData + " callback:" + iPanelCallback);
        if (iPanelCallback == null) {
            ALog.e(TAG, "setGroupProperties callback empty");
            return;
        }
        try {
            JSON.parseObject(str).getString("iotId");
        } catch (Exception e2) {
            ALog.w(TAG, "parseObject w:" + e2.toString());
        }
        SetGroupPropCallback setGroupPropCallback = new SetGroupPropCallback(iPanelCallback);
        new SkipGroupPropCallback(iPanelCallback);
        for (Map.Entry<String, LocalChannelDevice> entry : this.mLocalDeviceList.entrySet()) {
            if (list == null || list.isEmpty() || !list.contains(entry.getKey())) {
                ALog.d(TAG, "use alcs channel iotId=" + entry.getKey());
                entry.getValue().setProperties(str, panelMethodExtraData, setGroupPropCallback, true);
            } else {
                ALog.d(TAG, "already use cloud channel, iotId=" + entry.getKey());
            }
        }
    }

    public void setMeshGroupProperties(String str, final IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string;
        String string2;
        String string3;
        ALog.d(TAG, "setMeshGroupProperties params=" + str + "callback=" + iPanelCallback);
        HashMap map = new HashMap();
        try {
            JSONObject object = JSON.parseObject(str);
            string2 = object.getString("iotId");
            try {
                string3 = object.getString("controlGroupId");
                try {
                    string = object.getString("items");
                    try {
                        map.put("iotId", string2);
                        map.put("groupAddress", Integer.valueOf(TgMeshHelper.getGroupAddress(string3)));
                    } catch (Exception e2) {
                        e = e2;
                        ALog.w(TAG, "parseObject w:" + e.toString());
                        String str2 = string;
                        if (TextUtils.isEmpty(string3)) {
                        }
                        iPanelCallback.onComplete(false, null);
                    }
                } catch (Exception e3) {
                    e = e3;
                    string = null;
                }
            } catch (Exception e4) {
                e = e4;
                string = null;
                string3 = null;
            }
        } catch (Exception e5) {
            e = e5;
            string = null;
            string2 = null;
            string3 = null;
        }
        String str22 = string;
        if (!TextUtils.isEmpty(string3) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(str22)) {
            iPanelCallback.onComplete(false, null);
        } else {
            TgMeshManager.getInstance().sendGroupMessage(string3, "thing.attribute.set", str22, map, new IActionListener<Boolean>() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.1
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalGroup.TAG, "onFailure: s" + str3);
                    iPanelCallback.onComplete(false, null);
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Boolean bool) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalGroup.TAG, "onSuccess: aBoolean=" + bool);
                    iPanelCallback.onComplete(bool.booleanValue(), ResponseUtils.getSuccessRspJson(new JSONObject()));
                }
            });
        }
    }

    public void subAllEvents(IPanelGroupEventCallback iPanelGroupEventCallback, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mPanelEventCallback = iPanelGroupEventCallback;
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(true, null);
        }
        this.mLocalDeviceChangeListener = new LocalDeviceListChangeListenerWrapper(this.mPanelEventCallback, this.mGroupId, this.mGroupImpl.a().localGroupDeviceData);
        if (this.mGroupImpl.a().localGroupDeviceData == null) {
            getLocalState(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup.5
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(LocalGroup.TAG, "onComplete isSuccess:" + z2);
                }
            });
        }
        a.a().a(this.mLocalDeviceChangeListener);
    }

    public void unsubAllEvents(IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) {
        this.mPanelEventCallback = null;
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(true, null);
        }
        this.mLocalDeviceChangeListener = null;
    }

    protected void getLocalState(IPanelCallback iPanelCallback, AError aError, QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iPanelCallback == null) {
            ALog.e(TAG, "getLocalState boneCallback empty");
            return;
        }
        GroupLocalStatePayload groupLocalStatePayload = new GroupLocalStatePayload();
        groupLocalStatePayload.setCode(300);
        if (queryLocalGroupDeviceResData != null && queryLocalGroupDeviceResData.items != null) {
            LocalDeviceListChangeListenerWrapper localDeviceListChangeListenerWrapper = this.mLocalDeviceChangeListener;
            if (localDeviceListChangeListenerWrapper != null) {
                localDeviceListChangeListenerWrapper.setLocalGroupData(queryLocalGroupDeviceResData);
            }
            groupLocalStatePayload.setCode(200);
            GroupLocalStatePayload.GroupLocalStateData groupLocalStateData = new GroupLocalStatePayload.GroupLocalStateData();
            int i2 = 0;
            for (QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner queryLocalGroupDeviceResDataInner : queryLocalGroupDeviceResData.items) {
                DeviceBasicData deviceBasicData = DeviceManager.getInstance().getDeviceBasicData(TextHelper.combineStr(queryLocalGroupDeviceResDataInner.productKey, queryLocalGroupDeviceResDataInner.deviceName));
                GroupLocalStatePayload.DeviceLocalStatus deviceLocalStatus = new GroupLocalStatePayload.DeviceLocalStatus();
                deviceLocalStatus.iotId = queryLocalGroupDeviceResDataInner.iotId;
                deviceLocalStatus.productKey = queryLocalGroupDeviceResDataInner.productKey;
                deviceLocalStatus.deviceName = queryLocalGroupDeviceResDataInner.deviceName;
                deviceLocalStatus.time = System.currentTimeMillis();
                if (deviceBasicData != null) {
                    i2++;
                    deviceLocalStatus.status = 1;
                    deviceLocalStatus.localOnLineSubStatus = deviceBasicData.localDiscoveryType;
                } else {
                    deviceLocalStatus.status = 3;
                }
                groupLocalStateData.deviceLocalStatus.add(deviceLocalStatus);
            }
            if (i2 >= queryLocalGroupDeviceResData.totalNum) {
                groupLocalStateData.groupLocalStatus = 0;
            } else if (i2 == 0) {
                groupLocalStateData.groupLocalStatus = 2;
            } else {
                groupLocalStateData.groupLocalStatus = 1;
            }
            ALog.d(TAG, "sendRequest onSuccess onlineCount:" + i2);
            groupLocalStatePayload.setData(groupLocalStateData);
            iPanelCallback.onComplete(true, JSON.toJSONString(groupLocalStatePayload));
            return;
        }
        if (aError != null) {
            groupLocalStatePayload.setMsg(aError.getMsg());
        }
        iPanelCallback.onComplete(false, JSON.toJSONString(groupLocalStatePayload));
    }
}
