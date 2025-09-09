package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPacket;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.OpusUtil;
import androidx.media3.extractor.TrackOutput;
import java.util.List;

/* loaded from: classes.dex */
final class RtpOpusReader implements RtpPayloadReader {
    private static final int MEDIA_CLOCK_FREQUENCY = 48000;
    private static final String TAG = "RtpOpusReader";
    private boolean foundOpusCommentHeader;
    private boolean foundOpusIDHeader;
    private final RtpPayloadFormat payloadFormat;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private long firstReceivedTimestamp = -1;
    private int previousSequenceNumber = -1;

    public RtpOpusReader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private static void validateOpusIdHeader(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        Assertions.checkArgument(parsableByteArray.limit() > 18, "ID Header has insufficient data");
        Assertions.checkArgument(parsableByteArray.readString(8).equals("OpusHead"), "ID Header missing");
        Assertions.checkArgument(parsableByteArray.readUnsignedByte() == 1, "version number must always be 1");
        parsableByteArray.setPosition(position);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        Assertions.checkStateNotNull(this.trackOutput);
        if (!this.foundOpusIDHeader) {
            validateOpusIdHeader(parsableByteArray);
            List<byte[]> listBuildInitializationData = OpusUtil.buildInitializationData(parsableByteArray.getData());
            Format.Builder builderBuildUpon = this.payloadFormat.format.buildUpon();
            builderBuildUpon.setInitializationData(listBuildInitializationData);
            this.trackOutput.format(builderBuildUpon.build());
            this.foundOpusIDHeader = true;
        } else if (this.foundOpusCommentHeader) {
            int nextSequenceNumber = RtpPacket.getNextSequenceNumber(this.previousSequenceNumber);
            if (i2 != nextSequenceNumber) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d.", Integer.valueOf(nextSequenceNumber), Integer.valueOf(i2)));
            }
            int iBytesLeft = parsableByteArray.bytesLeft();
            this.trackOutput.sampleData(parsableByteArray, iBytesLeft);
            this.trackOutput.sampleMetadata(RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, 48000), 1, iBytesLeft, 0, null);
        } else {
            Assertions.checkArgument(parsableByteArray.limit() >= 8, "Comment Header has insufficient data");
            Assertions.checkArgument(parsableByteArray.readString(8).equals("OpusTags"), "Comment Header should follow ID Header");
            this.foundOpusCommentHeader = true;
        }
        this.previousSequenceNumber = i2;
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
