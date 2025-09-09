package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class CSBPDevicePmInfo {
    private String csbpPmData;
    private int csbpPmSize;

    public CSBPDevicePmInfo() {
    }

    public String getCsbpPmData() {
        return this.csbpPmData;
    }

    public int getCsbpPmSize() {
        return this.csbpPmSize;
    }

    public void setCsbpPmData(String str) {
        this.csbpPmData = str;
    }

    public void setCsbpPmSize(int i2) {
        this.csbpPmSize = i2;
    }

    public CSBPDevicePmInfo(String str, int i2) {
        this.csbpPmData = str;
        this.csbpPmSize = i2;
    }
}
