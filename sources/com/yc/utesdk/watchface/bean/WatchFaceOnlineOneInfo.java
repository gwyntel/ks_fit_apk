package com.yc.utesdk.watchface.bean;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class WatchFaceOnlineOneInfo implements Comparable<WatchFaceOnlineOneInfo> {
    private String author;
    private long capacity;
    private String createtime;
    private String customZip;
    private String description;
    private int download_num;
    private String dpi;
    private int id;
    private String initable;
    public transient boolean isSelect;
    private int level;
    private String material;
    private int maxCapacity;
    private String preview;
    private String resource;
    private int shape;
    private String title;
    private int type;

    public WatchFaceOnlineOneInfo(int i2) {
        this.id = i2;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull WatchFaceOnlineOneInfo watchFaceOnlineOneInfo) {
        return getId() - watchFaceOnlineOneInfo.getId();
    }

    public String getAuthor() {
        return this.author;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public String getCustomZip() {
        return this.customZip;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDownload_num() {
        return this.download_num;
    }

    public String getDpi() {
        return this.dpi;
    }

    public int getId() {
        return this.id;
    }

    public String getInitable() {
        return this.initable;
    }

    public int getLevel() {
        return this.level;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getPreview() {
        return this.preview;
    }

    public String getResouce() {
        return this.resource;
    }

    public String getResource() {
        return this.resource;
    }

    public int getShape() {
        return this.shape;
    }

    public String getTitle() {
        return this.title;
    }

    public int getType() {
        return this.type;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setAuthor(String str) {
        this.author = str;
    }

    public void setCapacity(long j2) {
        this.capacity = j2;
    }

    public void setCreatetime(String str) {
        this.createtime = str;
    }

    public void setCustomZip(String str) {
        this.customZip = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setDownload_num(int i2) {
        this.download_num = i2;
    }

    public void setDpi(String str) {
        this.dpi = str;
    }

    public void setId(int i2) {
        this.id = i2;
    }

    public void setInitable(String str) {
        this.initable = str;
    }

    public void setLevel(int i2) {
        this.level = i2;
    }

    public void setMaterial(String str) {
        this.material = str;
    }

    public void setPreview(String str) {
        this.preview = str;
    }

    public void setResouce(String str) {
        this.resource = str;
    }

    public void setResource(String str) {
        this.resource = str;
    }

    public void setSelect(boolean z2) {
        this.isSelect = z2;
    }

    public void setShape(int i2) {
        this.shape = i2;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setType(int i2) {
        this.type = i2;
    }
}
