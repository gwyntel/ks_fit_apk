package com.kingsmith.aliiot.bean;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class OTADeviceDetailInfo implements Serializable {
    public String currentTimestamp;
    public String currentVersion;
    public String desc;
    public String iotId;
    public String md5;
    public String name;
    public String netType;
    public String size;
    public String timestamp;
    public String url;
    public String version;

    public String toString() {
        return "OTADeviceDetailInfo{iotId='" + this.iotId + "', currentVersion='" + this.currentVersion + "', currentTimestamp='" + this.currentTimestamp + "', timestamp='" + this.timestamp + "', version='" + this.version + "', size='" + this.size + "', md5='" + this.md5 + "', name='" + this.name + "', url='" + this.url + "', desc='" + this.desc + "', netType='" + this.netType + "'}";
    }
}
