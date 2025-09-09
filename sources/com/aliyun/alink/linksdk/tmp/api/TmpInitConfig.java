package com.aliyun.alink.linksdk.tmp.api;

import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxy;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes2.dex */
public class TmpInitConfig {
    public static final int DAILY = 0;
    public static final int ONLINE = 2;
    public static final int PRE = 1;
    private static final String TAG = "[Tmp]TmpInitConfig";
    public ICloudProxy mCloudProxy;
    public int mEnv;
    public PalDeviceInfo mIcaPalDeviceInfo;
    public boolean mIsCheckChannelRootCrt;
    public List<IPlugin> mLpbsPluginList;
    public String mMqttChannelHost;

    public TmpInitConfig() {
        this(null, true);
    }

    public TmpInitConfig(String str, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mMqttChannelHost = str;
        this.mIsCheckChannelRootCrt = z2;
        ALog.d(TAG, "TmpInitConfig mMqttChannelHost:" + this.mMqttChannelHost);
        ALog.d(TAG, "TmpInitConfig mIsCheckChannelRootCrt:" + this.mIsCheckChannelRootCrt);
    }

    public TmpInitConfig(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mEnv = i2;
        if (i2 == 0) {
            this.mMqttChannelHost = "ssl://10.125.0.27:1883";
            this.mIsCheckChannelRootCrt = false;
        } else if (i2 == 1) {
            this.mMqttChannelHost = "ssl://100.67.80.75:80";
            this.mIsCheckChannelRootCrt = true;
        } else if (i2 == 2) {
            this.mMqttChannelHost = null;
            this.mIsCheckChannelRootCrt = true;
        }
        ALog.d(TAG, "TmpInitConfig mMqttChannelHost:" + this.mMqttChannelHost);
        ALog.d(TAG, "TmpInitConfig mIsCheckChannelRootCrt:" + this.mIsCheckChannelRootCrt);
    }
}
