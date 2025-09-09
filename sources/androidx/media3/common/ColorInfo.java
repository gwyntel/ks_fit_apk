package androidx.media3.common;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.dataflow.qual.Pure;

@UnstableApi
/* loaded from: classes.dex */
public final class ColorInfo {
    public final int chromaBitdepth;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    private int hashCode;

    @Nullable
    public final byte[] hdrStaticInfo;
    public final int lumaBitdepth;
    public static final ColorInfo SDR_BT709_LIMITED = new Builder().setColorSpace(1).setColorRange(2).setColorTransfer(3).build();
    public static final ColorInfo SRGB_BT709_FULL = new Builder().setColorSpace(1).setColorRange(1).setColorTransfer(2).build();
    private static final String FIELD_COLOR_SPACE = Util.intToStringMaxRadix(0);
    private static final String FIELD_COLOR_RANGE = Util.intToStringMaxRadix(1);
    private static final String FIELD_COLOR_TRANSFER = Util.intToStringMaxRadix(2);
    private static final String FIELD_HDR_STATIC_INFO = Util.intToStringMaxRadix(3);
    private static final String FIELD_LUMA_BITDEPTH = Util.intToStringMaxRadix(4);
    private static final String FIELD_CHROMA_BITDEPTH = Util.intToStringMaxRadix(5);

    public static final class Builder {
        private int chromaBitdepth;
        private int colorRange;
        private int colorSpace;
        private int colorTransfer;

        @Nullable
        private byte[] hdrStaticInfo;
        private int lumaBitdepth;

        public ColorInfo build() {
            return new ColorInfo(this.colorSpace, this.colorRange, this.colorTransfer, this.hdrStaticInfo, this.lumaBitdepth, this.chromaBitdepth);
        }

