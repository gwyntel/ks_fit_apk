package com.yc.utesdk.watchface.bean.acts;

import java.util.List;

/* loaded from: classes4.dex */
public class ImageWatchFaceConfig {
    public static final int POSITION_BOTTOM_CENTER = 2;
    public static final int POSITION_LEFT_DOWN = 4;
    public static final int POSITION_MIDDLE_CENTER = 3;
    public static final int POSITION_RIGHT_UP = 5;
    public static final int POSITION_TOP_CENTER = 1;
    private int dateColor;
    private int imageCount;
    private List<ImageInfoConfig> imageInfoConfigList;
    private int positionIndex;
    private int styleIndex;
    private int timeColor;
    private int transferNum;

    public static class ImageInfoConfig {
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

    public int getDateColor() {
        return this.dateColor;
    }

    public int getImageCount() {
        return this.imageCount;
    }

    public List<ImageInfoConfig> getImageInfoConfigList() {
        return this.imageInfoConfigList;
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

    public void setDateColor(int i2) {
        this.dateColor = i2;
    }

    public void setImageCount(int i2) {
        this.imageCount = i2;
    }

    public void setImageInfoConfigList(List<ImageInfoConfig> list) {
        this.imageInfoConfigList = list;
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
