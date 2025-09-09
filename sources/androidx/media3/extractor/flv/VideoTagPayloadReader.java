package androidx.media3.extractor.flv;

import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.container.NalUnitUtil;
import androidx.media3.extractor.AvcConfig;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.flv.TagPayloadReader;

/* loaded from: classes2.dex */
final class VideoTagPayloadReader extends TagPayloadReader {
    private static final int AVC_PACKET_TYPE_AVC_NALU = 1;
    private static final int AVC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int VIDEO_CODEC_AVC = 7;
    private static final int VIDEO_FRAME_KEYFRAME = 1;
    private static final int VIDEO_FRAME_VIDEO_INFO = 5;
    private int frameType;
    private boolean hasOutputFormat;
    private boolean hasOutputKeyframe;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private int nalUnitLengthFieldLength;

    public VideoTagPayloadReader(TrackOutput trackOutput) {
        super(trackOutput);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = (unsignedByte >> 4) & 15;
        int i3 = unsignedByte & 15;
        if (i3 == 7) {
            this.frameType = i2;
            return i2 != 5;
        }
        throw new TagPayloadReader.UnsupportedFormatException("Video format not supported: " + i3);
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    protected boolean b(ParsableByteArray parsableByteArray, long j2) throws ParserException {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        long int24 = j2 + (parsableByteArray.readInt24() * 1000);
        if (unsignedByte == 0 && !this.hasOutputFormat) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray(new byte[parsableByteArray.bytesLeft()]);
            parsableByteArray.readBytes(parsableByteArray2.getData(), 0, parsableByteArray.bytesLeft());
            AvcConfig avcConfig = AvcConfig.parse(parsableByteArray2);
            this.nalUnitLengthFieldLength = avcConfig.nalUnitLengthFieldLength;
            this.f5609a.format(new Format.Builder().setSampleMimeType(MimeTypes.VIDEO_H264).setCodecs(avcConfig.codecs).setWidth(avcConfig.width).setHeight(avcConfig.height).setPixelWidthHeightRatio(avcConfig.pixelWidthHeightRatio).setInitializationData(avcConfig.initializationData).build());
            this.hasOutputFormat = true;
            return false;
        }
        if (unsignedByte != 1 || !this.hasOutputFormat) {
            return false;
        }
        int i2 = this.frameType == 1 ? 1 : 0;
        if (!this.hasOutputKeyframe && i2 == 0) {
            return false;
        }
        byte[] data = this.nalLength.getData();
        data[0] = 0;
        data[1] = 0;
        data[2] = 0;
        int i3 = 4 - this.nalUnitLengthFieldLength;
        int i4 = 0;
        while (parsableByteArray.bytesLeft() > 0) {
            parsableByteArray.readBytes(this.nalLength.getData(), i3, this.nalUnitLengthFieldLength);
            this.nalLength.setPosition(0);
            int unsignedIntToInt = this.nalLength.readUnsignedIntToInt();
            this.nalStartCode.setPosition(0);
            this.f5609a.sampleData(this.nalStartCode, 4);
            this.f5609a.sampleData(parsableByteArray, unsignedIntToInt);
            i4 = i4 + 4 + unsignedIntToInt;
        }
        this.f5609a.sampleMetadata(int24, i2, i4, 0, null);
        this.hasOutputKeyframe = true;
        return true;
    }

    @Override // androidx.media3.extractor.flv.TagPayloadReader
    public void seek() {
        this.hasOutputKeyframe = false;
    }
}
