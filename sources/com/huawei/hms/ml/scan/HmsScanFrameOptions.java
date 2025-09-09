package com.huawei.hms.ml.scan;

/* loaded from: classes4.dex */
public class HmsScanFrameOptions {
    private boolean multiMode;
    private boolean parseResult;
    private boolean photoMode;
    private int type;

    public static class Creator {
        private int type = 0;
        private boolean multiMode = false;
        private boolean photoMode = false;
        private boolean parseResult = true;

        public HmsScanFrameOptions create() {
            return new HmsScanFrameOptions(this.type, this.photoMode, this.multiMode, this.parseResult);
        }

        public Creator setHmsScanTypes(int i2, int... iArr) {
            this.type = i2;
            if (iArr != null && iArr.length > 0) {
                for (int i3 : iArr) {
                    this.type = i3 | this.type;
                }
            }
            return this;
        }

        public Creator setMultiMode(boolean z2) {
            this.multiMode = z2;
            return this;
        }

        public Creator setParseResult(boolean z2) {
            this.parseResult = z2;
            return this;
        }

        public Creator setPhotoMode(boolean z2) {
            this.photoMode = z2;
            return this;
        }
    }

    public int getType() {
        return this.type;
    }

    public boolean isMultiMode() {
        return this.multiMode;
    }

    public boolean isParseResult() {
        return this.parseResult;
    }

    public boolean isPhotoMode() {
        return this.photoMode;
    }

    private HmsScanFrameOptions(int i2, boolean z2, boolean z3, boolean z4) {
        this.type = i2;
        this.photoMode = z2;
        this.multiMode = z3;
        this.parseResult = z4;
    }
}
