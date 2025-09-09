package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class EphemerisParams {
    public static final int VERSION_AIROHA = 2;
    public static final int VERSION_BROADCOM = 0;
    public static final int VERSION_SONY = 1;
    private int interval;
    private List<String> urlList;
    private int version;

    public int getInterval() {
        return this.interval;
    }

    public List<String> getUrlList() {
        return this.urlList;
    }

    public int getVersion() {
        return this.version;
    }

    public void setInterval(int i2) {
        this.interval = i2;
    }

    public void setUrlList(List<String> list) {
        this.urlList = list;
    }

    public void setVersion(int i2) {
        this.version = i2;
    }
}
