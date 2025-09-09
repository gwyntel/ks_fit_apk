package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.C;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.container.NalUnitUtil;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
final class RtpH265Reader implements RtpPayloadReader {
    private static final int FU_PAYLOAD_OFFSET = 3;
    private static final int MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int NAL_IDR_N_LP = 20;
    private static final int NAL_IDR_W_RADL = 19;
    private static final int RTP_PACKET_TYPE_AP = 48;
    private static final int RTP_PACKET_TYPE_FU = 49;
    private static final String TAG = "RtpH265Reader";
    private int bufferFlags;
    private int fragmentedSampleSizeBytes;
    private final RtpPayloadFormat payloadFormat;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private final ParsableByteArray fuScratchBuffer = new ParsableByteArray();
    private final ParsableByteArray nalStartCodeArray = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private long firstReceivedTimestamp = C.TIME_UNSET;
    private int previousSequenceNumber = -1;

    public RtpH265Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private static int getBufferFlagsFromNalType(int i2) {
        return (i2 == 19 || i2 == 20) ? 1 : 0;
    }

    @RequiresNonNull({"trackOutput"})
    private void processFragmentationUnitPacket(ParsableByteArray parsableByteArray, int i2) throws ParserException {
        if (parsableByteArray.getData().length < 3) {
            throw ParserException.createForMalformedManifest("Malformed FU header.", null);
        }
        int i3 = parsableByteArray.getData()[1] & 7;
        byte b2 = parsableByteArray.getData()[2];
        int i4 = b2 & 63;
        boolean z2 = (b2 & 128) > 0;
        boolean z3 = (b2 & 64) > 0;
        if (z2) {
            this.fragmentedSampleSizeBytes += writeStartCode();
            parsableByteArray.getData()[1] = (byte) ((i4 << 1) & 127);
            parsableByteArray.getData()[2] = (byte) i3;
            this.fuScratchBuffer.reset(parsableByteArray.getData());
            this.fuScratchBuffer.setPosition(1);
        } else {
            int i5 = (this.previousSequenceNumber + 1) % 65535;
            if (i2 != i5) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(i5), Integer.valueOf(i2)));
                return;
            } else {
                this.fuScratchBuffer.reset(parsableByteArray.getData());
                this.fuScratchBuffer.setPosition(3);
            }
        }
        int iBytesLeft = this.fuScratchBuffer.bytesLeft();
        this.trackOutput.sampleData(this.fuScratchBuffer, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        if (z3) {
            this.bufferFlags = getBufferFlagsFromNalType(i4);
        }
    }

    @RequiresNonNull({"trackOutput"})
    private void processSingleNalUnitPacket(ParsableByteArray parsableByteArray) {
        int iBytesLeft = parsableByteArray.bytesLeft();
        this.fragmentedSampleSizeBytes += writeStartCode();
        this.trackOutput.sampleData(parsableByteArray, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        this.bufferFlags = getBufferFlagsFromNalType((parsableByteArray.getData()[0] >> 1) & 63);
    }

    private int writeStartCode() {
        this.nalStartCodeArray.setPosition(0);
        int iBytesLeft = this.nalStartCodeArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(this.nalStartCodeArray, iBytesLeft);
        return iBytesLeft;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) throws ParserException {
        if (parsableByteArray.getData().length == 0) {
            throw ParserException.createForMalformedManifest("Empty RTP data packet.", null);
        }
        int i3 = (parsableByteArray.getData()[0] >> 1) & 63;
        Assertions.checkStateNotNull(this.trackOutput);
        if (i3 >= 0 && i3 < 48) {
            processSingleNalUnitPacket(parsableByteArray);
        } else {
            if (i3 == 48) {
                throw new UnsupportedOperationException("need to implement processAggregationPacket");
            }
            if (i3 != 49) {
                throw ParserException.createForMalformedManifest(String.format("RTP H265 payload type [%d] not supported.", Integer.valueOf(i3)), null);
            }
            processFragmentationUnitPacket(parsableByteArray, i2);
        }
        if (z2) {
            if (this.firstReceivedTimestamp == C.TIME_UNSET) {
                this.firstReceivedTimestamp = j2;
            }
            this.trackOutput.sampleMetadata(RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, MEDIA_CLOCK_FREQUENCY), this.bufferFlags, this.fragmentedSampleSizeBytes, 0, null);
            this.fragmentedSampleSizeBytes = 0;
        }
        this.previousSequenceNumber = i2;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 2);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.fragmentedSampleSizeBytes = 0;
        this.startTimeOffsetUs = j3;
    }
}
