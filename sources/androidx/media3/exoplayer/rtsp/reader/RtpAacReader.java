package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
import com.google.common.base.Ascii;

/* loaded from: classes.dex */
final class RtpAacReader implements RtpPayloadReader {
    private static final String AAC_HIGH_BITRATE_MODE = "AAC-hbr";
    private static final String AAC_LOW_BITRATE_MODE = "AAC-lbr";
    private static final String TAG = "RtpAacReader";
    private final ParsableBitArray auHeaderScratchBit = new ParsableBitArray();
    private final int auIndexFieldBitSize;
    private final int auSizeFieldBitSize;
    private long firstReceivedTimestamp;
    private final int numBitsInAuHeader;
    private final RtpPayloadFormat payloadFormat;
    private final int sampleRate;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;

    public RtpAacReader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
        this.sampleRate = rtpPayloadFormat.clockRate;
        String str = (String) Assertions.checkNotNull(rtpPayloadFormat.fmtpParameters.get("mode"));
        if (Ascii.equalsIgnoreCase(str, AAC_HIGH_BITRATE_MODE)) {
            this.auSizeFieldBitSize = 13;
            this.auIndexFieldBitSize = 3;
        } else {
            if (!Ascii.equalsIgnoreCase(str, AAC_LOW_BITRATE_MODE)) {
                throw new UnsupportedOperationException("AAC mode not supported");
            }
            this.auSizeFieldBitSize = 6;
            this.auIndexFieldBitSize = 2;
        }
        this.numBitsInAuHeader = this.auIndexFieldBitSize + this.auSizeFieldBitSize;
    }

    private static void outputSampleMetadata(TrackOutput trackOutput, long j2, int i2) {
        trackOutput.sampleMetadata(j2, 1, i2, 0, null);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        Assertions.checkNotNull(this.trackOutput);
        short s2 = parsableByteArray.readShort();
        int i3 = s2 / this.numBitsInAuHeader;
        long sampleTimeUs = RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, this.sampleRate);
        this.auHeaderScratchBit.reset(parsableByteArray);
        if (i3 == 1) {
            int bits = this.auHeaderScratchBit.readBits(this.auSizeFieldBitSize);
            this.auHeaderScratchBit.skipBits(this.auIndexFieldBitSize);
            this.trackOutput.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
            if (z2) {
                outputSampleMetadata(this.trackOutput, sampleTimeUs, bits);
                return;
            }
            return;
        }
        parsableByteArray.skipBytes((s2 + 7) / 8);
        for (int i4 = 0; i4 < i3; i4++) {
            int bits2 = this.auHeaderScratchBit.readBits(this.auSizeFieldBitSize);
            this.auHeaderScratchBit.skipBits(this.auIndexFieldBitSize);
            this.trackOutput.sampleData(parsableByteArray, bits2);
            outputSampleMetadata(this.trackOutput, sampleTimeUs, bits2);
            sampleTimeUs += Util.scaleLargeTimestamp(i3, 1000000L, this.sampleRate);
        }
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 1);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
        this.firstReceivedTimestamp = j2;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.startTimeOffsetUs = j3;
    }
}
