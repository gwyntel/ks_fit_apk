package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPacket;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;

/* loaded from: classes.dex */
final class RtpVp9Reader implements RtpPayloadReader {
    private static final int MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int SCALABILITY_STRUCTURE_SIZE = 4;
    private static final String TAG = "RtpVp9Reader";
    private boolean gotFirstPacketOfVp9Frame;
    private boolean isKeyFrame;
    private final RtpPayloadFormat payloadFormat;
    private boolean reportedOutputFormat;
    private TrackOutput trackOutput;
    private long firstReceivedTimestamp = C.TIME_UNSET;
    private int fragmentedSampleSizeBytes = -1;
    private long fragmentedSampleTimeUs = C.TIME_UNSET;
    private long startTimeOffsetUs = 0;
    private int previousSequenceNumber = -1;
    private int width = -1;
    private int height = -1;

    public RtpVp9Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private void outputSampleMetadataForFragmentedPackets() {
        TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.trackOutput);
        long j2 = this.fragmentedSampleTimeUs;
        boolean z2 = this.isKeyFrame;
        trackOutput.sampleMetadata(j2, z2 ? 1 : 0, this.fragmentedSampleSizeBytes, 0, null);
        this.fragmentedSampleSizeBytes = -1;
        this.fragmentedSampleTimeUs = C.TIME_UNSET;
        this.gotFirstPacketOfVp9Frame = false;
    }

    private boolean validateVp9Descriptor(ParsableByteArray parsableByteArray, int i2) {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        if ((unsignedByte & 8) == 8) {
            if (this.gotFirstPacketOfVp9Frame && this.fragmentedSampleSizeBytes > 0) {
                outputSampleMetadataForFragmentedPackets();
            }
            this.gotFirstPacketOfVp9Frame = true;
        } else {
            if (!this.gotFirstPacketOfVp9Frame) {
                Log.w(TAG, "First payload octet of the RTP packet is not the beginning of a new VP9 partition, Dropping current packet.");
                return false;
            }
            int nextSequenceNumber = RtpPacket.getNextSequenceNumber(this.previousSequenceNumber);
            if (i2 < nextSequenceNumber) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(nextSequenceNumber), Integer.valueOf(i2)));
                return false;
            }
        }
        if ((unsignedByte & 128) != 0 && (parsableByteArray.readUnsignedByte() & 128) != 0 && parsableByteArray.bytesLeft() < 1) {
            return false;
        }
        int i3 = unsignedByte & 16;
        Assertions.checkArgument(i3 == 0, "VP9 flexible mode is not supported.");
        if ((unsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(1);
            if (parsableByteArray.bytesLeft() < 1) {
                return false;
            }
            if (i3 == 0) {
                parsableByteArray.skipBytes(1);
            }
        }
        if ((unsignedByte & 2) != 0) {
            int unsignedByte2 = parsableByteArray.readUnsignedByte();
            int i4 = (unsignedByte2 >> 5) & 7;
            if ((unsignedByte2 & 16) != 0) {
                int i5 = i4 + 1;
                if (parsableByteArray.bytesLeft() < i5 * 4) {
                    return false;
                }
                for (int i6 = 0; i6 < i5; i6++) {
                    this.width = parsableByteArray.readUnsignedShort();
                    this.height = parsableByteArray.readUnsignedShort();
                }
            }
            if ((unsignedByte2 & 8) != 0) {
                int unsignedByte3 = parsableByteArray.readUnsignedByte();
                if (parsableByteArray.bytesLeft() < unsignedByte3) {
                    return false;
                }
                for (int i7 = 0; i7 < unsignedByte3; i7++) {
                    int unsignedShort = (parsableByteArray.readUnsignedShort() & 12) >> 2;
                    if (parsableByteArray.bytesLeft() < unsignedShort) {
                        return false;
                    }
                    parsableByteArray.skipBytes(unsignedShort);
                }
            }
        }
        return true;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        int i3;
        int i4;
        Assertions.checkStateNotNull(this.trackOutput);
        if (validateVp9Descriptor(parsableByteArray, i2)) {
            if (this.fragmentedSampleSizeBytes == -1 && this.gotFirstPacketOfVp9Frame) {
                this.isKeyFrame = (parsableByteArray.peekUnsignedByte() & 4) == 0;
            }
            if (!this.reportedOutputFormat && (i3 = this.width) != -1 && (i4 = this.height) != -1) {
                Format format = this.payloadFormat.format;
                if (i3 != format.width || i4 != format.height) {
                    this.trackOutput.format(format.buildUpon().setWidth(this.width).setHeight(this.height).build());
                }
                this.reportedOutputFormat = true;
            }
            int iBytesLeft = parsableByteArray.bytesLeft();
            this.trackOutput.sampleData(parsableByteArray, iBytesLeft);
            int i5 = this.fragmentedSampleSizeBytes;
            if (i5 == -1) {
                this.fragmentedSampleSizeBytes = iBytesLeft;
            } else {
                this.fragmentedSampleSizeBytes = i5 + iBytesLeft;
            }
            this.fragmentedSampleTimeUs = RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, MEDIA_CLOCK_FREQUENCY);
            if (z2) {
                outputSampleMetadataForFragmentedPackets();
            }
            this.previousSequenceNumber = i2;
        }
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 2);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
        Assertions.checkState(this.firstReceivedTimestamp == C.TIME_UNSET);
        this.firstReceivedTimestamp = j2;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.fragmentedSampleSizeBytes = -1;
        this.startTimeOffsetUs = j3;
    }
}
