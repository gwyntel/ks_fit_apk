package com.alibaba.ailabs.iot.mesh.bean;

/* loaded from: classes2.dex */
public class MeshDeviceInfo {
    public String devId;
    public boolean isLowCostMeshDevice;
    public boolean isLowPower;
    public String productKey;

    public MeshDeviceInfo() {
    }

    public String getDevId() {
        return this.devId;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public boolean isLowCostMeshDevice() {
        return this.isLowCostMeshDevice;
    }

    public boolean isLowPower() {
        return this.isLowPower;
    }

    public void setDevId(String str) {
        this.devId = str;
    }

    public void setLowCostMeshDevice(boolean z2) {
        this.isLowCostMeshDevice = z2;
    }

    public void setLowPower(boolean z2) {
        this.isLowPower = z2;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public MeshDeviceInfo(String str, String str2, boolean z2, boolean z3) {
        this.devId = str;
        this.productKey = str2;
        this.isLowCostMeshDevice = z2;
        this.isLowPower = z3;
    }
}
