package com.heytap.mcssdk.mode;

/* loaded from: classes3.dex */
public class AppLimitBean {
    private int count;
    private long lastedTime;

    public AppLimitBean(long j2, int i2) {
        this.lastedTime = j2;
        this.count = i2;
    }

    public int getCount() {
        return this.count;
    }

    public long getLastedTime() {
        return this.lastedTime;
    }

    public void setCount(int i2) {
        this.count = i2;
    }

    public void setLastedTime(long j2) {
        this.lastedTime = j2;
    }
}
