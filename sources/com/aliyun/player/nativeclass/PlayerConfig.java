package com.aliyun.player.nativeclass;

/* loaded from: classes3.dex */
public class PlayerConfig {
    public String mHttpProxy = "";
    public String mReferrer = "";
    public int mNetworkTimeout = 15000;
    public int mMaxDelayTime = 5000;
    public int mMaxBufferDuration = 50000;
    public int mHighBufferDuration = 3000;
    public int mStartBufferDuration = 500;
    public int mMaxProbeSize = -1;
    public boolean mClearFrameWhenStop = false;
    public boolean mEnableVideoTunnelRender = false;
    public boolean mEnableVideoBufferRender = false;
    public boolean mEnableSEI = false;
    public String mUserAgent = "";
    public int mNetworkRetryCount = 2;
    public int mLiveStartIndex = -3;
    public boolean mDisableAudio = false;
    public boolean mDisableVideo = false;
    public int mPositionTimerIntervalMs = 500;
    public long mMaxBackwardBufferDurationMs = 0;
    public boolean mPreferAudio = false;
    public boolean mEnableLocalCache = true;
    public int mEnableHttpDns = -1;
    public int mEnableEnhancedHttpDns = -1;
    public boolean mEnableHttp3 = false;
    public boolean mEnableStrictFlvHeader = false;
    public boolean mEnableLowLatencyMode = true;
    public boolean mEnableProjection = false;
    public boolean mEnableStrictAuthMode = true;
    public int mStartBufferLimit = 15000;
    public int mStopBufferLimit = 3000;
    public int mSelectTrackBufferMode = 0;
    public int mMaxAllowedAbrVideoPixelNumber = Integer.MAX_VALUE;
    private String[] mCustomHeaders = null;

    public enum PixelNumber {
        Resolution_360P(172800),
        Resolution_480P(345600),
        Resolution_540P(518400),
        Resolution_720P(921600),
        Resolution_1080P(2073600),
        Resolution_2K(3686400),
        Resolution_4K(8847360),
        Resolution_NoLimit(Integer.MAX_VALUE);

        private int mValue;

        PixelNumber(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    private PlayerConfig() {
    }

    public String[] getCustomHeaders() {
        return this.mCustomHeaders;
    }

    public void setCustomHeaders(String[] strArr) {
        this.mCustomHeaders = strArr;
    }

    protected PlayerConfig(int i2) {
    }
}
