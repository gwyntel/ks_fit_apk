package com.aliyun.alink.linksdk.alcs.api.server;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class AlcsServerConfig {
    protected String mBlacklist;
    protected String mDeviceName;
    protected int mHeartBeatTimeout;
    protected boolean mIsSecurityPost;
    protected List<PrefixSecretNode> mPreSecList;
    protected String mProductKey;
    protected int mSafePort;
    protected int mUnSafePort;
    private int sessionExpireValue;

    public static final class Builder {
        private AlcsServerConfig config = new AlcsServerConfig();

        public Builder addPrefixSec(String str, String str2) {
            this.config.mPreSecList.add(new PrefixSecretNode(str, str2));
            return this;
        }

        public AlcsServerConfig build() {
            return this.config;
        }

        public Builder setBlackList(String str) {
            this.config.mBlacklist = str;
            return this;
        }

        public Builder setDevcieName(String str) {
            this.config.mDeviceName = str;
            return this;
        }

        public Builder setHeartBeatTimeout(int i2) {
            this.config.mHeartBeatTimeout = i2;
            return this;
        }

        @Deprecated
        public Builder setIsSecurityPort(boolean z2) {
            this.config.mIsSecurityPost = z2;
            return this;
        }

        public Builder setProdKey(String str) {
            this.config.mProductKey = str;
            return this;
        }

        public Builder setSafePort(int i2) {
            this.config.mSafePort = i2;
            return this;
        }

        public Builder setSessionExpireValue(int i2) {
            this.config.sessionExpireValue = i2;
            return this;
        }

        public Builder setUnsafePort(int i2) {
            this.config.mUnSafePort = i2;
            return this;
        }
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public int getHeartBeatTimeout() {
        return this.mHeartBeatTimeout;
    }

    public List<PrefixSecretNode> getPreSecList() {
        return this.mPreSecList;
    }

    public String getProductKey() {
        return this.mProductKey;
    }

    public int getSafePort() {
        return this.mSafePort;
    }

    public int getSessionExpireValue() {
        return this.sessionExpireValue;
    }

    public int getUnSafePort() {
        return this.mUnSafePort;
    }

    @Deprecated
    public boolean isSecurityPost() {
        return this.mIsSecurityPost;
    }

    private AlcsServerConfig() {
        this.mIsSecurityPost = true;
        this.sessionExpireValue = 86400;
        this.mHeartBeatTimeout = 60000;
        this.mUnSafePort = 5683;
        this.mSafePort = 5684;
        this.mPreSecList = new ArrayList();
    }
}
