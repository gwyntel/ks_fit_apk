package datasource.bean;

import a.a.a.a.b.d.a;

/* loaded from: classes4.dex */
public class ProvisionAppKey implements Comparable<ProvisionAppKey> {
    public String appKey;
    public int appKeyIndex;

    public String getAppKey() {
        return this.appKey;
    }

    public int getAppKeyIndex() {
        return this.appKeyIndex;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setAppKeyIndex(int i2) {
        this.appKeyIndex = i2;
    }

    @Override // java.lang.Comparable
    public int compareTo(ProvisionAppKey provisionAppKey) {
        return a.f1335b ? -(this.appKeyIndex - provisionAppKey.appKeyIndex) : this.appKeyIndex - provisionAppKey.appKeyIndex;
    }
}
