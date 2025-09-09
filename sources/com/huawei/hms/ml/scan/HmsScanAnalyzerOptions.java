package com.huawei.hms.ml.scan;

import com.huawei.hms.scankit.p.w7;

/* loaded from: classes4.dex */
public class HmsScanAnalyzerOptions {
    public final boolean errorCheck;
    public final int mode;
    public final boolean parseResult;
    public final boolean photoMode;
    public final boolean showGuide;
    public final int viewType;

    public static class Creator {
        private int type = 0;
        private boolean photoMode = false;
        private int viewType = 0;
        private boolean errorCheck = false;
        private boolean parseResult = true;
        private boolean showGuide = false;

        public HmsScanAnalyzerOptions create() {
            return new HmsScanAnalyzerOptions(this.type, this.photoMode, this.viewType, this.errorCheck, this.parseResult, this.showGuide);
        }

        public Creator setErrorCheck(boolean z2) {
            this.errorCheck = z2;
            return this;
        }

        public Creator setHmsScanTypes(int i2, int... iArr) {
            int iA = w7.a(i2);
            this.type = iA;
            if (iArr != null && iArr.length > 0) {
                this.type = w7.b(iA);
                for (int i3 : iArr) {
                    this.type = w7.b(i3) | this.type;
                }
            }
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

        public Creator setShowGuide(boolean z2) {
            this.showGuide = z2;
            return this;
        }

        public Creator setViewType(int i2) {
            this.viewType = i2;
            return this;
        }
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int hashCode() {
        return super.hashCode();
    }

    private HmsScanAnalyzerOptions(int i2, boolean z2, int i3, boolean z3, boolean z4, boolean z5) {
        this.mode = i2;
        this.photoMode = z2;
        this.viewType = i3;
        this.errorCheck = z3;
        this.parseResult = z4;
        this.showGuide = z5;
    }
}
