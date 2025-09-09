package com.aliyun.player.source;

/* loaded from: classes3.dex */
public class LiveShift {
    private String coverPath;
    private String format = "m3u8";
    private String timeLineUrl;
    private String title;
    private String url;

    public String getCoverPath() {
        return this.coverPath;
    }

    public String getFormat() {
        return this.format;
    }

    public String getTimeLineUrl() {
        return this.timeLineUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setCoverPath(String str) {
        this.coverPath = str;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public void setTimeLineUrl(String str) {
        this.timeLineUrl = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
