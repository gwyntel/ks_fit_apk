package com.yc.utesdk.watchface.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class WatchFaceOnlineInfo {
    private int flag;
    private int totalCount;
    private List<WatchFaceOnlineOneInfo> watchFaceOnlineOneInfoList;

    public int getFlag() {
        return this.flag;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public List<WatchFaceOnlineOneInfo> getWatchFaceOnlineOneInfoList() {
        return this.watchFaceOnlineOneInfoList;
    }

    public void setFlag(int i2) {
        this.flag = i2;
    }

    public void setTotalCount(int i2) {
        this.totalCount = i2;
    }

    public void setWatchFaceOnlineOneInfoList(List<WatchFaceOnlineOneInfo> list) {
        this.watchFaceOnlineOneInfoList = list;
    }
}
