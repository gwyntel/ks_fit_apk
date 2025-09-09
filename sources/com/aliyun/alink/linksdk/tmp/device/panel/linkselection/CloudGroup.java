package com.aliyun.alink.linksdk.tmp.device.panel.linkselection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.ConnectListenerWrapper;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.property.BatchSetPropRequest;
import com.aliyun.alink.linksdk.tmp.device.payload.service.BatchInvokeServiceRequest;
import com.aliyun.alink.linksdk.tmp.device.payload.service.DeviceItem;
import com.aliyun.alink.linksdk.tmp.device.payload.service.GroupInvokeServiceRequest;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class CloudGroup {
    private static final String TAG = "[Tmp]CloudGroup";
    protected String mGroupId;

    public CloudGroup(String str) {
        this.mGroupId = str;
    }

    public void invokeBatchService(List<DeviceItem> list, String str, PanelMethodExtraData panelMethodExtraData, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject object = JSON.parseObject(str);
        BatchInvokeServiceRequest batchInvokeServiceRequest = new BatchInvokeServiceRequest();
        batchInvokeServiceRequest.deviceList = list;
        batchInvokeServiceRequest.args = (Map) object.get("args");
        batchInvokeServiceRequest.identifier = (String) object.get("identifier");
        batchInvokeServiceRequest.sendRequest(batchInvokeServiceRequest, iPanelCallback);
    }

    public void invokeGroupService(String str, PanelMethodExtraData panelMethodExtraData, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GroupInvokeServiceRequest groupInvokeServiceRequest = new GroupInvokeServiceRequest();
        JSONObject object = JSON.parseObject(str);
        groupInvokeServiceRequest.args = (Map) object.get("args");
        groupInvokeServiceRequest.identifier = (String) object.get("identifier");
        groupInvokeServiceRequest.controlGroupId = (String) object.get("controlGroupId");
        groupInvokeServiceRequest.sendRequest(groupInvokeServiceRequest, iPanelCallback);
    }

    public void setBatchDeviceProperties(List<DeviceItem> list, String str, PanelMethodExtraData panelMethodExtraData, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setBatchDeviceProperties params:" + str + " deviceItemList:" + list + " callback:" + iPanelCallback);
        BatchSetPropRequest batchSetPropRequest = new BatchSetPropRequest();
        batchSetPropRequest.deviceList = list;
        batchSetPropRequest.items = JSON.parseObject(str).get("items");
        batchSetPropRequest.sendRequest(batchSetPropRequest, iPanelCallback);
    }

    public void setGroupProperties(String str, PanelMethodExtraData panelMethodExtraData, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setGroupProperties params:" + str + " callback:" + iPanelCallback);
        CloudUtils.setGroupProperties(JSON.parseObject(str), new ConnectListenerWrapper(null, null, iPanelCallback));
    }
}
