package com.aliyun.player.source;

/* loaded from: classes3.dex */
public class SourceBase {
    private String mCoverPath;
    protected boolean mForceQuality = false;
    protected String mQuality;
    private String mTitle;

    public String getCoverPath() {
        return this.mCoverPath;
    }

    public String getQuality() {
        return this.mQuality;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public boolean isForceQuality() {
        return this.mForceQuality;
    }

    public void setCoverPath(String str) {
        this.mCoverPath = str;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }
}
