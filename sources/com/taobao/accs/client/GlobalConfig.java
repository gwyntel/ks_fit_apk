package com.taobao.accs.client;

import android.content.Context;
import com.taobao.accs.ChannelService;
import com.taobao.accs.IProcessName;
import com.taobao.accs.client.AccsConfig;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.t;

/* loaded from: classes4.dex */
public class GlobalConfig {
    public static AccsConfig.ACCS_GROUP mGroup = AccsConfig.ACCS_GROUP.OPEN;
    public static boolean enableCookie = true;
    private static boolean enableJobHeartbeat = false;
    private static boolean enableAlarmHeartbeat = false;

    public static boolean isAlarmHeartbeatEnable() {
        return enableAlarmHeartbeat;
    }

    public static boolean isJobHeartbeatEnable() {
        return enableJobHeartbeat;
    }

    public static void setAlarmHeartbeatEnable(boolean z2) {
        ALog.d("GlobalConfig", "setAlarmHeartbeatEnable", "enable", Boolean.valueOf(z2));
        enableAlarmHeartbeat = z2;
    }

    public static void setChannelProcessName(String str) {
        a.f20082d = str;
    }

    public static void setChannelReuse(boolean z2, AccsConfig.ACCS_GROUP accs_group) {
        GlobalClientInfo.f20068d = z2;
        mGroup = accs_group;
    }

    public static void setControlFrameMaxRetry(int i2) {
        Message.CONTROL_MAX_RETRY_TIMES = i2;
    }

    public static void setCurrProcessNameImpl(IProcessName iProcessName) {
        a.f20083e = iProcessName;
    }

    public static void setEnableForeground(Context context, boolean z2) {
        ALog.i("GlobalConfig", "setEnableForeground", "enable", Boolean.valueOf(z2));
        t.a(context, ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, z2 ? 21 : 0);
    }

    public static void setJobHeartbeatEnable(boolean z2) {
        ALog.d("GlobalConfig", "setJobHeartBeatEnable", "enable", Boolean.valueOf(z2));
        enableJobHeartbeat = z2;
    }

    public static void setMainProcessName(String str) {
        a.f20081c = str;
    }
}
