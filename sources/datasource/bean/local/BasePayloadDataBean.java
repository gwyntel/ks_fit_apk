package datasource.bean.local;

import java.util.Map;

/* loaded from: classes4.dex */
public class BasePayloadDataBean {
    public String alias;
    public int appKeyIndex;
    public String devId;
    public String devType;
    public String devTypeEn;
    public boolean infrared;
    public String iotId;
    public boolean lowerPower;
    public int netKeyIndex;
    public String originType;
    public String platform;
    public String productKey;
    public Map<String, Object> status;
    public boolean transparent;
    public int unicastAddress;
    public String uuid;
    public String zone;

    public String getAlias() {
        return this.alias;
    }

    public int getAppKeyIndex() {
        return this.appKeyIndex;
    }

    public String getDevId() {
        return this.devId;
    }

    public String getDevType() {
        return this.devType;
    }

    public String getDevTypeEn() {
        return this.devTypeEn;
    }

    public String getIotId() {
        return this.iotId;
    }

    public int getNetKeyIndex() {
        return this.netKeyIndex;
    }

    public String getOriginType() {
        return this.originType;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public Map<String, Object> getStatus() {
        return this.status;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getZone() {
        return this.zone;
    }

    public boolean isInfrared() {
        return this.infrared;
    }

    public boolean isLowerPower() {
        return this.lowerPower;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    public void setAppKeyIndex(int i2) {
        this.appKeyIndex = i2;
    }

    public void setDevId(String str) {
        this.devId = str;
    }

    public void setDevType(String str) {
        this.devType = str;
    }

    public void setDevTypeEn(String str) {
        this.devTypeEn = str;
    }

    public void setInfrared(boolean z2) {
        this.infrared = z2;
    }

    public void setIotId(String str) {
        this.iotId = str;
    }

    public void setLowerPower(boolean z2) {
        this.lowerPower = z2;
    }

    public void setNetKeyIndex(int i2) {
        this.netKeyIndex = i2;
    }

    public void setOriginType(String str) {
        this.originType = str;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public void setStatus(Map<String, Object> map) {
        this.status = map;
    }

    public void setTransparent(boolean z2) {
        this.transparent = z2;
    }

    public void setUnicastAddress(int i2) {
        this.unicastAddress = i2;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public void setZone(String str) {
        this.zone = str;
    }
}
