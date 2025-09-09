package com.taobao.accs;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.v;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class AccsClientConfig implements Serializable {
    public static final String DEFAULT_CONFIGTAG = "default";
    public static final int SECURITY_OFF = 2;
    private static final String TAG = "AccsClientConfig";
    private static Context mContext;
    private boolean mAccsHeartbeatEnable;
    private String mAppKey;
    private String mAppSecret;
    private String mAuthCode;
    private boolean mAutoUnit;
    private String mChannelHost;
    private int mChannelPubKey;
    private int mConfigEnv;
    private boolean mDisableChannel;
    private boolean mForePingEnable;
    private String mInappHost;
    private int mInappPubKey;
    private boolean mKeepalive;
    private int mPingTimeout;
    private boolean mPullUpEnable;
    private boolean mQuickReconnect;
    private int mSecurity;
    private String mStoreId;
    private String mTag;
    public static final String[] DEFAULT_CENTER_HOSTS = {"umengacs.m.taobao.com", "msgacs.wapa.taobao.com", "msgacs.waptest.taobao.com"};
    private static final String[] DEFAULT_CHANNEL_HOSTS = {"umengjmacs.m.taobao.com", "acs.wapa.taobao.com", "acs.waptest.taobao.com"};
    public static boolean loadedStaticConfig = false;

    @ENV
    public static int mEnv = 0;
    public static Map<String, AccsClientConfig> mReleaseConfigs = new ConcurrentHashMap(1);
    public static Map<String, AccsClientConfig> mPreviewConfigs = new ConcurrentHashMap(1);
    public static Map<String, AccsClientConfig> mDebugConfigs = new ConcurrentHashMap(1);

    public static class Builder {
        private String mAppKey = "";
        private String mTag = "";
        private String mAppSecret = "";
        private String mInappHost = "";
        private String mChannelHost = "";
        private String mAuthCode = "";
        private String mStoreId = "";
        private int mInappPubKey = -1;
        private int mChannelPubKey = -1;
        private boolean mKeepalive = true;
        private boolean mAutoUnit = true;
        private int mConfigEnv = 0;
        private boolean mDisableChannel = false;
        private boolean mQuickReconnect = false;
        private boolean mAccsHeartbeatEnable = false;
        private boolean mPullUpEnable = true;
        private boolean mForePingEnable = false;
        private int mPingTimeout = 0;

        public AccsClientConfig build() throws AccsException {
            if (TextUtils.isEmpty(this.mAppKey)) {
                throw new AccsException("appkey null");
            }
            AccsClientConfig accsClientConfig = new AccsClientConfig();
            accsClientConfig.mAppKey = this.mAppKey;
            accsClientConfig.mAppSecret = this.mAppSecret;
            accsClientConfig.mAuthCode = this.mAuthCode;
            accsClientConfig.mKeepalive = this.mKeepalive;
            accsClientConfig.mAutoUnit = this.mAutoUnit;
            accsClientConfig.mInappPubKey = this.mInappPubKey;
            accsClientConfig.mChannelPubKey = this.mChannelPubKey;
            accsClientConfig.mInappHost = this.mInappHost;
            accsClientConfig.mChannelHost = this.mChannelHost;
            accsClientConfig.mTag = this.mTag;
            accsClientConfig.mStoreId = this.mStoreId;
            accsClientConfig.mConfigEnv = this.mConfigEnv;
            accsClientConfig.mDisableChannel = this.mDisableChannel;
            accsClientConfig.mQuickReconnect = this.mQuickReconnect;
            accsClientConfig.mAccsHeartbeatEnable = this.mAccsHeartbeatEnable;
            accsClientConfig.mPullUpEnable = this.mPullUpEnable;
            accsClientConfig.mForePingEnable = this.mForePingEnable;
            accsClientConfig.mPingTimeout = this.mPingTimeout;
            if (accsClientConfig.mConfigEnv < 0) {
                accsClientConfig.mConfigEnv = AccsClientConfig.mEnv;
            }
            accsClientConfig.mSecurity = 2;
            if (TextUtils.isEmpty(accsClientConfig.mInappHost)) {
                accsClientConfig.mInappHost = AccsClientConfig.DEFAULT_CENTER_HOSTS[accsClientConfig.mConfigEnv];
            }
            if (TextUtils.isEmpty(accsClientConfig.mChannelHost)) {
                accsClientConfig.mChannelHost = AccsClientConfig.DEFAULT_CHANNEL_HOSTS[accsClientConfig.mConfigEnv];
            }
            if (TextUtils.isEmpty(accsClientConfig.mTag)) {
                accsClientConfig.mTag = AccsClientConfig.DEFAULT_CONFIGTAG;
            }
            int i2 = accsClientConfig.mConfigEnv;
            Map<String, AccsClientConfig> map = i2 != 1 ? i2 != 2 ? AccsClientConfig.mReleaseConfigs : AccsClientConfig.mDebugConfigs : AccsClientConfig.mPreviewConfigs;
            ALog.d(AccsClientConfig.TAG, "build", "config", accsClientConfig);
            AccsClientConfig accsClientConfig2 = map.get(accsClientConfig.getTag());
            if (accsClientConfig2 != null) {
                ALog.w(AccsClientConfig.TAG, "build conver", "old config", accsClientConfig2);
            }
            map.put(accsClientConfig.getTag(), accsClientConfig);
            return accsClientConfig;
        }

        public Builder setAccsHeartbeatEnable(boolean z2) {
            this.mAccsHeartbeatEnable = z2;
            return this;
        }

        public Builder setAppKey(String str) {
            this.mAppKey = str;
            return this;
        }

        public Builder setAppSecret(String str) {
            this.mAppSecret = str;
            return this;
        }

        public Builder setAutoCode(String str) {
            this.mAuthCode = str;
            return this;
        }

        public Builder setAutoUnit(boolean z2) {
            this.mAutoUnit = z2;
            return this;
        }

        public Builder setChannelHost(String str) {
            this.mChannelHost = str;
            return this;
        }

        public Builder setChannelPubKey(int i2) {
            this.mChannelPubKey = i2;
            return this;
        }

        public Builder setConfigEnv(@ENV int i2) {
            this.mConfigEnv = i2;
            return this;
        }

        public Builder setDisableChannel(boolean z2) {
            this.mDisableChannel = z2;
            return this;
        }

        public Builder setForePingEnable(boolean z2) {
            this.mForePingEnable = z2;
            return this;
        }

        public Builder setInappHost(String str) {
            this.mInappHost = str;
            return this;
        }

        public Builder setInappPubKey(int i2) {
            this.mInappPubKey = i2;
            return this;
        }

        public Builder setKeepAlive(boolean z2) {
            this.mKeepalive = z2;
            return this;
        }

        public Builder setPingTimeout(int i2) {
            this.mPingTimeout = i2;
            return this;
        }

        public Builder setPullUpEnable(boolean z2) {
            this.mPullUpEnable = z2;
            return this;
        }

        public Builder setQuickReconnect(boolean z2) {
            this.mQuickReconnect = z2;
            return this;
        }

        public Builder setStoreId(String str) {
            this.mStoreId = str;
            return this;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface ENV {
    }

    protected AccsClientConfig() {
    }

    @Deprecated
    public static AccsClientConfig getConfig(String str) {
        int i2 = mEnv;
        for (AccsClientConfig accsClientConfig : (i2 != 1 ? i2 != 2 ? mReleaseConfigs : mDebugConfigs : mPreviewConfigs).values()) {
            if (accsClientConfig.mAppKey.equals(str) && accsClientConfig.mConfigEnv == mEnv) {
                return accsClientConfig;
            }
        }
        ALog.e(TAG, "getConfigByTag return null", "appkey", str);
        return null;
    }

    public static AccsClientConfig getConfigByTag(String str) {
        int i2 = mEnv;
        AccsClientConfig accsClientConfig = i2 != 0 ? i2 != 1 ? i2 != 2 ? mReleaseConfigs.get(str) : mDebugConfigs.get(str) : mPreviewConfigs.get(str) : mReleaseConfigs.get(str);
        if (accsClientConfig == null) {
            ALog.e(TAG, "getConfigByTag return null", Constants.KEY_CONFIG_TAG, str);
        }
        return accsClientConfig;
    }

    public static Context getContext() {
        Context context = mContext;
        if (context != null) {
            return context;
        }
        synchronized (AccsClientConfig.class) {
            Context context2 = mContext;
            if (context2 != null) {
                return context2;
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(cls, null);
                mContext = (Context) objInvoke.getClass().getMethod("getApplication", null).invoke(objInvoke, null);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return mContext;
        }
    }

    public static void setAccsConfig(int i2, AccsClientConfig accsClientConfig) {
        Map<String, AccsClientConfig> map = i2 != 1 ? i2 != 2 ? mReleaseConfigs : mDebugConfigs : mPreviewConfigs;
        AccsClientConfig accsClientConfig2 = map.get(accsClientConfig.getTag());
        if (accsClientConfig2 != null) {
            ALog.w(TAG, "build conver", "old config", accsClientConfig2);
        }
        map.put(accsClientConfig.getTag(), accsClientConfig);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccsClientConfig)) {
            return false;
        }
        AccsClientConfig accsClientConfig = (AccsClientConfig) obj;
        return this.mSecurity == accsClientConfig.mSecurity && this.mInappPubKey == accsClientConfig.mInappPubKey && this.mChannelPubKey == accsClientConfig.mChannelPubKey && this.mKeepalive == accsClientConfig.mKeepalive && this.mAutoUnit == accsClientConfig.mAutoUnit && this.mConfigEnv == accsClientConfig.mConfigEnv && this.mDisableChannel == accsClientConfig.mDisableChannel && this.mQuickReconnect == accsClientConfig.mQuickReconnect && this.mAccsHeartbeatEnable == accsClientConfig.mAccsHeartbeatEnable && this.mPullUpEnable == accsClientConfig.mPullUpEnable && this.mForePingEnable == accsClientConfig.mForePingEnable && this.mPingTimeout == accsClientConfig.mPingTimeout && v.a(this.mAppKey, accsClientConfig.mAppKey) && v.a(this.mAppSecret, accsClientConfig.mAppSecret) && v.a(this.mInappHost, accsClientConfig.mInappHost) && v.a(this.mChannelHost, accsClientConfig.mChannelHost) && v.a(this.mStoreId, accsClientConfig.mStoreId) && v.a(this.mAuthCode, accsClientConfig.mAuthCode) && v.a(this.mTag, accsClientConfig.mTag);
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public String getAuthCode() {
        return this.mAuthCode;
    }

    public String getChannelHost() {
        return this.mChannelHost;
    }

    public int getChannelPubKey() {
        return this.mChannelPubKey;
    }

    public int getConfigEnv() {
        return this.mConfigEnv;
    }

    public boolean getDisableChannel() {
        return this.mDisableChannel;
    }

    public String getInappHost() {
        return this.mInappHost;
    }

    public int getInappPubKey() {
        return this.mInappPubKey;
    }

    public int getPingTimeout() {
        return this.mPingTimeout;
    }

    public int getSecurity() {
        return this.mSecurity;
    }

    public String getStoreId() {
        return this.mStoreId;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isAccsHeartbeatEnable() {
        return this.mAccsHeartbeatEnable;
    }

    public boolean isAutoUnit() {
        return this.mAutoUnit;
    }

    public boolean isForePingEnable() {
        return this.mForePingEnable;
    }

    public boolean isKeepalive() {
        return this.mKeepalive;
    }

    public boolean isPullUpEnable() {
        return this.mPullUpEnable;
    }

    public boolean isQuickReconnect() {
        return this.mQuickReconnect;
    }

    public void setForePingEnable(boolean z2) {
        this.mForePingEnable = z2;
    }

    public String toString() {
        return "AccsClientConfig{mAppKey='" + this.mAppKey + "', mAppSecret='" + this.mAppSecret + "', mInappHost='" + this.mInappHost + "', mChannelHost='" + this.mChannelHost + "', mStoreId='" + this.mStoreId + "', mSecurity=" + this.mSecurity + ", mAuthCode='" + this.mAuthCode + "', mInappPubKey=" + this.mInappPubKey + ", mChannelPubKey=" + this.mChannelPubKey + ", mKeepalive=" + this.mKeepalive + ", mAutoUnit=" + this.mAutoUnit + ", mTag='" + this.mTag + "', mConfigEnv=" + this.mConfigEnv + ", mDisableChannel=" + this.mDisableChannel + ", mQuickReconnect=" + this.mQuickReconnect + ", mAccsHeartbeatEnable=" + this.mAccsHeartbeatEnable + ", mPullUpEnable=" + this.mPullUpEnable + ", mForePingEnable=" + this.mForePingEnable + ", mPingTimeout=" + this.mPingTimeout + '}';
    }
}
