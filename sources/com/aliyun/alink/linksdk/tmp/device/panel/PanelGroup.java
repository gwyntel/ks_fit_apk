package com.aliyun.alink.linksdk.tmp.device.panel;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData;
import com.aliyun.alink.linksdk.tmp.device.panel.data.group.GroupLocalStatePayload;
import com.aliyun.alink.linksdk.tmp.device.panel.linkselection.CloudGroup;
import com.aliyun.alink.linksdk.tmp.device.panel.linkselection.LocalGroup;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelGroupEventCallback;
import com.aliyun.alink.linksdk.tmp.device.payload.service.DeviceItem;
import com.aliyun.alink.linksdk.tmp.error.ParamsError;
import com.aliyun.alink.linksdk.tmp.network.NetworkManager;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

/* loaded from: classes2.dex */
public class PanelGroup {
    public static final String TAG = "[Tmp]PanelGroup";
    private CloudGroup mCloudGroup;
    private String mGroupId;
    private LocalGroup mLocalGroup;
    private long clickTime = 0;
    private ScheduledFuture scheduledFuture = null;

    public PanelGroup(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "new PanelGroup groupId:" + str);
        if (TextUtils.isEmpty(str)) {
            ALog.w(TAG, "PanelGroup groupId empty");
            return;
        }
        this.mGroupId = str;
        this.mCloudGroup = new CloudGroup(str);
        this.mLocalGroup = new LocalGroup(str);
    }

    private boolean choseCloudChannel(GroupLocalStatePayload.DeviceLocalStatus deviceLocalStatus) {
        int i2 = deviceLocalStatus.status;
        if (i2 == 3) {
            return true;
        }
        return i2 == 1 && deviceLocalStatus.localOnLineSubStatus == TmpEnum.DeviceNetType.NET_BT.getValue();
    }

    public CloudGroup getCloudGroup() {
        return this.mCloudGroup;
    }

    public LocalGroup getLocalGroup() {
        return this.mLocalGroup;
    }

    public void getLocalState(IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LocalGroup localGroup = this.mLocalGroup;
        if (localGroup != null) {
            localGroup.getLocalState(iPanelCallback);
            return;
        }
        ALog.e(TAG, "getLocalState mLocalGroup empty");
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(false, new ParamsError());
        }
    }

    public void invokeGroupService(final String str, final IPanelCallback iPanelCallback, final PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "invokeGroupService params:" + str + " extraData:" + panelMethodExtraData + " callback:" + iPanelCallback);
        if (panelMethodExtraData == null) {
            panelMethodExtraData = new PanelMethodExtraData(TmpEnum.ChannelStrategy.LOCAL_CHANNEL_FIRST);
        }
        panelMethodExtraData.build(JSON.parseObject(str));
        TmpEnum.ChannelStrategy channelStrategy = TmpEnum.ChannelStrategy.CLOUD_CHANNEL_ONLY;
        TmpEnum.ChannelStrategy channelStrategy2 = panelMethodExtraData.channelStrategy;
        if (channelStrategy == channelStrategy2) {
            CloudGroup cloudGroup = this.mCloudGroup;
            if (cloudGroup != null) {
                cloudGroup.invokeGroupService(str, panelMethodExtraData, iPanelCallback);
                return;
            } else {
                ALog.e(TAG, "invokeGroupService mCloudGroup empty");
                iPanelCallback.onComplete(false, new ParamsError());
                return;
            }
        }
        if (TmpEnum.ChannelStrategy.LOCAL_CHANNEL_ONLY == channelStrategy2) {
            LocalGroup localGroup = this.mLocalGroup;
            if (localGroup != null) {
                localGroup.invokeGroupService(null, str, iPanelCallback, panelMethodExtraData);
                return;
            }
            ALog.e(TAG, "invokeGroupService mLocalGroup empty");
            if (iPanelCallback != null) {
                iPanelCallback.onComplete(false, new ParamsError());
                return;
            }
            return;
        }
        if (this.mLocalGroup == null) {
            CloudGroup cloudGroup2 = this.mCloudGroup;
            if (cloudGroup2 != null) {
                cloudGroup2.invokeGroupService(str, panelMethodExtraData, iPanelCallback);
                return;
            } else {
                ALog.e(TAG, "invokeGroupService mLocalGroup&mCloudGroup empty ");
                iPanelCallback.onComplete(false, new ParamsError());
                return;
            }
        }
        int networkType = NetworkManager.getNetworkType(TmpSdk.getContext());
        ALog.d(TAG, "getNetworkType networkType:" + networkType);
        if (networkType == 0) {
            this.mCloudGroup.invokeGroupService(str, panelMethodExtraData, iPanelCallback);
        } else {
            this.mLocalGroup.isAllDeviceLocalOnlineAndConnected(new IPanelCallback() { // from class: com.aliyun.alink.linksdk.tmp.device.panel.PanelGroup.6
                @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback
                public void onComplete(boolean z2, @Nullable Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(PanelGroup.TAG, "invokeGroupService isAllDeviceLocalOnlineAndConnected isSuccess:" + z2 + " data:" + obj);
                    GroupLocalStatePayload groupLocalStatePayload = (!z2 || obj == null) ? null : (GroupLocalStatePayload) JSON.parseObject(String.valueOf(obj), GroupLocalStatePayload.class);
                    if (groupLocalStatePayload == null || groupLocalStatePayload.getData() == null || !(groupLocalStatePayload.getData().groupLocalStatus == 1 || groupLocalStatePayload.getData().groupLocalStatus == 0)) {
                        ALog.d(PanelGroup.TAG, "PersistentConnectState connectState :" + PersistentNet.getInstance().getConnectState() + " networkType:" + NetworkManager.getNetworkType(TmpSdk.getContext()));
                        if (PanelGroup.this.mCloudGroup != null) {
                            PanelGroup.this.mCloudGroup.invokeGroupService(str, panelMethodExtraData, iPanelCallback);
                            return;
                        }
                        ALog.e(PanelGroup.TAG, "invokeGroupService mCloudGroup empty");
                        IPanelCallback iPanelCallback2 = iPanelCallback;
                        if (iPanelCallback2 != null) {
                            iPanelCallback2.onComplete(false, new ParamsError());
                            return;
                        }
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (GroupLocalStatePayload.DeviceLocalStatus deviceLocalStatus : groupLocalStatePayload.getData().deviceLocalStatus) {
                        if (deviceLocalStatus != null) {
                            if (deviceLocalStatus.status == 3) {
                                arrayList.add(new DeviceItem(deviceLocalStatus.iotId));
                            }
                            if (deviceLocalStatus.status == 1 && deviceLocalStatus.localOnLineSubStatus == TmpEnum.DeviceNetType.NET_BT.getValue()) {
                                if (PersistentNet.getInstance().getConnectState() == PersistentConnectState.CONNECTED) {
                                    arrayList.add(new DeviceItem(deviceLocalStatus.iotId));
                                } else {
                                    arrayList2.add(new DeviceItem(deviceLocalStatus.iotId));
                                }
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        PanelGroup.this.mCloudGroup.invokeBatchService(arrayList, str, panelMethodExtraData, iPanelCallback);
                    }
                    PanelGroup.this.mLocalGroup.invokeGroupService(arrayList2, str, iPanelCallback, panelMethodExtraData);
                }
            });
        }
    }

    public void setGroupId(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!TextUtils.isEmpty(this.mGroupId)) {
            ALog.w(TAG, "setGroupId groupId not empty");
            return;
        }
        this.mGroupId = str;
        this.mLocalGroup = new LocalGroup(str);
        this.mCloudGroup = new CloudGroup(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setGroupProperties(final java.lang.String r13, final com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback r14, com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData r15) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.device.panel.PanelGroup.setGroupProperties(java.lang.String, com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback, com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData):void");
    }

    public void subAllEvents(IPanelGroupEventCallback iPanelGroupEventCallback, IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LocalGroup localGroup = this.mLocalGroup;
        if (localGroup != null) {
            localGroup.subAllEvents(iPanelGroupEventCallback, iPanelCallback, panelMethodExtraData);
            return;
        }
        ALog.e(TAG, "subAllEvents mLocalGroup empty");
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(false, new ParamsError());
        }
    }

    public void unsubAllEvents(IPanelCallback iPanelCallback, PanelMethodExtraData panelMethodExtraData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LocalGroup localGroup = this.mLocalGroup;
        if (localGroup != null) {
            localGroup.unsubAllEvents(iPanelCallback, panelMethodExtraData);
            return;
        }
        ALog.e(TAG, "subAllEvents mLocalGroup empty");
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(false, new ParamsError());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setGroupProperties(final java.lang.String r16, final com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback r17, com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData r18, boolean r19) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.linksdk.tmp.device.panel.PanelGroup.setGroupProperties(java.lang.String, com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback, com.aliyun.alink.linksdk.tmp.device.panel.data.PanelMethodExtraData, boolean):void");
    }
}
