package com.aliyun.alink.linksdk.alcs.lpbs.api;

import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class PluginHelper {
    private static final String TAG = "[AlcsLPBS]PluginHelper";

    public static boolean asyncGroupSendRequest(PalGroupReqMessage palGroupReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPlugin pluginByPluginId = PluginMgr.getInstance().getPluginByPluginId("iot_ica");
        if (pluginByPluginId == null) {
            ALog.e(TAG, "asyncGroupSendRequest plugin empty");
            return false;
        }
        try {
            return pluginByPluginId.getPalBridge().getGroupConnect(palGroupReqMessage.groupInfo).asyncSendRequest(palGroupReqMessage, palMsgListener);
        } catch (Exception e2) {
            ALog.e(TAG, "asyncGroupSendRequest exception:" + e2.toString());
            return false;
        } catch (Throwable th) {
            ALog.e(TAG, "asyncGroupSendRequest Throwable:" + th.toString());
            return false;
        }
    }
}
