package com.yc.utesdk.watchface.bean.acts;

/* loaded from: classes4.dex */
public class DeviceWatchFaceDao {
    private String id;
    private int type;
    private String version;

    public DeviceWatchFaceDao() {
    }

    public String getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public String getVersion() {
        return this.version;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public DeviceWatchFaceDao(String str, String str2, int i2) {
        this.id = str;
        this.version = str2;
        this.type = i2;
    }
}
