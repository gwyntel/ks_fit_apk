package androidx.media3.extractor;

import androidx.annotation.Nullable;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.container.NalUnitUtil;
import java.util.ArrayList;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class AvcConfig {
    public final int bitdepthChroma;
    public final int bitdepthLuma;

    @Nullable
    public final String codecs;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    public final int height;
    public final List<byte[]> initializationData;
    public final int maxNumReorderFrames;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthHeightRatio;
    public final int width;

    private AvcConfig(List<byte[]> list, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, float f2, @Nullable String str) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i2;
        this.width = i3;
        this.height = i4;
        this.bitdepthLuma = i5;
        this.bitdepthChroma = i6;
        this.colorSpace = i7;
        this.colorRange = i8;
        this.colorTransfer = i9;
        this.maxNumReorderFrames = i10;
        this.pixelWidthHeightRatio = f2;
        this.codecs = str;
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray parsableByteArray) {
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(unsignedShort);
        return CodecSpecificDataUtil.buildNalUnit(parsableByteArray.getData(), position, unsignedShort);
    }

    public static AvcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        float f2;
        String strBuildAvcCodecString;
        int i9;
        try {
            parsableByteArray.skipBytes(4);
            int unsignedByte = (parsableByteArray.readUnsignedByte() & 3) + 1;
            if (unsignedByte == 3) {
                throw new IllegalStateException();
            }
            ArrayList arrayList = new ArrayList();
            int unsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
            for (int i10 = 0; i10 < unsignedByte2; i10++) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
            }
            int unsignedByte3 = parsableByteArray.readUnsignedByte();
            for (int i11 = 0; i11 < unsignedByte3; i11++) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
            }
            if (unsignedByte2 > 0) {
                NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit((byte[]) arrayList.get(0), unsignedByte, ((byte[]) arrayList.get(0)).length);
                int i12 = spsNalUnit.width;
                int i13 = spsNalUnit.height;
                int i14 = spsNalUnit.bitDepthLumaMinus8 + 8;
                int i15 = spsNalUnit.bitDepthChromaMinus8 + 8;
                int i16 = spsNalUnit.colorSpace;
                int i17 = spsNalUnit.colorRange;
                int i18 = spsNalUnit.colorTransfer;
                int i19 = spsNalUnit.maxNumReorderFrames;
                float f3 = spsNalUnit.pixelWidthHeightRatio;
                strBuildAvcCodecString = CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc);
                i8 = i18;
                i9 = i19;
                f2 = f3;
                i5 = i15;
                i6 = i16;
                i7 = i17;
                i2 = i12;
                i3 = i13;
                i4 = i14;
            } else {
                i2 = -1;
                i3 = -1;
                i4 = -1;
                i5 = -1;
                i6 = -1;
                i7 = -1;
                i8 = -1;
                f2 = 1.0f;
                strBuildAvcCodecString = null;
                i9 = 16;
            }
            return new AvcConfig(arrayList, unsignedByte, i2, i3, i4, i5, i6, i7, i8, i9, f2, strBuildAvcCodecString);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw ParserException.createForMalformedContainer("Error parsing AVC config", e2);
        }
    }
}
