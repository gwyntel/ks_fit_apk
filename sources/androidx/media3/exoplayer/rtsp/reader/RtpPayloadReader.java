package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.ParserException;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;

@UnstableApi
/* loaded from: classes.dex */
public interface RtpPayloadReader {

    public interface Factory {
        RtpPayloadReader createPayloadReader(RtpPayloadFormat rtpPayloadFormat);
    }

    void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) throws ParserException;

    void createTracks(ExtractorOutput extractorOutput, int i2);

    void onReceivingFirstPacket(long j2, int i2);

    void seek(long j2, long j3);
}
