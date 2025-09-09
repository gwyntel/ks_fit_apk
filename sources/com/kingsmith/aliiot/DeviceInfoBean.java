package com.kingsmith.aliiot;

/* loaded from: classes4.dex */
public class DeviceInfoBean {
    private String categoryImage;
    private String deviceName;
    private long gmtModified;
    private String identityAlias;
    private String identityId;
    private String iotId;
    private String netType;
    private String nickName;
    private String nodeType;
    private int owned;
    private String productKey;
    private String productName;
    private int status;
    private String thingType;

    public String getCategoryImage() {
        return this.categoryImage;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public long getGmtModified() {
        return this.gmtModified;
    }

    public String getIdentityAlias() {
        return this.identityAlias;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public String getIotId() {
        return this.iotId;
    }

    public String getNetType() {
        return this.netType;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public int getOwned() {
        return this.owned;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getStatus() {
        return this.status;
    }

    public String getThingType() {
        return this.thingType;
    }

    public void setCategoryImage(String str) {
        this.categoryImage = str;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public void setGmtModified(long j2) {
        this.gmtModified = j2;
    }

    public void setIdentityAlias(String str) {
        this.identityAlias = str;
    }

    public void setIdentityId(String str) {
        this.identityId = str;
    }

    public void setIotId(String str) {
        this.iotId = str;
    }

    public void setNetType(String str) {
        this.netType = str;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public void setNodeType(String str) {
        this.nodeType = str;
    }

    public void setOwned(int i2) {
        this.owned = i2;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setThingType(String str) {
        this.thingType = str;
    }

    public String toString() {
        return "DeviceInfoBean{gmtModified=" + this.gmtModified + ", categoryImage='" + this.categoryImage + "', netType='" + this.netType + "', nodeType='" + this.nodeType + "', productKey='" + this.productKey + "', deviceName='" + this.deviceName + "', productName='" + this.productName + "', identityAlias='" + this.identityAlias + "', iotId='" + this.iotId + "', owned=" + this.owned + ", identityId='" + this.identityId + "', thingType='" + this.thingType + "', status=" + this.status + '}';
    }
}
