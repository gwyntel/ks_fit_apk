package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.C;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPacket;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
import com.google.common.collect.ImmutableMap;

/* loaded from: classes.dex */
final class RtpMp4aReader implements RtpPayloadReader {
    private static final String PARAMETER_MP4A_CONFIG = "config";
    private static final String TAG = "RtpMp4aReader";
    private long firstReceivedTimestamp;
    private int fragmentedSampleSizeBytes;
    private long fragmentedSampleTimeUs;
    private final int numberOfSubframes;
    private final RtpPayloadFormat payloadFormat;
    private int previousSequenceNumber;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;

    public RtpMp4aReader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
        try {
            this.numberOfSubframes = getNumOfSubframesFromMpeg4AudioConfig(rtpPayloadFormat.fmtpParameters);
            this.firstReceivedTimestamp = C.TIME_UNSET;
            this.previousSequenceNumber = -1;
            this.fragmentedSampleSizeBytes = 0;
            this.startTimeOffsetUs = 0L;
            this.fragmentedSampleTimeUs = C.TIME_UNSET;
        } catch (ParserException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    private static int getNumOfSubframesFromMpeg4AudioConfig(ImmutableMap<String, String> immutableMap) throws ParserException {
        String str = immutableMap.get("config");
        int i2 = 0;
        i2 = 0;
        if (str != null && str.length() % 2 == 0) {
            ParsableBitArray parsableBitArray = new ParsableBitArray(Util.getBytesFromHexString(str));
            int bits = parsableBitArray.readBits(1);
            if (bits != 0) {
                throw ParserException.createForMalformedDataOfUnknownType("unsupported audio mux version: " + bits, null);
            }
            Assertions.checkArgument(parsableBitArray.readBits(1) == 1, "Only supports allStreamsSameTimeFraming.");
            int bits2 = parsableBitArray.readBits(6);
            Assertions.checkArgument(parsableBitArray.readBits(4) == 0, "Only suppors one program.");
            Assertions.checkArgument(parsableBitArray.readBits(3) == 0, "Only suppors one layer.");
            i2 = bits2;
        }
        return i2 + 1;
    }

    private void outputSampleMetadataForFragmentedPackets() {
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleMetadata(this.fragmentedSampleTimeUs, 1, this.fragmentedSampleSizeBytes, 0, null);
        this.fragmentedSampleSizeBytes = 0;
        this.fragmentedSampleTimeUs = C.TIME_UNSET;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        Assertions.checkStateNotNull(this.trackOutput);
        int nextSequenceNumber = RtpPacket.getNextSequenceNumber(this.previousSequenceNumber);
        if (this.fragmentedSampleSizeBytes > 0 && nextSequenceNumber < i2) {
            outputSampleMetadataForFragmentedPackets();
        }
        for (int i3 = 0; i3 < this.numberOfSubframes; i3++) {
            int i4 = 0;
            while (parsableByteArray.getPosition() < parsableByteArray.limit()) {
                int unsignedByte = parsableByteArray.readUnsignedByte();
                i4 += unsignedByte;
                if (unsignedByte != 255) {
                    break;
                }
            }
            this.trackOutput.sampleData(parsableByteArray, i4);
            this.fragmentedSampleSizeBytes += i4;
        }
        this.fragmentedSampleTimeUs = RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, this.payloadFormat.clockRate);
        if (z2) {
            outputSampleMetadataForFragmentedPackets();
        }
        this.previousSequenceNumber = i2;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 2);
        this.trackOutput = trackOutputTrack;
        ((TrackOutput) Util.castNonNull(trackOutputTrack)).format(this.payloadFormat.format);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
        Assertions.checkState(this.firstReceivedTimestamp == C.TIME_UNSET);
        this.firstReceivedTimestamp = j2;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.fragmentedSampleSizeBytes = 0;
        this.startTimeOffsetUs = j3;
    }
}
