package com.yc.utesdk.watchface.bean.acts;

/* loaded from: classes4.dex */
public class ApplyWatchFace {
    public static final int WATCH_FACE_OPERATE_APPLY = 1;
    public static final int WATCH_FACE_OPERATE_APPLY_GOOGLE_PRESET = 3;
    public static final int WATCH_FACE_OPERATE_DELETE = 2;
    private String id;
    private boolean needReceiveFile;
    private int operate;
    private String version;

    public String getId() {
        return this.id;
    }

    public boolean getNeedReceiveFile() {
        return this.needReceiveFile;
    }

    public int getOperate() {
        return this.operate;
    }

    public String getVersion() {
        return this.version;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setNeedReceiveFile(boolean z2) {
        this.needReceiveFile = z2;
    }

    public void setOperate(int i2) {
        this.operate = i2;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
