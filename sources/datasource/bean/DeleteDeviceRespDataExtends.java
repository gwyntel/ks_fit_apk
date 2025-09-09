package datasource.bean;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class DeleteDeviceRespDataExtends implements Serializable {
    public String deviceKey;
    public int netKeyIndex;
    public int primaryUnicastAddress;

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public int getNetKeyIndex() {
        return this.netKeyIndex;
    }

    public int getPrimaryUnicastAddress() {
        return this.primaryUnicastAddress;
    }

    public void setDeviceKey(String str) {
        this.deviceKey = str;
    }

    public void setNetKeyIndex(int i2) {
        this.netKeyIndex = i2;
    }

    public void setPrimaryUnicastAddress(int i2) {
        this.primaryUnicastAddress = i2;
    }
}