        @CanIgnoreReturnValue
        public Builder setChromaBitdepth(int i2) {
            this.chromaBitdepth = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setColorRange(int i2) {
            this.colorRange = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setColorSpace(int i2) {
            this.colorSpace = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setColorTransfer(int i2) {
            this.colorTransfer = i2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setHdrStaticInfo(@Nullable byte[] bArr) {
            this.hdrStaticInfo = bArr;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setLumaBitdepth(int i2) {
            this.lumaBitdepth = i2;
            return this;
        }

        public Builder() {
            this.colorSpace = -1;
            this.colorRange = -1;
            this.colorTransfer = -1;
            this.lumaBitdepth = -1;
            this.chromaBitdepth = -1;
        }

        private Builder(ColorInfo colorInfo) {
            this.colorSpace = colorInfo.colorSpace;
            this.colorRange = colorInfo.colorRange;
            this.colorTransfer = colorInfo.colorTransfer;
            this.hdrStaticInfo = colorInfo.hdrStaticInfo;
            this.lumaBitdepth = colorInfo.lumaBitdepth;
            this.chromaBitdepth = colorInfo.chromaBitdepth;
        }
    }

    private static String chromaBitdepthToString(int i2) {
        if (i2 == -1) {
            return "NA";
        }
        return i2 + "bit Chroma";
    }

    private static String colorRangeToString(int i2) {
        if (i2 == -1) {
            return "Unset color range";
        }
        if (i2 == 1) {
            return "Full range";
        }
        if (i2 == 2) {
            return "Limited range";
        }
        return "Undefined color range " + i2;
    }

    private static String colorSpaceToString(int i2) {
        if (i2 == -1) {
            return "Unset color space";
        }
        if (i2 == 6) {
            return "BT2020";
        }
        if (i2 == 1) {
            return "BT709";
        }
        if (i2 == 2) {
            return "BT601";
        }
        return "Undefined color space " + i2;
    }

    private static String colorTransferToString(int i2) {
        if (i2 == -1) {
            return "Unset color transfer";
        }
        if (i2 == 10) {
            return "Gamma 2.2";
        }
        if (i2 == 1) {
            return "Linear";
        }
        if (i2 == 2) {
            return "sRGB";
        }
        if (i2 == 3) {
            return "SDR SMPTE 170M";
        }
        if (i2 == 6) {
            return "ST2084 PQ";
        }
        if (i2 == 7) {
            return "HLG";
        }
        return "Undefined color transfer " + i2;
    }

    public static ColorInfo fromBundle(Bundle bundle) {
        return new ColorInfo(bundle.getInt(FIELD_COLOR_SPACE, -1), bundle.getInt(FIELD_COLOR_RANGE, -1), bundle.getInt(FIELD_COLOR_TRANSFER, -1), bundle.getByteArray(FIELD_HDR_STATIC_INFO), bundle.getInt(FIELD_LUMA_BITDEPTH, -1), bundle.getInt(FIELD_CHROMA_BITDEPTH, -1));
    }

    @EnsuresNonNullIf(expression = {"#1"}, result = false)
    public static boolean isEquivalentToAssumedSdrDefault(@Nullable ColorInfo colorInfo) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (colorInfo == null) {
            return true;
        }
        int i6 = colorInfo.colorSpace;
        return (i6 == -1 || i6 == 1 || i6 == 2) && ((i2 = colorInfo.colorRange) == -1 || i2 == 2) && (((i3 = colorInfo.colorTransfer) == -1 || i3 == 3) && colorInfo.hdrStaticInfo == null && (((i4 = colorInfo.chromaBitdepth) == -1 || i4 == 8) && ((i5 = colorInfo.lumaBitdepth) == -1 || i5 == 8)));
    }

    public static boolean isTransferHdr(@Nullable ColorInfo colorInfo) {
        int i2;
        return colorInfo != null && ((i2 = colorInfo.colorTransfer) == 7 || i2 == 6);
    }

    @Pure
    public static int isoColorPrimariesToColorSpace(int i2) {
        if (i2 == 1) {
            return 1;
        }
        if (i2 != 9) {
            return (i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7) ? 2 : -1;
        }
        return 6;
    }

    @Pure
    public static int isoTransferCharacteristicsToColorTransfer(int i2) {
        if (i2 == 1) {
            return 3;
        }
        if (i2 == 4) {
            return 10;
        }
        if (i2 == 13) {
            return 2;
        }
        if (i2 == 16) {
            return 6;
        }
        if (i2 != 18) {
            return (i2 == 6 || i2 == 7) ? 3 : -1;
        }
        return 7;
    }

    private static String lumaBitdepthToString(int i2) {
        if (i2 == -1) {
            return "NA";
        }
        return i2 + "bit Luma";
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ColorInfo.class != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        return this.colorSpace == colorInfo.colorSpace && this.colorRange == colorInfo.colorRange && this.colorTransfer == colorInfo.colorTransfer && Arrays.equals(this.hdrStaticInfo, colorInfo.hdrStaticInfo) && this.lumaBitdepth == colorInfo.lumaBitdepth && this.chromaBitdepth == colorInfo.chromaBitdepth;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((((((527 + this.colorSpace) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo)) * 31) + this.lumaBitdepth) * 31) + this.chromaBitdepth;
        }
        return this.hashCode;
    }

    public boolean isBitdepthValid() {
        return (this.lumaBitdepth == -1 || this.chromaBitdepth == -1) ? false : true;
    }

    public boolean isDataSpaceValid() {
        return (this.colorSpace == -1 || this.colorRange == -1 || this.colorTransfer == -1) ? false : true;
    }

    public boolean isValid() {
        return isBitdepthValid() || isDataSpaceValid();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(FIELD_COLOR_SPACE, this.colorSpace);
        bundle.putInt(FIELD_COLOR_RANGE, this.colorRange);
        bundle.putInt(FIELD_COLOR_TRANSFER, this.colorTransfer);
        bundle.putByteArray(FIELD_HDR_STATIC_INFO, this.hdrStaticInfo);
        bundle.putInt(FIELD_LUMA_BITDEPTH, this.lumaBitdepth);
        bundle.putInt(FIELD_CHROMA_BITDEPTH, this.chromaBitdepth);
        return bundle;
    }

    public String toLogString() {
        String str;
        String invariant = isDataSpaceValid() ? Util.formatInvariant("%s/%s/%s", colorSpaceToString(this.colorSpace), colorRangeToString(this.colorRange), colorTransferToString(this.colorTransfer)) : "NA/NA/NA";
        if (isBitdepthValid()) {
            str = this.lumaBitdepth + "/" + this.chromaBitdepth;
        } else {
            str = "NA/NA";
        }
        return invariant + "/" + str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ColorInfo(");
        sb.append(colorSpaceToString(this.colorSpace));
        sb.append(", ");
        sb.append(colorRangeToString(this.colorRange));
        sb.append(", ");
        sb.append(colorTransferToString(this.colorTransfer));
        sb.append(", ");
        sb.append(this.hdrStaticInfo != null);
        sb.append(", ");
        sb.append(lumaBitdepthToString(this.lumaBitdepth));
        sb.append(", ");
        sb.append(chromaBitdepthToString(this.chromaBitdepth));
        sb.append(")");
        return sb.toString();
    }

    private ColorInfo(int i2, int i3, int i4, @Nullable byte[] bArr, int i5, int i6) {
        this.colorSpace = i2;
        this.colorRange = i3;
        this.colorTransfer = i4;
        this.hdrStaticInfo = bArr;
        this.lumaBitdepth = i5;
        this.chromaBitdepth = i6;
    }
}
