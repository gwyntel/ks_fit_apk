package androidx.media3.extractor;

import androidx.annotation.Nullable;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.container.NalUnitUtil;
import java.util.Collections;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class HevcConfig {
    private static final int SPS_NAL_UNIT_TYPE = 33;
    public final int bitdepthChroma;
    public final int bitdepthLuma;

    @Nullable
    public final String codecs;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    public final int height;
    public final List<byte[]> initializationData;
    public final int maxNumReorderPics;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthHeightRatio;
    public final int width;

    private HevcConfig(List<byte[]> list, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f2, int i10, @Nullable String str) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i2;
        this.width = i3;
        this.height = i4;
        this.bitdepthLuma = i5;
        this.bitdepthChroma = i6;
        this.colorSpace = i7;
        this.colorRange = i8;
        this.colorTransfer = i9;
        this.pixelWidthHeightRatio = f2;
        this.maxNumReorderPics = i10;
        this.codecs = str;
    }

    public static HevcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        int i2;
        int i3;
        try {
            parsableByteArray.skipBytes(21);
            int unsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int unsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < unsignedByte2; i6++) {
                parsableByteArray.skipBytes(1);
                int unsignedShort = parsableByteArray.readUnsignedShort();
                for (int i7 = 0; i7 < unsignedShort; i7++) {
                    int unsignedShort2 = parsableByteArray.readUnsignedShort();
                    i5 += unsignedShort2 + 4;
                    parsableByteArray.skipBytes(unsignedShort2);
                }
            }
            parsableByteArray.setPosition(position);
            byte[] bArr = new byte[i5];
            int i8 = -1;
            int i9 = -1;
            int i10 = -1;
            int i11 = -1;
            int i12 = -1;
            int i13 = -1;
            int i14 = -1;
            int i15 = -1;
            float f2 = 1.0f;
            String strBuildHevcCodecString = null;
            int i16 = 0;
            int i17 = 0;
            while (i16 < unsignedByte2) {
                int unsignedByte3 = parsableByteArray.readUnsignedByte() & 63;
                int unsignedShort3 = parsableByteArray.readUnsignedShort();
                int i18 = i4;
                while (i18 < unsignedShort3) {
                    int unsignedShort4 = parsableByteArray.readUnsignedShort();
                    byte[] bArr2 = NalUnitUtil.NAL_START_CODE;
                    int i19 = unsignedByte2;
                    System.arraycopy(bArr2, i4, bArr, i17, bArr2.length);
                    int length = i17 + bArr2.length;
                    System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), bArr, length, unsignedShort4);
                    if (unsignedByte3 == 33 && i18 == 0) {
                        NalUnitUtil.H265SpsData h265SpsNalUnit = NalUnitUtil.parseH265SpsNalUnit(bArr, length, length + unsignedShort4);
                        int i20 = h265SpsNalUnit.width;
                        i9 = h265SpsNalUnit.height;
                        i10 = h265SpsNalUnit.bitDepthLumaMinus8 + 8;
                        i11 = h265SpsNalUnit.bitDepthChromaMinus8 + 8;
                        int i21 = h265SpsNalUnit.colorSpace;
                        int i22 = h265SpsNalUnit.colorRange;
                        int i23 = h265SpsNalUnit.colorTransfer;
                        float f3 = h265SpsNalUnit.pixelWidthHeightRatio;
                        int i24 = h265SpsNalUnit.maxNumReorderPics;
                        i2 = unsignedByte3;
                        i3 = unsignedShort3;
                        i8 = i20;
                        strBuildHevcCodecString = CodecSpecificDataUtil.buildHevcCodecString(h265SpsNalUnit.generalProfileSpace, h265SpsNalUnit.generalTierFlag, h265SpsNalUnit.generalProfileIdc, h265SpsNalUnit.generalProfileCompatibilityFlags, h265SpsNalUnit.constraintBytes, h265SpsNalUnit.generalLevelIdc);
                        i13 = i22;
                        i12 = i21;
                        i15 = i24;
                        f2 = f3;
                        i14 = i23;
                    } else {
                        i2 = unsignedByte3;
                        i3 = unsignedShort3;
                    }
                    i17 = length + unsignedShort4;
                    parsableByteArray.skipBytes(unsignedShort4);
                    i18++;
                    unsignedByte2 = i19;
                    unsignedByte3 = i2;
                    unsignedShort3 = i3;
                    i4 = 0;
                }
                i16++;
                i4 = 0;
            }
            return new HevcConfig(i5 == 0 ? Collections.emptyList() : Collections.singletonList(bArr), unsignedByte + 1, i8, i9, i10, i11, i12, i13, i14, f2, i15, strBuildHevcCodecString);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw ParserException.createForMalformedContainer("Error parsing HEVC config", e2);
        }
    }
}
