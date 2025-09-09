package com.yc.utesdk.watchface.bean.acts;

/* loaded from: classes4.dex */
public class WatchFaceReport {
    public static final int WATCH_FACE_REPORT_DELETE_IN_DEVICE = 3;
    public static final int WATCH_FACE_REPORT_FILE_TRANSMITTED_SUCCESS = 2;
    public static final int WATCH_FACE_REPORT_PHOTO_RESOURCE_ACCEPTED = 4;
    public static final int WATCH_FACE_REPORT_SET_CURRENT = 1;
    private String id;
    private int reportType;
    private String version;

    public String getId() {
        return this.id;
    }

    public int getReportType() {
        return this.reportType;
    }

    public String getVersion() {
        return this.version;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setReportType(int i2) {
        this.reportType = i2;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
