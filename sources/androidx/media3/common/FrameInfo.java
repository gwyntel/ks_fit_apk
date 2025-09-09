package androidx.media3.common;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@UnstableApi
/* loaded from: classes.dex */
public class FrameInfo {
    public final ColorInfo colorInfo;
    public final int height;
    public final long offsetToAddUs;
    public final float pixelWidthHeightRatio;
    public final int width;

    private FrameInfo(ColorInfo colorInfo, int i2, int i3, float f2, long j2) {
        Assertions.checkArgument(i2 > 0, "width must be positive, but is: " + i2);
        Assertions.checkArgument(i3 > 0, "height must be positive, but is: " + i3);
        this.colorInfo = colorInfo;
        this.width = i2;
        this.height = i3;
        this.pixelWidthHeightRatio = f2;
        this.offsetToAddUs = j2;
    }

    public static final class Builder {
        private ColorInfo colorInfo;
        private int height;
        private long offsetToAddUs;
        private float pixelWidthHeightRatio;
        private int width;

        public Builder(ColorInfo colorInfo, int i2, int i3) {
            this.colorInfo = colorInfo;
            this.width = i2;
            this.height = i3;
            this.pixelWidthHeightRatio = 1.0f;
        }

        public FrameInfo build() {
            return new FrameInfo(this.colorInfo, this.width, this.height, this.pixelWidthHeightRatio, this.offsetToAddUs);
        }

        @CanIgnoreReturnValue
        public Builder setColorInfo(ColorInfo colorInfo) {
            this.colorInfo = colorInfo;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHeight(int i2) {
            this.height = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setOffsetToAddUs(long j2) {
            this.offsetToAddUs = j2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPixelWidthHeightRatio(float f2) {
            this.pixelWidthHeightRatio = f2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setWidth(int i2) {
            this.width = i2;
            return this;
        }

        public Builder(FrameInfo frameInfo) {
            this.colorInfo = frameInfo.colorInfo;
            this.width = frameInfo.width;
            this.height = frameInfo.height;
            this.pixelWidthHeightRatio = frameInfo.pixelWidthHeightRatio;
            this.offsetToAddUs = frameInfo.offsetToAddUs;
        }
    }
}
