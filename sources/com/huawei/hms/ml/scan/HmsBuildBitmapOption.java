package com.huawei.hms.ml.scan;

import android.graphics.Bitmap;
import com.alipay.sdk.m.u.i;

/* loaded from: classes4.dex */
public class HmsBuildBitmapOption {
    public static final String TYPE_BUILD_BITMAP_BACKCOLOR = "BitmapBackColor";
    public static final String TYPE_BUILD_BITMAP_COLOR = "BitmapColor";
    public static final String TYPE_BUILD_BITMAP_CONTENT = "BitmapContent";
    public static final String TYPE_BUILD_BITMAP_FOTMAT = "BitmapFormat";
    public static final String TYPE_BUILD_BITMAP_HEIGHT = "BitmapHeight";
    public static final String TYPE_BUILD_BITMAP_MARGIN = "BitmapMargin";
    public static final String TYPE_BUILD_BITMAP_WIDTH = "BitmapWidth";
    public final ErrorCorrectionLevel QRErrorCorrection;
    public final int bimapColor;
    public final int bitmapBackgroundColor;
    public final Bitmap logoBitmap;
    public final int margin;

    public static class Creator {
        private Bitmap logobitmap;
        private int margin = 1;
        private int bimapBackgroundColor = -1;
        private int bimapColor = -1;
        private ErrorCorrectionLevel QRErrorCorrection = ErrorCorrectionLevel.L;

        public HmsBuildBitmapOption create() {
            return new HmsBuildBitmapOption(this.margin, this.bimapColor, this.bimapBackgroundColor, this.QRErrorCorrection, this.logobitmap);
        }

        public Creator setBitmapBackgroundColor(int i2) {
            this.bimapBackgroundColor = i2;
            return this;
        }

        public Creator setBitmapColor(int i2) {
            this.bimapColor = i2;
            return this;
        }

        public Creator setBitmapMargin(int i2) {
            this.margin = i2;
            return this;
        }

        public Creator setQRErrorCorrection(ErrorCorrectionLevel errorCorrectionLevel) {
            this.QRErrorCorrection = errorCorrectionLevel;
            return this;
        }

        public Creator setQRLogoBitmap(Bitmap bitmap) {
            this.logobitmap = bitmap;
            return this;
        }
    }

    public enum ErrorCorrectionLevel {
        L("L"),
        M("M"),
        Q("Q"),
        H("H");

        public final String value;

        ErrorCorrectionLevel(String str) {
            this.value = str;
        }
    }

    public String toString() {
        return "{margin:" + this.margin + i.f9802b + "bitmapBackgroundColor:" + this.bitmapBackgroundColor + i.f9802b + "bimapColor:" + this.bimapColor + i.f9804d;
    }

    private HmsBuildBitmapOption(int i2, int i3, int i4, ErrorCorrectionLevel errorCorrectionLevel, Bitmap bitmap) {
        this.margin = i2;
        this.bitmapBackgroundColor = i4;
        this.bimapColor = i3;
        this.QRErrorCorrection = errorCorrectionLevel;
        this.logoBitmap = bitmap;
    }
}
