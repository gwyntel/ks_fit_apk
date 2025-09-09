package com.aliyun.alink.linksdk.tmp.utils;

import java.util.List;

/* loaded from: classes2.dex */
public class MeshGroupInfo {
    private int count;
    private int groupAddress;
    private List<String> iotIds;

    public MeshGroupInfo(int i2, int i3) {
        this.count = i2;
        this.groupAddress = i3;
    }

    public int getCount() {
        return this.count;
    }

    public int getGroupAddress() {
        return this.groupAddress;
    }

    public List<String> getIotIds() {
        return this.iotIds;
    }

    public void setCount(int i2) {
        this.count = i2;
    }

    public void setGroupAddress(int i2) {
        this.groupAddress = i2;
    }

    public void setIotIds(List<String> list) {
        this.iotIds = list;
    }
}
