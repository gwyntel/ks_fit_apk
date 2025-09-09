package com.aliyun.alink.linksdk.tmp.device.panel.data;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes2.dex */
public class PanelMethodExtraData {
    private static final String TAG = "[Tmp]PanelMethodExtraData";
    public TmpEnum.ChannelStrategy channelStrategy;
    public boolean mNeedRsp;
    public TmpEnum.QoSLevel mQoSLevel;
    public boolean mRateLimiting;
    public String performanceId;

    public PanelMethodExtraData(TmpEnum.ChannelStrategy channelStrategy) {
        this(channelStrategy, TmpEnum.QoSLevel.QoS_CON);
    }

    public void build(Map<String, Object> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (map != null && map.containsKey(TmpConstant.KEY_IOT_PERFORMANCE_ID)) {
            try {
                this.performanceId = map.get(TmpConstant.KEY_IOT_PERFORMANCE_ID).toString();
            } catch (Exception e2) {
                ALog.e(TAG, "KEY_IOT_PERFORMANCE_ID error e:" + e2.toString());
            }
        }
        if (map != null && map.containsKey(TmpConstant.KEY_IOT_QOS_EX)) {
            try {
                this.mQoSLevel = TmpEnum.QoSLevel.getQoSLevel(String.valueOf(map.get(TmpConstant.KEY_IOT_QOS_EX)));
            } catch (Exception e3) {
                ALog.e(TAG, "KEY_IOT_QOS_EX error e:" + e3.toString());
            }
        } else if (map != null && map.containsKey(TmpConstant.KEY_IOT_QOS)) {
            try {
                this.mQoSLevel = TmpEnum.QoSLevel.getQoSLevel(String.valueOf(map.get(TmpConstant.KEY_IOT_QOS)));
            } catch (Exception e4) {
                ALog.e(TAG, "KEY_IOT_QOS error e:" + e4.toString());
            }
        }
        if (map != null && map.containsKey(TmpConstant.KEY_IOT_NeedRsp_EX)) {
            try {
                this.mNeedRsp = Boolean.valueOf(String.valueOf(map.get(TmpConstant.KEY_IOT_NeedRsp_EX))).booleanValue();
                return;
            } catch (Exception e5) {
                ALog.e(TAG, "KEY_IOT_NeedRsp_EX error e:" + e5.toString());
                return;
            }
        }
        if (map == null || !map.containsKey(TmpConstant.KEY_IOT_NeedRsp)) {
            return;
        }
        try {
            this.mNeedRsp = Boolean.valueOf(String.valueOf(map.get(TmpConstant.KEY_IOT_NeedRsp))).booleanValue();
        } catch (Exception e6) {
            ALog.e(TAG, "KEY_IOT_NeedRsp error e:" + e6.toString());
        }
    }

    public String toString() {
        return String.valueOf(this.channelStrategy);
    }

    public PanelMethodExtraData(TmpEnum.ChannelStrategy channelStrategy, TmpEnum.QoSLevel qoSLevel) {
        this.mRateLimiting = true;
        this.channelStrategy = channelStrategy;
        this.mQoSLevel = qoSLevel;
        this.mNeedRsp = true;
        this.performanceId = null;
    }
}
