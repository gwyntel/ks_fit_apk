package com.yc.utesdk.watchface.bean;

import android.graphics.Bitmap;
import com.yc.utesdk.watchface.close.PicUtils;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class WatchFaceCustomInfo implements Comparable<WatchFaceCustomInfo> {
    private Bitmap bitmap;
    private String createtime;
    private String defaultBg;
    private String defaultBgBg;
    private int defaultWidgetPosition;
    private String dpi;
    private String file;
    private String fileBg;
    private String folderDial;
    private String id;
    private String note;
    private int pathStatus;
    private List<PositionBean> position;
    private int type;

    public static class PositionBean {
        private String defaultWidget;
        private String defaultWidgetBg;
        private int widgetPosition;

        public String getDefaultWidget() {
            return this.defaultWidget;
        }

        public String getDefaultWidgetBg() {
            return this.defaultWidgetBg;
        }

        public int getWidgetPosition() {
            return this.widgetPosition;
        }

        public void setDefaultWidget(String str) {
            this.defaultWidget = str;
        }

        public void setDefaultWidgetBg(String str) {
            this.defaultWidgetBg = str;
        }

        public void setWidgetPosition(int i2) {
            this.widgetPosition = i2;
        }
    }

    public WatchFaceCustomInfo() {
        this.id = "0";
        this.type = 2;
        this.dpi = WatchFaceUtil.WATCH_FACE_DPI_240x240;
        this.file = "";
        this.note = "";
        this.createtime = "";
        this.folderDial = "";
        this.pathStatus = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(WatchFaceCustomInfo watchFaceCustomInfo) {
        return (PicUtils.getInstance().isNumeric(getId()) && PicUtils.getInstance().isNumeric(watchFaceCustomInfo.getId())) ? Integer.parseInt(getId()) - Integer.parseInt(watchFaceCustomInfo.getId()) : getId().compareTo(watchFaceCustomInfo.getId());
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public String getDefaultBg() {
        return this.defaultBg;
    }

    public String getDefaultBgBg() {
        return this.defaultBgBg;
    }

    public int getDefaultWidgetPosition() {
        return this.defaultWidgetPosition;
    }

    public String getDpi() {
        return this.dpi;
    }

    public String getFile() {
        return this.file;
    }

    public String getFileBg() {
        return this.fileBg;
    }

    public String getFolderDial() {
        return this.folderDial;
    }

    public String getId() {
        return this.id;
    }

    public String getNote() {
        return this.note;
    }

    public int getPathStatus() {
        return this.pathStatus;
    }

    public List<PositionBean> getPosition() {
        return this.position;
    }

    public int getType() {
        return this.type;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCreatetime(String str) {
        this.createtime = str;
    }

    public void setDefaultBg(String str) {
        this.defaultBg = str;
    }

    public void setDefaultBgBg(String str) {
        this.defaultBgBg = str;
    }

    public void setDefaultWidgetPosition(int i2) {
        this.defaultWidgetPosition = i2;
    }

    public void setDpi(String str) {
        this.dpi = str;
    }

    public void setFile(String str) {
        this.file = str;
    }

    public void setFileBg(String str) {
        this.fileBg = str;
    }

    public void setFolderDial(String str) {
        this.folderDial = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setNote(String str) {
        this.note = str;
    }

    public void setPathStatus(int i2) {
        this.pathStatus = i2;
    }

    public void setPosition(List<PositionBean> list) {
        this.position = list;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public WatchFaceCustomInfo(String str, int i2, String str2, String str3, String str4, String str5, Bitmap bitmap, String str6, int i3) {
        this.id = str;
        this.type = i2;
        this.dpi = str2;
        this.file = str3;
        this.note = str4;
        this.createtime = str5;
        this.bitmap = bitmap;
        this.folderDial = str6;
        this.pathStatus = i3;
    }

    public WatchFaceCustomInfo(String str, int i2, String str2, String str3, String str4, String str5, String str6, int i3) {
        this.id = str;
        this.type = i2;
        this.dpi = str2;
        this.file = str3;
        this.note = str4;
        this.createtime = str5;
        this.folderDial = str6;
        this.pathStatus = i3;
    }
}
