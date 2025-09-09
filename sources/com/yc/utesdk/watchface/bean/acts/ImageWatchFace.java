package com.yc.utesdk.watchface.bean.acts;

import java.util.List;

/* loaded from: classes4.dex */
public class ImageWatchFace {
    public static final int IMAGE_TYPE_BITMAP = 2;
    public static final int IMAGE_TYPE_PNG = 1;
    private boolean canIntellectColor;
    private int dateColor;
    private List<ImageInfo> imageInfoList;
    private int imageType;
    private int maxCount;
    private int positionIndex;
    private int styleIndex;
    private int timeColor;
    private int transferNum;

    public static class ImageInfo {
        private int index;
        private String name;

        public int getIndex() {
            return this.index;
        }

        public String getName() {
            return this.name;
        }

        public void setIndex(int i2) {
            this.index = i2;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    public boolean getCanIntellectColor() {
        return this.canIntellectColor;
    }

    public int getDateColor() {
        return this.dateColor;
    }

    public List<ImageInfo> getImageInfoList() {
        return this.imageInfoList;
    }

    public int getImageType() {
        return this.imageType;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public int getPositionIndex() {
        return this.positionIndex;
    }

    public int getStyleIndex() {
        return this.styleIndex;
    }

    public int getTimeColor() {
        return this.timeColor;
    }

    public int getTransferNum() {
        return this.transferNum;
    }

    public boolean isCanIntellectColor() {
        return this.canIntellectColor;
    }

    public void setCanIntellectColor(boolean z2) {
        this.canIntellectColor = z2;
    }

    public void setDateColor(int i2) {
        this.dateColor = i2;
    }

    public void setImageInfoList(List<ImageInfo> list) {
        this.imageInfoList = list;
    }

    public void setImageType(int i2) {
        this.imageType = i2;
    }

    public void setMaxCount(int i2) {
        this.maxCount = i2;
    }

    public void setPositionIndex(int i2) {
        this.positionIndex = i2;
    }

    public void setStyleIndex(int i2) {
        this.styleIndex = i2;
    }

    public void setTimeColor(int i2) {
        this.timeColor = i2;
    }

    public void setTransferNum(int i2) {
        this.transferNum = i2;
    }
}
