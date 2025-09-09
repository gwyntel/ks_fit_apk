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
final class RtpH263Reader implements RtpPayloadReader {
    private static final int I_VOP = 0;
    private static final int MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int PICTURE_START_CODE = 128;
    private static final String TAG = "RtpH263Reader";
    private int fragmentedSampleSizeBytes;
    private long fragmentedSampleTimeUs;
    private boolean gotFirstPacketOfH263Frame;
    private int height;
    private boolean isKeyFrame;
    private boolean isOutputFormatSet;
    private final RtpPayloadFormat payloadFormat;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private int width;
    private long firstReceivedTimestamp = C.TIME_UNSET;
    private int previousSequenceNumber = -1;

    public RtpH263Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private void outputSampleMetadataForFragmentedPackets() {
        TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.trackOutput);
        long j2 = this.fragmentedSampleTimeUs;
        boolean z2 = this.isKeyFrame;
        trackOutput.sampleMetadata(j2, z2 ? 1 : 0, this.fragmentedSampleSizeBytes, 0, null);
        this.fragmentedSampleSizeBytes = 0;
        this.fragmentedSampleTimeUs = C.TIME_UNSET;
        this.isKeyFrame = false;
        this.gotFirstPacketOfH263Frame = false;
    }

    private void parseVopHeader(ParsableByteArray parsableByteArray, boolean z2) {
        int position = parsableByteArray.getPosition();
        if (((parsableByteArray.readUnsignedInt() >> 10) & 63) != 32) {
            parsableByteArray.setPosition(position);
            this.isKeyFrame = false;
            return;
        }
        int iPeekUnsignedByte = parsableByteArray.peekUnsignedByte();
        int i2 = (iPeekUnsignedByte >> 1) & 1;
        if (!z2 && i2 == 0) {
            int i3 = (iPeekUnsignedByte >> 2) & 7;
            if (i3 == 1) {
                this.width = 128;
                this.height = 96;
            } else {
                int i4 = i3 - 2;
                this.width = 176 << i4;
                this.height = 144 << i4;
            }
        }
        parsableByteArray.setPosition(position);
        this.isKeyFrame = i2 == 0;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        Assertions.checkStateNotNull(this.trackOutput);
        int position = parsableByteArray.getPosition();
        int unsignedShort = parsableByteArray.readUnsignedShort();
        boolean z3 = (unsignedShort & 1024) > 0;
        if ((unsignedShort & 512) != 0 || (unsignedShort & 504) != 0 || (unsignedShort & 7) != 0) {
            Log.w(TAG, "Dropping packet: video reduncancy coding is not supported, packet header VRC, or PLEN or PEBIT is non-zero");
            return;
        }
        if (z3) {
            if (this.gotFirstPacketOfH263Frame && this.fragmentedSampleSizeBytes > 0) {
                outputSampleMetadataForFragmentedPackets();
            }
            this.gotFirstPacketOfH263Frame = true;
            if ((parsableByteArray.peekUnsignedByte() & 252) < 128) {
                Log.w(TAG, "Picture start Code (PSC) missing, dropping packet.");
                return;
            } else {
                parsableByteArray.getData()[position] = 0;
                parsableByteArray.getData()[position + 1] = 0;
                parsableByteArray.setPosition(position);
            }
        } else {
            if (!this.gotFirstPacketOfH263Frame) {
                Log.w(TAG, "First payload octet of the H263 packet is not the beginning of a new H263 partition, Dropping current packet.");
                return;
            }
            int nextSequenceNumber = RtpPacket.getNextSequenceNumber(this.previousSequenceNumber);
            if (i2 < nextSequenceNumber) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(nextSequenceNumber), Integer.valueOf(i2)));
                return;
            }
        }
        if (this.fragmentedSampleSizeBytes == 0) {
            parseVopHeader(parsableByteArray, this.isOutputFormatSet);
            if (!this.isOutputFormatSet && this.isKeyFrame) {
                int i3 = this.width;
                Format format = this.payloadFormat.format;
                if (i3 != format.width || this.height != format.height) {
                    this.trackOutput.format(format.buildUpon().setWidth(this.width).setHeight(this.height).build());
                }
                this.isOutputFormatSet = true;
            }
        }
        int iBytesLeft = parsableByteArray.bytesLeft();
        this.trackOutput.sampleData(parsableByteArray, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        this.fragmentedSampleTimeUs = RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, MEDIA_CLOCK_FREQUENCY);
        if (z2) {
            outputSampleMetadataForFragmentedPackets();
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
