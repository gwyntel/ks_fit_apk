package com.yc.utesdk.watchface.close;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RgbAarrayInfo {
    private List<PicTypeConfigInfo> PicTypeConfigList;
    private List<PicDataInfo> picDataConfig;
    private List<PicTypeInfo> picTypeConfig;
    private byte[] watchConfig;
    private WatchConfigInfo watchConfigInfo;

    public RgbAarrayInfo() {
        this.picTypeConfig = new ArrayList();
        this.picDataConfig = new ArrayList();
    }

    public List<PicDataInfo> getPicDataConfig() {
        return this.picDataConfig;
    }

    public List<PicTypeInfo> getPicTypeConfig() {
        return this.picTypeConfig;
    }

    public List<PicTypeConfigInfo> getPicTypeConfigList() {
        return this.PicTypeConfigList;
    }

    public byte[] getWatchConfig() {
        return this.watchConfig;
    }

    public WatchConfigInfo getWatchConfigInfo() {
        return this.watchConfigInfo;
    }

    public void setPicDataConfig(List<PicDataInfo> list) {
        this.picDataConfig = list;
    }

    public void setPicTypeConfig(List<PicTypeInfo> list) {
        this.picTypeConfig = list;
    }

    public void setPicTypeConfigList(List<PicTypeConfigInfo> list) {
        this.PicTypeConfigList = list;
    }

    public void setWatchConfig(byte[] bArr) {
        this.watchConfig = bArr;
    }

    public void setWatchConfigInfo(WatchConfigInfo watchConfigInfo) {
        this.watchConfigInfo = watchConfigInfo;
    }

    public RgbAarrayInfo(byte[] bArr, List<PicTypeInfo> list, List<PicDataInfo> list2) {
        this.picTypeConfig = new ArrayList();
        new ArrayList();
        this.watchConfig = bArr;
        this.picTypeConfig = list;
        this.picDataConfig = list2;
    }
}
